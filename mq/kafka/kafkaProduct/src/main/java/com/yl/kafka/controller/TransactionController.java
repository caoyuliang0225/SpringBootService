package com.yl.kafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cao Yuliang on 2020-04-09.
 */
@Slf4j
@RestController
public class TransactionController {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * 使用本地事务，本地事务不支持嵌套事务
     * @return
     */
    @RequestMapping(value = "/kafka/send/transaction/commit", method = RequestMethod.GET)
    public String sendCommitMessage() {

        this.kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback<String, Object, Object>() {
            @Override
            public Object doInOperations(KafkaOperations operations) {
                operations.send("test.topic005", "msg1");
                operations.send("test.topic005", "commit");
                return null;
            }
        });

        return "OK";
    }

    @RequestMapping(value = "/kafka/send/transaction/rollback", method = RequestMethod.GET)
    public String sendRollbackMessage() {

        // <String, Object, Object>   "test.topic002"
        this.kafkaTemplate.executeInTransaction(operations -> {
            operations.send("test.topic005", "msg2");
            operations.send("test.topic005", "rollback");
            throw new RuntimeException();
        });

        return "OK";
    }

    /**
     * 使用 kafka事务管理器
     */
//    @RequestMapping(value = "/kafka/send/transaction/t/commit", method = RequestMethod.GET)
//    @Transactional(transactionManager = "kafkaTransactionManager",rollbackFor = Exception.class)
//    public String sendTMessageC() {
//
//        kafkaTemplate.send("test.topic005", "msg2");
//        kafkaTemplate.send("test.topic005", "commit");
//        return "OK";
//    }
//
//    @RequestMapping(value = "/kafka/send/transaction/t/rollback", method = RequestMethod.GET)
//    @Transactional(transactionManager = "kafkaTransactionManager",rollbackFor = Exception.class)
//    public String sendTMessageR() {
//
//        kafkaTemplate.send("test.topic005", "msg2");
//        kafkaTemplate.send("test.topic005", "rollback");
//        throw new RuntimeException();
//    }
}
