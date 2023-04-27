package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.ModelDataMapper;
import com.eden.gallery.model.ModelData;
import com.eden.gallery.repository.mongo.ModelDataRepository;
import com.eden.gallery.service.ModelCrawlService;
import com.eden.gallery.viewmodel.ModelDataVM;
import org.bson.types.ObjectId;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ModelCrawlServiceImpl implements ModelCrawlService {

    private ModelDataRepository modelDataRepository;

    private final ModelDataMapper modelDataMapper = Mappers.getMapper(ModelDataMapper.class);

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
    public ModelDataVM findById(Long aLong) {
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
    public ModelDataVM delete(Long aLong) {
        return null;
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
    public String moveModelData(ModelDataVM request) {

        if (!ObjectId.isValid(request.getObjectId())) {
            return null;
        }
        ModelData existing = modelDataRepository.findById(new ObjectId(request.getObjectId())).orElse(null);
        if (existing == null) {
            return null;
        }
        existing.setMoved(true);
        existing.setUpdatedAt(LocalDateTime.now());
        modelDataRepository.save(existing);

        return "Need implement creating model on postgresql";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public String linkModel(Integer modelId, String objectId) {

        return "not yet implemented";
    }

    /**
     * Setter.
     */
    @Autowired
    public void setModelDataRepository(ModelDataRepository modelDataRepository) {
        this.modelDataRepository = modelDataRepository;
    }
}
