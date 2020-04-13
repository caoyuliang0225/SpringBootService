package com.yl.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Cao Yuliang on 2020-04-08.
 */
@Configuration
public class CommonConfig {

    @Bean
    public NewTopic testTopic() {
        return new NewTopic("test.topic1", 1, (short) 1);
    }

    @Bean
    public NewTopic topic5w() {
        NewTopic newTopic = new NewTopic("topic.50000", 1, (short) 1);
        return newTopic;
    }

    @Bean
    public NewTopic testTopic002() {
        return new NewTopic("test.topic002", 1, (short) 1);
    }

    @Bean
    public NewTopic testTopic003() {
        return new NewTopic("test.topic003", 1, (short) 1);
    }

    @Bean
    public NewTopic testTopic005() {
        return new NewTopic("test.topic005", 1, (short) 1);
    }

    @Bean
    public NewTopic testTopicSeek() {
        return new NewTopic("topic.seek", 1, (short) 1);
    }

    @Bean
    public NewTopic testTopicDead() {
        return new NewTopic("test.topic.dead", 1, (short) 1);
    }

    @Bean
    public NewTopic testTopicDeadDlt() {
        return new NewTopic("test.topic.dead.DLT", 1, (short) 1);
    }

    /**
     * 使用事务管理器需要开启
     * @return
     */
//    @Bean
//    public KafkaTransactionManager<Integer, String> kafkaTransactionManager() {
//
//        Properties properties = new Properties();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.65.39:9092");
//        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
//
//        DefaultKafkaProducerFactory<Integer,String> producerFactory =  new DefaultKafkaProducerFactory(properties);
//        //设置事务Id前缀 开启事务
//        producerFactory.setTransactionIdPrefix("tx-");
//
//        return new KafkaTransactionManager<>(producerFactory);
//    }
}
