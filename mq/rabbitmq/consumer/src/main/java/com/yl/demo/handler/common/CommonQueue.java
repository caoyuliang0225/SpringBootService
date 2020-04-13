package com.yl.demo.handler.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020-03-30.
 * 47 48 55
 */
@Slf4j
@Component
@RabbitListener(queues = "QUEUE.01")
public class CommonQueue {

    @RabbitHandler
    public void processMessage(String msg) {
//        logger.info("time: {}", System.currentTimeMillis());
        logger.info("message: " + msg);
    }
}
