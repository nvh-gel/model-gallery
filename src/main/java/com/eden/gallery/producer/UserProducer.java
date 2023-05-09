package com.eden.gallery.producer;

import com.eden.gallery.viewmodel.UserVM;
import com.eden.queue.producer.BaseProducer;
import com.eden.queue.util.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.eden.queue.util.Strings.SEND_MESSAGE;

/**
 * Kafka producer for user.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class UserProducer implements BaseProducer<UserVM> {

    private final KafkaTemplate<String, QueueMessage<UserVM>> kafkaTemplate;

    @Value("${spring.kafka.properties.topic.user}")
    private String topic;

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(QueueMessage<UserVM> queueMessage) {

        log.info(SEND_MESSAGE, queueMessage, topic);
        this.kafkaTemplate.send(topic, queueMessage.getId().toString(), queueMessage);
    }
}
