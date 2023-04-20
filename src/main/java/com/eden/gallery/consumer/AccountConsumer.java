package com.eden.gallery.consumer;

import com.eden.gallery.service.AccountService;
import com.eden.gallery.viewmodel.AccountVM;
import com.eden.queue.consumer.BaseConsumer;
import com.eden.queue.util.Action;
import com.eden.queue.util.QueueMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.eden.queue.util.Strings.RECEIVED_MESSAGE;

/**
 * Kafka consumer for account handling.
 */
@Component
@Log4j2
public class AccountConsumer extends BaseConsumer<AccountVM> {

    @Value("${spring.kafka.properties.topic.account}")
    private String topic;

    /**
     * Constructor.
     *
     * @param accountService account service for action mapping
     */
    public AccountConsumer(AccountService accountService) {

        this.actionMap.put(Action.CREATE, accountService::create);
        this.actionMap.put(Action.UPDATE, accountService::update);
        this.actionMap.put(Action.DELETE, acc -> accountService.delete(acc.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @KafkaListener(topics = "${spring.kafka.properties.topic.account}",
            autoStartup = "${spring.kafka.consumer.properties.auto-start:true}")
    public void processMessage(QueueMessage<AccountVM> queueMessage) {

        log.info(RECEIVED_MESSAGE, queueMessage, topic);
        processByActionMap(queueMessage);
    }
}
