/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Role functionality Controller. Performs operation for add, update and get
 * Role
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@WebServlet(name = "RoleCtl", urlPatterns = { "/ctl/RoleCtl" })
public class RoleCtl extends BaseCtl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(RoleCtl.class);

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("RoleCtl Method validate Started");

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("role"))) {
			request.setAttribute("role", PropertyReader.getValue("error.require", "Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("desc"))) {
			request.setAttribute("desc", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("RoleCtl Method validate Ended");

		return pass;
	}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("RoleCtl Method populatebean Started");

		RoleBean bean = new RoleBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("role")));
		bean.setDescription(DataUtility.getString(request.getParameter("desc")));

		populateDTO(bean, request);

		log.debug("RoleCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains Display logics.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("RoleCtl Method doGet Started");

		System.out.println("In do get");

		String op = DataUtility.getString(request.getParameter("operation"));

		// get model
		RoleModel model = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			RoleBean bean;
			bean = model.findByPK(id);
			ServletUtility.setBean(bean, request);
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("RoleCtl Method doGetEnded");
	}

	/**
	 * Contains Submit logics.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("RoleCtl Method doGet Started");
		
		RoleBean bean= (RoleBean) populateBean(request);
		String op = request.getParameter("operation");
		RoleModel model= new RoleModel();
		
		if(OP_SAVE.equalsIgnoreCase(op)){
		try {
			model.add(bean)	;
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage("Role is succesfully added", request);;
			
		} catch (DuplicateRecordException e) {
			ServletUtility.setErrorMessage("Role is already exist", request);
			e.printStackTrace();
		}
		
		
		}
		else if(OP_RESET.equalsIgnoreCase(op)){
			 ServletUtility.redirect(ORSView.ROLE_CTL, request, response);	
			  return;
		}
		else if(OP_CANCEL.equalsIgnoreCase(op))
		{
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;	
		}
		else if(OP_UPDATE.equalsIgnoreCase(op)){
			bean= (RoleBean) populateBean(request);
			model.update(bean);
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage(" Record update succes", request);
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.ROLE_VIEW;
	}
	}