package com.yl.demo.controller;

import com.yl.demo.config.FanoutExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020-04-01.
 * 广播发送
 * 48
 */
@Slf4j
@RestController
public class FanoutExchangeController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/send/fanout", method = RequestMethod.GET)
    public String sendFanout(@RequestParam String message) {
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.LIND_FANOUT_EXCHANGE, null, message);
        return "OK";
    }
}
