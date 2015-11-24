package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import util.TokenThread;

public class InitServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1417022156477133421L;
	
	 public void init() throws ServletException {
	        
	        
	      new Thread(new TokenThread()).start();
	        
	 }

}
