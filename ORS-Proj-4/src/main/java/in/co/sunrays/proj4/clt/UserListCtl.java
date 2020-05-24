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
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User
 * 
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */
@ WebServlet(name="UserListCtl",urlPatterns={"/ctl/UserListCtl"})
public class UserListCtl extends BaseCtl {
    
    /** The log. */
    private static Logger log = Logger.getLogger(UserListCtl.class);
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) 
	{
		RoleModel rmodel = new RoleModel();
		List list = null;
		try {
			list = rmodel.list();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("rolelist", list);
	}

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
        UserBean bean = new UserBean();

        bean.setFirstName(DataUtility.getString(request
                .getParameter("firstName")));

        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

        bean.setLogin(DataUtility.getString(request.getParameter("login")));
        bean.setRoleId(DataUtility.getInt(request.getParameter("rolelist")));
		

        return bean;
    }
    
    /**
     * Contain diplay logic.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   UserBean bean= (UserBean) populateBean(request);
   List list=null;
   List next=null;
   int pageNo=1;
   int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
   
   UserModel model=new UserModel();
   
  list= model.search(bean, pageNo, pageSize);
  next=model.search(bean, pageNo+1, pageSize);
  if (list == null || list.size() == 0) {
      ServletUtility.setErrorMessage("No record found ", request);
  }
 System.out.println(next.isEmpty()+"next page"); 
  if(next==null || next.size()==0)
	{
	 request.setAttribute("nextListSize", "0");	
	}else
	{
		 request.setAttribute("nextListSize", next.size());		
	}
   ServletUtility.setPageNo(pageNo, request);
   ServletUtility.setPageSize(pageSize, request);
   ServletUtility.setList(list, request);
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
	UserBean bean = (UserBean)populateBean(request);
	UserModel model = new UserModel();
	List<UserBean> list = new ArrayList<UserBean>();
	
	int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	
	pageNo = (pageNo==0)?1:pageNo;
	pageSize = (pageSize==0)?DataUtility.getInt(PropertyReader.getValue("page.size"))
			:pageSize;
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
			UserBean bean3 = (UserBean)populateBean(request);
			  System.out.println("ids   "+ids);
			  for (String id2 : ids) 
			  {
				  System.out.println("Delete Id is  -"+id2);
				  int id1  = DataUtility.getInt(id2);
				  System.out.println("Delete Id1 is  -"+id2);
				  bean3.setId(id1);
				  try {
					UserModel.delete(bean3);
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
	    ServletUtility.redirect(ORSView.USER_CTL, request, response);
	    return;
	  }
	
	if(OP_RESET.equalsIgnoreCase(op))
	{
		  ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);	
		  return;
	}
	try{
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
	ServletUtility.setBean(bean, request);
	ServletUtility.setList(list, request);
	ServletUtility.setPageNo(pageNo, request);
	ServletUtility.setPageSize(pageSize, request);
	}catch (Exception e) {
		ServletUtility.handleException(e, request, response);
		return;
	}
	ServletUtility.forward(getView(), request, response);
}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_LIST_VIEW;
	}
}

