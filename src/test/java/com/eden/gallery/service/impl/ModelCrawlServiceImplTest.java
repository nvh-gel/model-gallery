package com.eden.gallery.service.impl;

import com.eden.gallery.model.ModelData;
import com.eden.gallery.repository.mongo.ModelDataRepository;
import com.eden.gallery.service.ModelCrawlService;
import com.eden.gallery.service.ModelService;
import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.ModelDataVM;
import com.eden.gallery.viewmodel.ModelVM;
import jakarta.persistence.EntityNotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
class ModelCrawlServiceImplTest {

    @Autowired
    private ModelCrawlServiceImpl modelCrawlService;
    @MockBean
    private ModelDataRepository modelDataRepository;
    @MockBean
    private ModelService modelService;
    @MockBean
    private NicknameService nicknameService;

    @BeforeEach
    public void setUp() {
        modelCrawlService.setModelDataRepository(modelDataRepository);
        modelCrawlService.setModelService(modelService);
        modelCrawlService.setNicknameService(nicknameService);

        Mockito.when(modelDataRepository.findById(new ObjectId("6453c6c027dd41fbf9007da7")))
                .thenReturn(Optional.empty());
        Mockito.when(modelDataRepository.findById(new ObjectId("6453cf2f27dd41fbf9007da8")))
                .thenReturn(Optional.of(new ModelData()));
        Mockito.when(modelDataRepository.save(any()))
                .thenReturn(new ModelData());
        Mockito.when(modelService.findById(999L))
                .thenReturn(null);
        Mockito.when(modelService.findById(1L))
                .thenReturn(new ModelVM());
        Mockito.when(nicknameService.createOnQueue(any()))
                .thenReturn(UUID.randomUUID().toString());
        Mockito.when(modelService.createOnQueue(any()))
                .thenReturn(UUID.randomUUID().toString());
    }

    @Test
    void testCreateModelDataNotImplemented() {
        ModelDataVM vm = new ModelDataVM();
        ModelDataVM result = modelCrawlService.create(vm);
        Assertions.assertNull(result);
    }

    @Test
    void testCreateModelDataOnQueueNotImplemented() {
        ModelDataVM vm = new ModelDataVM();
        String result = modelCrawlService.createOnQueue(vm);
        Assertions.assertNull(result);
    }

    @Test
    void testModelDataUpdateInvalidObjectId() {
        ModelDataVM vm = new ModelDataVM();
        vm.setObjectId("123");
        ModelDataVM result = modelCrawlService.update(vm);
        Assertions.assertNull(result);
    }

    @Test
    void testModelDataUpdateObjectIdNotFound() {
        ModelDataVM vm = new ModelDataVM();
        vm.setObjectId("6453c6c027dd41fbf9007da7");
        ModelDataVM result = modelCrawlService.update(vm);
        Assertions.assertNull(result);
    }

    @Test
    void testModelDataUpdateSuccess() {
        ModelDataVM vm = new ModelDataVM();
        vm.setObjectId("6453cf2f27dd41fbf9007da8");
        ModelDataVM result = modelCrawlService.update(vm);
        Assertions.assertNotNull(result);
    }

    @Test
    void testLinkModelInvalidModelId() {
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> modelCrawlService.linkModel(1L, "abc")
        );
        assertEquals("Invalid object id", exception.getMessage());
    }

    @Test
    void testLinkModelNotFoundModel() {
        Exception exception = assertThrows(
                RuntimeException.class,
                () -> modelCrawlService.linkModel(999L, "6453cf2f27dd41fbf9007da8")
        );
        assertEquals("Cannot find model data with given ids", exception.getMessage());

        Exception ex = assertThrows(
                RuntimeException.class,
                () -> modelCrawlService.linkModel(1L, "6453c6c027dd41fbf9007da7")
        );
        assertEquals("Cannot find model data with given ids", ex.getMessage());
    }

    @Test
    void testLinkModelSuccess() {
        String result = modelCrawlService.linkModel(1L, "6453cf2f27dd41fbf9007da8");
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testMoveModelInvalidObjectId() {
        ModelVM modelVM = new ModelVM();
        modelVM.setObjectId("123");
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> modelCrawlService.moveModelData(modelVM)
        );
        assertEquals("Invalid model object id", exception.getMessage());
    }

    @Test
    void testMoveModelObjectIdNotFound() {
        ModelVM modelVM = new ModelVM();
        modelVM.setObjectId("6453c6c027dd41fbf9007da7");
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> modelCrawlService.moveModelData(modelVM)
        );
        assertEquals("No model data exist with id: 6453c6c027dd41fbf9007da7", exception.getMessage());
    }

    @Test
    void testMoveModelSuccess() {
        ModelVM modelVM = new ModelVM();
        modelVM.setObjectId("6453cf2f27dd41fbf9007da8");
        String result = modelCrawlService.moveModelData(modelVM);
        assertNotNull(result);
        UUID uuid = UUID.fromString(result);
        assertNotNull(uuid);
    }

    @TestConfiguration
    static class ModelCrawlServiceImplTestContextConfig {
        @Bean
        public ModelCrawlService modelCrawlService() {
            return new ModelCrawlServiceImpl(null, null, null);
        }
    }
}
