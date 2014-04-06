package rug.netcom.crimemeter.server;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import rug.netcom.crimemeter.server.database.DBConnector;

public class RMIServer {

	public static void main (String[] argv) {

		
		DBConnector m = new DBConnector();
		try {
			m.readDataBase(null, 10);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
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


