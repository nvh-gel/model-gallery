package com.eden.gallery.service.data;

import com.eden.gallery.repository.sql.UserRepository;
import com.eden.gallery.service.UserService;
import com.eden.gallery.service.impl.UserServiceImpl;
import com.eden.gallery.viewmodel.UserVM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

@DataJpaTest
class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Test
    void testFindAllUsers() {

        Page<UserVM> result = userService.findAll(0, 10);
        Assertions.assertEquals(0, result.getTotalElements());
        Assertions.assertEquals(0, result.getPageable().getPageNumber());

        result = userService.findAll(5, 10);
        Assertions.assertEquals(0, result.getTotalElements());
        Assertions.assertEquals(4, result.getPageable().getPageNumber());

    }

    @TestConfiguration
    public static class UserServiceTestContext {

        @Autowired
        private UserRepository userRepository;

        @Bean
        public UserService userService() {
            return new UserServiceImpl(userRepository,
                    null,
                    null,
                    null,
                    null);
        }
    }
}
