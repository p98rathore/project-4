/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;


// TODO: Auto-generated Javadoc
/**
 * Servlet implementation class MarksheetlistCtl.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="MarksheetListCtl",urlPatterns={"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {

    /** The log. */
    private static Logger log = Logger.getLogger(MarksheetListCtl.class);

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        MarksheetBean bean = new MarksheetBean();

        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        bean.setName(DataUtility.getString(request.getParameter("name")));

        return bean;
    }
    
    /**
     * Contains Display logic.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	int pageNo=1;
    	int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
    	List<MarksheetBean> list= null;
    	List next=null;
		
    	MarksheetBean bean= new MarksheetBean();
    	MarksheetModel model=new MarksheetModel();
    	try {
			list=model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			if(list==null || list.size()==0)
			{
				
			 ServletUtility.setErrorMessage("No Record Found", request);	
			}
			if(next==null || next.size()==0)
			{
			 request.setAttribute("nextListSize", "0");	
			}else
			{
				 request.setAttribute("nextListSize", next.size());		
			}
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.setList(list, request);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	ServletUtility.forward(getView(), request, response);
    	
    }
    
    /**
     * Contains Submit logic.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("doPost");
		String[] ids = (String[])request.getParameterValues("ids");
		String op = request.getParameter("operation");
		MarksheetBean bean = (MarksheetBean)populateBean(request);
		MarksheetModel model = new MarksheetModel();
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		pageNo = (pageNo==0)?1:pageNo;
		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		
		
		List<MarksheetBean> list = new ArrayList<MarksheetBean>();
		List next=null;
		
		if(OP_SEARCH.equalsIgnoreCase(op))
		{
			System.out.println("doPost search");
			pageNo=1;
		}else if(OP_NEXT.equalsIgnoreCase(op))
		{
			pageNo++;
		}else if(OP_PREVIOUS.equalsIgnoreCase(op))
		{
			if(pageNo>1)
			{
				pageNo--;
			}else
			{
				pageNo=1;
			ServletUtility.setErrorMessage("No Previous Page Available", request);	
			}
		}else if(OP_DELETE.equalsIgnoreCase(op))
		  {
			if(ids!=null && ids.length!=0)
			{
				System.out.println("Yes Null");
				MarksheetBean bean3 = (MarksheetBean)populateBean(request);
				  System.out.println("ids   "+ids);
				  for (String id2 : ids) 
				  {
					  System.out.println("Delete Id is  -"+id2);
					  int id1  = DataUtility.getInt(id2);
					  System.out.println("Delete Id1 is  -"+id2);
					  bean3.setId(id1);
					  try {
						MarksheetModel.Delete(bean3);
						ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
					
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
			      }
				  //ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
			}else
			{
				ServletUtility.setErrorMessage("Select Atleast One Record", request);
			}
		  }else if(OP_NEW.equalsIgnoreCase(op))
		  {
		    ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
		    return;
		  }
		
		if(OP_RESET.equalsIgnoreCase(op))
		{
			  ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);	
			  return;
		}
		
		try {
			list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op))
			{
				System.out.println("doPost list is null");
		     ServletUtility.setErrorMessage("No Record Found", request);		
			}
			if(next==null || next.size()==0)
			{
			 request.setAttribute("nextListSize", "0");	
			}else
			{
				 request.setAttribute("nextListSize", next.size());		
			}
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
