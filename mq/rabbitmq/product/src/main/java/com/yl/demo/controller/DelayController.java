package com.yl.demo.controller;

import com.yl.demo.config.DelayExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020-04-01.
 * 48
 */
@Slf4j
@RestController
public class DelayController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/send/delay", method = RequestMethod.GET)
    public String sendConfirmMsg (
            @RequestParam Integer delay) {

        rabbitTemplate.setMandatory(true);

        String msg = "OK";
        CorrelationData correlationData = new CorrelationData("99");
        rabbitTemplate.convertAndSend(DelayExchangeConfig.ORDER_TTL_EXCHANGE, DelayExchangeConfig.ORDER_TTL_ROUTINGKEY, msg, message -> {
            //设置延时超时时间
            message.getMessageProperties().setExpiration(String.valueOf(delay));
            return message;
        }, correlationData);
        logger.info("time: {}", System.currentTimeMillis());

        return "OK";
    }
}
