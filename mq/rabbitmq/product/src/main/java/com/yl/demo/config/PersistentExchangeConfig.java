package com.yl.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Cao Yuliang on 2020-04-03.
 */
@Configuration
public class PersistentExchangeConfig {

    public static final String EXCHANGE_NAME = "PersistentExchange";
//    public static final String QUEUE_1 = "PersistentQueue1";
//    public static final String QUEUE_2 = "PersistentQueue2";
    public static final String QUEUE_3 = "PersistentQueue3";
//    public static final String ROUTE_KEY_1 = "PersistentRouteKey1";
    public static final String ROUTE_KEY_2 = "PersistentRouteKey2";

    /**
     * direct -> routingKey -> queue，相当一种点对点的消息投递，如果路由键匹配，就直接投递到相应的队列
     *
     * @return
     */
    @Bean
    public DirectExchange persistentExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(EXCHANGE_NAME)
                .durable(true)
                .build();
    }

//    @Bean
//    public Queue persistentQueue1() {
//        return QueueBuilder.durable(QUEUE_1)
//                .build();
//    }
//
//    @Bean
//    public Queue persistentQueue2() {
//        return QueueBuilder.durable(QUEUE_2)
//                .build();
//    }

    @Bean
    public Queue persistentQueue3() {
        return QueueBuilder.durable(QUEUE_3)
                .build();
    }

//    @Bean
//    public Binding persistentQueue1Binding(DirectExchange persistentExchange, Queue persistentQueue1) {
//        return BindingBuilder
//                .bind(persistentQueue1)
//                .to(persistentExchange)
//                .with(ROUTE_KEY_1);
//    }
//
//    @Bean
//    public Binding persistentQueue2Binding(DirectExchange persistentExchange, Queue persistentQueue2) {
//        return BindingBuilder
//                .bind(persistentQueue2)
//                .to(persistentExchange)
//                .with(ROUTE_KEY_1);
//    }

    @Bean
    public Binding persistentQueue3Binding(DirectExchange persistentExchange, Queue persistentQueue3) {
        return BindingBuilder
                .bind(persistentQueue3)
                .to(persistentExchange)
                .with(ROUTE_KEY_2);
    }
}
