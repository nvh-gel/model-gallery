package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.NicknameMapper;
import com.eden.gallery.model.Nickname;
import com.eden.gallery.producer.NicknameProducer;
import com.eden.gallery.repository.NicknameRepository;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.NicknameVM;
import com.eden.queue.util.Action;
import com.eden.queue.util.QueueMessage;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for nickname service.
 */
@Service
public class NicknameServiceImpl implements NicknameService {

    private NicknameRepository nicknameRepository;
    private final NicknameMapper nicknameMapper = Mappers.getMapper(NicknameMapper.class);
    private NicknameProducer nicknameProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public NicknameVM create(NicknameVM nicknameVM) {

        Nickname toCreate = nicknameMapper.toModel(nicknameVM);
        toCreate.setUuid(UUID.randomUUID());
        toCreate.setCreatedAt(LocalDateTime.now());
        toCreate.setUpdatedAt(LocalDateTime.now());
        Nickname created = nicknameRepository.save(toCreate);
        return nicknameMapper.toViewModel(created);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(NicknameVM nicknameVM) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<NicknameVM> queueMessage = new QueueMessage<>(Action.CREATE, uuid, nicknameVM);
        nicknameProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<NicknameVM> findAll() {
        return List.of();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM findById(Long aLong) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public NicknameVM update(NicknameVM nicknameVM) {

        Nickname exist = nicknameRepository.findById(nicknameVM.getId()).orElse(null);
        if (null == exist) {
            return null;
        }
        Nickname toUpdate = nicknameMapper.toModel(nicknameVM);
        nicknameMapper.mapUpdate(exist, toUpdate);
        exist.setUpdatedAt(LocalDateTime.now());
        Nickname updated = nicknameRepository.save(exist);
        return nicknameMapper.toViewModel(updated);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(NicknameVM nicknameVM) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<NicknameVM> queueMessage = new QueueMessage<>(Action.UPDATE, uuid, nicknameVM);
        nicknameProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public NicknameVM delete(Long id) {

        Nickname exist = nicknameRepository.findById(id).orElse(null);
        if (null == exist) {
            return null;
        }
        nicknameRepository.deleteById(id);
        exist.setUpdatedAt(LocalDateTime.now());
        return nicknameMapper.toViewModel(exist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long id) {

        NicknameVM vm = new NicknameVM();
        vm.setId(id);
        UUID uuid = UUID.randomUUID();
        QueueMessage<NicknameVM> queueMessage = new QueueMessage<>(Action.DELETE, uuid, vm);
        nicknameProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * Setter.
     */
    @Autowired
    public void setNicknameRepository(NicknameRepository nicknameRepository) {
        this.nicknameRepository = nicknameRepository;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setNicknameProducer(NicknameProducer nicknameProducer) {
        this.nicknameProducer = nicknameProducer;
    }
}
