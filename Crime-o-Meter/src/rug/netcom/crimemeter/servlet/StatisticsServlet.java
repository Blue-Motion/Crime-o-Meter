package rug.netcom.crimemeter.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rug.netcom.crimemeter.server.StatisticsInterface;


@WebServlet("/statistics")
public class StatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StatisticsServlet() {
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
 
	protected void doGet(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		// Use "request" to read incoming HTTP headers (e.g. parameters)
		// and HTML form data (e.g. data the user entered and submitted)
		// Use "response" to specify the HTTP response line and headers
		// (e.g. specifying the content type, setting cookies).
		ArrayList<String> str = null;
		try {
			  Registry registry = LocateRegistry.getRegistry();
			  StatisticsInterface statistics = (StatisticsInterface) registry.lookup(StatisticsInterface.SERVICE_NAME);
			  str = statistics.getCrimesByLocation("Groningen");
			  System.out.println(str);
		  } catch (Exception e) {
			  System.out.println ("RMIClient exception: " + e);
		  }

		PrintWriter out = response.getWriter();
		out.println("<html><h1>Welcome to the statistics page.</h1> Statistics: </br> " + 
				" </br> <img src=\"http://localhost:8080/Crime-o-Meter/graph?days=20\"/> </html>");
	}
 }