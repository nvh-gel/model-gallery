package com.eden.gallery.consumer;

import com.eden.gallery.service.NicknameService;
import com.eden.gallery.viewmodel.NicknameVM;
import com.eden.queue.consumer.BaseConsumer;
import com.eden.queue.util.Action;
import com.eden.queue.util.QueueMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.eden.queue.util.Strings.RECEIVED_MESSAGE;

/**
 * Kafka consumer for nickname handling.
 */
@Component
@Log4j2
public class NicknameConsumer extends BaseConsumer<NicknameVM> {

    @Value("${spring.kafka.properties.topic.nick}")
    private String topic;

    /**
     * Constructor.
     */
    public NicknameConsumer(NicknameService nicknameService) {

        actionMap.put(Action.CREATE, nicknameService::create);
        actionMap.put(Action.UPDATE, nicknameService::update);
        actionMap.put(Action.DELETE, nick -> nicknameService.delete(nick.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = "${spring.kafka.properties.topic.nick}")
    public void processMessage(QueueMessage<NicknameVM> queueMessage) {

        log.info(RECEIVED_MESSAGE, queueMessage, topic);
        processByActionMap(queueMessage);
    }
}
