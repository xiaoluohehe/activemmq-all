package com.mq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import sun.awt.geom.AreaOp;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {

    public static final String ACTIVE_URL ="tcp://192.168.111.136:61616";
    public static final String QUEUE_NAME ="QUEUE_NAME_01";

    public static void main(String[] args) throws JMSException, IOException {

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
        MessageConsumer producer = session.createConsumer(queue);
        //6.接收消息
       /* 方式一
       while (true){
//            TextMessage receive =(TextMessage) producer.receive();//receive 为空就会一直等(一直堵塞)
            TextMessage receive =(TextMessage) producer.receive(4000l);//receive 等4s
            if (receive!=null){
                System.out.println("接收消息"+ receive.getText());
            }else {
                break;
            }
        }*/
       //方式二    通过监听的方式

        producer.setMessageListener(message -> {
            if(message != null && message instanceof TextMessage){
                TextMessage textMessage= (TextMessage) message;
                try {
                    System.out.println("接收消息" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read(); //保证控制台不灭，连接linux的activemq需要时间，但是监听是不会等，然后就给关了


        //释放资源
        producer.close();
        session.close();
        connection.close();
    }
}
