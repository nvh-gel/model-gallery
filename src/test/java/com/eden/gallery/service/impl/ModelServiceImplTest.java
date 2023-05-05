package com.eden.gallery.service.impl;

import com.eden.gallery.model.Model;
import com.eden.gallery.repository.sql.ModelRepository;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.viewmodel.ModelVM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class ModelServiceImplTest {

    @Autowired
    private ModelServiceImpl modelService;
    @MockBean
    private ModelRepository modelRepository;

    @BeforeEach
    void setUp() {
        modelService.setModelRepository(modelRepository);

        Mockito.when(modelRepository.findById(999L))
                .thenReturn(Optional.empty());
        Model model = new Model();
        model.setId(1L);
        Mockito.when(modelRepository.findById(1L))
                .thenReturn(Optional.of(model));
        Mockito.when(modelRepository.save(any()))
                .thenReturn(model);
    }

    @Test
    void testUpdateModelNotFound() {
        ModelVM toUpdate = new ModelVM();
        toUpdate.setId(999L);
        ModelVM result = modelService.update(toUpdate);
        assertNull(result);
    }

    @Test
    void testUpdateSuccess() {
        ModelVM toUpdate = new ModelVM();
        toUpdate.setId(1L);
        ModelVM result = modelService.update(toUpdate);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testFindAllByPage() {
        int page = 0;
        int size = 10;
        Mockito.when(modelRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 10));

        Page<ModelVM> result = modelService.findAll(page, size);
        assertEquals(10, result.getPageable().getPageSize());
        assertEquals(0, result.getPageable().getPageNumber());

        Mockito.when(modelRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(), PageRequest.of(2, 10), 10));
        page = 3;
        result = modelService.findAll(page, size);
        assertEquals(2, result.getPageable().getPageNumber());
    }

    @TestConfiguration
    public static class ModelServiceConfiguration {

        @Bean
        public ModelService modelService() {
            return new ModelServiceImpl(null, null, null);
        }
    }
}
