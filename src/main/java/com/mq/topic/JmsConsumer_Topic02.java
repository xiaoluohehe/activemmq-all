package com.mq.topic;


import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsConsumer_Topic02 {
    public static final String ACTIVE_URL ="tcp://192.168.111.136:61616";
    public static final String QUEUE_NAME ="Topic_NAME_01";
    public static void main(String[] args) throws JMSException {
        //1.创建连接工场
        ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(ACTIVE_URL);
        //2.获得连接工场,并启动
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.setClientID("z3");
//        3.创建会话,事物，签收
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地
        Topic queue =  session.createTopic(QUEUE_NAME);
        //持久化的订阅
        TopicSubscriber remke = session.createDurableSubscriber(queue, "remke");
        //发布订阅
        connection.start();
        Message message = remke.receive();//一直等,消息
//产生持久化的topic
        while (null !=message){
            TextMessage textMessage = (TextMessage) message;
            System.out.println("收到持久化的topic" + textMessage);
            message = remke.receive(5000l);
        }
        //释放资源
        session.close();
        connection.close();

    }
}
