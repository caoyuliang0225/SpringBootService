package com.yl.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Cao Yuliang on 2020-04-03.
 */
@Configuration
public class DelayExchangeConfig {

    // ttl(延时)消息交换机名称
    public static final String ORDER_TTL_EXCHANGE = "order.ttl.exchange";

    // ttl(延时)路由key
    public static final String ORDER_TTL_ROUTINGKEY = "order.ttl.routingkey";

    // ttl(延时)消息队列名称
    public static final String ORDER_TTL_QUEUE = "order.ttl.queue";


    // 消息二次转发的路由key
    public static final String ORDER_DLX_ROUTINGKEY = "order.dlx.routingkey";

    // 消息二次转发的队列名称
    public static final String ORDER_DLX_QUEUE = "order.dlx.queue";

    /**
     * TTL 消息交换机配置
     * @return
     */
    @Bean
    public DirectExchange messageTTLDirectExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(ORDER_TTL_EXCHANGE)
                .durable(true)
                .build();
    }

    /**
     * TTL 消息队列配置
     * durable 为持久队列创建构建器
     * withArgument 最终队列将包含用于声明队列的参数。
     * build 建立最终队列。
     * @return
     */
    @Bean
    public Queue messageTTLQueue() {
        return QueueBuilder.durable(ORDER_TTL_QUEUE)
                .withArgument("x-dead-letter-exchange", ORDER_TTL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", ORDER_DLX_ROUTINGKEY)
                .build();
    }

    /**
     * 消息二次转发的队列配置
     * @return
     */
    @Bean
    public Queue messageDLXQueue() {
        return new Queue(ORDER_DLX_QUEUE);
    }

    /**
     * 延时交换机与延时消息队列的绑定 -routingkey
     * @param messageTTLDirectExchange
     * @param messageTTLQueue
     * @return
     */
    @Bean
    public Binding messageTTLBinding(DirectExchange messageTTLDirectExchange, Queue messageTTLQueue) {
        return BindingBuilder
                .bind(messageTTLQueue)
                .to(messageTTLDirectExchange)
                .with(ORDER_TTL_ROUTINGKEY);
    }

    /**
     * 延时交换机与二次转发消队列的绑定 - routingkey
     * @param messageTTLDirectExchange
     * @param messageDLXQueue
     * @return
     */
    @Bean
    public Binding messageDLXBinding(DirectExchange messageTTLDirectExchange, Queue messageDLXQueue) {
        return BindingBuilder
                .bind(messageDLXQueue)
                .to(messageTTLDirectExchange)
                .with(ORDER_DLX_ROUTINGKEY);
    }
}
