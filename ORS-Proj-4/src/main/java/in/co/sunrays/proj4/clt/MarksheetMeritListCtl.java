/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Marksheet Merit List functionality Controller. Performance operation of
 * Marksheet Merit List
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="MarksheetMeritListCtl",urlPatterns={"/ctl/MarksheetMeritListCtl"})
public class MarksheetMeritListCtl extends BaseCtl {

    
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

     System.out.println("merit get");
     int pageNo = 1;

     int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

     MarksheetBean bean =new MarksheetBean();
     MarksheetModel  model= new MarksheetModel();
     List<MarksheetBean> list=null;
     try {
	 list =	model.getMeritList(pageNo, pageSize);
	 request.setAttribute("result", list);
	
	 
	 if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record Found", request);
		}

		request.setAttribute("list", list);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
	} catch (Exception e) {
		e.printStackTrace();
	}
	ServletUtility.forward(getView(), request, response);
}
    		 
    
    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("do Pooost");
		String op = request.getParameter("op");

		if (OP_BACK.equalsIgnoreCase(op)) {
			ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
		}
	}

       
    

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.MARKSHEET_MERIT_LIST_VIEW;
    }
}
