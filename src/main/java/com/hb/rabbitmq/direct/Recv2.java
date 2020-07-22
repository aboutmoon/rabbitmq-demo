package com.hb.rabbitmq.direct;

import com.hb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    private final static String EXCHANGE_NAME = "direct";

    private final static String QUEUE_NAME = "direct_queue_2";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false,false,false, null);

        // 绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "insert");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "update");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");

        // 每个消费者同时只能处理一条消息, 只有在消费完后（返回ACK），才能获取下一条消息
        channel.basicQos(1);

        // 定义队列的消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用

            // 模拟消费能力比较强的消费者
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("[消费者-2] received: " + msg + "!");

                // 手动ACK
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

        // 监听队列，第二个参数true，手动进行ACK
        channel.basicConsume(QUEUE_NAME, false, consumer);

    }
}
