package com.eden.gallery.service.data;

import com.eden.gallery.repository.mongo.ModelDataRepository;
import com.eden.gallery.service.ModelCrawlService;
import com.eden.gallery.service.impl.ModelCrawlServiceImpl;
import com.eden.gallery.viewmodel.ModelDataVM;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataMongoTest
class ModelCrawlServiceImplDataTest {

    @Autowired
    ModelCrawlServiceImpl modelCrawlService;

    @Test
    void testFindAll() {
        Page<ModelDataVM> result = modelCrawlService.findAll(0, 10);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getPageable().getPageNumber());

        result = modelCrawlService.findAll(1, 10);
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getPageable().getPageNumber());
    }

    @TestConfiguration
    public static class TestContext {

        @Autowired
        ModelDataRepository modelDataRepository;

        @Bean
        public ModelCrawlService modelCrawlService() {
            return new ModelCrawlServiceImpl(modelDataRepository, null, null);
        }
    }
}
