package com.hb.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {

    public static Connection getConnection() throws IOException, TimeoutException {
        // 定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        // 设置服务地址
        factory.setHost("127.0.0.1");

        // 端口
        factory.setPort(5672);

        // 设置帐号信息，用户名，密码，vhost
        factory.setVirtualHost("/leyou");
        factory.setUsername("leyou");
        factory.setPassword("leyou");

        // 通过工厂获取连接
        Connection connection = factory.newConnection();

        return connection;
    }
}
