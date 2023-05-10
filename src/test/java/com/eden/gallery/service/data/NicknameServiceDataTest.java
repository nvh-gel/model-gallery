package com.eden.gallery.service.data;

import com.eden.gallery.repository.sql.NicknameRepository;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.service.impl.NicknameServiceImpl;
import com.eden.gallery.viewmodel.NicknameVM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 */
@DataJpaTest
class NicknameServiceDataTest {

    @Autowired
    NicknameServiceImpl nicknameService;

    @Test
    void testFindAllNicknameSuccess() {
        Page<NicknameVM> result = nicknameService.findAll(0, 10);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getPageable().getPageNumber());

        result = nicknameService.findAll(4, 10);
        assertEquals(0, result.getTotalElements());
        assertEquals(3, result.getPageable().getPageNumber());
    }

    @TestConfiguration
    static class NicknameServiceDataTestContext {

        @Autowired
        NicknameRepository nicknameRepository;

        @Bean
        public NicknameService nicknameService() {
            return new NicknameServiceImpl(nicknameRepository, null);
        }
    }
}
