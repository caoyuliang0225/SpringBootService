package com.yl.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Created by Cao Yuliang on 2020-03-30.
 * 发送普通消息
 * 47
 */
@Slf4j
@RestController
public class CommonMessageController {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    // 47 48 55
    @RequestMapping(value = "/send/async", method = RequestMethod.GET)
    public String sendAsyncMessage (
            @RequestParam Integer num) {

        sendTemplate("QUEUE.01", num);
        return "OK";
    }

    @RequestMapping(value = "/send/async1", method = RequestMethod.GET)
    public String sendAsyncMessage1 (
            @RequestParam Integer num) {

        sendTemplate1("QUEUE.01", num);
        return "OK";
    }

    @RequestMapping(value = "/send/async2", method = RequestMethod.GET)
    public String sendAsyncMessage2 (
            @RequestParam Integer num) {

        sendTemplate2("QUEUE.01", num);
        return "OK";
    }

    // 47
    @RequestMapping(value = "/send/sync", method = RequestMethod.GET)
    public String sendSycnMessage(
            @RequestParam Integer num) {
        String queue = "QUEUE.02";
        long ST = System.currentTimeMillis();
        for (int i=1; i<num; i++) {
            this.rabbitTemplate.convertAndSend(queue, "" + i);
            String msg = (String) rabbitTemplate.receiveAndConvert(queue);
            logger.info("msg: ", msg);
        }
        long ET = System.currentTimeMillis();
        long time = ET - ST;
        logger.info("TIME: {}", time);
        return "OK";
    }

    // 53
    @RequestMapping(value = "/send/decimal", method = RequestMethod.GET)
    public String sendBigDecimalMessage(
            @RequestParam String decimal) {

        BigDecimal bigDecimal = new BigDecimal(decimal);
        this.rabbitTemplate.convertAndSend("QUEUE_NAME_DECIMAL", bigDecimal);
        return "OK";
    }

    private void sendTemplate(String queue, Integer num) {
        long ST = System.currentTimeMillis();
        for (int i=1; i<num; i++) {
            this.rabbitTemplate.convertAndSend(queue, "" + i);
        }
        long ET = System.currentTimeMillis();
        long time = ET - ST;
        logger.info("TIME: {}", time);
    }

    private void sendTemplate1(String queue, Integer num) {
        long ST = System.currentTimeMillis();
        for (int i=1; i<num; i++) {
            this.rabbitTemplate.convertAndSend(queue, "" + i);
            i++;
        }
        long ET = System.currentTimeMillis();
        long time = ET - ST;
        logger.info("TIME: {}", time);
    }

    private void sendTemplate2(String queue, Integer num) {
        long ST = System.currentTimeMillis();
        for (int i=2; i<num; i++) {
            this.rabbitTemplate.convertAndSend(queue, "" + i);
            i++;
        }
        long ET = System.currentTimeMillis();
        long time = ET - ST;
        logger.info("TIME: {}", time);
    }


}
