package rug.netcom.crimemeter.messages;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;

public class Report implements Serializable {

	private static final long serialVersionUID = -8081460720336176259L;
	private int id;
	private String type, message, location;
	private Timestamp time;

	public Report(int id, String type, String message, Timestamp time) {
		this.id = id;
		this.message = message;
		this.type = type;
		this.time = time;
		this.location = location;
	}

	public static Report fromByteArray(byte[] data) {
		Report report = null;
		
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream is = new ObjectInputStream(in);
			report = (Report) is.readObject();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return report;
	}

	public byte[] toByteArray() {
		ByteArrayOutputStream out = null;
		try {
			out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
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
	
	public String getLocation(){
		return location;
	}
	
	public void setLocation(String location){
		this.location = location;
	}

	public Report(String type, String message) {
		this.type = type;
		this.message = message;

	}

	public String toString() {
		return time + ":" + type + "." + message;
	}

}
