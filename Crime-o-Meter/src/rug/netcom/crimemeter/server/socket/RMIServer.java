package rug.netcom.crimemeter.server.socket;
import java.net.*;
import java.io.*;

import rug.netcom.crimemeter.server.Mysqldriver;

public class RMIServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        
  
        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }

        while (listening) new RMIServerThread(serverSocket.accept()).start();

        serverSocket.close();
    }
}