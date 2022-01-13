package com.test.demo.Rocetmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.CollectionUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@SpringBootTest
@Slf4j
public class Rocetmq {

    /**
     * 发送消息
     */
    @Test
   public void test1() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 创建消息生产者，指定生成组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        // 指定NameServer的地址
        producer.setNamesrvAddr("127.0.0.1:9876");
        // 启动生产者
        producer.start();
        for (int i = 0; i < 10; i++) {
            //4.创建消息对象， 指定主题Topic、 详细Tag 、消息内容
            Message msg = new Message("base", "tag3", ("msg" + i).getBytes());
            // 5.发送单向消息
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.println(throwable);
                }
            });
//            SendStatus sendStatus = send.getSendStatus();//发送状态
//            String msgId = send.getMsgId();//消息id
//            MessageQueue messageQueue = send.getMessageQueue();
//            System.out.println("发送状态:"+sendStatus+" 消息ID:"+msgId+"  messageQueue:"+messageQueue);
            TimeUnit.SECONDS.sleep(1);
        }
        // 6.关闭生产者producer
        producer.shutdown();
    }

    /**
     *接收消息
     */
    @Test
    public void test2() throws MQClientException {
        // 创建消费者Consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group1");
        // 指定Nameserver地址
        consumer.setNamesrvAddr("127.0.0.1:9876");
        // 订阅主题Topic和Tag
        consumer.subscribe("base", "tag1 || tag2 || tag3");

        // 设置消费模式负载均衡（不设置也是默认）
        consumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置匿名内部类，处理消息
/*        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                list.forEach(item -> {
                    System.out.println(new String(item.getBody()));
                });
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });*/
        consumer.registerMessageListener((List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext)->{
            list.forEach(item -> {
                System.out.println(new String(item.getBody()));
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        // 启动消费者consumer
        consumer.start();
    }
}
