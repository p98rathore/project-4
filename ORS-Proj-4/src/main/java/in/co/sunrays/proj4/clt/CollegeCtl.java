/*
 * 
 */
package in.co.sunrays.proj4.clt;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.BaseBean;
import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.DataValidator;
import in.co.sunrays.proj4.util.PropertyReader;
import in.co.sunrays.proj4.util.ServletUtility;

// TODO: Auto-generated Javadoc
/**
 * College functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
@ WebServlet(name="CollegeCtl",urlPatterns={"/ctl/CollegeCtl"})
public class CollegeCtl extends BaseCtl {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The log. */
    private static Logger log = Logger.getLogger(CollegeCtl.class);

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("CollegeCtl Method validate Started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("name"))) {
            request.setAttribute("name",
                    PropertyReader.getValue("error.require", "Name"));
            pass = false;
        }
        else if(!DataValidator.isName(request.getParameter("name")))
	    {
		  request.setAttribute("name","Please Enter Correct Name");
		  pass = false;	
		}

        if (DataValidator.isNull(request.getParameter("address"))) {
            request.setAttribute("address",
                    PropertyReader.getValue("error.require", "Address"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("state"))||request.getParameter("state").equals("-------Select State--------")) {
            request.setAttribute("state",
                    PropertyReader.getValue("error.require", "State"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("city"))) {
            request.setAttribute("city",
                    PropertyReader.getValue("error.require", "City"));
            pass = false;
        }
        if (DataValidator.isNull(request.getParameter("phone"))) {
            request.setAttribute("phone",
                    PropertyReader.getValue("error.require", "Phone No"));
            pass = false;
        }
        else if(!DataValidator.isPhoneLength(request.getParameter("phone")))
		{
			 request.setAttribute("phone", "Please Enter Valid Mobile Number");
			 pass=false;	
		}

        log.debug("CollegeCtl Method validate Ended");

        return pass;
    }

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#populateBean(javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("CollegeCtl Method populatebean Started");

        CollegeBean bean = new CollegeBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));

        bean.setName(DataUtility.getString(request.getParameter("name")));

        bean.setAddress(DataUtility.getString(request.getParameter("address")));

        bean.setState(DataUtility.getString(request.getParameter("state")));

        bean.setCity(DataUtility.getString(request.getParameter("city")));

        bean.setPhoneNo(DataUtility.getString(request.getParameter("phone")));

        populateDTO(bean, request);

        log.debug("CollegeCtl Method populatebean Ended");

        return bean;
    }

    /**
     * Contains display logic.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        CollegeModel model = new CollegeModel();

        long id = DataUtility.getLong(request.getParameter("id"));

        if (id > 0) {
            CollegeBean bean=null;
            try {
                bean = model.findByPK(id);
                ServletUtility.setBean(bean, request);
            } catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        ServletUtility.forward(getView(), request, response);
    }

    /**
     * Contains Submit logics.
     *
     * @param request the request
     * @param response the response
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        log.debug("CollegeCtl Method doPost Started");

        String op = DataUtility.getString(request.getParameter("operation"));

        // get model
        CollegeModel model = new CollegeModel();
        CollegeBean bean = (CollegeBean) populateBean(request);

        long id = DataUtility.getLong(request.getParameter("id"));

        if (OP_SAVE.equalsIgnoreCase(op)) {

            

            try {
               
            
                	System.out.println("add college");
                    CollegeModel.add(bean);
                    ServletUtility.setSuccessMessage("College detail successfully saved",
                            request);

                    
                
                ServletUtility.setBean(bean, request);
                ServletUtility.forward(getView(), request, response);
               
            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("College Name already exists",
                        request);
                ServletUtility.forward(getView(), request, response);
            } catch (DatabaseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            

        } 
        else if(OP_UPDATE.equalsIgnoreCase(op))
		{
			try {
				CollegeModel.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("College Updated Successfully", request);
				ServletUtility.forward(getView(), request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        else if (OP_CANCEL.equalsIgnoreCase(op)) {

            ServletUtility
                    .redirect(ORSView.COLLEGE_LIST_CTL, request, response);
            return;

        }
        else if(OP_RESET.equalsIgnoreCase(op))
		{
			  ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);	
			  return;
		}

        log.debug("CollegeCtl Method doGet Ended");
    }

    /* (non-Javadoc)
     * @see in.co.sunrays.proj4.clt.BaseCtl#getView()
     */
    @Override
    protected String getView() {
        return ORSView.COLLEGE_VIEW;
    }

}
