package com.hb.rabbitmq.simple;

import com.hb.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] argv) throws Exception {
        // 获取连接
        Connection connection = ConnectionUtil.getConnection();

        // 从连接中创建通道，使用通道才能瓦城消息相关操作
        Channel channel = connection.createChannel();

        // 创建队列
        channel.queueDeclare(QUEUE_NAME, false,false,false, null);

        // 消息内容
        String message = "Hello World";

        // 向指定的队列中发送消息
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        System.out.println("[x] send: " + message + "!");

        // 关闭通道和连接
        channel.close();
        connection.close();

    }
}
