package com.hb.rabbitmq.fanout;

import com.hb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv {
    private final static String EXCHANGE_NAME = "fanout";

    private final static String QUEUE_NAME = "fanout_queue_1";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false,false,false, null);

        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        // 默认平均分配,每个消费者同时只能处理一条消息
        channel.basicQos(1);

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用

            // 模拟消费能力比较弱的消费者
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("[消费者-1] received: " + msg + "!");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                }

                // 手动ACK
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 监听队列，第二个参数true，手动进行ACK
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
