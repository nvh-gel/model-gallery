package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.AccountMapper;
import com.eden.gallery.model.Account;
import com.eden.gallery.producer.AccountProducer;
import com.eden.gallery.repository.AccountRepository;
import com.eden.gallery.service.AccountService;
import com.eden.gallery.viewmodel.AccountVM;
import com.eden.queue.util.Action;
import com.eden.queue.util.QueueMessage;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * Implementation for account service.
 */
@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;
    private final AccountMapper accountMapper = Mappers.getMapper(AccountMapper.class);
    private AccountProducer accountProducer;

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountVM create(AccountVM accountVM) {

        Account toCreate = accountMapper.toModel(accountVM);
        toCreate.setUuid(UUID.randomUUID());
        toCreate.setCreatedAt(LocalDateTime.now());
        toCreate.setUpdatedAt(LocalDateTime.now());
        String verifyToken = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
        toCreate.setVerifyToken(verifyToken);
        Account created = accountRepository.save(toCreate);
        return accountMapper.toViewModel(created);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(AccountVM accountVM) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<AccountVM> queueMessage = new QueueMessage<>(Action.CREATE, uuid, accountVM);
        accountProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AccountVM> findAll() {

        return accountMapper.toViewModel(accountRepository.findAll());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountVM findById(Long id) {

        return accountMapper.toViewModel(accountRepository.findById(id).orElse(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountVM update(AccountVM accountVM) {

        Account exist = accountRepository.findById(accountVM.getId()).orElse(null);
        if (null == exist) {
            return null;
        }
        Account toUpdate = accountMapper.toModel(accountVM);
        accountMapper.mapUpdate(exist, toUpdate);
        exist.setUpdatedAt(LocalDateTime.now());
        return accountMapper.toViewModel(accountRepository.save(exist));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(AccountVM accountVM) {

        UUID uuid = UUID.randomUUID();
        QueueMessage<AccountVM> queueMessage = new QueueMessage<>(Action.UPDATE, uuid, accountVM);
        accountProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountVM delete(Long id) {

        Account exist = accountRepository.findById(id).orElse(null);
        if (null == exist) {
            return null;
        }
        exist.setUpdatedAt(LocalDateTime.now());
        accountRepository.deleteById(id);
        return accountMapper.toViewModel(exist);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deleteOnQueue(Long id) {

        AccountVM accountVM = new AccountVM();
        accountVM.setId(id);
        UUID uuid = UUID.randomUUID();
        QueueMessage<AccountVM> queueMessage = new QueueMessage<>(Action.DELETE, uuid, accountVM);
        accountProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String activateAccount(Long id) {

        AccountVM accountVM = new AccountVM();
        accountVM.setId(id);
        accountVM.setIsActive(Boolean.TRUE);
        UUID uuid = UUID.randomUUID();
        QueueMessage<AccountVM> queueMessage = new QueueMessage<>(Action.UPDATE, uuid, accountVM);
        accountProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deactivateAccount(Long id) {

        AccountVM accountVM = new AccountVM();
        accountVM.setId(id);
        accountVM.setIsActive(Boolean.FALSE);
        UUID uuid = UUID.randomUUID();
        QueueMessage<AccountVM> queueMessage = new QueueMessage<>(Action.UPDATE, uuid, accountVM);
        accountProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String verifyAccount(Long id, String token) {

        Account existing = accountRepository.findAccountByIdAndVerifyTokenEquals(id, token).orElse(null);
        if (null == existing) {
            return null;
        }
        UUID uuid = UUID.randomUUID();
        AccountVM accountVM = new AccountVM();
        accountVM.setId(id);
        accountVM.setIsVerified(true);
        QueueMessage<AccountVM> queueMessage = new QueueMessage<>(Action.UPDATE, uuid, accountVM);
        accountProducer.send(queueMessage);
        return uuid.toString();
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setAccountProducer(AccountProducer accountProducer) {
        this.accountProducer = accountProducer;
    }
}
