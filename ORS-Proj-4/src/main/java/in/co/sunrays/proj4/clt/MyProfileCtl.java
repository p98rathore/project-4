/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * My Profile functionality Controller. Performs operation for update your
 * Profile
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="MyProfileCtl",urlPatterns={"/ctl/MyProfileCtl"})
public class MyProfileCtl extends BaseCtl {

    /** The Constant OP_CHANGE_MY_PASSWORD. */
    public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";
    
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    protected boolean validate(HttpServletRequest request) {

       

        boolean pass = true;

        String op = DataUtility.getString(request.getParameter("operation"));

        if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op) || op == null) {

            return pass;
        }

        if (DataValidator.isNull(request.getParameter("firstName"))) {
            System.out.println("firstName" + request.getParameter("firstName"));
            request.setAttribute("firstName",
                    PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }
        else if(!DataValidator.isName(request.getParameter("firstName")))
	    {
		  request.setAttribute("firstName","Please Enter Correct Name");
		  //ServletUtility.setErrorMessage("firstName", request);
		   pass = false;	
	    }

        if (DataValidator.isNull(request.getParameter("lastName"))) {
            request.setAttribute("lastName",
                    PropertyReader.getValue("error.require", "Last Name"));
            pass = false;
        }
        else if(!DataValidator.isName(request.getParameter("lastName")))
	    {
		  request.setAttribute("lastName","Please Enter Correct Name");
		  //ServletUtility.setErrorMessage("firstName", request);
		   pass = false;	
	    }

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender",
                    PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("mobile"))) {
            request.setAttribute("mobile",
                    PropertyReader.getValue("error.require", "MobileNo"));
            pass = false;
        }
        
        else if(!DataValidator.isPhoneLength(request.getParameter("mobile")))
		{
			 request.setAttribute("mobile", "Please Enter Valid Mobile Number");
			 pass=false;	
			}
       

        if (DataValidator.isNull(request.getParameter("dob"))) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        }
        else   if (!DataValidator.isValidAge(request.getParameter("dob"))){
        	request.setAttribute("dob","Age must be greater than 18!");
            pass = false;
        }
        
        return pass;

    }
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    protected BaseBean populateBean(HttpServletRequest request) {
 

        UserBean bean = new UserBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setLogin(DataUtility.getString(request.getParameter("login")));

        bean.setFirstName(DataUtility.getString(request
                .getParameter("firstName")));

        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

        bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));

        bean.setGender(DataUtility.getString(request.getParameter("gender")));
String s= request.getParameter("dob");
        bean.setDob(DataUtility.getDate(s));
System.out.println(bean.getDob()+  "my dob");
        populateDTO(bean, request);

        return bean;
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
		System.out.println("do get profile");
		HttpSession session= request.getSession();
		  UserBean UserBean = (UserBean) session.getAttribute("user");
	        long id = UserBean.getId();
	        System.out.println(UserBean.getId()+"do get id");
	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get model
	        UserModel model = new UserModel();
	        if (id > 0 || op != null) {
	            System.out.println("in id > 0  condition");
	            UserBean bean;
	            try {
	                bean = model.findByPK(id);
	                ServletUtility.setBean(bean, request);
	            } catch (ApplicationException e) {
	                
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
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
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
       System.out.println("profile post");
        UserBean userBean = (UserBean) session.getAttribute("user");
        System.out.println("id"+userBean.getId());
        long id = userBean.getId();
        
        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        UserModel model = new UserModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {
            UserBean bean = (UserBean) populateBean(request);
            System.out.println("dob"+request.getParameter("dob"));
            try {
                if (id > 0) {
                    userBean.setFirstName(bean.getFirstName());
                    userBean.setLastName(bean.getLastName());
                    userBean.setGender(bean.getGender());
                    userBean.setMobileNo(bean.getMobileNo());
                    userBean.setDob(bean.getDob());
                    model.update(userBean);
                    ServletUtility.setBean(userBean, request);
                    ServletUtility.setSuccessMessage(
                            "Profile has been updated Successfully. ", request);
                    ServletUtility.forward(getView(), request, response);
                }
                
            } catch (ApplicationException e) {
                
                ServletUtility.handleException(e, request, response);
                return;
            }
        } else if (OP_CHANGE_MY_PASSWORD.equalsIgnoreCase(op)) {

            ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request,
                    response);
            return;

        }

    //   ServletUtility.forward(getView(), request, response);

        
    }

 

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MY_PROFILE_VIEW;
	}

}
