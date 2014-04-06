package rug.netcom.crimemeter.server;

import rug.netcom.crimemeter.messages.Report;
import rug.netcom.crimemeter.server.database.DBConnector;

public class Statistics implements StatisticsInterface {
	
	public Statistics(){
		
	}
	
	
	public String getStatistics(){
		DBConnector m = new DBConnector();
		Report r = m.getReport(1);
		return r.toString();
	}
}
