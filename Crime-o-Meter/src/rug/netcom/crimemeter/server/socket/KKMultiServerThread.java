package rug.netcom.crimemeter.server.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import rug.netcom.crimemeter.messages.Report;

public class KKMultiServerThread extends Thread {
	private Socket socket = null;

	public KKMultiServerThread(Socket socket) {
		super("KKMultiServerThread");
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

			out.println("what can I do for you");

			try {
				while ((message = (Report)ois.readObject()) != null) {
					System.out.println(message);
					out.println(message);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
