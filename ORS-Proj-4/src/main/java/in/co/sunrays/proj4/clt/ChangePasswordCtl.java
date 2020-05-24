/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;


// TODO: Auto-generated Javadoc
/**
 * Change Password functionality Controller. Performs operation for Change
 * Password
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */

@ WebServlet(name="ChangePasswordCtl",urlPatterns={"/ctl/ChangePasswordCtl"})
public class ChangePasswordCtl extends BaseCtl {

	
	
	
    /** The Constant OP_CHANGE_MY_PROFILE. */
    public static final String OP_CHANGE_MY_PROFILE = "Change My Profile";
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    protected boolean validate(HttpServletRequest request) {

boolean pass = true;
		
		String opass = request.getParameter("opass");
		String npass = request.getParameter("npass");
		String cpass = request.getParameter("cpass");
		String op = request.getParameter("operation");
		
		if(op.equals("Change My Profile"))
		{
			System.out.println("validate if");
		 pass=true;	
		}else
		{
			System.out.println("validate else");
			if(DataValidator.isNull(opass))
			{
			 request.setAttribute("opass", 
					PropertyReader.getValue("error.require", "Old Password"));
	          pass=false;
			}
			if(DataValidator.isNull(npass))
			{
				request.setAttribute("npass", 
						PropertyReader.getValue("error.require", "New Password"));
		          pass=false;	
			}else if (!DataValidator.isPasswordLength(npass)) {
				request.setAttribute("npass", "Password should be 8 to 12 characters");
				pass = false;
			}else if(!DataValidator.isPassword(npass))
		    {
			   request.setAttribute("npass","Must contain uppercase, lowercase, digit & special character");
			   pass = false; 	
			}
			if(DataValidator.isNull(cpass))
			{
				request.setAttribute("cpass", 
						PropertyReader.getValue("error.require", "Confirm Password"));
		          pass=false;	
			}
			else if(!npass.equals(cpass))
			{
			request.setAttribute("cpass", "New and Confirm Password Not Matched");	
		    pass=false;
			}	
		}
		return pass;
	}
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    protected BaseBean populateBean(HttpServletRequest request) {
        

        UserBean bean = new UserBean();

        bean.setPassword(DataUtility.getString(request
                .getParameter("opass")));

        bean.setConfirmPassword(DataUtility.getString(request
                .getParameter("cpass")));

        populateDTO(bean, request);

       
        return bean;
    }
  
  /**
   *   
   * 
   * contain submit logic.
   *
   * @param request the request
   * @param response the response
   * @throws IOException Signals that an I/O exception has occurred.
   * @throws ServletException the servlet exception
   */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("change passget");
		ServletUtility.forward(getView(), request, response);
	}
    
    /**
     *  Contains submit logic.
     *
     * @param request the request
     * @param response the response
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ServletException the servlet exception
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException  {
    	boolean b = false;
		String op = request.getParameter("operation");
		HttpSession session = request.getSession();
		UserBean bean = (UserBean)session.getAttribute("user");
		
		String logUser = bean.getLogin();
		
		String opass = request.getParameter("opass");
		String npass = request.getParameter("npass");
		String cpass = request.getParameter("cpass");
		
		if(OP_SAVE.equalsIgnoreCase(op))
		{
			System.out.println( "op save");
			try {
				b =UserModel.changePassword(bean.getId(), opass, npass);
				if(b)
				{
					System.out.println( "save true");
					
				 ServletUtility.setSuccessMessage("Password Changed Successfully", request);
				 ServletUtility.forward(getView(), request, response);
				}else
				{
				 //request.setAttribute("oldPass", "Old Password Not Exists");	
				 ServletUtility.setErrorMessage("Old Password Not Exists", request);
				 ServletUtility.forward(getView(), request, response);
				}
			} catch (Exception e) {
				ServletUtility.handleException(e, request, response);
				return;
			}
			
		}else if(op.equals("Change My Profile"))
		{
		 
		 ServletUtility.setBean(bean, request);
		 ServletUtility.redirect(ORSView.MY_PROFILE_CTL, request, response);		
		}	
    }
    
    
    
    
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.CHANGE_PASSWORD_VIEW;
	}

}