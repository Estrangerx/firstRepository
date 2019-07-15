package com.qyjmed.client.demo.mq;

import com.alibaba.fastjson.JSON;
import com.qyjmed.client.demo.bean.User;
import com.qyjmed.client.demo.common.constants.MQConstants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by michangtao in 2019/7/11 15:50
 */
@Component
@RabbitListener(queues = MQConstants.PLUGIN_QUEUE)
public class MqPluginConsumer {

    @RabbitHandler
    public void process(String json){
        System.out.println(new Date());
        User user = JSON.parseObject(json,User.class);
        System.out.println(user);
    }

}
