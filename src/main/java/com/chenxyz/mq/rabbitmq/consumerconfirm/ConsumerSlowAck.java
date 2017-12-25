package com.chenxyz.mq.rabbitmq.consumerconfirm;

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
 * 消费者的slow ack
 *
 * @author chenxyz
 * @version 1.0
 * @date 2017-12-25
 */
public class ConsumerSlowAck {

    private static final String EXCHANGE_NAME = "direct_cc_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = "consumer_confirm";
        channel.queueDeclare(queueName, false, false, false, null);

        String server = "error";
        channel.queueBind(queueName, EXCHANGE_NAME, server);
        System.out.println("Waiting message......");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                // 每隔25秒会接收一条消息
                try {
                    Thread.sleep(25000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Accept:" + envelope.getRoutingKey() + ":" + new String(body, "UTF-8"));
                this.getChannel().basicAck(envelope.getDeliveryTag(), false);
            }
        };

        channel.basicConsume(queueName, false, consumer);

    }
}
