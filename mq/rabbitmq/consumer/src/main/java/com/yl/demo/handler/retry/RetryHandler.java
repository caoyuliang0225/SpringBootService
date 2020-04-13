package com.yl.demo.handler.retry;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * Created by Cao Yuliang on 2020-04-01.
 * 50
 */
@Slf4j
@Component
@RabbitListener(queues = "consumer.retry.ttl.queue")
public class RetryHandler {

    @RabbitHandler
    public void processMessage(
            @Payload String message,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
            Channel channel) throws Exception {
        logger.info("message: " + message);
        // 为了测试重试抛出异常
        throw new Exception();
    }
}
