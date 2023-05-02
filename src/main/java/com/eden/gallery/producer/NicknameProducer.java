package com.eden.gallery.producer;

import com.eden.gallery.viewmodel.NicknameVM;
import com.eden.queue.producer.BaseProducer;
import com.eden.queue.util.QueueMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.eden.queue.util.Strings.SEND_MESSAGE;

/**
 * Kafka producer for Nickname handling.
 */
@Component
@Log4j2
public class NicknameProducer implements BaseProducer<NicknameVM> {

    @Value("${spring.kafka.properties.topic.nick}")
    private String topic;

    private KafkaTemplate<String, QueueMessage<NicknameVM>> kafkaTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(QueueMessage<NicknameVM> queueMessage) {

        log.info(SEND_MESSAGE, queueMessage, topic);
        this.kafkaTemplate.send(topic, queueMessage.getId().toString(), queueMessage);
    }

    /**
     * Setter.
     */
    @Autowired
    public void setKafkaTemplate(KafkaTemplate<String, QueueMessage<NicknameVM>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
}
