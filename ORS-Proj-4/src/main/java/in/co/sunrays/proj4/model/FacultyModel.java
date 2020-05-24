/*
 * 
 */
package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.FacultyBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.DataUtility;
import in.co.sunrays.proj4.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 * JDBC Implementation of Faculty Model.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */
public class FacultyModel {

	
	/**
	 *  Find next pk of faculty model.
	 *
	 * @return next primary key
	 */
	public static long nextPk(){
		long i=0;
		Connection con= null;
		ResultSet rs= null;
		PreparedStatement ps= null;
		try {
			con =JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps= con.prepareStatement("SELECT MAX(ID) FROM ST_FACULTY");
			rs=ps.executeQuery();
			
			while(rs.next()){
				i=rs.getLong(1);
			}
			con.commit();
			ps.close();
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JDBCDataSource.closeConnection(con);
		}
		
		return i+1;
		
	}
	
	/**
	 * Add facuty.
	 *
	 * @param bean the bean
	 * @return long
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws ApplicationException the application exception
	 * @throws SQLException the SQL exception
	 */
	public static long add(FacultyBean bean) throws DuplicateRecordException, ApplicationException, SQLException {
		
		Connection con= null;
		ResultSet rs= null;
		FacultyBean duplicate=findByLogin(bean.getEmail());
		if(duplicate!=null){
			throw new DuplicateRecordException("faculty already exisst");
		}
		
		CollegeBean ccb= CollegeModel.findByPK(bean.getCollegeId());
		bean.setCollegeName(ccb.getName());
		CourseBean cb= CourseModel.findByPK(bean.getCourseId());
		bean.setCourseName(cb.getName());
		SubjectBean sb= SubjectModel.findByPK(bean.getSubjectId());
		bean.setSubjectName(sb.getName());
		
		
		Date d= new Date(bean.getDob().getTime());
		PreparedStatement ps= null;
		long a=0;
			try {
				con =JDBCDataSource.getConnection();
				con.setAutoCommit(false);
				ps= con.prepareStatement("INSERT INTO ST_FACULTY VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				ps.setLong(1, nextPk());
				ps.setString(2, bean.getFirstName());
				ps.setString(3, bean.getLastName());
				ps.setString(4, bean.getGender());
				
				ps.setDate(5, d);
				ps.setString(6, bean.getQualification());
				ps.setString(7, bean.getEmail());
				ps.setString(8, bean.getMobileNo());
				ps.setLong(9, bean.getCollegeId());
				ps.setString(10, bean.getCollegeName());
				ps.setLong(11, bean.getCourseId());
				ps.setString(12, bean.getCourseName());
				ps.setLong(13, bean.getSubjectId());
				ps.setString(14, bean.getSubjectName());
				ps.setString(15, bean.getCreatedBy());
				ps.setString(16, bean.getModifiedBy());
				ps.setTimestamp(17, bean.getCreatedDatetime());
				ps.setTimestamp(18, bean.getModifiedDatetime());

				a=ps.executeUpdate();
				ps.close();
				con.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JDBCDataSource.closeConnection(con);
		return a;
	}
           
           /**
            * Update a faculty.
            *
            * @param bean the bean
            */
        public static void update(FacultyBean bean){
        	   Date d= new Date(bean.getDob().getTime());
	Connection con =null;
	PreparedStatement ps= null; 
	try {
		con = JDBCDataSource.getConnection();
		ps = con.prepareStatement("UPDATE ST_FACULTY SET FIRST_NAME=?,LAST_NAME=?,MOBILE_NO=?,EMAIL_ID=?,COURSE_ID=?,SUBJECT_ID=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=?,COllEGE_ID=?,COLLEGE_NAME=?,COURSE_NAME=?,SUBJECT_NAME=?,GENDER=?,DOB=?,QUALIFICATION=? WHERE ID=?");
            con.setAutoCommit(false);
		ps.setLong(18, bean.getId());
            
            ps.setString(1, bean.getFirstName());
            ps.setString(2, bean.getLastName());
            ps.setString(3, bean.getMobileNo());
            ps.setString(4, bean.getEmail());
            ps.setLong(5, bean.getCourseId());
            ps.setLong(6, bean.getSubjectId());
            ps.setString(7, bean.getCreatedBy());
            ps.setString(8, bean.getModifiedBy());
            ps.setTimestamp(9, bean.getCreatedDatetime());
            ps.setTimestamp(10, bean.getModifiedDatetime());
            ps.setLong(11, bean.getCollegeId());
            ps.setString(12, bean.getCollegeName());
            ps.setString(13, bean.getCourseName());
            ps.setString(14, bean.getSubjectName());
            ps.setString(15, bean.getGender());
            ps.setDate(16,d);
            ps.setString(17, bean.getQualification());
            ps.executeUpdate();
            ps.close();
            con.commit();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	JDBCDataSource.closeConnection(con);
	
	}
           
           /**
            * To delete a faculty.
            *
            * @param bean the bean
            * @throws ApplicationException the application exception
            */
        public static void delete(FacultyBean bean) throws ApplicationException {
           //	log.debug("Model delete Started");
               Connection conn = null;
               try {
                   conn = JDBCDataSource.getConnection();
                   System.out.println("conn start");
                   conn.setAutoCommit(false); // Begin transaction
                   PreparedStatement pstmt = conn
                           .prepareStatement("DELETE FROM ST_FACULTY WHERE ID=?");
                   pstmt.setLong(1, bean.getId());
                  int i= pstmt.executeUpdate();
                  System.out.println(i);
                   conn.commit(); // End transaction
                   pstmt.close();

               } catch (Exception e) {
               	 //log.error("Database Exception..", e);
                   e.printStackTrace();
               } finally {
                   JDBCDataSource.closeConnection(conn);
                 //  log.debug("Model delete ended");
               }
               
           }
           
           /**
            * Find a faculty by its primary key.
            *
            * @param pk the pk
            * @return the faculty bean
            * @throws ApplicationException the application exception
            */
        public static FacultyBean findByPK(long pk) throws ApplicationException {
          	// log.debug("Model findByPk Start");
              StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE ID=?");
              FacultyBean bean = null;
              Connection conn = null;

              try {
                  conn = JDBCDataSource.getConnection();
                  PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                  pstmt.setLong(1, pk);
                  ResultSet rs = pstmt.executeQuery();
                  while (rs.next()) {
                      bean = new FacultyBean();
                      bean.setId(rs.getLong(1));
                      bean.setFirstName(rs.getString(2));
                      bean.setLastName(rs.getString(3));
                      bean.setGender(rs.getString(4));
                      bean.setDob(rs.getDate(5));
                      bean.setQualification(rs.getString(6));
                      bean.setEmail(rs.getString(7)); 
                      bean.setMobileNo(rs.getString(8));
                       bean.setCollegeId(rs.getLong(9)); 
                       bean.setCollegeName(rs.getString(10));
                     bean.setCourseId(rs.getLong(11));
                     bean.setCourseName(rs.getString(12));
                     bean.setSubjectId(rs.getLong(13));
                     bean.setSubjectName(rs.getString(14));
                     bean.setCreatedBy(rs.getString(15));
                     bean.setModifiedBy(rs.getString(16));
                     bean.setCreatedDatetime(rs.getTimestamp(17));
                     bean.setModifiedDatetime(rs.getTimestamp(18));
                     }
                  pstmt.close();
                  rs.close();
              } catch (Exception e) {
              //	 log.error("Database Exception..", e);
                  e.printStackTrace();
                 
              } finally {
                  JDBCDataSource.closeConnection(conn);
              }
            //  log.debug("Model findByPK End");
              return bean;
          }
           
           /**
            * Find a faculty by Email.
            *
            * @param login the login
            * @return the faculty bean
            * @throws ApplicationException the application exception
            */
        public static FacultyBean findByLogin(String login) throws ApplicationException {
             	// log.debug("Model findByPk Start");
                 StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE EMAIL_ID=?");
                 FacultyBean bean = null;
                 Connection conn = null;

                 try {
                     conn = JDBCDataSource.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql.toString());
                     pstmt.setString(1, login);
                     ResultSet rs = pstmt.executeQuery();
                     while (rs.next()) {
                    	 bean = new FacultyBean();
                         bean.setId(rs.getLong(1));
                         bean.setFirstName(rs.getString(2));
                         bean.setLastName(rs.getString(3));
                         bean.setGender(rs.getString(4));
                         bean.setDob(rs.getDate(5));
                         bean.setQualification(rs.getString(6));
                         bean.setEmail(rs.getString(7)); 
                         bean.setMobileNo(rs.getString(8));
                          bean.setCollegeId(rs.getLong(9)); 
                          bean.setCollegeName(rs.getString(10));
                        bean.setCourseId(rs.getLong(11));
                        bean.setCourseName(rs.getString(12));
                        bean.setSubjectId(rs.getLong(13));
                        bean.setSubjectName(rs.getString(14));
                        bean.setCreatedBy(rs.getString(15));
                        bean.setModifiedBy(rs.getString(16));
                        bean.setCreatedDatetime(rs.getTimestamp(17));
                        bean.setModifiedDatetime(rs.getTimestamp(18));
                        }
                     pstmt.close();
                     rs.close();
                 } catch (Exception e) {
                 //	 log.error("Database Exception..", e);
                     e.printStackTrace();
                    
                 } finally {
                     JDBCDataSource.closeConnection(conn);
                 }
               //  log.debug("Model findByPK End");
                 return bean;
             }
           
           /**
            * Search faculty with pagination.
            *
            * @param bean the bean
            * @param pageNo the page no
            * @param pageSize the page size
            * @return list of faculty
            */
        public static List search(FacultyBean bean,int pageNo,int pageSize){
    		 //  log.debug("user model search(beaan,pgno,pgsize) strt");
    		   StringBuffer sql = new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");

    	        if (bean != null) {
    	            if (bean.getId() > 0) {
    	                sql.append(" AND id = " + bean.getId());
    	            }
    	            if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
    	                sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
    	            }
    	            if (bean.getLastName() != null && bean.getLastName().length() > 0) {
    	                sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
    	            }
    	            if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
    	                sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
    	            }
    	            if(bean.getEmail() !=null && bean.getEmail().length()>0){
    	            	sql.append(" AND EMAIL LIKE  '" + bean.getEmail()+"%'");
    	            }
    	            if(bean.getCourseId() >0){
    	            	sql.append(" AND COURSE_ID= " + bean.getCourseId());
    	            }
    	            if(bean.getSubjectId() >0){
    	            	sql.append(" AND SUBJECT_ID = " + bean.getSubjectId());
    	            }
}
    	        if (pageSize > 0) {
    	            // Calculate start record index
    	            pageNo = (pageNo - 1) * pageSize;

    	            sql.append(" Limit " + pageNo + ", " + pageSize);
    	            // sql.append(" limit " + pageNo + "," + pageSize);
    	        }
    	        System.out.println(sql);
    	        List<FacultyBean> list = new ArrayList<FacultyBean>();
    	        Connection conn = null;
    	        try {
    	            conn = JDBCDataSource.getConnection();
    	            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
    	            ResultSet rs = pstmt.executeQuery();
    	            while (rs.next()) {
    	            	bean = new FacultyBean();
                        bean.setId(rs.getLong(1));
                        bean.setFirstName(rs.getString(2));
                        bean.setLastName(rs.getString(3));
                        bean.setGender(rs.getString(4));
                        bean.setDob(rs.getDate(5));
                        bean.setQualification(rs.getString(6));
                        bean.setEmail(rs.getString(7)); 
                        bean.setMobileNo(rs.getString(8));
                         bean.setCollegeId(rs.getLong(9)); 
                         bean.setCollegeName(rs.getString(10));
                       bean.setCourseId(rs.getLong(11));
                       bean.setCourseName(rs.getString(12));
                       bean.setSubjectId(rs.getLong(13));
                       bean.setSubjectName(rs.getString(14));
                       bean.setCreatedBy(rs.getString(15));
                       bean.setModifiedBy(rs.getString(16));
                       bean.setCreatedDatetime(rs.getTimestamp(17));
                       bean.setModifiedDatetime(rs.getTimestamp(18));
                       list.add(bean);
    	                
    	            }
    	            rs.close();
    	        } catch (Exception e) {
    	        	 // log.error("user model search Database Exception..", e);
    	           e.printStackTrace();
    	        } finally {
    	            JDBCDataSource.closeConnection(conn);
    	        }

    	       // log.debug("user model search ended");
    	        return list;
    		   
}
           
           /**
            *  Search faculty.
            *
            * @param bean the bean
            * @return list of faculty
            */
        public static List search(FacultyBean bean){
			return search(bean, 0, 0);
        	   
           }
           
           /**
            * get list of user with pagination.
            *
            * @param bean the bean
            * @param pageNo the page no
            * @param pageSize the page size
            * @return list of faculty
            */
        public static List list(FacultyBean bean,int pageNo,int pageSize){
        	   List<FacultyBean> list= new ArrayList<FacultyBean>();
        	   Connection con = null;
        	   StringBuffer sql= new StringBuffer("SELECT * FROM ST_FACULTY WHERE 1=1");
        	   if(pageSize>0){
        		   pageNo=(pageNo-1)*pageSize;
        		   sql.append(" Limit "+pageNo+","+pageSize);
        	   }
        	   System.out.println(sql);
        	   PreparedStatement ps=null;
        	   try {
        		   con= JDBCDataSource.getConnection();
				ps= con.prepareStatement(sql.toString());
				
				ResultSet rs=ps.executeQuery();
				
				while (rs.next()){
					//bean = new FacultyBean();
					bean = new FacultyBean();
                    bean.setId(rs.getLong(1));
                    bean.setFirstName(rs.getString(2));
                    bean.setLastName(rs.getString(3));
                    bean.setGender(rs.getString(4));
                    bean.setDob(rs.getDate(5));
                    bean.setQualification(rs.getString(6));
                    bean.setEmail(rs.getString(7)); 
                    bean.setMobileNo(rs.getString(8));
                     bean.setCollegeId(rs.getLong(9)); 
                     bean.setCollegeName(rs.getString(10));
                   bean.setCourseId(rs.getLong(11));
                   bean.setCourseName(rs.getString(12));
                   bean.setSubjectId(rs.getLong(13));
                   bean.setSubjectName(rs.getString(14));
                   bean.setCreatedBy(rs.getString(15));
                   bean.setModifiedBy(rs.getString(16));
                   bean.setCreatedDatetime(rs.getTimestamp(17));
                   bean.setModifiedDatetime(rs.getTimestamp(18));
                   list.add(bean);
				}
        	   
	            rs.close();
	        } catch (Exception e) {
	        	 // log.error("user model search Database Exception..", e);
	           e.printStackTrace();
	        } finally {
	            JDBCDataSource.closeConnection(con);
	        }

	       // log.debug("user model search ended");
	        return list;
           
           }
           
           /**
            * get list of faculty.
            *
            * @param bean the bean
            * @return list of faculty
            */
        public static List list(FacultyBean bean){
			return list(bean, 0, 0);
        	   
           }
}