package com.yl.kafka.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020-04-10.
 */
@Slf4j
@Component
public class DeadLetterListener {

    @KafkaListener(topics = "test.topic.dead", groupId = "testDead", containerFactory = "kafkaListenerContainerFactoryDead")
    public void onMessage(String message) {
        logger.info("message: {}", message);
        throw new RuntimeException();
    }

    @KafkaListener(topics = "test.topic.dead.DLT", groupId = "testDeadDlt", containerFactory = "kafkaListenerContainerFactoryDead")
    public void onMessage2(String message) {
        logger.info("DLT message: {}", message);
    }
}
