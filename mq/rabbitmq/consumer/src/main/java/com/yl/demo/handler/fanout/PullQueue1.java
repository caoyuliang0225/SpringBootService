package com.yl.demo.handler.fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cao Yuliang on 2020-03-30.
 * rabbit mq 消费者已 pull 方式拉取队列中的消息
 * 48
 */
@Slf4j
@Component
public class PullQueue1 {

    @Qualifier("getMyChannel")
    @Autowired
    private Channel channel;

    private String bridgeQueueName = "lindFanoutQueue1";
    private int batchSize = 500;
    private List<GetResponse> responseList = new ArrayList<>();
    private long tag = 0;

    public void pullMessage() throws Exception {

        while (responseList.size() < batchSize) {
            GetResponse getResponse = channel.basicGet(bridgeQueueName, false);
            if (getResponse == null) {
                break;
            }
            responseList.add(getResponse);
            String msg = new String(getResponse.getBody(), StandardCharsets.UTF_8);
            logger.info("Pull Message: {}", msg);
            tag = getResponse.getEnvelope().getDeliveryTag();
            logger.info("tag: {}", tag);
        }
        channel.basicAck(tag, true);
        tag = 0;
        responseList.clear();
    }
}
