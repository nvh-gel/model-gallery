package com.eden.gallery.consumer;

import com.eden.gallery.service.ModelService;
import com.eden.gallery.viewmodel.ModelVM;
import com.eden.queue.consumer.BaseConsumer;
import com.eden.queue.util.Action;
import com.eden.queue.util.QueueMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.eden.queue.util.Strings.RECEIVED_MESSAGE;

/**
 * Consumer for model service.
 */
@Component
@Log4j2
public class ModelConsumer extends BaseConsumer<ModelVM> {

    @Value("${spring.kafka.properties.topic.model}")
    private String topic;

    /**
     * Constructor.
     *
     * @param modelService model service to set to action map
     */
    public ModelConsumer(ModelService modelService) {
        actionMap.put(Action.CREATE, modelService::create);
        actionMap.put(Action.UPDATE, modelService::update);
        actionMap.put(Action.DELETE, model -> modelService.delete(model.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = "${spring.kafka.properties.topic.model}",
            autoStartup = "${spring.kafka.consumer.properties.auto-start:true}")
    public void processMessage(QueueMessage<ModelVM> queueMessage) {
        log.info(RECEIVED_MESSAGE, queueMessage, topic);
        processByActionMap(queueMessage);
    }
}
