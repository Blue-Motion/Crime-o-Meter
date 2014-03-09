package rug.netcom.crimemeter.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

	public static void main (String[] argv) {
		//System.setSecurityManager (new RMISecurityManager());
		try {
			StatisticsInterface statistics = new Statistics();
			StatisticsInterface statisticsStub = (StatisticsInterface) UnicastRemoteObject.exportObject(statistics,0);
		    Registry registry = LocateRegistry.getRegistry();
		    registry.rebind(StatisticsInterface.SERVICE_NAME, statisticsStub);
		    System.out.println ("Report Server is ready.");
		} catch (Exception e) {
		    System.out.println ("Report Server failed: " + e);
		    e.printStackTrace();
		}
	}
}


