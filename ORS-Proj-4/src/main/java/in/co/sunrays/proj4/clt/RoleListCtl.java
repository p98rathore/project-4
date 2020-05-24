/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;


import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;
// TODO: Auto-generated Javadoc
/**
 * Role List functionality Controller. Performs operation for list, search
 * operations of Role
 * 
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */
@ WebServlet(name="RoleListCtl",urlPatterns={"/ctl/RoleListCtl"})

public class RoleListCtl extends BaseCtl {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** The log. */
	private static Logger log = Logger.getLogger(RoleListCtl.class);



	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		RoleBean bean = new RoleBean();
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setId(DataUtility.getLong(request.getParameter("roleId")));

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
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 long id = DataUtility.getLong(request.getParameter("id"));
		  List list = null;
		  List next=null;
		  int pageNo = 1;
		  int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		  RoleModel model = new RoleModel();
		  RoleBean bean = new RoleBean();  
		  
			 list = model.search(bean, pageNo, pageSize);
			next = model.search(bean, pageNo + 1, pageSize);
			 
			 if(list==null || list.size()==0)
			 {
			  ServletUtility.setErrorMessage("No Records Found", request);	 
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
			 ServletUtility.setBean(bean, request);
			 
			 ServletUtility.forward(getView(), request, response);
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
		String op = request.getParameter("operation");
		RoleBean bean = (RoleBean)populateBean(request);
		String[] ids = (String[])request.getParameterValues("ids");
		
		List list = null;
		List next=null;
		
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        
        pageNo = (pageNo==0)?1:pageNo;
        pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size"))
        		:pageSize;
		
		RoleModel model = new RoleModel();
		
		if(OP_SEARCH.equalsIgnoreCase(op))
		{
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
			 ServletUtility.setErrorMessage("No Previous Record Found", request);	
			}
		}else if(OP_DELETE.equalsIgnoreCase(op))
		{
			  if(ids!=null && ids.length!=0)
				{
					System.out.println("Yes Null");
					RoleBean bean3 = (RoleBean)populateBean(request);
					  System.out.println("ids   "+ids);
					  for (String id2 : ids) 
					  {
						  System.out.println("Delete Id is  -"+id2);
						  int id1  = DataUtility.getInt(id2);
						  System.out.println("Delete Id1 is  -"+id2);
						  bean3.setId(id1);
						  try {
							RoleModel model2 = new RoleModel();
							model2.delete(bean3);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
						
				      }
					  ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
				}else
				{
					ServletUtility.setErrorMessage("Select Atleast One Record", request);
				}
		}else if(OP_NEW.equalsIgnoreCase(op))
		  {
		    ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
		    return;
		  }
		
		if(OP_RESET.equalsIgnoreCase(op))
		{
			  ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);	
			  return;
		}
		
		list = model.search(bean, pageNo, pageSize);
		next = model.search(bean, pageNo + 1, pageSize);
		
        ServletUtility.setList(list, request);
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
		
	}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_LIST_VIEW;
	}
}