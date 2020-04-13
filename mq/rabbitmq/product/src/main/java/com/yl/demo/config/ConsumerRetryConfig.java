package com.yl.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Cao Yuliang on 2020-04-03.
 */
@Configuration
public class ConsumerRetryConfig {

    // ttl(延时)消息交换机名称
    public static final String CONSUMER_RETRY_TTL_EXCHANGE = "consumer.retry.ttl.exchange";

    // ttl(延时)路由key
    public static final String CONSUMER_RETRY_TTL_ROUTINGKEY = "consumer.retry.ttl.routingkey";

    // ttl(延时)消息队列名称
    public static final String CONSUMER_RETRY_TTL_QUEUE = "consumer.retry.ttl.queue";


    // 消息二次转发的路由key
    public static final String CONSUMER_RETRY_DLX_ROUTINGKEY = "consumer.retry.dlx.routingkey";

    // 消息二次转发的队列名称
    public static final String CONSUMER_RETRY_DLX_QUEUE = "consumer.retry.dlx.queue";

    /**
     * TTL 消息交换机配置
     * @return
     */
    @Bean
    public DirectExchange consumerRetryTTLDirectExchange() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(CONSUMER_RETRY_TTL_EXCHANGE)
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
    public Queue consumerRetryTTLQueue() {
        return QueueBuilder.durable(CONSUMER_RETRY_TTL_QUEUE)
                .withArgument("x-dead-letter-exchange", CONSUMER_RETRY_TTL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", CONSUMER_RETRY_DLX_ROUTINGKEY)
                .build();
    }

    /**
     * 消息二次转发的队列配置
     * @return
     */
    @Bean
    public Queue consumerRetryDLXQueue() {
        return new Queue(CONSUMER_RETRY_DLX_QUEUE);
    }

    /**
     * 延时交换机与延时消息队列的绑定 -routingkey
     * @param consumerRetryTTLDirectExchange
     * @param consumerRetryTTLQueue
     * @return
     */
    @Bean
    public Binding consumerRetryTTLBinding(DirectExchange consumerRetryTTLDirectExchange, Queue consumerRetryTTLQueue) {
        return BindingBuilder
                .bind(consumerRetryTTLQueue)
                .to(consumerRetryTTLDirectExchange)
                .with(CONSUMER_RETRY_TTL_ROUTINGKEY);
    }

    /**
     * 延时交换机与二次转发消队列的绑定 - routingkey
     * @param consumerRetryTTLDirectExchange
     * @param consumerRetryDLXQueue
     * @return
     */
    @Bean
    public Binding consumerRetryDLXBinding(DirectExchange consumerRetryTTLDirectExchange, Queue consumerRetryDLXQueue) {
        return BindingBuilder
                .bind(consumerRetryDLXQueue)
                .to(consumerRetryTTLDirectExchange)
                .with(CONSUMER_RETRY_DLX_ROUTINGKEY);
    }
}
