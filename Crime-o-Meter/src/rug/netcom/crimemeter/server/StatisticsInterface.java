package rug.netcom.crimemeter.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StatisticsInterface extends Remote {
	public static final String SERVICE_NAME = "StatisticsEngine";
	public String getStatistics() throws RemoteException;
}
