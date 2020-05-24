/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Welcome functionality Controller. Performs operation for Show Welcome page
 * 
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */

	@ WebServlet(name="WelcomeCtl",urlPatterns={"/WelcomeCtl"})
	public class WelcomeCtl extends HttpServlet {

	    /** The Constant serialVersionUID. */
    	private static final long serialVersionUID = 1L;

	    /** The log. */
    	private static Logger log = Logger.getLogger(WelcomeCtl.class);

	    /**
    	 * Contain Display logic.
    	 *
    	 * @param request the request
    	 * @param response the response
    	 * @throws ServletException the servlet exception
    	 * @throws IOException Signals that an I/O exception has occurred.
    	 */
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        log.debug("WelcomeCtl Method doGet Started");
            System.out.println("welcomrctl");
	        ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);

	        log.debug("WelcomeCtl Method doGet Ended");
	    }

	    
	   
	}
