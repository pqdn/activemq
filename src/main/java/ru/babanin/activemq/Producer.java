package ru.babanin.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;


import javax.jms.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Producer implements Runnable{
    BoxFlag stopFlag;

    public Producer(BoxFlag stopFlag) {
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
            Topic myChat = session.createTopic("myChat");

            MessageProducer producer = session.createProducer(myChat);
            //MessageConsumer consumer = session.createDurableSubscriber(topic, "SUB1234");

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            while (!stopFlag.isFlag()) {
                String line = reader.readLine();
                TextMessage message = session.createTextMessage(line);
                producer.send(message);
            }

            session.close();
            connection.close();
        }  catch (Exception e) {
            stopFlag.setFlag();
        }
    }
}

