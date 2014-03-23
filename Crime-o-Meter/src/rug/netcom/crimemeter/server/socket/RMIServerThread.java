package rug.netcom.crimemeter.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import rug.netcom.crimemeter.messages.Report;
import rug.netcom.crimemeter.server.Mysqldriver;

public class RMIServerThread extends Thread {
	private Socket socket = null;

	public RMIServerThread(Socket socket) {
		super("RMIServerThread");
		this.socket = socket;
	}

	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			InputStream is = socket.getInputStream();  
			ObjectInputStream ois = new ObjectInputStream(is);  
			Report message;  
			
			String inputLine, outputLine;

		      System.out.println("start");
		        
		        Mysqldriver dao = new Mysqldriver();
		        try {
					dao.readDataBase();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			
			out.println("what can I do for you");

			try {
				while ((message = (Report)ois.readObject()) != null) {
					System.out.println(message);
					
					out.println(message);
				}
			} catch (ClassNotFoundException e) {
				out.println("I expected a report. This is not a valid report!");
				System.out.println("wrong type");
				e.printStackTrace();
			}
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
