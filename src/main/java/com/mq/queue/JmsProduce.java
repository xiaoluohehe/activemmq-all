package java.com.mq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {

    public static final String ACTIVE_URL ="tcp://192.168.111.136:61616";
    public static final String QUEUE_NAME ="QUEUE_NAME_01";

    public static void main(String[] args) throws JMSException {

        //1.创建连接工场
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVE_URL);
        //2.获得连接工场,并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
//        3.创建会话,事物，签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地
        Queue queue = session.createQueue(QUEUE_NAME);

        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        //6.发送消息
        TextMessage aaaa = session.createTextMessage("aaaa");
        producer.send(aaaa);
        //释放资源
        producer.close();
        session.close();
        connection.close();

    }


}
