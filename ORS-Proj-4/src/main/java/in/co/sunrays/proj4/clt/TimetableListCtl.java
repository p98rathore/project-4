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
import in.co.sunrays.proj4.bean.TimetableBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.model.TimetableModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Timetable List functionality Controller. Performs operation for list, search
 * and delete operations of Timetable
 * 
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */
@WebServlet(name = "TimetableListCtl", urlPatterns = { "/ctl/TimetableListCtl" })
public class TimetableListCtl extends BaseCtl {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(TimetableListCtl.class);

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void preload(HttpServletRequest request) {
		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);

		} catch (ApplicationException e) {
			log.error(e);
		}

	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		TimetableBean bean = new TimetableBean();

		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		bean.setSubId(DataUtility.getInt(request.getParameter("subjectId")));

		bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));

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
		int pageNo=1;
		int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
		List list=null;
		List next=null;
		TimetableBean bean= (TimetableBean) populateBean(request);
		
		TimetableModel model= new TimetableModel();
		try {
			list=model.search(bean, pageNo, pageSize);
			next= model.search(bean, pageNo+1, pageSize);
			if(list==null){
				ServletUtility.setErrorMessage("no record found", request);}
			if(next==null || next.size()==0)
			{
			 request.setAttribute("nextListSize", "0");	
			}else
			{
				 request.setAttribute("nextListSize", next.size());		
			}
			ServletUtility.setList(list, request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	 System.out.println("Do Post");
	  String op = request.getParameter("operation");
	  TimetableBean bean = (TimetableBean)populateBean(request);
	  List<TimetableBean> list = new ArrayList<TimetableBean>();
	  List next = new ArrayList();
	  TimetableModel model = new TimetableModel();
	  
	  String[] ids = (String[])request.getParameterValues("ids");
	  
	  int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	  int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	  pageNo = (pageNo==0)?1:pageNo;
	  pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
	  
	  if(OP_SEARCH.equalsIgnoreCase(op))
	  {
       pageNo=1;
	  }else if(OP_NEXT.equalsIgnoreCase(op))
	  {
		  pageNo++;  
	  }else if(OP_PREVIOUS.equalsIgnoreCase(op))
	  {
       pageNo--;
	  }else if(OP_DELETE.equalsIgnoreCase(op))
	  {
		  if(ids!=null && ids.length!=0)
			{
				System.out.println("Yes Null");
				TimetableBean bean3 = (TimetableBean)populateBean(request);
				  System.out.println("ids   "+ids);
				  for (String id2 : ids) 
				  {
					  System.out.println("Delete Id is  -"+id2);
					  int id1  = DataUtility.getInt(id2);
					  System.out.println("Delete Id1 is  -"+id2);
					  bean3.setId(id1);
					  try {
						TimetableModel.delete(bean3);
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
	    ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
	    return;
	  }
	  
	  if(OP_RESET.equalsIgnoreCase(op))
		{
			  ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);	
			  return;
		}
	  System.out.println("Back Operation   "+op);
	  if(OP_BACK.equalsIgnoreCase(op))
	  {
		  ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
		  return;
	  }
	  
	  try {
		list = TimetableModel.search(bean, pageNo, pageSize);
		bean = (TimetableBean)populateBean(request);
		ServletUtility.setBean(bean, request);
		next = TimetableModel.search(bean, pageNo + 1, pageSize);
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
	} catch (Exception e) {
		e.printStackTrace();
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
		return ORSView.TIMETABLE_LIST_VIEW;
	}

}
