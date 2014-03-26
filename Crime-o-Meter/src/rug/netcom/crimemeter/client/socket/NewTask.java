package rug.netcom.crimemeter.client.socket;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;

import rug.netcom.crimemeter.messages.Report;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class NewTask {
  
  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void main(String[] argv) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    
    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
    
    //String message = getMessage(argv);
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("What type of report do you want to send?");
	String reporttype = stdIn.readLine();
	System.out.println("What is your message?");
	String message = stdIn.readLine();

	Report report = new Report(reporttype, message);
    
    channel.basicPublish( "", TASK_QUEUE_NAME, 
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                report.toByteArray());
    System.out.println(" [x] Sent '" + report + "'");
    
    channel.close();
    connection.close();
  }
    
  
}