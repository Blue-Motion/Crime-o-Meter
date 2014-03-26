package rug.netcom.crimemeter.messages;

import java.io.Serializable;
import java.sql.Timestamp;

public class Report implements Serializable{

	private static final long serialVersionUID = -8081460720336176259L;
	private int id;
	private String type, message;
	private Timestamp time;
	
	public Report(int id, String type, String message, Timestamp time){
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

	public Report(String type, String message){
		this.type = type;
		this.message = message;
		
	}

	
	public String toString(){
		return time + ":" + type+ "." +message;
	}
	
}
