package rug.netcom.crimemeter.server.socket;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

import rug.netcom.crimemeter.messages.Report;
import rug.netcom.crimemeter.server.database.DBConnector;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
  
public class Worker {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    
    channel.basicQos(1);
    
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
    
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      byte[] data = delivery.getBody();
      
      Report report = Report.fromByteArray(data);
      
      DBConnector dao = new DBConnector();
	  try {
		dao.addReport(report.getType(), report.getMessage(), report.getLocation());
	  } catch (Exception e1) {
		e1.printStackTrace();
	  }
	  
      System.out.println(" [x] Received '" + report + "'");
      System.out.println(" [x] Done");

      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
    }         
  }
  
  private static void doWork(String task) throws InterruptedException {
    for (char ch: task.toCharArray()) {
      if (ch == '.') Thread.sleep(1000);
    }
  }
}