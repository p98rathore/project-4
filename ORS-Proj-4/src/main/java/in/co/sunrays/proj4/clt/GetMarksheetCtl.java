/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Get Marksheet functionality Controller. Performs operation for Get Marksheet

 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="GetMarksheetCtl",urlPatterns={"/ctl/GetMarksheetCtl"})
public class GetMarksheetCtl extends BaseCtl {

    /** The log. */
    private static Logger log = Logger.getLogger(GetMarksheetCtl.class);
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {
    	boolean pass= true;
    	
    	if(DataValidator.isNull(request.getParameter("rollNo"))){
    		request.setAttribute("rollNo", "Roll No is required");
    		pass= false;
    	}
    	
    	/*else if(!DataValidator.isRollNo(request.getParameter("rollNo"))){
    		request.setAttribute("rollNo", "Roll no format not matched");
    		pass= false;
    	}*/
    	return pass;
    }
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    protected BaseBean populateBean(HttpServletRequest request) {

		MarksheetBean bean = new MarksheetBean();

		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

		return bean;
	}

/**
 * Contain Display logic.
 *
 * @param request the request
 * @param response the response
 * @throws ServletException the servlet exception
 * @throws IOException Signals that an I/O exception has occurred.
 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Marksheet  doGet");
		ServletUtility.forward(ORSView.GET_MARKSHEET_VIEW, request, response);
	}
	
	/**
	 * Contain Submit logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doPost");

		String op = request.getParameter("operation");
		String roll = request.getParameter("rollNo");
		MarksheetModel mm = new MarksheetModel();
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		// MarkSheetBean bean = new MarkSheetBean();
		boolean vali = false;

		if (OP_GO.equalsIgnoreCase(op)) 
		{
			System.out.println("Go");
			
				try {
					bean = MarksheetModel.findByRollNo(roll);
					if (bean != null) 
					{
						System.out.println("not null bean");
						ServletUtility.setBean(bean, request);						
						ServletUtility.forward(getView(), request, response);
					} else {
						System.out.println("bean  null");
						
						ServletUtility.setErrorMessage("RollNo Does Not exists", request);
						ServletUtility.forward(ORSView.GET_MARKSHEET_VIEW, request, response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} 
		} 

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.GET_MARKSHEET_VIEW;
	}


	

}

