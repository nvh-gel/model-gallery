package com.eden.gallery.service.impl;

import com.eden.gallery.mapper.UserMapper;
import com.eden.gallery.producer.UserProducer;
import com.eden.gallery.repository.UserRepository;
import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.AuthorityVM;
import com.eden.gallery.viewmodel.UserVM;
import com.eden.queue.util.Action;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Implementation for user service.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserDetailsManager userDetailsService;
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    private UserProducer userProducer;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(Transactional.TxType.REQUIRED)
    public UserVM create(UserVM userVM) {

        userDetailsService.createUser(User
                .withUsername(userVM.getUsername())
                .password(bCryptPasswordEncoder.encode(userVM.getPassword()))
                .roles("USER")
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

    @Override
    public Iterable<UserVM> findAll(int page, int size) {
        return null;
    }

    @Override
    public UserVM findById(Long id) {
        return null;
    }

    @Override
    public UserVM update(UserVM userVM) {
        return null;
    }

    @Override
    public String updateOnQueue(UserVM userVM) {
        return null;
    }

    @Override
    public UserVM delete(Long id) {
        return null;
    }

    @Override
    public String deleteOnQueue(Long id) {
        return null;
    }

    @Override
    public UserVM create(UserVM request, String... roles) {

        userDetailsService.createUser(User
                .withUsername(request.getUsername())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build());
        return userMapper.toViewModel(userRepository.findByUsername(request.getUsername()).orElse(null));
    }

    @Override
    public String createOnQueue(UserVM request, String... roles) {

        request.setAuthorities(Arrays.stream(roles).map(r -> {
            AuthorityVM authorityVM = new AuthorityVM();
            authorityVM.setUsername(request.getUsername());
            authorityVM.setAuthorities(r);
            return authorityVM;
        }).toList());
        return userProducer.sendMessageToQueue(Action.CREATE, request);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserDetailsService(UserDetailsManager userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setUserProducer(UserProducer userProducer) {
        this.userProducer = userProducer;
    }

    /**
     * Setter.
     */
    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}
