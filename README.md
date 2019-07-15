##rabbitmq中间件实现延时消费的两种方式
*通过TTL和DLX特性实现延时消费，原理即先插入死信队列，设置到期时间，并给其绑定一个真实消费的exchange，到期后进入真实消费的队列，实现延时；
*使用rabbitmq-delayed-message-exchange插件实现延时消费


##服务器使用vmware本地虚拟机
