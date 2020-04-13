package com.yl.demo.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yl.demo.config.PersistentExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020-04-03.
 */
@Slf4j
@RestController
public class PersistentController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Channel channel;

    @RequestMapping(value = "/send/persistent", method = RequestMethod.GET)
    public String sendPersistentMsg () {

        rabbitTemplate.setMandatory(true);

        String msg = "OK";
        rabbitTemplate.convertAndSend(
                PersistentExchangeConfig.EXCHANGE_NAME,
                PersistentExchangeConfig.ROUTE_KEY_2,
                msg);

        return "OK";
    }

    @RequestMapping(value = "/send/persistent/2", method = RequestMethod.GET)
    public String send2() throws Exception {

//        AMQP.BasicProperties props = new AMQP.BasicProperties().builder().deliveryMode(2).build();

        channel.exchangeDeclare(PersistentExchangeConfig.EXCHANGE_NAME, "direct", true);
        channel.queueDeclare(PersistentExchangeConfig.QUEUE_3, true, false, false, null);
        channel.queueBind(
                PersistentExchangeConfig.QUEUE_3,
                PersistentExchangeConfig.EXCHANGE_NAME,
                PersistentExchangeConfig.ROUTE_KEY_2);

        String msg = "OK";
        channel.basicPublish(
                PersistentExchangeConfig.EXCHANGE_NAME,
                PersistentExchangeConfig.ROUTE_KEY_2,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                msg.getBytes());

        return "OK";
    }
}
