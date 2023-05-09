package com.eden.gallery.producer;

import com.eden.gallery.viewmodel.ModelVM;
import com.eden.queue.producer.BaseProducer;
import com.eden.queue.util.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.eden.queue.util.Strings.SEND_MESSAGE;

/**
 * Kafka producer for model handling.
 */
@Component
@Log4j2
@RequiredArgsConstructor
@Setter
public class ModelProducer implements BaseProducer<ModelVM> {

    private final KafkaTemplate<String, QueueMessage<ModelVM>> kafkaTemplate;

    @Value("${spring.kafka.properties.topic.model}")
    private String topic;

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(QueueMessage<ModelVM> queueMessage) {
        log.info(SEND_MESSAGE, queueMessage, topic);
        this.kafkaTemplate.send(topic, queueMessage.getId().toString(), queueMessage);
    }
}
