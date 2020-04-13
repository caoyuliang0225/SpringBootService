package com.yl.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

/**
 * Created by Cao Yuliang on 2020-04-09.
 */
@Slf4j
@RestController
public class ProductRetryController implements ListenableFutureCallback {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @RequestMapping(value = "/kafka/send/retry/sync", method = RequestMethod.GET)
    public String sendRetryMessage() throws Exception {
        for (int i=1; i<5; i++) {
            ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send("test.topic002", i);
            SendResult<String, Object> sendResult = future.get();
            RecordMetadata recordMetadata = sendResult.getRecordMetadata();
            ProducerRecord producerRecord = sendResult.getProducerRecord();
            logger.info("分区为：" + recordMetadata.partition() + "偏移量为:" + recordMetadata.offset());
            logger.info("RecordMetadata：" + recordMetadata.toString());
            logger.info("producerRecord：" + producerRecord.toString());
            future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    //
                }

                @Override
                public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                    //
                }
            });
        }
        return "OK";
    }

    @RequestMapping(value = "/kafka/send/retry/async", method = RequestMethod.GET)
    public String sendRetryMessage2() {

        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.65.39:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.RETRIES_CONFIG, 3);
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 5000);


        KafkaProducer<String, Object> producer = new KafkaProducer<>(properties);
        ProducerRecord<String, Object> record = new ProducerRecord<>("test.topic003", "hello, Kafka!");

        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace();
                logger.info("exception：" + exception.getMessage());
            } else {
                //分区为：0偏移量为:240048
                logger.info("分区为：" + metadata.partition() + "偏移量为:" + metadata.offset());
            }
        });

//        producer.send(record, new Callback() {
//            @Override
//            public void onCompletion(RecordMetadata metadata, Exception exception) {
//                if (exception != null) {
//                    exception.printStackTrace();
//                } else {
//                    //分区为：0偏移量为:240048
//                    logger.info("分区为：" + metadata.partition() + "偏移量为:" + metadata.offset());
//                }
//            }
//        });

        return "OK";
    }

    @RequestMapping(value = "/kafka/send/retry/async3", method = RequestMethod.GET)
    public String sendRetryMessage3() throws Exception {

        ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send("test.topic002", "1");
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                logger.info("onFailure" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> stringObjectSendResult) {
                logger.info(stringObjectSendResult.getRecordMetadata().toString());
            }
        });

        return "OK";
    }

    @RequestMapping(value = "/kafka/send/retry/async4", method = RequestMethod.GET)
    public String sendRetryMessage4() throws Exception {

        ListenableFuture<SendResult<String, Object>> future = this.kafkaTemplate.send("test.topic002", "1");
        future.addCallback(this);

        return "OK";
    }

    @Override
    public void onFailure(Throwable throwable) {
        logger.info("ProductRetryController onFailure" + throwable.getMessage());
    }

    @Override
    public void onSuccess(Object o) {
        logger.info("ProductRetryController" + ((SendResult<String, Object>) o).getRecordMetadata().toString());
    }
}
