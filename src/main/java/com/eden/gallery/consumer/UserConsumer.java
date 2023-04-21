package com.eden.gallery.consumer;

import com.eden.gallery.service.UserService;
import com.eden.gallery.viewmodel.UserVM;
import com.eden.queue.consumer.BaseConsumer;
import com.eden.queue.util.Action;
import com.eden.queue.util.QueueMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.eden.queue.util.Strings.RECEIVED_MESSAGE;

/**
 * Kafka consumer for user.
 */
@Component
@Log4j2
public class UserConsumer extends BaseConsumer<UserVM> {

    @Value("${spring.kafka.properties.topic.user}")
    private String topic;

    public UserConsumer(UserService userService) {
        actionMap.put(Action.CREATE, userService::create);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = "${spring.kafka.properties.topic.user}",
//            autoStartup = "${spring.kafka.consumer.properties.auto-start:true}"
            autoStartup = "true"
    )
    public void processMessage(QueueMessage<UserVM> queueMessage) {

        log.info(RECEIVED_MESSAGE, queueMessage, topic);
        processByActionMap(queueMessage);
    }
}
