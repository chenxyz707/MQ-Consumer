package com.chenxyz.mq.rabbitmq.spring.service.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 接收所有的错误消息
 *
 * @author chenxyz
 * @version 1.0
 * @date 2017-12-29
 */
@Component
public class AllErrorTopicService implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(AllErrorTopicService.class);

    @Override
    public void onMessage(Message message) {
        logger.info("Get message:"+new String(message.getBody()));
    }
}
