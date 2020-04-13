package com.yl.demo.handler.retry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020-04-01.
 * 50
 */
@Slf4j
@Component
@RabbitListener(queues = "consumer.retry.dlx.queue")
public class RetryDLXHandler {

    @RabbitHandler
    public void processMessage(String msg) {
        logger.info("time: {}", System.currentTimeMillis());
        logger.info("message: " + msg);
    }
}
