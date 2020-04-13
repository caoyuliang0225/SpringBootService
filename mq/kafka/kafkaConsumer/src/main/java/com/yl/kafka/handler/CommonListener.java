package com.yl.kafka.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Cao Yuliang on 2020-04-08.
 */
@Slf4j
@Component
public class CommonListener {

    @KafkaListener(topics = "test.topic1", groupId = "testGroup1")
    public void onMessage(String message) {
        logger.info("message: {}", message);
    }

    @KafkaListener(topics = "topic.50000", groupId = "fiveWGroup")
    public void onMessage(int message) {
        logger.info("message: {}", message);
    }

//    @KafkaListener(topics = "test.topic002", containerFactory = "kafkaListenerContainerFactory")
//    public void onPullMessage(String message) {
//        logger.info("message: {}", message);
//    }

    @KafkaListener(topics = "test.topic002", containerFactory = "kafkaListenerContainerFactory")
    public void onPullMessage(List<ConsumerRecord<?, ?>> list) {
        List<String> messages = new ArrayList<>();
        for (ConsumerRecord<?, ?> record : list) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            // 获取消息
            kafkaMessage.ifPresent(o -> messages.add(o.toString()));
        }
        for (String msg : messages) {
            logger.info("message: {}", msg);
        }
    }

    @KafkaListener(topics = "test.topic003", groupId = "testGroup1")
    public void onMessage3(String message) {
        logger.info("message: {}", message);
    }

    @KafkaListener(topics = "test.topic005", groupId = "testGroup1")
    public void onMessage5(String message) {
        logger.info("message: {}", message);
    }

    @KafkaListener(topics = "topic.seek", groupId = "testSeekGroup")
    public void onMessageSeek(String message) {
        logger.info("message: {}", message);
    }

    @KafkaListener(topics = "test.topic.ack", containerFactory = "kafkaListenerContainerFactory")
    public void onAckMessage(List<ConsumerRecord> list, Acknowledgment ack) {
        List<String> messages = new ArrayList<>();
        for (ConsumerRecord<?, ?> record : list) {
            Optional<?> kafkaMessage = Optional.ofNullable(record.value());
            // 获取消息
            kafkaMessage.ifPresent(o -> messages.add(o.toString()));
        }
        for (String msg : messages) {
            logger.info("message: {}", msg);
        }
        ack.acknowledge();
    }
}
