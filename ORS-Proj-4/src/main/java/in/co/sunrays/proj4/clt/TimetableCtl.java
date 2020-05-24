/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.TimetableBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.CourseModel;
import in.co.sunrays.proj4.model.SubjectModel;
import in.co.sunrays.proj4.model.TimetableModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Timetable functionality Controller. Performs operation for add, update,
 * delete and get Timetable
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@WebServlet(name = "TimetableCtl", urlPatterns = { "/ctl/TimetableCtl" })
public class TimetableCtl extends BaseCtl {

	/** Default serial version ID. */
	private static final long serialVersionUID = 1L;

	/** The log. */
	private static Logger log = Logger.getLogger(TimetableCtl.class);

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("preload time table");
		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();

		try {
			List subjectList = subjectModel.list();
			request.setAttribute("subjectList", subjectList);

			List courseList = courseModel.list();
			request.setAttribute("courseList", courseList);

		} catch (ApplicationException e) {
			log.error(e);
		}

	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	protected boolean validate(HttpServletRequest request) {

		log.debug("TimetableCtl Method validate Started");

		boolean pass = true;
		
		String examDate = request.getParameter("examDate");
		

		if (DataValidator.isNull(request.getParameter("semester"))) {
			request.setAttribute("semester", PropertyReader.getValue("error.require", "Semester"));
			pass = false;
		}
		if (DataValidator.isNull(examDate)) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Date of Exam"));
			pass = false;
		} 
		if (DataValidator.isNull(request.getParameter("examTime"))) {
			request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
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
		if(DataValidator.isNull(request.getParameter("description")))
		{
		  request.setAttribute("description", " Description is required");
		  pass=false;
		}

		log.debug("TimetableCtl Method validate Ended");

		return pass;
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
	 */
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("TimetableCtl Method populatebean Started");
		String examDate = request.getParameter("examDate");
		

		TimetableBean bean = new TimetableBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setSemester(DataUtility.getString(request.getParameter("semester")));

		bean.setDescription(DataUtility.getString(request.getParameter("description")));

		bean.setExamTime(DataUtility.getString(request.getParameter("examTime")));

		bean.setExamDate(DataUtility.getDate(examDate));

		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));

		bean.setSubId(DataUtility.getInt(request.getParameter("subjectId")));;

		populateDTO(bean, request);
		System.out.println("8"+examDate+"8");

		log.debug("TimetableCtl Method populatebean Ended");

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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
System.out.println("do get timetabe");
		log.debug("TimetableCtl Method doGet Started");

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
       System.out.println("id dopst"+id);
		// get model

		TimetableModel model = new TimetableModel();
		if (id > 0 || op != null) {
			TimetableBean bean;
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("TimetableCtl Method doGet Ended");
	}
	
/**
 * Contain Submit logic .
 *
 * @param request the request
 * @param response the response
 * @throws ServletException the servlet exception
 * @throws IOException Signals that an I/O exception has occurred.
 */
@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	
	String op = request.getParameter("operation");
	long id = DataUtility.getLong(request.getParameter("id"));
	
	TimetableBean bean = (TimetableBean) populateBean(request);
	TimetableModel model = new TimetableModel();
	
	if(OP_SAVE.equalsIgnoreCase(op))
	{
		TimetableBean bean1;
		TimetableBean bean2;
		TimetableBean bean3;
		
		try {
			bean1 = model.checkByCourseName(bean.getCourseId(), bean.getExamDate());

			bean2 = model.checkBySubjectName(bean.getCourseId(), bean.getSubId(), bean.getExamDate());
			
			bean3 = model.checkBysemester(bean.getCourseId(), bean.getSubId(), bean.getSemester(),
					bean.getExamDate());
			
			if (bean1 == null && bean2 == null && bean3 == null) {

				model.add(bean);
//				bean.setId(pk);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is successfully saved", request);
			} else {
//				bean = (TimetableBean) populateBean(request);
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Exam already exist!", request);
			}
			
			} catch (Exception e) {
			e.printStackTrace();;
		}
	  ServletUtility.forward(getView(), request, response);	
	}
	else if(OP_CANCEL.equalsIgnoreCase(op))
	{
		ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
		return;
	}
	else if(OP_UPDATE.equalsIgnoreCase(op))
	{
		TimetableBean bean6 = (TimetableBean) populateBean(request);

		TimetableBean bean4 = null;
		
		try {
			bean4 = model.checkByExamTime(bean6.getCourseId(), bean6.getSubId(), bean6.getSemester(), bean6.getExamDate(), bean6.getExamTime());
		} catch (ApplicationException e1) {
			ServletUtility.handleException(e1, request, response);
		}
		if (id > 0 && bean4 == null) {
			try {
				model.update(bean6);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ServletUtility.setBean(bean6, request);
			ServletUtility.setSuccessMessage("Data is successfully updated", request);
		} else {
			bean = (TimetableBean) populateBean(request);
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Exam already exist!", request);
		}
		ServletUtility.forward(getView(), request, response);
	}
	
	else if(OP_DELETE.equalsIgnoreCase(op))
	{
		try {
			model.delete(bean);
			ServletUtility.setSuccessMessage("Data Deleted Successfully", request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
		return;
	}else if(OP_RESET.equalsIgnoreCase(op))
	{
		  ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);	
		  return;
	}


}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.TIMETABLE_VIEW;
	}


}
