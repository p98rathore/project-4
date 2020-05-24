/*
 * 
 */
package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 * JDBC Implementation of Student Model.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (C) Proxy
 */
public class StudentModel {
	
	/** The conn. */
	Connection conn=null;
	
	/** The stmt. */
	PreparedStatement stmt=null;
	
	/** The rs. */
	ResultSet rs=null;
 
 /**
  * Getting next PrimaryKey.
  *
  * @return Next primarykey
  * @throws Exception the exception
  */
public int nextPK() throws Exception {
	 int id=0;
	 try{
	 conn=JDBCDataSource.getConnection();
	 stmt=conn.prepareStatement("select max(id) from student");
	 rs=stmt.executeQuery();
	 while(rs.next()){
		id=rs.getInt(1); 
	 }
	 
	 }catch(SQLException e)
	 {
		 e.printStackTrace();
	 }
	 return  id+1;
 }
 
 /**
  * Add a Student.
  *
  * @param bean the bean
  * @return long
  * @throws Exception the exception
  */
public long add(StudentBean bean) throws Exception    {
	// log.debug("Model add Started");
     Connection conn = null;
     int pk = 0;
     java.util.Date date=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
     CollegeModel model= new CollegeModel();
     CollegeBean cbean = model.findByPK(bean.getCollegeID());
     bean.setCollegeName(cbean.getName());
 
     StudentBean duplicateName = findByLogin(bean.getEmail());

     if (duplicateName != null) {
         throw new DuplicateRecordException("student already exist");
     }

	 try{
		
		 pk=nextPK();
		 conn=JDBCDataSource.getConnection();
		 stmt=conn.prepareStatement("insert into student values(?,?,?,?,?,?,?,?,?,?,?,?)");
	     stmt.setInt(1,pk);
	     stmt.setLong(2, bean.getCollegeID());
	     stmt.setString(3,bean.getCollegeName());
	     stmt.setString(4, bean.getFirstName());
	     stmt.setString(5, bean.getLastName());
	     stmt.setDate(6, sqlDate);
	     stmt.setString(7, bean.getMobileNo());
	     stmt.setString(8, bean.getEmail());
	     stmt.setString(9, bean.getCreatedBy());
	     stmt.setString(10, bean.getModifiedBy());
	     stmt.setTimestamp(11, bean.getCreatedDatetime());
	     stmt.setTimestamp(12, bean.getModifiedDatetime());
	     stmt.executeUpdate();
	 }catch(SQLException e){
		 e.printStackTrace();
	 }
	 return pk;
 }


/**
 *  
 * Delete a Student.
 *
 * @param bean the bean
 * @throws Exception the exception
 */
public void delete(StudentBean bean) throws Exception{
	 try{
		 conn=JDBCDataSource.getConnection();
		 stmt=conn.prepareStatement("delete from student where id=?");
		 stmt.setLong(1, bean.getId());
		 stmt.executeUpdate();
		 
	 }catch(SQLException e){
		 e.printStackTrace();
	 }
	 JDBCDataSource.closeConnection(conn);
 }
 
 /**
  * Find a Student by Email.
  *
  * @param login the login
  * @return StudentBean
  */
public StudentBean findByLogin(String login){
// 	log.debug("model findbylogin starts");
 	System.out.println(login);
 	StudentBean b=new StudentBean();
 	try{
 		conn=JDBCDataSource.getConnection();
 		stmt=conn.prepareStatement("select * from student where email=?");
 		
 		stmt.setString(1, login);
 		rs=stmt.executeQuery();
 		while(rs.next()){
 			System.out.println("ok");
 			b.setId(rs.getInt(1));
 			b.setCollegeID(rs.getLong(2));
 			b.setCollegeName(rs.getString(3));
 			b.setFirstName(rs.getString(4));
 			b.setLastName(rs.getString(5));
 			b.setDob(rs.getDate(6));
 			b.setMobileNo(rs.getString(7));
 			b.setEmail(rs.getString(8));
 			b.setCreatedBy(rs.getString(9));
 			b.setModifiedBy(rs.getString(10));
 			b.setCreatedDatetime(rs.getTimestamp(11));
 			b.setModifiedDatetime(rs.getTimestamp(12));
 		}
 		
 	}catch(Exception e)
 	{
 		
 		e.printStackTrace();
 	}
 	System.out.println(b.getFirstName());
 	JDBCDataSource.closeConnection(conn);
 	//log.debug("model findbylogin end");
 	if(b.getFirstName()==null)
 		return null;
 	else
 	return b;
 }
 
 /**
  * Find a Student By PrimaryKey.
  *
  * @param long1 the long 1
  * @return StudentBean
  */
public StudentBean findByPK(Long long1){
		//log.debug("model findbyPk started");
		StudentBean b=new StudentBean();
	    	try{
	    		conn=JDBCDataSource.getConnection();
	    		stmt=conn.prepareStatement("select * from student where id=?");
	    		stmt.setLong(1, long1);
	    		rs=stmt.executeQuery();
	    		while(rs.next()){
	    			b.setId(rs.getInt(1));
	    			b.setCollegeID(rs.getLong(2));
	    			b.setCollegeName(rs.getString(3));
	    			b.setFirstName(rs.getString(4));
	    			b.setLastName(rs.getString(5));
	    			b.setDob(rs.getDate(6));
	    			b.setMobileNo(rs.getString(7));
	    			b.setEmail(rs.getString(8));
	    			b.setCreatedBy(rs.getString(9));
	    			b.setModifiedBy(rs.getString(10));
	    			b.setCreatedDatetime(rs.getTimestamp(11));
	    			b.setModifiedDatetime(rs.getTimestamp(12));
	    			
	    		}
	    		
	    	}catch(Exception e)
	    	{  //log.error("findbypk database error",e);
	    		e.printStackTrace();
	    	}
	    	JDBCDataSource.closeConnection(conn);
	    	//log.debug("model findbyPK end");
	    	return b;
	    }
 
 /**
  * Update A student.
  *
  * @param bean the bean
  * @throws Exception the exception
  */
public void update(StudentBean bean) throws Exception{
		//log.debug("model update started");
	 CollegeBean bean1=CollegeModel.findByPK(bean.getCollegeID());
	 bean.setCollegeName(bean1.getName());
		try {
			conn=JDBCDataSource.getConnection();
			stmt=conn.prepareStatement("update  student set first_name=?,last_name=?,college_id=?,college_name=?,date_of_birth=?,mobile_no=?,email=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			stmt.setString(1, bean.getFirstName());
			stmt.setString(2, bean.getLastName());
			stmt.setLong(3, bean.getCollegeID());
			stmt.setString(4, bean.getCollegeName());
			stmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
			stmt.setString(6, bean.getMobileNo());
			stmt.setString(7, bean.getEmail());
			stmt.setString(8, bean.getCreatedBy());
		     stmt.setString(9, bean.getModifiedBy());
		     stmt.setTimestamp(10, bean.getCreatedDatetime());
		     stmt.setTimestamp(11, bean.getModifiedDatetime());
			stmt.setLong(12, bean.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			//log.error("update database error",e);
			e.printStackTrace();
		}
		JDBCDataSource.closeConnection(conn);
		//log.debug("model update ended");
	}
 
 /**
  * Search Student with Pagination.
  *
  * @param bean the bean
  * @param pageNo the page no
  * @param pageSize the page size
  * @return List of Student
  */
public ArrayList<StudentBean> search(StudentBean bean, int pageNo, int pageSize) {
	    //log.debug("Model search Started");
	    StringBuffer sql = new StringBuffer("select * from student where 1=1");

	    if (bean != null) {
	        if (bean.getId() > 0) {
	            sql.append(" AND id = " + bean.getId());
	        }
	        if (bean.getCollegeID() > 0) {
	            sql.append(" AND college_id = " + bean.getCollegeID());
	        }
	        if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
	            sql.append(" AND FIRST_NAME like '" + bean.getFirstName() + "%'");
	        }
	        if (bean.getLastName() != null && bean.getLastName().length() > 0) {
	            sql.append(" AND LAST_NAME like '" + bean.getLastName() + "%'");
	        }
	        if (bean.getEmail() != null && bean.getEmail().length() > 0) {
	            sql.append(" AND EMAIL like '" + bean.getEmail() + "%'");
	        }
	        if (bean.getCollegeName() != null && bean.getCollegeName().length() > 0) {
	            sql.append(" AND PASSWORD like '" + bean.getCollegeName() + "%'");
	        }
	        if (bean.getDob() != null && bean.getDob().getDate() > 0) {
	            sql.append(" AND DOB = " + bean.getDob());
	        }
	        if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
	            sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
	        }
	        
	        if (bean.getCreatedBy() != null
	                && bean.getCreatedBy().length() > 0) {
	            sql.append(" AND REGISTERED_IP like '" + bean.getCreatedBy() + "%'");
	        }
	        if (bean.getModifiedBy() != null
	                && bean.getModifiedBy().length() > 0) {
	            sql.append(" AND LAST_LOGIN_IP like '" + bean.getModifiedBy()+ "%'");
	        }
	       

	    }

	    // if page size is greater than zero then apply pagination
	    if (pageSize > 0) {
	        // Calculate start record index
	        pageNo = (pageNo - 1) * pageSize;

	        sql.append(" Limit " + pageNo + ", " + pageSize);
	        // sql.append(" limit " + pageNo + "," + pageSize);
	    }

	    System.out.println(sql);
	    ArrayList<StudentBean> list = new ArrayList<StudentBean>();
	    Connection conn = null;
	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            bean = new StudentBean();
	            bean.setId(rs.getInt(1));
	            bean.setCollegeID(rs.getLong(2));
	            bean.setCollegeName(rs.getString(3));
	            bean.setFirstName(rs.getString(4));
	            bean.setLastName(rs.getString(5));
	            bean.setDob(rs.getDate(6));
	            bean.setMobileNo(rs.getString(7));
	            bean.setEmail(rs.getString(8));
	            bean.setCreatedBy(rs.getString(9));
	            bean.setModifiedBy(rs.getString(10));
	            bean.setCreatedDatetime(rs.getTimestamp(11));
	            bean.setModifiedDatetime(rs.getTimestamp(12));

	            list.add(bean);
	        }
	        rs.close();
	    } catch (Exception e) {
	        //log.error("Database Exception..", e);
	    	e.printStackTrace();
	        System.out.println("Exception : Exception in search user");
	    } finally {
	        JDBCDataSource.closeConnection(conn);
	    }

	    //log.debug("Model search End");
	    return list;
	}
 
 /**
  * Getting list of Student with Pagination.
  *
  * @param pageNo the page no
  * @param pageSize the page size
  * @return List of Student
  */
public ArrayList<StudentBean> list(int pageNo,int pageSize){
		ArrayList<StudentBean> list=new ArrayList<StudentBean>();
		StudentBean bean=null;
		StringBuffer sql=new StringBuffer("select * from student where 1=1");
		 if (pageSize > 0) {
	            pageNo = (pageNo - 1) * pageSize;
	            sql.append(" limit " + pageNo + "," + pageSize);
	        }
		 try{
				try {
					conn=JDBCDataSource.getConnection();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    stmt=conn.prepareStatement(sql.toString());
			    rs=stmt.executeQuery();
			    
			    while(rs.next()){
			    	bean = new StudentBean();
		            bean.setId(rs.getInt(1));
		            bean.setCollegeID(rs.getLong(2));
		            bean.setCollegeName(rs.getString(3));
		            bean.setFirstName(rs.getString(4));
		            bean.setLastName(rs.getString(5));
		            bean.setDob(rs.getDate(6));
		            bean.setMobileNo(rs.getString(7));
		            bean.setEmail(rs.getString(8));
		            bean.setCreatedBy(rs.getString(9));
		            bean.setModifiedBy(rs.getString(10));
		            bean.setCreatedDatetime(rs.getTimestamp(11));
		            bean.setModifiedDatetime(rs.getTimestamp(12));

	list.add(bean);
			    }
			}catch(SQLException e){
				//log.error("search list database error",e);
			}
		 JDBCDataSource.closeConnection(conn);
	return list;
	}

	/**
	 * Getting list of Student.
	 *
	 * @return List of Student
	 */
	public ArrayList<StudentBean> list(){
		return list(0,0);
	}

 
}

