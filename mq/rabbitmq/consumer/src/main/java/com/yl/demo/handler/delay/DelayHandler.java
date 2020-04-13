package com.yl.demo.handler.delay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020-04-01.
 * 48
 */
@Slf4j
@Component
@RabbitListener(queues = "order.dlx.queue")
//@RabbitListener(
//        bindings = @QueueBinding(
//                exchange = @Exchange(name = "liveness-exchange", durable = "true", type = "direct"),
//                value = @Queue(value = "liveness-queue",durable = "true",
//                        arguments = {'x-dead-letter-exchange': exchange, 'x-dead-letter-routing-key': queue}),
//                key="liveness.callback"
//        )
//)
public class DelayHandler {

    @RabbitHandler
    public void processMessage(String msg) {
        logger.info("time: {}", System.currentTimeMillis());
        logger.info("message: " + msg);
    }
}
