/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.model.UserModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Login functionality Controller. Performs operation for Login
 * 
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */
@ WebServlet(name="LoginCtl",urlPatterns={"/LoginCtl"})
public class LoginCtl extends BaseCtl {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The Constant OP_REGISTER. */
    public static final String OP_REGISTER = "Register";
    
    /** The Constant OP_SIGN_IN. */
    public static final String OP_SIGN_IN = "SignIn";
    
    /** The Constant OP_SIGN_UP. */
    public static final String OP_SIGN_UP = "SignUp";
    
    /** The Constant OP_LOG_OUT. */
    public static final String OP_LOG_OUT = "logout";
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    protected BaseBean populateBean(HttpServletRequest request) {

	       

        UserBean bean = new UserBean();

       bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setLogin(DataUtility.getString(request.getParameter("userId")));
        bean.setPassword(DataUtility.getString(request.getParameter("password")));

        return bean;
    }
   
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    protected boolean validate(HttpServletRequest request)  {
        boolean pass = true;
System.out.println("login validate");
        String op = request.getParameter("operation");
        if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
            return pass;
        }

        String login = request.getParameter("userId");

        if (DataValidator.isNull(login.trim())) {
        	 request.setAttribute("userId",
                     PropertyReader.getValue("error.require", "Login id"));
            pass = false;
        } else if (!DataValidator.isEmail(login)) {
        	 request.setAttribute("userId",
                     PropertyReader.getValue("error.email", "Login id"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("password"))) {
        	 request.setAttribute("password",
                     PropertyReader.getValue("error.require", "Password"));
            pass = false;
        }
        else if (!DataValidator.isPasswordLength(request.getParameter("password"))) {
			request.setAttribute("password","Password must be of 8 character");
			pass = false;
		} else if (!DataValidator.isPassword(request.getParameter("password"))) {
			request.setAttribute("password", "Password Must contain uppercase, lowercase, digit & special character");
			pass = false;
		}

        return pass;
    }

   /**
    * Contain Display logic.
    *
    * @param request the request
    * @param response the response
    * @throws ServletException the servlet exception
    * @throws IOException Signals that an I/O exception has occurred.
    */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("loginget");
		//String op= request.getParameter("operation");
		   HttpSession session = request.getSession(true);
	        

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get model
	        UserModel model = new UserModel();
	        RoleModel role = new RoleModel();

	        long id = DataUtility.getLong(request.getParameter("id"));
	        if (id > 0) {
	            UserBean userbean=new UserBean();
	            try {
					userbean = model.findByPK(id);
				} catch (in.co.sunrays.proj4.exception.ApplicationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ServletUtility.setBean(userbean, request);
	        }
		if(OP_LOG_OUT.equalsIgnoreCase(op)){
			request.getSession().invalidate();
			ServletUtility.setSuccessMessage("Logout Successfully!", request);
			//ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
		}
		
ServletUtility.forward(getView(), request, response);
	}
	
	/**
	 * Contain Submit logic .
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("do post login");
		String op = request.getParameter("operation");

		HttpSession session = request.getSession();

		// get Models
		UserModel user = new UserModel();
		RoleModel role = new RoleModel();
		UserBean bean = new UserBean();

		if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			bean = (UserBean) populateBean(request);
System.out.println(" login sign in");
			try {
				System.out.println(bean.getLogin()+"    login+pass         "+bean.getPassword());
				bean = UserModel.authenticate(bean.getLogin(), bean.getPassword());
				if (bean != null) {
System.out.println("bean!=null");
					session.setAttribute("user", bean);
					long roleId = bean.getRoleId();

					RoleBean rolebean = role.findByPK((int) roleId);

					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}
					String uri=(String) request.getParameter("uri");
					if(uri==null||"null".equalsIgnoreCase(uri)){
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					}
					else{
						ServletUtility.redirect(uri, request, response);
						return;
					}
				} else {
					System.out.println("wrong");
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Login id or Password is Wrong", request);
					ServletUtility.forward(ORSView.LOGIN_VIEW, request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			System.out.println("Register");
			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			// return;
		}
}



	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		
		return ORSView.LOGIN_VIEW;
	}
}