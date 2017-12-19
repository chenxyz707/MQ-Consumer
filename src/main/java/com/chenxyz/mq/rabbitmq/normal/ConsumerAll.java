package com.chenxyz.mq.rabbitmq.normal;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Description
 *
 * @author chenxyz
 * @version 1.0
 * @date 2017-11-08
 */
public class ConsumerAll {

    /*private static final String EXCHANGE_NAME = "direct_logs";

    private static final BuiltinExchangeType EXCHANGE_TYPE = BuiltinExchangeType.DIRECT;*/

    private final static String EXCHANGE_NAME = "fanout_logs";

    private static final BuiltinExchangeType EXCHANGE_TYPE = BuiltinExchangeType.FANOUT;

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);

        //声明随机队列
        String queueName = channel.queueDeclare().getQueue();
        String[] serverities = {"error","info","warning"};

        for(String server:serverities){
            //队列和交换器的绑定
            channel.queueBind(queueName,EXCHANGE_NAME, server);
        }

        System.out.println("Waiting message...." );

        Consumer consumerA = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("Accept:"+envelope.getRoutingKey()+":"+message);
            }
        };

        channel.basicConsume(queueName, true, consumerA);
    }
}
