package rug.netcom.crimemeter.server;

import rug.netcom.crimemeter.messages.Report;
import rug.netcom.crimemeter.server.database.Mysqldriver;

public class Statistics implements StatisticsInterface {
	
	public Statistics(){
		
	}
	
	
	public String getStatistics(){
		Mysqldriver m = new Mysqldriver();
		Report r = m.getReport(1);
		return r.toString();
	}
}
