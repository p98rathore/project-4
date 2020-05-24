/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;


import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA.portable.ApplicationException;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;


// TODO: Auto-generated Javadoc
/**
 * User registration functionality Controller. Performs operation for User
 * Registration
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="UserRegistrationCtl",urlPatterns={"/UserRegistrationCtl"})
public class UserRegistrationCtl extends BaseCtl{
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {

	    boolean pass = true;

	    String login = request.getParameter("login");
         String dob = request.getParameter("dob");
	  //  String dob = request.getParameter("newDate");
	    String gen = request.getParameter("gender");
	    String mobile = request.getParameter("mobile");
	    System.out.println("Date is Now "+dob);
	    
System.out.println("gen  "+request.getParameter("gender"));
	    //First Name Null Checking
	    if (DataValidator.isNull(request.getParameter("firstName"))) 
	    {
	      request.setAttribute("firstName",PropertyReader.getValue("error.require", "First Name  "));
	      pass = false;
	    }else if(!DataValidator.isName(request.getParameter("firstName")))
	    {
	      request.setAttribute("firstName","Please Enter Valid Name");
		  pass = false;	
	    }
	  //Last Name Null Checking
	    if (DataValidator.isNull(request.getParameter("lastName"))) 
	    {
	      request.setAttribute("lastName",PropertyReader.getValue("error.require", "Last Name  "));
	      pass = false;
	    }else if(!DataValidator.isName(request.getParameter("lastName")))
	    {
		  request.setAttribute("lastName","Please Enter Valid Name");
	      pass = false;	
		}
	    
	  //Login Id Null Checking
	    if (DataValidator.isNull(login)) 
	    {
	      request.setAttribute("login",PropertyReader.getValue("error.require", "Email "));
	      pass = false;
	    } //Email Regex Checking
	    else if (!DataValidator.isEmail(login)) 
	    {
	       request.setAttribute("login",PropertyReader.getValue("error.email", "Email "));
	       pass = false;
	    }
	  //Password Null Checking
	    if (DataValidator.isNull(request.getParameter("password"))) {
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password","Password must be of 8 character");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Password Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}
	  //Confirm-Password  Null Checking
	    System.out.println("pass2  "+request.getParameter("confirmPassword"));
	    if (DataValidator.isNull(request.getParameter("confirmPassword"))) 
	    {
	      request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password "));
	      pass = false;
	    }
	  //Gender Null Checking
	    if (DataValidator.isNull(gen)) 
	    {
	      request.setAttribute("gender",PropertyReader.getValue("error.require", "Gender "));
	      pass = false;
	    }
	    // Mobile No null Checking 
	    if(DataValidator.isNull(mobile))
		{
		 request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile Number "));
		 pass=false;
		 
		}else if(!DataValidator.isPhoneNo(mobile))
		{
		 request.setAttribute("mobile", "Please Enter Valid Mobile Number");
		 pass=false;	
		}
	  //Date Of Birth  Null Checking
	    if (DataValidator.isNull(dob)) 
	    {
	      request.setAttribute("dob",PropertyReader.getValue("error.require", "Date Of Birth "));
	      pass = false;
	    }else if (!DataValidator.isDate(dob)) 
	    {
	    	System.out.println("Date is Not Valid  "+dob);
	      request.setAttribute("dob","Date Of Birth is Not Valid");
	      pass = false;
	    }else if(!DataValidator.isValidAge(dob))
	    {
	    	request.setAttribute("dob","Age Should be greater then 18 Year");
	    	pass=false;
	    }
	  //Password and Confirm-Password Match Checking
	    if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
	                && !"".equals(request.getParameter("confirmPassword"))) 
	    {
	    	request.setAttribute("confirmPassword", "Confirm Password Should Be Matched");

	      pass = false;
	    
	    }

	        return pass;
  
	}
	
	/**
	 * Contain Display logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		System.out.println("registsere do get");
		ServletUtility.forward(getView(), request, response);
	}
	 
 	/* (non-Javadoc)
 	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
 	 */
 	protected BaseBean  populateBean(HttpServletRequest request) {

	        
	        UserBean bean = new UserBean();
	      

	      bean.setId(DataUtility.getLong(request.getParameter("id")));

	        bean.setRoleId(RoleBean.STUDENT);

	        bean.setFirstName(DataUtility.getString(request
	                .getParameter("firstName")));

	        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

	        bean.setLogin(DataUtility.getString(request.getParameter("login")));

	        bean.setPassword(DataUtility.getString(request.getParameter("password")));
            bean.setRoleId(RoleBean.STUDENT);
	        bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
            bean.setMobileNo(request.getParameter("mobile"));
	        bean.setGender(DataUtility.getString(request.getParameter("gender")));
	      System.out.println("**********"+request.getParameter("dob"));
	        bean.setDob(DataUtility.getDate(request.getParameter("dob")));
            populateDTO(bean, request);
	        return bean;
	    }

	/**
	 * Contain Submit logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServletException the servlet exception
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	
		System.out.println("operation "+request.getParameter("operation"));
		System.out.println("Registration DoPost");
		Date date=null;
	
		String op = request.getParameter("operation");
	
			if(op.equalsIgnoreCase("SignUp"))
			{
				UserBean bean= (UserBean) populateBean(request);
				
				long l=0;
				
				try {
					l= UserModel.registerUser(bean);
					System.out.println("l=== "+l);
					if(l>0)
					{
					  System.out.println("l>0");
					  ServletUtility.setSuccessMessage("You are Registered Successfully", request);	
					  ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);	
					}
					System.out.println("Inserted Cntlr");
				} catch (in.co.sunrays.proj4.exception.ApplicationException e) {
					ServletUtility.handleException(e, request, response);
					return;
				} catch (DuplicateRecordException e) {
					System.out.println("Catch Me  ");
					              bean=(UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Login Id is Already Exists", request);
					ServletUtility.forward(getView(), request, response);
				} catch (SQLException e) {
					ServletUtility.handleException(e, request, response);
					return;
				}
			}else if(OP_RESET.equalsIgnoreCase(op))
			{
				  ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);	
				  return;
			}
			
	}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.USER_REGISTRATION_VIEW;
	}

}
