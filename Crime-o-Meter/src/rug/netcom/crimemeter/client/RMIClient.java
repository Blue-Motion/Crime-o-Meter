package rug.netcom.crimemeter.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import rug.netcom.crimemeter.server.StatisticsInterface;

public class RMIClient {
	public static void main(String[] argv) {
		ArrayList<String> str = null;
		try {
			Registry registry = LocateRegistry.getRegistry();
			StatisticsInterface statistics = (StatisticsInterface) registry.lookup(StatisticsInterface.SERVICE_NAME);
			str = statistics.getCrimesByLocation("Groningen");
			System.out.println(str);
		} catch (Exception e) {
			System.out.println("RMIClient exception: " + e);
			e.printStackTrace();
		}
	}

}
