package com.yl.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Cao Yuliang on 2020-04-01.
 * 48
 */
@Configuration
public class FanoutExchangeConfig {

    public static final String LIND_FANOUT_EXCHANGE = "lindFanoutExchange";
    public static final String LIND_FANOUT_QUEUE_1 = "lindFanoutQueue1";
    public static final String LIND_FANOUT_QUEUE_2 = "lindFanoutQueue2";
    public static final String LIND_FANOUT_ROUTE_KEY_1 = "lindFanoutRouteKey1";
    public static final String LIND_FANOUT_ROUTE_KEY_2 = "lindFanoutRouteKey2";

    /**
     * fanout交换器相当于实现了一（交换器）对多（队列）的广播投递方式
     *
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(LIND_FANOUT_EXCHANGE);
    }

    @Bean
    public Queue lindFanoutQueue1() {
        return new Queue(LIND_FANOUT_QUEUE_1);
    }

    @Bean
    public Queue lindFanoutQueue2() {
        return new Queue(LIND_FANOUT_QUEUE_2);
    }

    @Bean
    public Binding queue1Binding(FanoutExchange fanoutExchange, Queue lindFanoutQueue1) {
        return BindingBuilder
                .bind(lindFanoutQueue1)
                .to(fanoutExchange);
    }

    @Bean
    public Binding queue2Binding(FanoutExchange fanoutExchange, Queue lindFanoutQueue2) {
        return BindingBuilder
                .bind(lindFanoutQueue2)
                .to(fanoutExchange);
    }
}
