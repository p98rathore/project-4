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
import org.omg.CORBA.Request;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Course List functionality Controller. Performs operation for list, search
 * operations of Course
 * 
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */
@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {
	
	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;
	
	/** The log. */
	private static Logger log = Logger.getLogger(CourseListCtl.class);
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
	
		CourseModel model= new CourseModel();
		
		try {
			List list= model.list();
			 request.setAttribute("clist", list);
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		CourseBean bean= new CourseBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("courseId")));
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
	CourseBean bean= new CourseBean();
	List<CourseBean> list= null;
	List next= null;
	int pageNo=1;
	int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		CourseModel model= new CourseModel();
		try {
			list = model.search(bean, pageNo, pageSize);
			next= model.search(bean, pageNo+1, pageSize);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
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
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 *  
	 * Contain Submit Logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long id = DataUtility.getLong(request.getParameter("id"));
		  String[] ids = (String[])request.getParameterValues("ids");
		  String op = request.getParameter("operation");
		  int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		  int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		  
		  pageNo = (pageNo==0)?1:pageNo;
		  pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
		  
		  CourseBean bean = (CourseBean)populateBean(request);
		  List<CourseBean> list = new ArrayList<CourseBean>();
		  List next=null;
		  
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
					CourseBean bean3 = (CourseBean)populateBean(request);
					  System.out.println("ids   "+ids);
					  for (String id2 : ids) 
					  {
						  System.out.println("Delete Id is  -"+id2);
						  int id1  = DataUtility.getInt(id2);
						  System.out.println("Delete Id1 is  -"+id2);
						  bean3.setId(id1);
						  try {
							CourseModel.delete(bean3);
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
		    ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
		    return;
		  }
		  
		  if(OP_RESET.equalsIgnoreCase(op))
			{
				  ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);	
				  return;
			}
		  
		  
		  try {
			list = CourseModel.search(bean, pageNo, pageSize);
			next = CourseModel.search(bean, pageNo + 1, pageSize);
		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
		}
		  
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
		  
		  ServletUtility.setBean(bean, request);
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
		return ORSView.COURSE_LIST_VIEW;
	}

}
