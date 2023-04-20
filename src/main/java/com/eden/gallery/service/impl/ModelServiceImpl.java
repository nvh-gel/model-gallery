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
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        return modelProducer.sendMessageToQueue(Action.CREATE, modelVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ModelVM> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);
        Page<Model> result = modelRepository.findAll(pageable);

        return new PageImpl<>(
                modelMapper.toViewModel(result.toList()),
                result.getPageable(),
                result.getTotalElements()
        );
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

        return modelProducer.sendMessageToQueue(Action.UPDATE, modelVM);
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
    public String deleteOnQueue(Long id) {

        ModelVM modelVM = new ModelVM();
        modelVM.setId(id);
        return modelProducer.sendMessageToQueue(Action.DELETE, modelVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ModelVM> searchModel(SearchRequest<ModelCriteria> request) {

        Paging paging = Paging.fromPaging(request.getPaging());
        Pageable pageable = PageRequest.of(
                paging.getPage() - 1,
                paging.getPageSize(),
                Sort.Direction.valueOf(paging.getOrder()),
                paging.getSortBy());

        SpecificationBuilder<Model> specBuilder = new ModelSpecificationBuilder(request.getCriteria());
        Page<Model> result = modelRepository.findAll(specBuilder.build(), pageable);
        return new PageImpl<>(
                result.stream().map(modelMapper::toViewModel).toList(),
                result.getPageable(),
                result.getTotalElements()
        );
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
