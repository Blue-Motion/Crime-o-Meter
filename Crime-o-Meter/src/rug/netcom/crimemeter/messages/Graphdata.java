package rug.netcom.crimemeter.messages;

import java.io.Serializable;
import java.sql.Date;

public class Graphdata implements Comparable<Graphdata>, Serializable {
	private static final long serialVersionUID = 1L;
	public final Date DATE;
	public final String TYPE;
	public final int NUMBER;
	
	public Graphdata (Date d, String t, int n){
		DATE = d;
		TYPE = t;
		NUMBER = n;
	}

	public Graphdata (Date d, int n){
		DATE = d;
		TYPE = null;
		NUMBER = n;
	}

	@Override
	public int compareTo(Graphdata o) {
		// TODO Auto-generated method stub
		return NUMBER - o.NUMBER;
	}
	
	@Override
	public String toString(){
		return DATE + " : " + TYPE + " : " + NUMBER; 
	}
	
}
