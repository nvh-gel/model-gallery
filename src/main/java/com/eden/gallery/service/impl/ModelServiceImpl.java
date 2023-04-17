package com.eden.gallery.service.impl;

import com.eden.common.utils.Paging;
import com.eden.common.utils.SearchRequest;
import com.eden.gallery.mapper.ModelMapper;
import com.eden.gallery.model.Model;
import com.eden.gallery.producer.ModelProducer;
import com.eden.gallery.repository.ModelRepository;
import com.eden.gallery.search.SpecificationBuilder;
import com.eden.gallery.search.impl.ModelSpecificationBuilder;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.utils.ModelCriteria;
import com.eden.gallery.viewmodel.ModelVM;
import com.eden.queue.util.Action;
import com.eden.queue.util.QueueMessage;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for model service.
 */
@Service
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;
    private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);
    private ModelProducer modelProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public ModelVM create(ModelVM modelVM) {

        Model toCreate = modelMapper.toModel(modelVM);
        toCreate.setUuid(UUID.randomUUID());
        toCreate.setCreatedAt(LocalDateTime.now());
        toCreate.setUpdatedAt(LocalDateTime.now());
        Model created = modelRepository.save(toCreate);
        return modelMapper.toViewModel(created);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(ModelVM modelVM) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<ModelVM> queueMessage = new QueueMessage<>(Action.CREATE, uuid, modelVM);
        modelProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ModelVM> findAll() {

        List<Model> result = modelRepository.findAll();
        return modelMapper.toViewModel(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelVM findById(Long id) {

        Model result = modelRepository.findById(id).orElse(null);
        return modelMapper.toViewModel(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public ModelVM update(ModelVM modelVM) {

        Model existing = modelRepository.findById(modelVM.getId()).orElse(null);
        if (null == existing) {
            return null;
        }
        Model toUpdate = modelMapper.toModel(modelVM);
        modelMapper.mapUpdate(existing, toUpdate);
        existing.setUpdatedAt(LocalDateTime.now());
        Model updated = modelRepository.save(existing);
        return modelMapper.toViewModel(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(ModelVM modelVM) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<ModelVM> queueMessage = new QueueMessage<>(Action.UPDATE, uuid, modelVM);
        modelProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public ModelVM delete(Long id) {

        Model existing = modelRepository.findById(id).orElse(null);
        if (null == existing) {
            return null;
        }
        modelRepository.deleteById(id);
        existing.setUpdatedAt(LocalDateTime.now());
        return modelMapper.toViewModel(existing);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long aLong) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Model> searchModel(SearchRequest<ModelCriteria> request) {

        Paging paging = Paging.fromPaging(request.getPaging());
        Pageable pageable = PageRequest.of(
                paging.getPage() - 1,
                paging.getPageSize(),
                Sort.Direction.valueOf(paging.getOrder()),
                paging.getSortBy());

        SpecificationBuilder<Model> specBuilder = new ModelSpecificationBuilder(request.getCriteria());
        return modelRepository.findAll(specBuilder.build(), pageable);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelProducer(ModelProducer modelProducer) {
        this.modelProducer = modelProducer;
    }
}
