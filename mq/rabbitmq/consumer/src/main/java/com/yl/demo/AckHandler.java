package com.yl.demo;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by Cao Yuliang on 2020-03-31.
 * yml 需要添加手动配置ack
 */
@Slf4j
@Component
@RabbitListener(queues = "ACK_QUEUE")
public class AckHandler {

    @RabbitHandler
    public void processMessage(
            @Payload String message,
            @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
            Channel channel) {
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.basicNack(deliveryTag, true, true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        logger.info("message: " + message);
    }
}
