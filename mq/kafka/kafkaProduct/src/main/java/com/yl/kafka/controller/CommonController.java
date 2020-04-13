package com.yl.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020-04-08.
 */
@Slf4j
@RestController
public class CommonController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @RequestMapping(value = "/kafka/send/{num}", method = RequestMethod.GET)
    public String send50000Message(@PathVariable int num) {
        long st = System.currentTimeMillis();
        for (int i=1; i<num; i++) {
//            this.kafkaTemplate.send("topic.50000", i);
            this.kafkaTemplate.send("topic.seek", i);
        }
        long et = System.currentTimeMillis();
        logger.info("time: {}", et - st);
        return "OK";
    }

    @RequestMapping(value = "/kafka/send/25", method = RequestMethod.GET)
    public String send25000Message() {
        for (int i=1; i<10000; i++) {
            this.kafkaTemplate.send("topic.50000", i);
            i++;
        }
        return "OK";
    }

    @RequestMapping(value = "/kafka/send/pull/{num}", method = RequestMethod.GET)
    public String sendPullMessage(@PathVariable int num) {
        for (int i=1; i<num; i++) {
            this.kafkaTemplate.send("test.topic002", i);
        }
        return "OK";
    }

    @RequestMapping(value = "/kafka/send", method = RequestMethod.GET)
    public String sendCommonMessage() {
        this.kafkaTemplate.send("test.topic1", "WaHaHa");
        return "OK";
    }

    @RequestMapping(value = "/kafka/send/dead", method = RequestMethod.GET)
    public String sendDeadMessage() {
        this.kafkaTemplate.send("test.topic.dead", "WaHaHa");
        return "OK";
    }
}
