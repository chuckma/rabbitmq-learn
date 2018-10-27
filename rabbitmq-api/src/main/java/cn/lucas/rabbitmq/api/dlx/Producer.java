package cn.lucas.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 死信队列演示 Dead Letter Exchange
 * 消息没有被消费，就成了死信
 *
 * 变成死信的情况
 *      1. 消息被拒绝 basic.reject,basic.nack，requeue = false
 *      2. TTL 过期
 *      3. 队列达到最大长度
 */
public class Producer {

	
	public static void main(String[] args) throws Exception {
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("47.99.198.77");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		String exchange = "test_dlx_exchange";
		String routingKey = "dlx.save";
		
		String msg = "Hello RabbitMQ DLX Message";
		
		for(int i =0; i<1; i ++){
			
			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.deliveryMode(2)
					.contentEncoding("UTF-8")
					.expiration("10000")
					.build();
			channel.basicPublish(exchange, routingKey, true, properties, msg.getBytes());
		}
		
	}
}
