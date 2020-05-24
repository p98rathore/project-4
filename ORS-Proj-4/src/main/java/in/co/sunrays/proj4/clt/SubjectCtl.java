/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.StudentModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Subject functionality Controller. Performs operation for add, update, delete
 * and get Subject
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@WebServlet(name = "SubjectCtl", urlPatterns = {"/ctl/SubjectCtl"})
public class SubjectCtl extends BaseCtl {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(SubjectCtl.class);

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("preload subject");
		CourseModel model = new CourseModel();
		try {
			List l = model.list();
			request.setAttribute("courseList", l);
		} catch (ApplicationException e) {
			log.error(e);
		}

	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {

		log.debug("SubjectCtl Method validate Started");

		boolean pass = true;

		
		if (DataValidator.isNull(request.getParameter("subject"))) {
			request.setAttribute("subject", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		log.debug("SubjectCtl Method validate Ended");

		return pass;
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("SubjectCtl Method populatebean Started");

		SubjectBean bean = new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setName(DataUtility.getString(request.getParameter("subject")));

		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		populateDTO(bean, request);

		log.debug("SubjectCtl Method populatebean Ended");

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
		System.out.println("doget subject");
		String op= request.getParameter("operation");
		SubjectModel model= new SubjectModel();
		
		long id =DataUtility.getLong(request.getParameter("id"));
		if(id > 0 ||op!=null){
			SubjectBean bean=null;
			try {
				bean= model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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

	String op= request.getParameter("operation");
	long id = DataUtility.getLong(request.getParameter("id"));
	
	if(OP_SAVE.equalsIgnoreCase(op)){
		SubjectBean bean=(SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();
		try {
			long l=model.add(bean);
			
			if(l>0){
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Record added successfully", request);;
			}
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Record Already Exisst ", request);
			e.printStackTrace();
		}
	}
	else if (OP_UPDATE.equalsIgnoreCase(op)) {

		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model= new SubjectModel();

		try {
			
				model.update(bean);
				
	
			ServletUtility.setBean(bean, request);
			ServletUtility.setSuccessMessage("Data is successfully updated", request);

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_DELETE.equalsIgnoreCase(op)) {
		SubjectModel model= new SubjectModel();
		SubjectBean bean = (SubjectBean) populateBean(request);
		try {
			model.delete(bean);
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	} else if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
		return;

	} else if (OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
		return;
	}
	ServletUtility.forward(getView(), request, response);

	log.debug("SubjectCtl Method doPost Ended");
	
}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.SUBJECT_VIEW;
	}


}
