/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.FacultyModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Faculty List functionality Controller. Performs operation for list, search
 * and delete operations of Faculty
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(FacultyListCtl.class);

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		FacultyBean bean = new FacultyBean();
        bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		return bean;
	}
	
	/**
	 * Contain Display Logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		List<FacultyBean> list = null;
		List next = null;
		FacultyBean bean= (FacultyBean) populateBean(request);
		FacultyModel model= new  FacultyModel();
		list= model.search(bean, pageNo, pageSize);
		next = model.search(bean, pageNo+1, pageSize);
		
		if(list==null){
			ServletUtility.setErrorMessage("no record found", request);
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
	
	/**
	 * Contain Submit logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String op = request.getParameter("operation");
		  String[] ids = (String[])request.getParameterValues("ids");
		  FacultyBean bean = (FacultyBean)populateBean(request);
		  List<FacultyBean> list = new ArrayList<FacultyBean>();
		  List next=null;
		  int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		  int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		  
		  pageNo = (pageNo==0)?1:pageNo;
		  pageSize =(pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		  
		  if(OP_SEARCH.equalsIgnoreCase(op))
		  {
			  pageNo=1; 
		  }else if(OP_PREVIOUS.equalsIgnoreCase(op))
		  {
			if(pageNo>1)
			{
				pageNo--;
			}else
			{
				pageNo=1;
				ServletUtility.setErrorMessage("No Previous Page Found", request);
			}
		  }else if(OP_NEXT.equalsIgnoreCase(op))
		  {
			  pageNo++;
		  }else if(OP_DELETE.equalsIgnoreCase(op))
		  {
			  if(ids!=null && ids.length!=0)
				{
					System.out.println("Yes Null");
					FacultyBean bean3 = (FacultyBean)populateBean(request);
					  System.out.println("ids   "+ids);
					  for (String id2 : ids) 
					  {
						  System.out.println("Delete Id is  -"+id2);
						  int id1  = DataUtility.getInt(id2);
						  System.out.println("Delete Id1 is  -"+id2);
						  bean3.setId(id1);
						  try {
							FacultyModel.delete(bean3);
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
		    ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
		    return;
		  }
		  
		  if(OP_RESET.equalsIgnoreCase(op))
			{
				  ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);	
				  return;
			}
		  
		  list = FacultyModel.search(bean, pageNo, pageSize);
		next = FacultyModel.search(bean, pageNo + 1, pageSize);
		  
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
		return ORSView.FACULTY_LIST_VIEW;
	}
}
