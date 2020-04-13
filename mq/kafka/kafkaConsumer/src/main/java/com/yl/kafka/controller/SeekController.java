package com.yl.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * Created by Cao Yuliang on 2020-04-09.
 */
@Slf4j
@RestController
public class SeekController {

    @Qualifier("seekConsumer")
    @Autowired
    private KafkaConsumer kafkaConsumer;

    @RequestMapping(value = "/kafka/seek", method = RequestMethod.GET)
    public String seek() {

        Duration duration = Duration.ofSeconds(2);
        ConsumerRecords<String, Object> records = kafkaConsumer.poll(duration);
        if (records != null) {
            for (ConsumerRecord<String, Object> record : records) {
                logger.info(record.toString());
            }
        }

        return "OK";
    }
}
