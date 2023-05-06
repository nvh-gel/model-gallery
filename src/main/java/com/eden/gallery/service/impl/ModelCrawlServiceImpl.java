package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.ModelDataMapper;
import com.eden.gallery.model.ModelData;
import com.eden.gallery.repository.mongo.ModelDataRepository;
import com.eden.gallery.service.ModelCrawlService;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.ModelDataVM;
import com.eden.gallery.viewmodel.ModelVM;
import com.eden.gallery.viewmodel.NicknameVM;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for crawler service.
 */
@Service
@Log4j2
@AllArgsConstructor
@Setter
public class ModelCrawlServiceImpl implements ModelCrawlService {

    private final ModelDataMapper modelDataMapper = Mappers.getMapper(ModelDataMapper.class);
    private ModelDataRepository modelDataRepository;
    private ModelService modelService;
    private NicknameService nicknameService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelDataVM create(ModelDataVM modelDataVM) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(ModelDataVM modelDataVM) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ModelDataVM> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, Sort.Direction.ASC, "id");
        Page<ModelData> result = modelDataRepository
                .findModelDataBySkipIsFalseAndMovedIsFalseAndNumberOfAlbumGreaterThan(1, pageable);
        return new PageImpl<>(
                modelDataMapper.toViewModel(result.getContent()).stream().toList(),
                result.getPageable(),
                result.getTotalElements()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelDataVM findById(Long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ModelDataVM update(ModelDataVM modelDataVM) {
        if (!ObjectId.isValid(modelDataVM.getObjectId())) {
            return null;
        }
        ObjectId id = new ObjectId(modelDataVM.getObjectId());
        ModelData existing = modelDataRepository.findById(id).orElse(null);
        if (null == existing) {
            return null;
        }
        ModelData toUpdate = modelDataMapper.toDocument(modelDataVM);
        modelDataMapper.mapUpdate(existing, toUpdate);
        existing.setUpdatedAt(LocalDateTime.now());
        return modelDataMapper.toViewModel(modelDataRepository.save(existing));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String updateOnQueue(ModelDataVM modelDataVM) {
        UUID uuid = UUID.randomUUID();
        this.update(modelDataVM);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModelDataVM delete(Long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ModelDataVM> findTopModels(int top) {
        Pageable pageable = PageRequest.of(0, top, Sort.Direction.DESC, "avg");
        Page<ModelData> result = modelDataRepository.findModelDataByAvgGreaterThan(-1, pageable);
        return modelDataMapper.toViewModel(result.getContent()).stream().toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String skipModel(String id) {
        if (!ObjectId.isValid(id)) {
            return null;
        }
        ModelData existing = modelDataRepository.findById(new ObjectId(id)).orElse(null);
        if (null == existing) {
            return null;
        }
        existing.setSkip(true);
        existing.setUpdatedAt(LocalDateTime.now());
        return modelDataRepository.save(existing).getId().toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String moveModelData(ModelVM request) {
        if (!ObjectId.isValid(request.getObjectId())) {
            throw new IllegalArgumentException("Invalid model object id");
        }
        ModelData existing = modelDataRepository.findById(new ObjectId(request.getObjectId())).orElse(null);
        if (existing == null) {
            throw new EntityNotFoundException("No model data exist with id: " + request.getObjectId());
        }

        String transactionId = modelService.createOnQueue(request);

        existing.setMoved(true);
        existing.setUpdatedAt(LocalDateTime.now());
        modelDataRepository.save(existing);
        return transactionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String linkModel(Long modelId, String objectId) {
        if (!ObjectId.isValid(objectId)) {
            throw new IllegalArgumentException("Invalid object id");
        }
        ModelVM existing = modelService.findById(modelId);
        ModelData existingModelData = modelDataRepository.findById(new ObjectId(objectId)).orElse(null);
        if (null == existing || null == existingModelData) {
            throw new EntityNotFoundException("Cannot find model data with given ids");
        }

        NicknameVM toLink = new NicknameVM();
        toLink.setNick(existingModelData.getName());
        toLink.setUrl(existingModelData.getUrl());
        toLink.setModelId(modelId);
        String transactionId = nicknameService.createOnQueue(toLink);

        existingModelData.setMoved(true);
        existingModelData.setUpdatedAt(LocalDateTime.now());
        modelDataRepository.save(existingModelData);

        return transactionId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ModelDataVM> findByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, size, Sort.Direction.ASC, "id");
        String queryName = name == null ? "" : name;

        Page<ModelData> result = modelDataRepository.findModelByName(queryName, 1, pageable);
        return new PageImpl<>(
                modelDataMapper.toViewModel(result.getContent()),
                result.getPageable(),
                result.getTotalElements()
        );
    }
}
