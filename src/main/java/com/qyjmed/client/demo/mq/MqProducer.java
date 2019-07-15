package com.qyjmed.client.demo.mq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * Created by michangtao in 2019/7/11 15:49
 */
@Component
public class MqProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMqNow(String queueName, Object messageContent) {
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        if (!(messageContent instanceof Integer || messageContent instanceof String || messageContent instanceof Map)) {
            messageContent = JSON.toJSONString(messageContent);
        }
        rabbitTemplate.convertAndSend(queueName, messageContent);
    }

    public void sendMqDelay(String exchange,String routeKey,Object messageContent, final long delayTimes){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        if (!(messageContent instanceof Integer || messageContent instanceof String || messageContent instanceof Map)) {
            messageContent = JSON.toJSONString(messageContent);
        }
        if(!StringUtils.isEmpty(exchange)){
            System.out.println(new Date());
            rabbitTemplate.convertAndSend(exchange,routeKey,messageContent,message -> {
                //设置消息延迟时间
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            });
        }
    }


    public void sendMqPlugin(String exchange,String routeKey,Object messageContent, final long delayTimes){
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        if (!(messageContent instanceof Integer || messageContent instanceof String || messageContent instanceof Map)) {
            messageContent = JSON.toJSONString(messageContent);
        }
        if(!StringUtils.isEmpty(exchange)){
            System.out.println(new Date());
            rabbitTemplate.convertAndSend(exchange,routeKey,messageContent,message -> {
                //设置消息延迟时间
                message.getMessageProperties().setHeader("x-delay",String.valueOf(delayTimes));
                return message;
            });
        }
    }
}
