package rug.netcom.crimemeter.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;

import rug.netcom.crimemeter.messages.Graphdata;

public interface StatisticsInterface extends Remote {
	public static final String SERVICE_NAME = "StatisticsEngine";
	public String getLastCrime() throws RemoteException;
	public ArrayList<String> getCrimes(String type, String location, Timestamp date) throws RemoteException;
	public ArrayList<String> getCrimesByLocation(String location) throws RemoteException;
	public ArrayList<String> getCrimesByType(String type) throws RemoteException;
	public ArrayList<Graphdata> getCrimesByDate(int days) throws RemoteException;
}
