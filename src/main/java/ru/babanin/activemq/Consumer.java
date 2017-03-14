package ru.babanin.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer implements Runnable{
    BoxFlag stopFlag;

    public Consumer(BoxFlag stopFlag) {
        this.stopFlag = stopFlag;
    }

    @Override
    public void run() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        try {
            Connection connection = factory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            Queue dest = session.createQueue("Dest");
            Topic topic = session.createTopic("myChat");
            MessageConsumer consumer = session.createConsumer(topic);

            Message message;

            while (!stopFlag.isFlag()) {
                message = consumer.receive();
                System.out.println(((TextMessage) message).getText());
            }

            session.close();
            connection.close();
        }  catch (Exception e) {
            stopFlag.setFlag();
        }
    }
}
