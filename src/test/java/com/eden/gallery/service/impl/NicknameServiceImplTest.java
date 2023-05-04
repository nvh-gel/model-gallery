package com.eden.gallery.service.impl;

import com.eden.gallery.model.Nickname;
import com.eden.gallery.repository.sql.NicknameRepository;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.NicknameVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class NicknameServiceImplTest {

    @Autowired
    private NicknameServiceImpl nicknameService;
    @MockBean
    private NicknameRepository nicknameRepository;

    @BeforeEach
    void setUp() {
        nicknameService.setNicknameRepository(nicknameRepository);

        Mockito.when(nicknameRepository.existsByNickAndUrlAndModelId("nick", "url", 1L)).thenReturn(true);
        Mockito.when(nicknameRepository.existsByNickAndUrlAndModelId("nick2", "url", 1L)).thenReturn(false);
        Mockito.when(nicknameRepository.save(any())).thenReturn(new Nickname());
    }

    @Test
    void testCreateNickExisting() {
        NicknameVM toCreate = new NicknameVM();
        toCreate.setNick("nick");
        toCreate.setUrl("url");
        toCreate.setModelId(1L);
        NicknameVM result = nicknameService.create(toCreate);
        assertNull(result);
    }

    @Test
    void testCreateNickSuccess() {
        NicknameVM toCreate = new NicknameVM();
        toCreate.setNick("nick2");
        toCreate.setUrl("url");
        toCreate.setModelId(1L);
        NicknameVM result = nicknameService.create(toCreate);
        assertNotNull(result);
    }

    @TestConfiguration
    static class NicknameServiceTestContext {
        @Bean
        public NicknameService nicknameService() {
            return new NicknameServiceImpl();
        }
    }
}
