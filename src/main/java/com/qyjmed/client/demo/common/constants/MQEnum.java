package com.qyjmed.client.demo.common.constants;

/**
 * Created by michangtao in 2019/7/12 10:35
 */
public enum MQEnum {

    //ttl队列
    MESSAGE_TTL_QUEUE("ttl.demo.exchange","ttl.demo.queueName","ttl.demo.route.key"),

    //实际消费队列
    MESSAGE_QUEUE("demo.exchange","demo.queueName","demo.route.key"),

    //plugin 队列
    MESSAGE_PLUGIN_QUEUE("plugin.demo.exchange","plugin.demo.queueName","plugin.demo.route.key");

    private String exchange;

    private String queueName;

    private String routeKey;

    MQEnum(String exchange,String queueName,String routeKey) {
        this.exchange = exchange;
        this.queueName = queueName;
        this.routeKey = routeKey;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(String routeKey) {
        this.routeKey = routeKey;
    }
}
