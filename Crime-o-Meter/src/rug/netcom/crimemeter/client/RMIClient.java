package rug.netcom.crimemeter.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import rug.netcom.crimemeter.server.StatisticsInterface;

public class RMIClient {
//asdaf
	public static void main (String[] argv) {
		  try {
			  Registry registry = LocateRegistry.getRegistry();
			  StatisticsInterface statistics = (StatisticsInterface) registry.lookup(StatisticsInterface.SERVICE_NAME);
			  String str = statistics.getStatistics();
			  System.out.println(str);
		  } catch (Exception e) {
			  System.out.println ("RMIClient exception: " + e);
			  e.printStackTrace();
		  }
		}

}

