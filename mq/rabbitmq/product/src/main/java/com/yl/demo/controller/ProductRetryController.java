package com.yl.demo.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yl.demo.config.ChannelConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Cao Yuliang on 2020-03-31.
 * 消息持久化，product 收 confirm 标志
 * 49 55
 */
@Slf4j
@RestController
public class ProductRetryController implements Runnable {

    @Qualifier("myChannel")
    @Autowired
    private Channel channel;

    private static Map<Integer, MsgEntity> msgMap = new HashMap<>();

    @RequestMapping(value = "/send/confirm", method = RequestMethod.GET)
    public String sendConfirmMsg (
            @RequestParam(required = false) Integer num) {

//        try {
//            init();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        MsgEntity msgEntity = new MsgEntity();
        msgEntity.setId(num);
        msgEntity.setMsg("ok:" + num);
        msgEntity.setTimes(1);
        msgMap.put(num, msgEntity);
        try {
            channel.basicPublish(
                    "",
                    "PRODUCT_RETRY_QUEUE",
                    MessageProperties.PERSISTENT_TEXT_PLAIN,
                    msgEntity.getMsg().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (channel.waitForConfirms()) {
                logger.info("发送成功: {}", num);
                msgMap.remove(num);
            } else {
                //发送失败这里可进行消息重新投递的逻辑
                logger.info("发送失败: {}", num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        for (int i=1; i<num; i++) {
//            String msg = "" + i;
//            MsgEntity msgEntity = new MsgEntity();
//            msgEntity.setId(i);
//            msgEntity.setMsg(msg);
//            msgEntity.setTimes(1);
//            msgMap.put(i, msgEntity);
//            try {
//                channel.basicPublish(
//                        "",
//                        "PRODUCT_RETRY_QUEUE",
//                        MessageProperties.PERSISTENT_TEXT_PLAIN,
//                        msgEntity.getMsg().getBytes());
//            } catch (IOException e) {
//                e.printStackTrace();
//                continue;
//            }
//            try {
//                if (channel.waitForConfirms()) {
//                    logger.info("发送成功: {}", i);
//                    msgMap.remove(i);
//                } else {
//                    //发送失败这里可进行消息重新投递的逻辑
//                    logger.info("发送失败: {}", i);
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                continue;
//            }
//        }
//        channel.addConfirmListener(new ConfirmListener() {
//            @Override
//            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
//                logger.info("发送成功: {} - {}", deliveryTag, multiple);
//            }
//
//            @Override
//            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
//                logger.info("发送失败: {} - {}", deliveryTag, multiple);
//            }
//        });

        return "OK";
    }

    private void init() throws Exception {
        // 参数1 exchange ：交换器名
        // 参数2 type ：交换器类型
        // 参数3 durable ：是否持久化
//        channel.exchangeDeclare("RETRY_EXCHANGE", "direct", true);
        // 参数1 queue ：队列名
        // 参数2 durable ：是否持久化
        // 参数3 exclusive ：仅创建者可以使用的私有队列，断开后自动删除
        // 参数4 autoDelete : 当所有消费客户端连接断开后，是否自动删除队列
        // 参数5 arguments
//        channel.queueDeclare("PRODUCT_RETRY_QUEUE", true, false, false, null);

        //绑定exchange和queue
//        channel.queueBind("PRODUCT_RETRY_QUEUE", "RETRY_EXCHANGE", "PRODUCT_RETRY_QUEUE");

        // 参数1 exchange ：交换器
        // 参数2 routingKey ： 路由键
        // 参数3 props ： 消息的其他参数,其中 MessageProperties.PERSISTENT_TEXT_PLAIN 表示持久化
        // 参数4 body ： 消息体
//        channel.basicPublish("", "QUEUE_NAME_1", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());

        // 开启 confirm 模式
//        channel.confirmSelect();
    }

    @Override
    public void run() {
        while (true) {
            logger.info("run");
            if (msgMap.size() > 0) {
                msgMap.get(50);
            }
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (MsgEntity msgEntity : msgMap.values()) {
                try {
                    if (channel == null) {
                        ChannelConfig channelConfig = new ChannelConfig();
                        channel = channelConfig.myChannel();
                    }
                    channel.basicPublish(
                            "",
                            "PRODUCT_RETRY_QUEUE",
                            MessageProperties.PERSISTENT_TEXT_PLAIN,
                            msgEntity.getMsg().getBytes());
                    if (channel.waitForConfirms()) {
                        logger.info("发送成功: {}", msgEntity.getMsg());
                        msgMap.remove(msgEntity.getId());
                    } else {
                        //发送失败这里可进行消息重新投递的逻辑
                        logger.info("发送失败: {}", msgEntity.getId());
                    }
//                    int times = msgEntity.getTimes();
//                    if (times > 3) {
//                        msgMap.remove(msgEntity.getId());
//                    } else {
//                        msgEntity.setTimes(times + 1);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    class MsgEntity {

        int id;
        String msg;
        int times;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getTimes() {
            return times;
        }

        public void setTimes(int times) {
            this.times = times;
        }
    }

    @RequestMapping(value = "/send/big", method = RequestMethod.GET)
    public String sendConfirmMsg () throws Exception {
        init();
        String msg = "每一个RabbitMQ服务器都能创建虚拟消息服务器，我们称之为虚拟主机（vhost）。" +
                "每一个vhost本质上是一个mini版的RabbitMQ服务器，拥有自己的队列、交换器和绑定......更重要的是，" +
                "他拥有自己的权限限制。这使得你能够安全的使用一个RabbitMQ服务器来服务众多应用程序，" +
                "而不用担心你的Sudoku（数独）应用可能会删除狗狗防丢跟踪器正在使用的队列。" +
                "vhost之于Rabbit就像虚拟机之于物理服务器一样：他们通过在各个实例间提供逻辑上分离，" +
                "允许你为不同应用程序安全保密的运行数据。这很有用，他既能将同一个Rabbit的众多客户区分开来，" +
                "又可以避免队列和交换器的命令冲突。否则你可能不得不运行多个Rabbit，并忍受随之而来头疼的管理问题。" +
                "相反，你可以只运行一个Rabbit，然后按";
        logger.info("time: {}", System.currentTimeMillis());
        channel.basicPublish(
                "",
                "QUEUE_NAME_1",
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                msg.getBytes());
        return "OK";
    }
}
