package com.yl.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Cao Yuliang on 2020-04-03.
 */
@Configuration
public class RetryConfig {

    public static final String EXCHANGE_NAME = "RETRY_EXCHANGE";
    //    public static final String QUEUE_1 = "PersistentQueue1";
//    public static final String QUEUE_2 = "PersistentQueue2";
    public static final String QUEUE_3 = "PRODUCT_RETRY_QUEUE";
    //    public static final String ROUTE_KEY_1 = "PersistentRouteKey1";
    public static final String ROUTE_KEY_2 = "PRODUCT_RETRY_QUEUE";

    @Bean
    public DirectExchange retryExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

    @Bean
    public Queue retryQueue3() {
        return QueueBuilder.durable(QUEUE_3)
                .build();
    }

    @Bean
    public Binding retryQueue3Binding(DirectExchange retryExchange, Queue retryQueue3) {
        return BindingBuilder
                .bind(retryQueue3)
                .to(retryExchange)
                .with(ROUTE_KEY_2);
    }
}
