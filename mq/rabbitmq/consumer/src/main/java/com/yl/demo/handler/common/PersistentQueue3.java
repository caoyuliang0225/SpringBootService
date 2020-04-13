package com.yl.demo.handler.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020-04-03.
 */
@Slf4j
@Component
@RabbitListener(queues = "PersistentQueue3")
public class PersistentQueue3 {

    @RabbitHandler
    public void processMessage(String msg) {
        logger.info("message: " + msg);
    }
}
