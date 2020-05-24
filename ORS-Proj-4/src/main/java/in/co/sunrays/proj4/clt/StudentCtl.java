/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.StudentModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Student functionality Controller. Performs operation for add, update, delete
 * and get Student
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="StudentCtl",urlPatterns={"/ctl/StudentCtl"})
public class StudentCtl extends BaseCtl {

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	protected void preload(HttpServletRequest request) {
		System.out.println("prload");
		CollegeModel model = new CollegeModel();
		List<CollegeBean> l= new ArrayList<CollegeBean>();
		try {
		l=	model.list();
		request.setAttribute("collegeList", l);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {
		System.out.println("validate");
	
		boolean pass = true;

		String college  = request.getParameter("college");
    	String Fname  = request.getParameter("fname");
    	String Lname  = request.getParameter("lname");
    	String dob  = request.getParameter("dob");
    	String mobile  = request.getParameter("mobile");
    	String email  = request.getParameter("email");
    	System.out.println("CollegeList validate "+college);
   
    	if(DataValidator.isNull(Fname))
    	{
    		request.setAttribute("fname", "First Name Can not Be Empty");
      	  pass=false;
    	}
    	else if(!DataValidator.isName(Fname))
	    {
		  request.setAttribute("fname","Please Enter Correct Name");
		  pass = false;	
		}
    	if(DataValidator.isNull(Lname))
    	{
    		request.setAttribute("lname", "Last Name Can not Be Empty");
      	  pass=false;
    	}
    	else if(!DataValidator.isName(Lname))
	    {
		  request.setAttribute("lname","Please Enter Correct Name");
		  pass = false;	
		}
    	if(DataValidator.isNull(college))
    	{
    	  request.setAttribute("college", "College Can not Be Empty");
    	  pass=false;
    	}
    	if(DataValidator.isNull(dob))
    	{
    		System.out.println("Date is nulllll");
    		request.setAttribute("dob", "Date Of Birth Can not Be Empty");
      	  pass=false;
    	}

    	if(DataValidator.isNull(mobile))
    	{
    		request.setAttribute("mobile", "Mobile Can not Be Empty");
      	  pass=false;
    	}
    	if(!DataValidator.isPhoneNo(mobile))
    	{
    	  request.setAttribute("mobile", "Enter Valid Mobile No.");
          pass=false;	
    	}
    	if(DataValidator.isNull(email))
    	{
    		request.setAttribute("email", "Email Can not Be Empty");
      	  pass=false;
    	}else if(!DataValidator.isEmail(email))
    	{
    		request.setAttribute("email", "Enter Valid Email Id");
        	pass=false;	
    	} 	
    	System.out.println("Validate End");
    	return pass;
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		System.out.println("populatebean");

		StudentBean bean = new StudentBean();
  
		bean.setId(DataUtility.getLong(request.getParameter("id")));
   System.out.println("id in populebean"+bean.getId()+"    "+request.getParameter("id"));
		bean.setFirstName(DataUtility.getString(request.getParameter("fname")));
System.out.println(bean.getFirstName());
		bean.setLastName(DataUtility.getString(request.getParameter("lname")));
 String s= request.getParameter("dob");
 
		bean.setDob(DataUtility.getDate(s));

		//bean.setGender(DataUtility.getString(request.getParameter("gender")));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));

		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		bean.setCollegeID(DataUtility.getLong(request.getParameter("college")));
  
		//bean.setCollegeName(DataUtility.getString(request.getParameter("college")));
		
		
		populateDTO(bean, request);

		
		return bean;
	}
	
	/**
	 * Contain Diplay logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("do get");
		// get model

		StudentModel model = new StudentModel();
		if (id > 0 || op != null) {
			StudentBean bean;
			bean = model.findByPK(id);
			ServletUtility.setBean(bean, request);
		}
	
		ServletUtility.forward(getView(), request, response);
	}
	
	/**
	 * contain Submit logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("dopost");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model

		StudentModel model = new StudentModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
System.out.println("save");
			StudentBean bean = (StudentBean) populateBean(request);

			try {
				long l=model.add(bean);
				bean.setId(l);
				ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Student added succesfully", request);
				
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Student already exist", request);
				e.printStackTrace();
			} catch (Exception e) {
				ServletUtility.setBean(bean, request);
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			ServletUtility.forward(getView(), request, response);


		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			System.out.println("OP_Update");
			StudentBean bean = (StudentBean)populateBean(request);
			System.out.println("student DoPost  id is :"+bean.getId());
			System.out.println("student DoPost  Collegeid is :"+bean.getCollegeID());
System.out.println("update karo");
			
				try {
					if (id > 0) 
					{
					model.update(bean);
					}
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Student record is successfully updated", request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			
			
			ServletUtility.forward(getView(), request, response);
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			System.out.println("dlete");

			StudentBean bean = (StudentBean) populateBean(request);
			

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println("cancel");

			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;

		} else if (OP_RESET.equalsIgnoreCase(op)) {
			System.out.println("reset");
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}
		//ServletUtility.forward(getView(), request, response);


	}
	
	
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.STUDENT_VIEW;
	}
}
