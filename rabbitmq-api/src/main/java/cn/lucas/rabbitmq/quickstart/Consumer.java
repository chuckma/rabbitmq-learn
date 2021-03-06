package cn.lucas.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class Consumer {

	public static void main(String[] args) throws Exception {
		
		//1 创建一个ConnectionFactory, 并进行配置
		ConnectionFactory connectionFactory = new ConnectionFactory();
		connectionFactory.setHost("47.99.198.77");
		connectionFactory.setPort(5672);
		connectionFactory.setVirtualHost("/");
		
		//2 通过连接工厂创建连接
		Connection connection = connectionFactory.newConnection();
		
		//3 通过connection创建一个Channel
		Channel channel = connection.createChannel();
		
		//4 声明（创建）一个队列
		String queueName = "test001";
        /**
         * durable 是否持久化，exclusive 是否独占(此队列只有这一个channel可以监听，目的就是为了保证顺序消费)
         * autoDelete 队列如果和其他的exchange 没有绑定关系就自动删除
         * arguments 扩展参数
         */
		channel.queueDeclare(queueName, true, false, false, null);
		
		//5 创建消费者
		QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
		
		//6 设置Channel autoAck 是否自动签收
		channel.basicConsume(queueName, true, queueingConsumer);
		while(true){
			//7 获取消息 阻塞获取，没有消息过来就阻塞在这里
			Delivery delivery = queueingConsumer.nextDelivery();
			String msg = new String(delivery.getBody());
			System.err.println("消费端: " + msg);
//			Envelope envelope = delivery.getEnvelope();
		}
		
	}
}
