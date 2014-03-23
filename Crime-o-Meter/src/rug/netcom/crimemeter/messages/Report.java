package rug.netcom.crimemeter.messages;

import java.io.Serializable;
import java.util.Date;

public class Report implements Serializable{

	private static final long serialVersionUID = -8081460720336176259L;
	private int id;
	private String type, message;
	private Date time;
	
	public Report(int id, String type, String message, Date time){
		this.id = id;
		this.message = message;
		this.type = type;
		this.time = time;
	}
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


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
