package com.chenxyz.mq.rabbitmq.spring.service.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 消费监听2
 *
 * @author chenxyz
 * @version 1.0
 * @date 2017-12-29
 */
public class ListenerService2 implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(ListenerService2.class);

    @Override
    public void onMessage(Message message) {
        logger.info("Get message:"+new String(message.getBody()));
    }
}
