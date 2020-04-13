package com.yl.demo.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yl.demo.config.PersistentExchangeConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by Cao Yuliang on 2020-03-31.
 */
@Slf4j
@RestController
public class TransactionController {

    @Qualifier("myChannel")
    @Autowired
    private Channel channel;

    @RequestMapping(value = "/send/transaction", method = RequestMethod.GET)
    public String sendTransaction() {

        String message1 = "WaHaHa";
        String message2 = "NongFuShanQuan";

        try {
            channel.txSelect();
            channel.basicPublish(
                    PersistentExchangeConfig.EXCHANGE_NAME,
                    PersistentExchangeConfig.ROUTE_KEY_2,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message1.getBytes());

            channel.basicPublish(
                    PersistentExchangeConfig.EXCHANGE_NAME,
                    PersistentExchangeConfig.ROUTE_KEY_2,
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    message2.getBytes());

            int a = 0/0;

            channel.txCommit();
        } catch (Exception e) {
            logger.info("TransactionController--sendTransaction--Exception");
            try {
                channel.txRollback();
            } catch (IOException ex) {
                logger.info("TransactionController--sendTransaction--txRollback--IOException");
            }
        }

        return "OK";
    }
}
