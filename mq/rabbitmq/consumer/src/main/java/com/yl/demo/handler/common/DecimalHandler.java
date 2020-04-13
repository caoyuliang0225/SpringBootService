package com.yl.demo.handler.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Created by Cao Yuliang on 2020-03-31.
 * 53
 */
@Slf4j
@Component
@RabbitListener(queues = "QUEUE_NAME_DECIMAL")
public class DecimalHandler {

    @RabbitHandler
    public void processMessage(BigDecimal msg) {
        logger.info("message: " + msg.toString());
    }
}
