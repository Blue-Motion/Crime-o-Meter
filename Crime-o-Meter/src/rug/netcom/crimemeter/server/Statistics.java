package rug.netcom.crimemeter.server;

import java.util.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import rug.netcom.crimemeter.messages.Graphdata;
import rug.netcom.crimemeter.messages.Report;
import rug.netcom.crimemeter.server.database.DBConnector;

public class Statistics implements StatisticsInterface {
	private DBConnector m = new DBConnector();

	public String getLastCrime() {
		ArrayList<Report> r = null;
		try {
			r = m.readDataBase(null, null, null, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return r.get(0).toString();
	}

	public ArrayList<String> getCrimes(String type, String location, Timestamp date) {
		ArrayList<Report> r = null;
		ArrayList<String> result = new ArrayList<String>();
		try {
			r = m.readDataBase(type, location, date, 10);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Report a : r) {
			result.add(a.toString());
		}

		return result;
	}

	public ArrayList<String> getCrimesByLocation(String location) {
		return getCrimes(null, location, null);
	}

	public ArrayList<String> getCrimesByType(String type) {
		return getCrimes(type, null, null);
	}
	
	public ArrayList<Graphdata> getCrimesByDate(int days){
		Date date = new Date();
		ArrayList<Graphdata> results = null;
		Date ts = new Date(date.getTime() - days*1000*60*60*24);
		try {
			results = m.readCrimesPerDay(null, new java.sql.Date(ts.getTime()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}
}
