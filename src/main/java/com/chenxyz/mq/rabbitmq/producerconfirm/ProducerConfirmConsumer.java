package com.chenxyz.mq.rabbitmq.producerconfirm;

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
 * 生产者确认的消费者
 *
 * @author chenxyz
 * @version 1.0
 * @date 2017-12-26
 */
public class ProducerConfirmConsumer {

    private static final String EXCHANGE_NAME = "producer_confirm";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = "producer_confirm";
        channel.queueDeclare(queueName, false, false, false, null);

        String server = "error";

        channel.queueBind(queueName, EXCHANGE_NAME, server);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("Received [" + envelope.getRoutingKey() + "]" + msg);
            }
        };

        channel.basicConsume(queueName, true, consumer);

    }


}
