package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.AccountMapper;
import com.eden.gallery.model.Account;
import com.eden.gallery.producer.AccountProducer;
import com.eden.gallery.repository.AccountRepository;
import com.eden.gallery.service.AccountService;
import com.eden.gallery.viewmodel.AccountVM;
import com.eden.queue.util.Action;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
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
    @Transactional(Transactional.TxType.REQUIRED)
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

        return accountProducer.sendMessageToQueue(Action.CREATE, accountVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<AccountVM> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);
        Page<Account> result = accountRepository.findAll(pageable);
        return new PageImpl<>(
                accountMapper.toViewModel(result.toList()),
                result.getPageable(),
                result.getTotalElements()
        );
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
    @Transactional(Transactional.TxType.REQUIRED)
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

        return accountProducer.sendMessageToQueue(Action.UPDATE, accountVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
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
        return accountProducer.sendMessageToQueue(Action.DELETE, accountVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String activateAccount(Long id) {

        AccountVM accountVM = new AccountVM();
        accountVM.setId(id);
        accountVM.setIsActive(Boolean.TRUE);
        return accountProducer.sendMessageToQueue(Action.UPDATE, accountVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String deactivateAccount(Long id) {

        AccountVM accountVM = new AccountVM();
        accountVM.setId(id);
        accountVM.setIsActive(Boolean.FALSE);
        return accountProducer.sendMessageToQueue(Action.UPDATE, accountVM);
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
        AccountVM accountVM = new AccountVM();
        accountVM.setId(id);
        accountVM.setIsVerified(true);
        return accountProducer.sendMessageToQueue(Action.UPDATE, accountVM);
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
