package rug.netcom.crimemeter.server.database;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import rug.netcom.crimemeter.messages.Graphdata;
import rug.netcom.crimemeter.messages.Report;

public class DBConnector {
	private Connection connect = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	public ArrayList<Graphdata> readCrimesPerDay(String type, Date date)
			throws Exception {
		ArrayList<Graphdata> results = new ArrayList<Graphdata>();
		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://"
					+ DBCredentials.host + DBCredentials.db,
					DBCredentials.user, DBCredentials.password);

			String tp = (type == null) ? "" : " AND type = ?";
			String tpgb = (type == null) ? "" : ", type";
			String ts = (date == null) ? "" : " AND DATE(timestamp) > ?";

			preparedStatement = connect
					.prepareStatement("SELECT count(id) AS number" + tpgb
							+ ", DATE(timestamp) AS date FROM Report WHERE 1"
							+ tp + ts + " GROUP BY date" + tpgb + " DESC");

			int StmtIndex = 1;

			if (type != null)
				preparedStatement.setString(StmtIndex++, type);
			if (date != null)
				preparedStatement.setDate(StmtIndex++, date);

			resultSet = preparedStatement.executeQuery();
			results = writeGraphData(resultSet, false); //TODO: Fix
			System.out.println(results);
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return results;

	}

	public ArrayList<Report> readDataBase(String type, String location,
			Timestamp date, int limit) throws Exception {
		ArrayList<Report> results = null;

		try {
			// this will load the MySQL driver, each DB has its own driver
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://"
					+ DBCredentials.host + DBCredentials.db,
					DBCredentials.user, DBCredentials.password);

			String loc = (location == null) ? "" : " AND location = ?";
			String tp = (type == null) ? "" : " AND type = ?";
			String ts = (date == null) ? "" : " AND timestamp > ?";

			preparedStatement = connect
					.prepareStatement("SELECT * from Report WHERE 1" + tp + loc
							+ ts + " ORDER By timestamp DESC LIMIT ?");

			int StmtIndex = 1;

			if (location != null)
				preparedStatement.setString(StmtIndex++, location);
			if (type != null)
				preparedStatement.setString(StmtIndex++, type);
			if (date != null)
				preparedStatement.setTimestamp(StmtIndex++, date);
			preparedStatement.setInt(StmtIndex, limit);

			resultSet = preparedStatement.executeQuery();
			results = writeResults(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

		return results;
	}

	public boolean addReport(String type, String location, String message) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://"
					+ DBCredentials.host + DBCredentials.db,
					DBCredentials.user, DBCredentials.password);
			preparedStatement = connect
					.prepareStatement("INSERT into Report (type, location, message) VALUES (?,?,?) ;");
			preparedStatement.setString(1, type);
			preparedStatement.setString(2, location);
			preparedStatement.setString(3, message);
			preparedStatement.executeUpdate();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql exception");
			e.printStackTrace();
		}
		return true;

	}

	public Report getReport(int id) {

		Report report = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://"
					+ DBCredentials.host + DBCredentials.db,
					DBCredentials.user, DBCredentials.password);

			preparedStatement = connect
					.prepareStatement("SELECT * from Report WHERE id=?;");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			report = writeReport(resultSet);

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("sql exception");
			e.printStackTrace();
		}

		return report;
	}

	@SuppressWarnings("unused")
	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// now get some metadata from the database
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " "
					+ resultSet.getMetaData().getColumnName(i));
		}
	}

	private Report writeReport(ResultSet resultSet) throws SQLException {
		// resultSet is initialised before the first data set
		resultSet.next();

		int id = resultSet.getInt("id");
		String type = resultSet.getString("type");
		String location = resultSet.getString("location");
		String message = resultSet.getString("message");
		Timestamp date = resultSet.getTimestamp("timestamp");
		return new Report(id, type, location, message, date);
	}

	private ArrayList<Report> writeResults(ResultSet resultSet)
			throws SQLException {
		// resultSet is initialised before the first data set
		ArrayList<Report> reports = new ArrayList<Report>();
		while (resultSet.next()) {
			// it is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g., resultSet.getSTring(2);
			int id = resultSet.getInt("id");
			String type = resultSet.getString("type");
			String message = resultSet.getString("message");
			String location = resultSet.getString("location");
			Timestamp date = resultSet.getTimestamp("timestamp");
			reports.add(new Report(id, type, location, message, date));
		}
		return reports;
	}

	private ArrayList<Graphdata> writeGraphData(ResultSet resultSet,
			boolean type) throws SQLException {
		// resultSet is initialised before the first data set
		ArrayList<Graphdata> data = new ArrayList<Graphdata>();
		while (resultSet.next()) {
			// it is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g., resultSet.getSTring(2);
			String crimetype = null;
			if (type)
				crimetype = resultSet.getString("type");
			Date date = resultSet.getDate("date");
			int number = resultSet.getInt("number");
			data.add(new Graphdata(date, crimetype, number));
		}
		return data;
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// resultSet is initialised before the first data set
		while (resultSet.next()) {
			// it is possible to get the columns via name
			// also possible to get the columns via the column number
			// which starts at 1
			// e.g., resultSet.getSTring(2);
			String id = resultSet.getString("id");
			String type = resultSet.getString("type");
			String message = resultSet.getString("message");
			Timestamp date = resultSet.getTimestamp("timestamp");
			System.out.println("ID: " + id);
			System.out.println("Type: " + type);
			System.out.println("Message: " + message);
			System.out.println("Date: " + date);
		}
	}

	// you need to close all three to make sure
	private void close() {
		// TODO: FIX THIS
		// close(resultSet);
		// close(connect);
	}

	private void close(Closeable c) {
		try {
			if (c != null) {
				c.close();
			}
		} catch (Exception e) {
			// don't throw now as it might leave following closables in
			// undefined state
		}
	}
}