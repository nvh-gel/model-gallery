package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.UserMapper;
import com.eden.gallery.producer.UserProducer;
import com.eden.gallery.repository.sql.UserRepository;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.AuthorityVM;
import com.eden.gallery.viewmodel.UserVM;
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
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;

/**
 * Implementation for user service.
 */
@Service
@Log4j2
@AllArgsConstructor
@Setter
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private UserRepository userRepository;
    private UserDetailsManager userDetailsService;
    private UserProducer userProducer;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public UserVM create(UserVM userVM) {
        String password = new String(Base64.getDecoder().decode(userVM.getPassword()));
        userDetailsService.createUser(User
                .withUsername(userVM.getUsername())
                .password(bCryptPasswordEncoder.encode(password))
                .roles(userVM.getAuthorities().stream().map(AuthorityVM::getAuthority).toArray(String[]::new))
                .build()
        );
        return userMapper.toViewModel(userRepository.findByUsername(userVM.getUsername()).orElse(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(UserVM userVM) {
        return userProducer.sendMessageToQueue(Action.CREATE, userVM);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<UserVM> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size, Sort.Direction.ASC, "id");
        Page<com.eden.gallery.model.User> users = userRepository.findAll(pageable);
        return new PageImpl<>(
                userMapper.toViewModel(users.getContent()),
                users.getPageable(),
                users.getTotalElements()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVM findById(Long id) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVM update(UserVM userVM) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String updateOnQueue(UserVM userVM) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserVM delete(Long id) {
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
    public UserVM create(UserVM request, String... roles) {
        String password = new String(Base64.getDecoder().decode(request.getPassword()));
        userDetailsService.createUser(User
                .withUsername(request.getUsername())
                .password(bCryptPasswordEncoder.encode(password))
                .roles(roles)
                .build());
        return userMapper.toViewModel(userRepository.findByUsername(request.getUsername()).orElse(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String createOnQueue(UserVM request, String... roles) {
        request.setAuthorities(Arrays.stream(roles).map(r -> {
            AuthorityVM authorityVM = new AuthorityVM();
            authorityVM.setUsername(request.getUsername());
            authorityVM.setAuthority(r);
            return authorityVM;
        }).toList());
        return userProducer.sendMessageToQueue(Action.CREATE, request);
    }
}
