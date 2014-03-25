package rug.netcom.crimemeter.server.database;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import rug.netcom.crimemeter.messages.Report;

public class Mysqldriver {
  private Connection connect = null;
  private Statement statement = null;
  private PreparedStatement preparedStatement = null;
  private ResultSet resultSet = null;

  public void readDataBase() throws Exception {
    try {
      // this will load the MySQL driver, each DB has its own driver
      Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection("jdbc:mysql://" + DBCredentials.host + DBCredentials.db , DBCredentials.user, DBCredentials.password);

      //statement = connect.createStatement();
      //resultSet = statement.executeQuery("select * from Report");
      //writeResultSet(resultSet);

      preparedStatement = connect
          .prepareStatement("SELECT * from Report");
 
      resultSet = preparedStatement.executeQuery();
      writeResultSet(resultSet);

//      // remove again the insert comment
//      preparedStatement = connect
//      .prepareStatement("delete from FEEDBACK.COMMENTS where myuser= ? ; ");
//      preparedStatement.setString(1, "Test");
//      preparedStatement.executeUpdate();
//      
//      resultSet = statement
//      .executeQuery("select * from FEEDBACK.COMMENTS");
//      writeMetaData(resultSet);
//      
    } catch (Exception e) {
      throw e;
    } finally {
      close();
    }

  }
  
  public boolean addReport(String str1, String str2){
	  
	  String type = str1;
	  String message = str2;
	  
      try {
  		Class.forName("com.mysql.jdbc.Driver");
		connect = DriverManager.getConnection("jdbc:mysql://" + DBCredentials.host + DBCredentials.db , DBCredentials.user, DBCredentials.password);
		preparedStatement  = connect.prepareStatement("INSERT into Report (type, message) VALUES (?,?) ;");
		preparedStatement.setString(1, type);
		preparedStatement.setString(2, message);
		preparedStatement.executeUpdate();
      
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("sql exception");
		e.printStackTrace();
	}
	  return true;
	  
  }

  public Report getReport(int id){

	  Report report = null;
	  
	  try{
	  Class.forName("com.mysql.jdbc.Driver");
      connect = DriverManager.getConnection("jdbc:mysql://" + DBCredentials.host + DBCredentials.db , DBCredentials.user, DBCredentials.password);


      preparedStatement = connect
          .prepareStatement("SELECT * from Report WHERE id=?;");
      preparedStatement.setInt(1, id);
      resultSet = preparedStatement.executeQuery();
      report = writeReport(resultSet);

	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("sql exception");
		e.printStackTrace();
	}

	  return report;
	  
  }
  
  private void writeMetaData(ResultSet resultSet) throws SQLException {
    // now get some metadata from the database
    System.out.println("The columns in the table are: ");
    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
    }
  }

  
  private Report writeReport(ResultSet resultSet) throws SQLException {
	    // resultSet is initialised before the first data set
	    resultSet.next();
	      
	      int id = resultSet.getInt("id");
	      String type = resultSet.getString("type");
	      String message = resultSet.getString("message");
	      Date date = resultSet.getDate("timestamp");
	      return new Report(id, type, message, date);
	  }
  
  private void writeResultSet(ResultSet resultSet) throws SQLException {
    // resultSet is initialised before the first data set
    while (resultSet.next()) {
      // it is possible to get the columns via name
      // also possible to get the columns via the column number
      // which starts at 1
      // e.g., resultSet.getSTring(2);
      String user = resultSet.getString("id");
      String website = resultSet.getString("type");
      String summary = resultSet.getString("message");
      //Date date = resultSet.getDate("timestamp");
      System.out.println("User: " + user);
      System.out.println("Website: " + website);
      System.out.println("Summary: " + summary);
      //System.out.println("Date: " + date);
    }
  }

  // you need to close all three to make sure
  private void close() {
    //close(resultSet);
    //close(statement);
    //close(connect);
  }
  
  private void close(Closeable c) {
    try {
      if (c != null) {
        c.close();
      }
    } catch (Exception e) {
    // don't throw now as it might leave following closables in undefined state
    }
  }
} 