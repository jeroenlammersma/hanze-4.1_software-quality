package hanze.nl.bussimulator;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subject = "XML_Message";

    private Session session;
    private Connection connection;

    public Producer() {
    }

    public void sendMessage(String message) {
        try {
            createConnection();
            sendTextMessage(message);
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void createConnection() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    private void sendTextMessage(String message) throws JMSException {
        Destination destination = session.createQueue(subject);
        MessageProducer producer = session.createProducer(destination);
        TextMessage msg = session.createTextMessage(message);
        producer.send(msg);
    }
}
