package com.yl.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.Properties;

/**
 * Created by Cao Yuliang on 2020-04-09.
 */
@Configuration
public class SeekConfig {

    @Bean
    public Properties seekProperties() {

        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.65.39:9092");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "testSeekGroup");

        return properties;
    }

    @Bean(name = "seekConsumer")
    public KafkaConsumer<String, Object> seekConsumer(Properties seekProperties) {

        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(seekProperties);
//        Map<TopicPartition, OffsetAndMetadata> hashMaps = new HashMap<>();
//        hashMaps.put(new TopicPartition("topic.seek", 0), new OffsetAndMetadata(0));
//        consumer.commitSync(hashMaps);
//        consumer.subscribe(Collections.singletonList("topic.seek"));


        consumer.assign(Collections.singletonList(new TopicPartition("topic.seek", 0)));
        consumer.seek(new TopicPartition("topic.seek", 0), 10);//不改变当前offset

        return consumer;
    }
}
