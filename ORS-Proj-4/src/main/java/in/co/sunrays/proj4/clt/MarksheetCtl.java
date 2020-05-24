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
import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.MarksheetModel;
import in.co.sunrays.proj4.model.StudentModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="MarksheetCtl",urlPatterns={"/ctl/MarksheetCtl"})
public class MarksheetCtl extends BaseCtl {

    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The log. */
	private static Logger log = Logger.getLogger(MarksheetCtl.class);

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void preload(HttpServletRequest request) {
        StudentModel model = new StudentModel();
        List l = model.list();
		request.setAttribute("studentList", l);
		
    }

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("MarksheetCtl Method validate Started");

        boolean pass=true;
		
		 String rollno = request.getParameter("rollNo");
		 String student = request.getParameter("studentId");
		 String phy = request.getParameter("physics");
		 String chem = request.getParameter("chemistry");
		 String math = request.getParameter("maths");
		 
		 if(DataValidator.isNull(rollno))
		 {
			request.setAttribute("rollNo", "Please Enter Roll No");
			pass=false;
		 }else if(!DataValidator.isRollNo(rollno))
		 {
		   request.setAttribute("rollNo", "Please Enter Valid Roll No");
		   pass=false;	 
		 }
		 if(DataValidator.isNull(student))
		 {
			request.setAttribute("studentId", "Please Select Student Name");
			pass=false;	 
		 }
		 if(DataValidator.isNull(phy))
		 {
			request.setAttribute("physics", "Please Enter Physics Marks");
			pass=false; 
		 }
		 if(DataUtility.getInt(phy) > 100  || DataUtility.getInt(phy) < 0)
		 {
			request.setAttribute("physics", "Marks Must Be Less then 100 and Greater then 0");
			pass=false;	 
		 }
		 if(!DataValidator.isInteger(phy)&& DataValidator.isNotNull(phy))
		 {
			request.setAttribute("physics", "Please Enter Number Only");
			pass=false;	 
		 }
		 
		 if(DataValidator.isNull(chem))
		 {
			request.setAttribute("chemistry", "Please Enter Chemistry Marks");
			pass=false; 
		 }else if(DataUtility.getInt(chem)>100 || DataUtility.getInt(chem) < 0)
		 {
			 request.setAttribute("chemistry", "Marks Must Be Less then 100 and Greater then 0");
				pass=false;	 
		 }
		 if(!DataValidator.isInteger(chem) && DataValidator.isNotNull(chem))
		 {
			request.setAttribute("chemistry", "Please Enter Number Only");
			pass=false;	 
		 }
		 
		 if(DataValidator.isNull(math))
		 {
			request.setAttribute("maths", "Please Enter Maths Marks");
			pass=false;	 
		 }else if(DataUtility.getInt(math)>100 || DataUtility.getInt(math) < 0)
		 {
			 request.setAttribute("maths", "Marks Must Be Less then 100 and Greater then 0");
				pass=false;	 
		 }else if(!DataValidator.isInteger(math)&& DataValidator.isNotNull(math))
		 {
			request.setAttribute("maths", "Please Enter Number Only");
			pass=false;	 
		 }
		
		return pass;
    }
    
    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("MarksheetCtl Method populatebean Started");

        MarksheetBean bean = new MarksheetBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

        bean.setName(DataUtility.getString(request.getParameter("name")));

        bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));

        bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));

        bean.setMaths(DataUtility.getInt(request.getParameter("maths")));

        bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
        System.out.println(request.getParameter("studentId")+"student id");

        populateDTO(bean, request);

        System.out.println("Population done");

        log.debug("MarksheetCtl Method populatebean Ended");

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
    	  MarksheetModel model = new MarksheetModel();
          long id = DataUtility.getLong(request.getParameter("id"));
          if (id > 0) {
              MarksheetBean bean;
              try {
                  bean = model.findByPK(id);
                  ServletUtility.setBean(bean, request);
              } catch (ApplicationException e) {
                  log.error(e);
                  ServletUtility.handleException(e, request, response);
                  return;
              } catch (Exception e) {
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
	String op = request.getParameter("operation");
	long id = DataUtility.getLong(request.getParameter("id"));
	System.out.println("doPost id "+id);
	MarksheetBean bean = (MarksheetBean)populateBean(request);
	MarksheetModel model = new MarksheetModel();
	
	if(OP_SAVE.equalsIgnoreCase(op))
	{
		System.out.println("save Rollno "+bean.getRollNo());
	try {
			model.add(bean);
			ServletUtility.setSuccessMessage("Data Saved Successfully", request);
		} catch (Exception e) 
	    {
			ServletUtility.setErrorMessage("Roll Number already exists", request);
		}
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);
		
	}else if(OP_UPDATE.equalsIgnoreCase(op))
	{
		MarksheetBean bean1 = (MarksheetBean)populateBean(request);
		System.out.println("Update id "+id);
		if(id>0)
		{
			try {
				MarksheetModel.update(bean1);
			 ServletUtility.setSuccessMessage("Data Updated Successfully", request);	
			} catch (Exception e) {
		     ServletUtility.setErrorMessage("No Record Found", request);		
				e.printStackTrace();
			}
		}
		ServletUtility.setBean(bean1, request);
		ServletUtility.forward(getView(), request, response);
		
	}else if(OP_CANCEL.equalsIgnoreCase(op))
	{
//	 ServletUtility.forward(getView(), request, response);
		ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
		return;
	}
	else if (OP_DELETE.equalsIgnoreCase(op)) {

       MarksheetBean bean2 = (MarksheetBean) populateBean(request);
        System.out.println("in try");
        try {
            model.Delete(bean2);
            ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,
                    response);
            System.out.println("in try");
            return;
        } catch (ApplicationException e) {
            System.out.println("in catch");
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    } else if(OP_RESET.equalsIgnoreCase(op))
	{
		  ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);	
		  return;
	}
  //  ServletUtility.forward(getView(), request, response);

    log.debug("MarksheetCtl Method doPost Ended");
}
		
		
	


	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.MARKSHEET_VIEW ;
	}
}
