/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;


// TODO: Auto-generated Javadoc
/**
 * * User functionality Controller. Performs operation for add, update and get
 * User
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="UserCtl",urlPatterns={"/ctl/UserCtl"})
public class UserCtl extends BaseCtl {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The log. */
    private static Logger log = Logger.getLogger(UserCtl.class);

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void preload(HttpServletRequest request) {
    	System.out.println("user prload");
        RoleModel model = new RoleModel();
        List l = model.list();
		request.setAttribute("roleList", l);

    }
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    protected boolean validate(HttpServletRequest request) {

        log.debug("UserCtl Method validate Started");

        boolean pass = true;

        String login = request.getParameter("login");
        String dob = request.getParameter("dob");
               
               if (DataValidator.isNull(request.getParameter("mobile"))) {
                   request.setAttribute("mobile",
                           PropertyReader.getValue("error.require", "Mobile No"));
                   pass = false;
               }    
               else if(!DataValidator.isPhoneNo(request.getParameter("mobile")))
       		{
       		 request.setAttribute("mobile", "Please Enter Valid Mobile Number");
       		 pass=false;	
       		}
        if (DataValidator.isNull(request.getParameter("firstName"))) {
            request.setAttribute("firstName",
                    PropertyReader.getValue("error.require", "First Name"));
            pass = false;
        }
        else if(!DataValidator.isName(request.getParameter("firstName")))
	    {
		  request.setAttribute("firstname","Please Enter Correct Name");
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
		  request.setAttribute("firstname","Please Enter Correct Name");
	      //ServletUtility.setErrorMessage("firstName", request);
		  pass = false;	
		}

        if (DataValidator.isNull(login)) {
            request.setAttribute("login",
                    PropertyReader.getValue("error.require", "Login Id"));
            pass = false;
        } else if (!DataValidator.isEmail(login)) {
            request.setAttribute("login",
                    PropertyReader.getValue("error.email", "Login "));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("password"))) {
            request.setAttribute("password",
                    PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }
        else if(!DataValidator.isPassword(request.getParameter("password")))
	    {
		  request.setAttribute("password","Please Enter Correct Password");
		  //ServletUtility.setErrorMessage("password", request);
		  pass = false; 	
		}

        if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
            request.setAttribute("confirmPassword", PropertyReader.getValue(
                    "error.require", "Confirm Password"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("gender"))) {
            request.setAttribute("gender",
                    PropertyReader.getValue("error.require", "Gender"));
            pass = false;
        }
        if (DataValidator.isNull(dob)) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.require", "Date Of Birth"));
            pass = false;
        } else if (!DataValidator.isDate(dob)) {
            request.setAttribute("dob",
                    PropertyReader.getValue("error.date", "Date Of Birth"));
            pass = false;
        }
        else if(!DataValidator.isValidAge(dob))
	    {
	    	 request.setAttribute("dob","Age Must be greater then 18 year");
			  pass=false;
	    }
        if (!request.getParameter("password").equals(
                request.getParameter("confirmPassword"))
                && !"".equals(request.getParameter("confirmPassword"))) {
            ServletUtility.setErrorMessage(
                    "Confirm  Password  should  be matched.", request);
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("roleId"))) {
            request.setAttribute("roleId",
                    PropertyReader.getValue("error.require", "Role "));
            pass = false;
        } 

        log.debug("UserCtl Method validate Ended");

        return pass;
    }
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {
    	 log.debug("UserCtl Method populatebean Started");

         UserBean bean = new UserBean();

         bean.setId(DataUtility.getLong(request.getParameter("id")));
                bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));

         bean.setFirstName(DataUtility.getString(request
                 .getParameter("firstName")));
          bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));
         bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

         bean.setLogin(DataUtility.getString(request.getParameter("login")));

         bean.setPassword(DataUtility.getString(request.getParameter("password")));

         bean.setConfirmPassword(DataUtility.getString(request
                 .getParameter("confirmPassword")));

         bean.setGender(DataUtility.getString(request.getParameter("gender")));
         String dob= request.getParameter("dob");
         
         bean.setDob(DataUtility.getDate(dob));

         populateDTO(bean, request);

         log.debug("UserCtl Method populatebean Ended");

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
        String op = DataUtility.getString(request.getParameter("operation"));
        // get model
        UserModel model = new UserModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        if (id > 0 || op != null) {
            System.out.println("in id > 0  condition");
            UserBean bean;
            try {
                bean = model.findByPK(id);
                bean.setConfirmPassword(bean.getPassword());
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error(e);
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String op = request.getParameter("operation");
    	long l;
    	long id= DataUtility.getLong(request.getParameter("id"));
    	UserBean bean= (UserBean) populateBean(request);
    UserModel model = new UserModel();
    if(OP_SAVE.equalsIgnoreCase(op)){
    	
    	try {
			l=model.add(bean);
			ServletUtility.setBean(bean, request);
			if(l>0){
			
			ServletUtility.setSuccessMessage("User Added Succesfully", request);
			}
			
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("User Already Register", request);
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    else if(OP_UPDATE.equalsIgnoreCase(op))
	{
		UserBean bean1 = (UserBean)populateBean(request);
		System.out.println("Update id is  :"+bean1.getId());
		try {
			UserModel.update(bean1);
			ServletUtility.setBean(bean1, request);
			ServletUtility.setSuccessMessage("Data Updated Successfully", request);
		} catch (Exception e) {
			ServletUtility.setBean(bean1, request);
			e.printStackTrace();
		}
		ServletUtility.setBean(bean1, request);
	 // ServletUtility.forward(getView(), request, response);	
	}
    else if(OP_DELETE.equalsIgnoreCase(op)){
    	 try {
			model.delete(bean);
			 ServletUtility.redirect(ORSView.USER_LIST_CTL, request,
	                 response);
		} catch (ApplicationException e) {
			ServletUtility.setBean(bean, request);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
    }
    
    else if(OP_CANCEL.equalsIgnoreCase(op)){
    	ServletUtility.redirect(ORSView.USER_LIST_CTL, request,
                response);
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
		return ORSView.USER_VIEW;
	}

}
