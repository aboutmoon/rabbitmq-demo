package com.hb.rabbitmq.fanout;

import com.hb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
    private final static String EXCHANGE_NAME = "fanout";
    public static void main(String[] argv) throws Exception {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道，使用通道才能瓦城消息相关操作
        Channel channel = connection.createChannel();

        // 声明exchange，指定类型为fanout
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 消息内容
        String msg = "Hello Everyone";

        // 发布消息到Exchange
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("[生产者] Sent: " + msg + "!");

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
