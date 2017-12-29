package com.chenxyz.mq.rabbitmq.spring.service.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 接收所有的日志消息
 *
 * @author chenlinchao
 * @version 1.0
 * @date 2017-12-29
 * Copyright 青海粮食云项目组
 */
@Component
public class AllLogTopicService implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(AllLogTopicService.class);

    @Override
    public void onMessage(Message message) {
        logger.info("Get message:"+new String(message.getBody()));
    }
}
