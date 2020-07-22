package com.hb.rabbitmq.topic;

import com.hb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

public class Send {
    private final static String EXCHANGE_NAME = "topic";
    public static void main(String[] argv) throws Exception {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道，使用通道才能瓦城消息相关操作
        Channel channel = connection.createChannel();

        // 声明exchange，指定类型为fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);

        // 消息内容
        String msg = "商品删除了，id=1001";

        // 发布消息到Exchange
        channel.basicPublish(EXCHANGE_NAME, "item.update", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
        System.out.println("[生产者] Sent: " + msg + "!");

        // 关闭通道和连接
        channel.close();
        connection.close();
    }
}
