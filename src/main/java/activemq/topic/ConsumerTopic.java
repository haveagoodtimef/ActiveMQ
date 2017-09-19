package activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 主题模式消费者
 * @author Administrator
 *
 */
public class ConsumerTopic {
	private static final String url = "tcp://192.168.1.105:61616";
	private static final String topicName = "topic-one";
	
	public static void main(String[] args) throws JMSException {
		//1.创建连接工厂
		ConnectionFactory cf = new ActiveMQConnectionFactory(url);
		
		//2.创建连接
		Connection conn = cf.createConnection();
		
		//3.启动连接
		conn.start();
		
		//4.创建会话
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//前5步与生产者一样.
		//5,创建一个目标  , 这个目前是当前操作消息列队的名字
		Destination destination =  session.createTopic(topicName);
		
		//6,创建一个消费者
		MessageConsumer mc = session.createConsumer(destination);
		
		//7,创建一个监听器
		mc.setMessageListener(new MessageListener() {
			
			public void onMessage(Message message) {
				TextMessage tm = (TextMessage) message;
				try {
					System.out.println("接受消息:"+tm.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		//8,关闭连接 链接是异步的,再程序退出的时候才关闭.
		//conn.close();
	}
}
