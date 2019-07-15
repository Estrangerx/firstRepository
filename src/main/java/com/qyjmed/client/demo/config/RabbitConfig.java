package com.qyjmed.client.demo.config;

import com.qyjmed.client.demo.common.constants.MQConstants;
import com.qyjmed.client.demo.common.constants.MQEnum;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by michangtao in 2019/7/11 15:48
 */
@Configuration
public class RabbitConfig {

    /**
     * 指定mq 投递消息的序列化方式，直接转成json发送，避免传入object时的序列化操作
     * 此处只定义了消费者 消费消息的转换方式
     * 生成者发送消息时还需 手动指定rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
     */
    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 10:50
     * @Description : 测试队列
     */
    @Bean
    public Queue testMQ(){
        return new Queue(MQConstants.TEST_QUEUE);
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 10:50
     * @Description : ttl死信exchange
     */
    @Bean
    public DirectExchange ttlExchange(){
        return new DirectExchange(MQEnum.MESSAGE_TTL_QUEUE.getExchange());
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 10:52
     * @Description : ttl死信队列
     */
    @Bean
    public Queue ttlQueue(){
        return QueueBuilder
                .durable(MQEnum.MESSAGE_TTL_QUEUE.getQueueName())
                //配置到期后转发的exchange
                .withArgument("x-dead-letter-exchange",MQEnum.MESSAGE_QUEUE.getExchange())
                //配置到期后转发的路由键
                .withArgument("x-dead-letter-routing-key",MQEnum.MESSAGE_QUEUE.getRouteKey())
                .build();

//        Map<String,Object> args = new HashMap<>();
//        //声明死信exchange
//        args.put("x-dead-letter-exchange",MQEnum.MESSAGE_QUEUE.getExchange());
//        //声明死信路由键
//        args.put("x-dead-letter-routing-key",MQEnum.MESSAGE_QUEUE.getRouteKey());
//        //声明队列过期时间
//        args.put("x-message-ttl",10000);//10秒
//        return new Queue(MQEnum.MESSAGE_TTL_QUEUE.getQueueName(),true,false,false,args);
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 11:21
     * @Description : 实际消费exchange
     */
    @Bean
    public DirectExchange exactExchange(){
        return new DirectExchange(MQEnum.MESSAGE_QUEUE.getExchange());
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 11:22
     * @Description : 实际消费队列
     */
    @Bean
    public Queue exactQueue(){
        return new Queue(MQEnum.MESSAGE_QUEUE.getQueueName());
    }
    
    /**  
     * @author : 糜长涛
     * @date : 2019/7/12 11:15 
     * @Description : 死信队列绑定死信交换机
     */
    @Bean
    public Binding bindingTTLDirect(){
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with(MQEnum.MESSAGE_TTL_QUEUE.getRouteKey());
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 11:20
     * @Description : 实际消费队列绑定交换机
     */
    @Bean
    public Binding bindingExactDirect(){
        return BindingBuilder.bind(exactQueue()).to(exactExchange()).with(MQEnum.MESSAGE_QUEUE.getRouteKey());
    }



    /**
     * @author : 糜长涛
     * @date : 2019/7/12 16:30
     * @Description : plugin exchange
     */
    @Bean
    public CustomExchange pluginExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(MQEnum.MESSAGE_PLUGIN_QUEUE.getExchange(), "x-delayed-message",true, false,args);
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 16:34
     * @Description : plugin queue
     */
    @Bean
    public Queue pluginQueue(){
        return new Queue(MQEnum.MESSAGE_PLUGIN_QUEUE.getQueueName());
    }

    /**
     * @author : 糜长涛
     * @date : 2019/7/12 16:35
     * @Description : bind exchang and queue
     */
    @Bean
    public Binding bindingPluginDirect(){
        return BindingBuilder.bind(pluginQueue()).to(pluginExchange()).with(MQEnum.MESSAGE_PLUGIN_QUEUE.getRouteKey()).noargs();
    }
}
