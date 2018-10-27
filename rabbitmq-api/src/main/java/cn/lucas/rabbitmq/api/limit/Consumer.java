package cn.lucas.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Consumer {

	
	public static void main(String[] args) throws Exception {
		
		
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("47.99.198.77");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		Connection connection = connectionFactory.newConnection();
		Channel channel = connection.createChannel();
		
		
		String exchangeName = "test_qos_exchange";
		String queueName = "test_qos_queue";
		String routingKey = "qos.#";
		
		channel.exchangeDeclare(exchangeName, "topic", true, false, null);
		channel.queueDeclare(queueName, true, false, false, null);
		channel.queueBind(queueName, exchangeName, routingKey);
		
		// prefetchSize 消息大小限制 0 为不限制； prefetchCount 一次最多处理多少条消息；
        // global false 在consumer 上限制 ；true 在channel 上限制
		channel.basicQos(0, 1, false);

        //1 限流方式  第一件事就是 autoAck设置为 false，否则上面 basicQos 设置的参数是无效的 。
        channel.basicConsume(queueName, false, new MyConsumer(channel));
		
		
	}
}
