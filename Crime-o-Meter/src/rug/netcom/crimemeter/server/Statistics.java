package rug.netcom.crimemeter.server;

import java.util.ArrayList;

import rug.netcom.crimemeter.messages.Report;

public class Statistics implements StatisticsInterface {
	
	private ArrayList<Report> reports = new ArrayList<Report>();
    
	public Statistics(){
		
	}
	
	public boolean addReport(Report r){
		return reports.add(r);
	}
	
	public String getStatistics(){
		return "No bicycles stolen this week";
	}
}
