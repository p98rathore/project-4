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
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.FacultyModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Faculty functionality Controller. Performs operation for add, update, delete
 * and get Faculty
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(FacultyCtl.class);

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void preload(HttpServletRequest request)  {
		CollegeModel collegeModel = new CollegeModel();
		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List collegeList = collegeModel.list();
			request.setAttribute("collegeList", collegeList);

			List subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);

		} catch (ApplicationException e) {
			log.error(e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {

		log.debug("FacultyCtl Method validate Started");

		boolean pass = true;

		String email = request.getParameter("email");
		String dob = request.getParameter("dob");
		

		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		}
		else if(!DataValidator.isName(request.getParameter("firstName")))
	    {
		  request.setAttribute("firstname",PropertyReader.getValue("error.require", "First Name "));
		  pass = false;	
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} 
		else if(!DataValidator.isName(request.getParameter("lastName")))
	    {
		  request.setAttribute("lastname",PropertyReader.getValue("error.require", "First Name "));
		  pass = false;	
		}
		
		System.out.println("gender"+request.getParameter("gender"));
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("qualification"))) {
			request.setAttribute("qualification", PropertyReader.getValue("error.require", "Qualification"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of Birth"));
			pass = false;
		} else if (!DataValidator.isDate(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.date", "Date of Birth"));
			pass = false;
		}
		else if(!DataValidator.isValidAge(dob))
		{
		  request.setAttribute("dob","Age Must be greater then 18 year");
		  pass=false;	
		}
		if (DataValidator.isNull(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email "));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobile"))) {
			request.setAttribute("mobile", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
			pass = false;
		}

		log.debug("FacultyCtl Method validate Ended");

		return pass;
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("FacultyCtl Method populatebean Started");

		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		
		bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
String d=request.getParameter("dob");

		bean.setDob(DataUtility.getDate(d));

		bean.setMobileNo(DataUtility.getString(request.getParameter("mobile")));

		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		populateDTO(bean, request);

		log.debug("FacultyCtl Method populatebean Ended");

		return bean;
	}
	
	/**
	 * Contain Display Logic.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String op = DataUtility.getString(request.getParameter("operation"));
	long id = DataUtility.getLong(request.getParameter("id"));

	// get model

	FacultyModel model = new FacultyModel();
	if (id > 0 || op != null) {
		FacultyBean bean;
		try {
			bean = model.findByPK(id);
			ServletUtility.setBean(bean, request);
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	ServletUtility.forward(getView(), request, response);
	log.debug("FacultyCtl Method doGet Ended");
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
	log.debug("FacultyCtl Method doPost Started");

	String op = DataUtility.getString(request.getParameter("operation"));

	// get model

	FacultyModel model = new FacultyModel();

	long id = DataUtility.getLong(request.getParameter("id"));
long l=0;
	if (OP_SAVE.equalsIgnoreCase(op)) {

		FacultyBean bean = (FacultyBean) populateBean(request);

		
		try {
			l=	model.add(bean);
			ServletUtility.setBean(bean, request);
			
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
				
			
		} catch (DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Faculty already exisst", request);
			e.printStackTrace();
		} catch (ApplicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ServletUtility.forward(getView(), request, response);
		
	} else if (OP_UPDATE.equalsIgnoreCase(op)) {

		FacultyBean bean = (FacultyBean) populateBean(request);

		
			model.update(bean);
		
		ServletUtility.setBean(bean, request);
		ServletUtility.setSuccessMessage("Data is successfully updated", request);
		ServletUtility.forward(getView(), request, response);

	}
	
	else if (OP_DELETE.equalsIgnoreCase(op)) {

	FacultyBean	bean=(FacultyBean) populateBean(request);
	
         try {
			model.delete(bean);
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;

		} catch (ApplicationException e) {
			
			e.printStackTrace();
		}
		}
	else if (OP_CANCEL.equalsIgnoreCase(op)) {

		ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
		return;

	} else if (OP_RESET.equalsIgnoreCase(op)) {
		ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
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
		return ORSView.FACULTY_VIEW;
	}


}
