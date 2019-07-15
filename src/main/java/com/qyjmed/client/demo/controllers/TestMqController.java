package com.qyjmed.client.demo.controllers;

import com.qyjmed.client.demo.bean.User;
import com.qyjmed.client.demo.common.constants.MQConstants;
import com.qyjmed.client.demo.common.constants.MQEnum;
import com.qyjmed.client.demo.mq.MqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by michangtao in 2019/7/11 16:37
 */
@RestController
@RequestMapping("/mq")
public class TestMqController {

    @Autowired
    private MqProducer mqProducer;

    @RequestMapping("/test")
    public void testMq(){
        User user = new User();
        user.setId(1);
        user.setName("小明");
        user.setAge(18);
        mqProducer.sendMqNow(MQConstants.TEST_QUEUE,user);
    }

    @RequestMapping("/testDelay")
    public void testDelay(){
        User user = new User();
        user.setId(2);
        user.setName("小狗");
        user.setAge(2);
        mqProducer.sendMqDelay(MQEnum.MESSAGE_TTL_QUEUE.getExchange(),MQEnum.MESSAGE_TTL_QUEUE.getRouteKey(),user,8000);
    }

    @RequestMapping("/testPlugin")
    public void testPlugin(){
        User user = new User();
        user.setId(3);
        user.setName("小猪");
        user.setAge(10);
        mqProducer.sendMqPlugin(MQEnum.MESSAGE_PLUGIN_QUEUE.getExchange(),MQEnum.MESSAGE_PLUGIN_QUEUE.getRouteKey(),user,8000);
    }

}
