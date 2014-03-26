package rug.netcom.crimemeter.client.socket;
import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Date;

import rug.netcom.crimemeter.messages.Report;

public class TCPClient {
	public static void main(String[] args) throws IOException {
		Socket kkSocket = null;
		ObjectOutputStream out = null;
		BufferedReader in = null;
		try {
			kkSocket = new Socket("localhost", 4444);
			//out = new PrintWriter(kkSocket.getOutputStream(), true);
			OutputStream os = kkSocket.getOutputStream();  
			out = new ObjectOutputStream(os);
			
			in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: localhost.");
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromServer;

		while ((fromServer = in.readLine()) != null) {
			System.out.println("Server: " + fromServer);
			if (fromServer.equals("Bye."))
				break;
			System.out.println("What type of report do you want to send?");
			String reporttype = stdIn.readLine();
			System.out.println("What is your message?");
			String message = stdIn.readLine();

			Report report = new Report(reporttype, message);
			
			if (report != null) {
				out.writeObject(report);
			}
		}

		out.close();
		in.close();
		stdIn.close();
		kkSocket.close();
	}
}
