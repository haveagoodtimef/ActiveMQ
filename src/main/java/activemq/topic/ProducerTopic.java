package activemq.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 主题模式生产者
 * @author Administrator
 *
 */
public class ProducerTopic {
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
		
		//5,创建一个目标
		Destination destination =  session.createTopic(topicName);
		
		//6,创建一个生产者
		MessageProducer mp = session.createProducer(destination);
		
		for (int i = 0; i < 100; i++) {
			//7.创建消息
			TextMessage tm = session.createTextMessage("topic"+i);
			
			//8.发布消息
			mp.send(tm);
			System.out.println("send:"+tm.getText());
		}
		
		//9,关闭连接
		conn.close();
	}
	
}
