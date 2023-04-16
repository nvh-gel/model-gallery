package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.ModelMapper;
import com.eden.gallery.model.Model;
import com.eden.gallery.repository.ModelRepository;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.viewmodel.ModelVM;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ModelServiceImpl implements ModelService {

    private ModelRepository modelRepository;
    private final ModelMapper modelMapper = Mappers.getMapper(ModelMapper.class);

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

    @Override
    public String createOnQueue(ModelVM modelVM) {
        return null;
    }

    @Override
    public List<ModelVM> findAll() {

        List<Model> result = modelRepository.findAll();
        return modelMapper.toViewModel(result);
    }

    @Override
    public ModelVM findById(Long id) {

        Model result = modelRepository.findById(id).orElse(null);
        return modelMapper.toViewModel(result);
    }

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

    @Override
    public String updateOnQueue(ModelVM modelVM) {
        return null;
    }

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

    @Override
    public String deleteOnQueue(Long aLong) {
        return null;
    }

    @Autowired
    public void setModelRepository(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }
}
