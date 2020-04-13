package com.yl.demo.handler.fanout;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020-04-01.
 * 48
 */
@Slf4j
@Component
@RabbitListener(queues = "lindFanoutQueue2")
public class FanoutQueue2 {

    @RabbitHandler
    public void processMessage(String msg) {
        logger.info("time: {}", System.currentTimeMillis());
        logger.info("message: " + msg);
    }
}
