package com.eden.gallery.service.data;

import com.eden.common.utils.SearchRequest;
import com.eden.gallery.repository.sql.ModelRepository;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.service.impl.ModelServiceImpl;
import com.eden.gallery.utils.ModelCriteria;
import com.eden.gallery.viewmodel.ModelVM;
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
class ModelServiceDataTest {

    @Autowired
    ModelServiceImpl modelService;

    @Test
    void testSearchModelSuccess() {
        SearchRequest<ModelCriteria> request = new SearchRequest<>();
        ModelCriteria criteria = new ModelCriteria();
        criteria.setName("model");
        request.setCriteria(criteria);

        Page<ModelVM> result = modelService.searchModel(request);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getPageable().getPageNumber());
    }

    @TestConfiguration
    static class ModelServiceDataTestContext {

        @Autowired
        ModelRepository modelRepository;

        @Bean
        public ModelService modelService() {
            return new ModelServiceImpl(modelRepository, null, null);
        }
    }
}
