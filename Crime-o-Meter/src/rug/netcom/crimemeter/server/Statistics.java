package rug.netcom.crimemeter.server;

import java.util.ArrayList;

import rug.netcom.crimemeter.messages.Report;
import rug.netcom.crimemeter.server.database.Mysqldriver;

public class Statistics implements StatisticsInterface {
	
	private ArrayList<Report> reports = new ArrayList<Report>();
    
	public Statistics(){
		
	}
	
	public boolean addReport(Report r){
		return reports.add(r);
	}
	
	public String getStatistics(){
		Mysqldriver m = new Mysqldriver();
		Report r = m.getReport(0);
		return r.toString();
	}
}
