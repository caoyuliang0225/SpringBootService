package com.yl.demo.config;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cao Yuliang on 2020-04-03.
 * 49
 */
@Configuration
public class ChannelConfig {

    @Bean(name = "myChannel")
    public Channel myChannel() throws Exception {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
//        channel.confirmSelect();

//        createTtlExchange(channel);
//        createTtlQueue(channel);
//        createDlxQueue(channel);
//        bindTtlQueue(channel);
//        bindDtlQueue(channel);

        createCommonQueue(channel);

        return channel;
    }

    private void createCommonQueue(Channel myChannel) throws Exception {

        myChannel.queueDeclare("QUEUE.01", true, false, false, null);
        myChannel.queueDeclare("QUEUE_NAME_DECIMAL", true, false, false, null);
        myChannel.queueDeclare("ACK_QUEUE", true, false, false, null);

        myChannel.exchangeDeclare("PersistentExchange", BuiltinExchangeType.DIRECT, true);
        myChannel.queueDeclare("PersistentQueue3", true, false, false, null);
        myChannel.queueBind("PersistentQueue3", "PersistentExchange", "PersistentRouteKey2");

        myChannel.exchangeDeclare("RETRY_EXCHANGE", BuiltinExchangeType.DIRECT, true);
        myChannel.queueDeclare("PRODUCT_RETRY_QUEUE", true, false, false, null);
        myChannel.queueBind("PRODUCT_RETRY_QUEUE", "RETRY_EXCHANGE", "PRODUCT_RETRY_QUEUE");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("x-dead-letter-exchange", "order.ttl.exchange");
        map1.put("x-dead-letter-routing-key", "order.dlx.routingkey");
        myChannel.exchangeDeclare("order.ttl.exchange", BuiltinExchangeType.DIRECT, true);
        myChannel.queueDeclare("order.ttl.queue", true, false, false, map1);
        myChannel.queueBind("order.ttl.queue", "order.ttl.exchange", "order.ttl.routingkey");

        myChannel.queueDeclare("order.dlx.queue", true, false, false, null);
        myChannel.queueBind("order.dlx.queue", "order.ttl.exchange", "order.dlx.routingkey");

        myChannel.exchangeDeclare("lindFanoutExchange", BuiltinExchangeType.FANOUT, true);
        myChannel.queueDeclare("lindFanoutQueue1", true, false, false, null);
        myChannel.queueBind("lindFanoutQueue1", "lindFanoutExchange", "");

        myChannel.queueDeclare("lindFanoutQueue2", true, false, false, null);
        myChannel.queueBind("lindFanoutQueue2", "lindFanoutExchange", "");

        Map<String, Object> map2 = new HashMap<>();
        map2.put("x-dead-letter-exchange", "consumer.retry.ttl.exchange");
        map2.put("x-dead-letter-routing-key", "consumer.retry.dlx.routingkey");
        myChannel.exchangeDeclare("consumer.retry.ttl.exchange", BuiltinExchangeType.DIRECT, true);
        myChannel.queueDeclare("consumer.retry.ttl.queue", true, false, false, map2);
        myChannel.queueBind("consumer.retry.ttl.queue", "consumer.retry.ttl.exchange", "consumer.retry.ttl.routingkey");

        myChannel.queueDeclare("consumer.retry.dlx.queue", true, false, false, null);
        myChannel.queueBind("consumer.retry.dlx.queue", "consumer.retry.ttl.exchange", "consumer.retry.dlx.routingkey");
    }

    public void createTtlExchange(Channel myChannel) throws Exception {
        myChannel.exchangeDeclare("channelExchange", BuiltinExchangeType.DIRECT, true);
    }

    public void createTtlQueue(Channel myChannel) throws Exception {

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "channelExchange");
        arguments.put("x-dead-letter-routing-key", "channel-dlx-route-key");

        myChannel.queueDeclare("channelTtlQueue", true, false, false, arguments);
    }

    public void createDlxQueue(Channel myChannel) throws Exception {

        myChannel.queueDeclare("channelDlxQueue", true, false, false, null);
    }

    public void bindTtlQueue(Channel myChannel) throws Exception {
        myChannel.queueBind("channelTtlQueue", "channelExchange", "channel-ttl-route-key");
    }

    public void bindDtlQueue(Channel myChannel) throws Exception {
        myChannel.queueBind("channelDlxQueue", "channelExchange", "channel-dlx-route-key");
    }
}
