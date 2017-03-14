package ru.babanin.activemq;

/**
 * Created by makcim on 14.03.17.
 */
public class Main {
    public static void main(String[] args) {
        BoxFlag stopFlag = new BoxFlag();

        Thread threadConsumer = new Thread(new Consumer(stopFlag));
        Thread threadProducer = new Thread(new Producer(stopFlag));

        threadConsumer.start();
        threadProducer.start();
    }
}

