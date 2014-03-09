package rug.netcom.crimemeter.messages;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable{

	private String type, message;
	private Date timestamp;
	
	public Report(String type, Date timestamp, String message){
	
		this.timestamp = timestamp;
		this.type = type;
		this.message = message;
		
	}

	public String toString(){
		return timestamp + ":" + type+ "." +message;
	}
	
}
