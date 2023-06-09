package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.NicknameMapper;
import com.eden.gallery.model.Nickname;
import com.eden.gallery.producer.NicknameProducer;
import com.eden.gallery.repository.sql.NicknameRepository;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.NicknameVM;
import com.eden.queue.util.Action;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for nickname service.
 */
@Service
@Log4j2
@AllArgsConstructor
@Setter
public class NicknameServiceImpl implements NicknameService {

    private final NicknameMapper nicknameMapper = Mappers.getMapper(NicknameMapper.class);
    private NicknameRepository nicknameRepository;
    private NicknameProducer nicknameProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public NicknameVM create(NicknameVM nicknameVM) {
        boolean existing = nicknameRepository.existsByNickAndUrlAndModelId(
                nicknameVM.getNick(),
                nicknameVM.getUrl(),
                nicknameVM.getModelId());
        if (existing) {
            log.info("Already exist nick name: {}", nicknameVM.getNick());
            return null;
        }
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
        return nicknameProducer.sendMessageToQueue(Action.CREATE, nicknameVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<NicknameVM> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);
        Page<Nickname> nicknames = nicknameRepository.findAll(pageable);
        return new PageImpl<>(
                nicknameMapper.toViewModel(nicknames.toList()),
                nicknames.getPageable(),
                nicknames.getTotalElements()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NicknameVM findById(Long id) {
        return nicknameMapper.toViewModel(nicknameRepository.findById(id).orElse(null));
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
        return nicknameProducer.sendMessageToQueue(Action.UPDATE, nicknameVM);
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

        NicknameVM nicknameVM = new NicknameVM();
        nicknameVM.setId(id);
        return nicknameProducer.sendMessageToQueue(Action.DELETE, nicknameVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public List<NicknameVM> create(List<NicknameVM> nicknameVMS) {
        List<Nickname> toCreate = nicknameMapper.toModel(nicknameVMS);
        toCreate.forEach(n -> {
            n.setUuid(UUID.randomUUID());
            n.setCreatedAt(LocalDateTime.now());
            n.setUpdatedAt(LocalDateTime.now());
        });
        return nicknameMapper.toViewModel(nicknameRepository.saveAll(toCreate));
    }
}
