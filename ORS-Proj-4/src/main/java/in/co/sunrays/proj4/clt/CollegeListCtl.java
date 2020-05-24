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

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * College List functionality Controller. Performs operation for list, search
 * and delete operations of College
 * 
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */
@ WebServlet(name="CollegeListCtl",urlPatterns={"/ctl/CollegeListCtl"})
public class CollegeListCtl extends BaseCtl {

    /** The log. */
    private static Logger log = Logger.getLogger(CollegeListCtl.class);
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
    @Override
	protected void preload(HttpServletRequest request) {
		List l = null;
		try {
			l = CourseModel.list();
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		request.setAttribute("CoutLi", l);
	}
	

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        CollegeBean bean = new CollegeBean();

        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setCity(DataUtility.getString(request.getParameter("city")));
        populateDTO(bean, request);

        return bean;
    }
    
    /**
     * Contains display logic.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	

 		int pageNo=1;
 		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
 		
 		CollegeBean bean = (CollegeBean)populateBean(request);
 		CollegeModel model = new CollegeModel();
 		
 		List l=null;
 		List next=null;
 		
 		try {
 			l = model.search(bean, pageNo, pageSize);
 			next = model.search(bean, pageNo + 1, pageSize);
 		} catch (ApplicationException e) {
 			e.printStackTrace();
 			return;
 		}

 		if(l==null || l.size()==0)
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
 		
 		ServletUtility.setList(l, request);
 		ServletUtility.setPageNo(pageNo, request);
 		ServletUtility.setPageSize(pageSize, request);
 		ServletUtility.forward(getView(), request, response);
 	
    }
     
     /**
      * Contains display logic.
      *
      * @param request the request
      * @param response the response
      * @throws ServletException the servlet exception
      * @throws IOException Signals that an I/O exception has occurred.
      */
     protected void doPost(HttpServletRequest request,
             HttpServletResponse response) throws ServletException, IOException {

    	 List list = null;
 		List next = null;
 		String[] ids = (String[])request.getParameterValues("ids");
 		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
 		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
 		
 		pageNo = (pageNo==0)?1:pageNo;
 		pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size")):pageSize;
 		
 		CollegeBean bean = (CollegeBean)populateBean(request);
 		bean.setName(request.getParameter("name"));
 		bean.setCity(request.getParameter("city"));
 		
 		String  op = DataUtility.getString(request.getParameter("operation"));
 		CollegeModel model = new CollegeModel();
 		
 		if(OP_SEARCH.equalsIgnoreCase(op))
 		{
 			pageNo=1;
 		}else if(OP_PREVIOUS.equalsIgnoreCase(op))
 		{
 			System.out.println("pageNo :-" +pageNo);
 			if(pageNo>1)
 			{
 				pageNo--;
 			}else {
 			 pageNo=1;
 			 ServletUtility.setErrorMessage("No Previous Page Available", request);
 			}
 		}else if(OP_NEXT.equalsIgnoreCase(op))
 		{
 			pageNo++;	
 		}else if(OP_DELETE.equalsIgnoreCase(op))
 		{
 			if(ids!=null && ids.length!=0)
 			{
 				System.out.println("Yes Null");
 				CollegeBean bean3 = (CollegeBean)populateBean(request);
 				  System.out.println("ids   "+ids);
 				  for (String id2 : ids) 
 				  {
 					  System.out.println("Delete Id is  -"+id2);
 					  int id1  = DataUtility.getInt(id2);
 					  System.out.println("Delete Id1 is  -"+id2);
 					  bean3.setId(id1);
 					  try {
 						CollegeModel.delete(bean3);
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
 		    ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
 		    return;
 		}
 		if(OP_RESET.equalsIgnoreCase(op))
 		{
 			  ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);	
 			  return;
 		}
 		
 		try {
 			System.out.println("list nameee"+bean.getName());
 			list = model.search(bean, pageNo, pageSize);
 			next = model.search(bean, pageNo + 1, pageSize);
// 			ServletUtility.setList(list, request);
 			if(list==null || list.size()==0 && !OP_DELETE.equalsIgnoreCase(op))
			{
				System.out.println("doPost list is null");
		     ServletUtility.setErrorMessage("No Record Found", request);		
			}
 	        ServletUtility.setList(list, request);

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
 	        ServletUtility.forward(getView(), request, response);
 	        
 		} catch (ApplicationException e) {
 			e.printStackTrace();
 		}
 		
         
     }
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.COLLEGE_LIST_VIEW;
	}

}