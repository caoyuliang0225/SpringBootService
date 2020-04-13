package com.yl.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cao Yuliang on 2020-04-08.
 */
@Configuration
public class PullMessageConfig {

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1);
        factory.setBatchListener(true);// 开启批量监听
        factory.getContainerProperties().setPollTimeout(3000);
//        factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.MANUAL));// 手动ack
        return factory;
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.65.39:9092");
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "testPullGroup");
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        propsMap.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);//设置每次接收Message的数量
//        propsMap.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
        return propsMap;
    }

    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> properties = consumerConfigs();
        return new DefaultKafkaConsumerFactory<>(properties);
    }
}
