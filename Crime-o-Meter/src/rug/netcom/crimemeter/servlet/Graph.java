package rug.netcom.crimemeter.servlet;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import rug.netcom.crimemeter.messages.Graphdata;
import rug.netcom.crimemeter.server.StatisticsInterface;

/**
 * source: http://www.java2s.com/Code/Java/Servlets/ServlettodrawaGraphicalChartinresponsetoauserrequest.htm
 * Servlet to draw a Graphical Chart in response to a user request
 */
@WebServlet("/graph")
public class Graph extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int W = 300, H = 200;

	/** Draw a Graphical Chart in response to a user request */
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException {
		ArrayList<Graphdata> results = null;
		Calendar cal = Calendar.getInstance();
		Date d = cal.getTime();
		String days = request.getParameter("days");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
		int ndays = (days != null) ? Integer.parseInt(days) : 5;

		try {
			  Registry registry = LocateRegistry.getRegistry();
			  StatisticsInterface statistics = (StatisticsInterface) registry.lookup(StatisticsInterface.SERVICE_NAME);
			  results = statistics.getCrimesByDate(ndays);
		  } catch (Exception e) {
			  System.out.println ("RMIClient exception: " + e);
		  }
		
		int maxnumber = Collections.max(results).NUMBER;
		W = 20 + 10* ndays;
        H = 30 + 20 * maxnumber;
        
		response.setContentType("image/jpeg");

		// Create an Image
		BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);

		// Get the Image's Graphics, and draw.
		Graphics2D g = img.createGraphics();

		// In real life this would call some charting software...
		g.setColor(Color.white);
		g.fillRect(0, 0, W, H);
		g.setColor(Color.black);
		for(int i=0;i<=maxnumber;i++) g.drawString(Integer.toString(i), 10, H - i*20);
		g.setColor(Color.red);
		int j=0;
		for(int i=ndays-1;i>=0;i--){
			int n = results.get(j).NUMBER;
			if(sdf.format(results.get(j).DATE).equals(sdf.format(new java.sql.Date(d.getTime() - i*1000*60*60*24)))){
				g.fillRect(10+10*(ndays-i), H-20*n-10, 6, 20*n+10);
				j++;
				if(j >= results.size()) break;
			}
			
		}
			
		// Write the output
		OutputStream os = response.getOutputStream();
		ImageOutputStream ios = ImageIO.createImageOutputStream(os);

		if (!ImageIO.write(img, "jpeg", ios)) {
			log("Boo hoo, failed to write JPEG");
		}
		ios.close();
		os.close();
	}
}