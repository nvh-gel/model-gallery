package com.eden.gallery.service.impl;

import com.eden.common.utils.Paging;
import com.eden.common.utils.SearchRequest;
import com.eden.gallery.mapper.ModelMapper;
import com.eden.gallery.model.Model;
import com.eden.gallery.producer.ModelProducer;
import com.eden.gallery.repository.sql.ModelRepository;
import com.eden.gallery.search.SpecificationBuilder;
import com.eden.gallery.search.impl.ModelSpecificationBuilder;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.utils.ModelCriteria;
import com.eden.gallery.viewmodel.ModelVM;
import com.eden.gallery.viewmodel.NicknameVM;
import com.eden.queue.util.Action;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
@AllArgsConstructor
@Setter
public class ModelServiceImpl implements ModelService {

    private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);
    private ModelRepository modelRepository;
    private ModelProducer modelProducer;
    private NicknameService nicknameService;

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
        ModelVM createdVM = modelMapper.toViewModel(created);

        modelVM.getNicknames().forEach(n -> n.setModelId(created.getId()));
        List<NicknameVM> createdNickname = nicknameService.create(modelVM.getNicknames());
        createdVM.setNicknames(createdNickname);

        return createdVM;
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
}
