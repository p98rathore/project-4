/*
 * 
 */
package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 *  JDBC Implementation of CollegeModel.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (c) proxy
 */
public class CollegeModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(CollegeModel.class);
	
	/**
	 * Find next PK of College.
	 *
	 * @return long
	 * @throws SQLException the SQL exception
	 */
	public static long nextpk() throws SQLException{
		log.debug("college model next pk start");

		long id=0;
		Connection con =null;
		PreparedStatement ps= null;
		ResultSet rs= null;
		try {
			con= JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			 ps= con.prepareStatement("select max(id) from st_college");
			 rs=ps.executeQuery();
			 con.commit();
			while(rs.next()){
				id=rs.getInt(1);
			}
			rs.close();
			ps.close();
			JDBCDataSource.closeConnection(con);
			
		} catch (Exception e) {
			log.error("student model pk database exception");
			con.rollback();
			e.printStackTrace();
		} 
		
		return id+1;
		}
	
	/**
	 * Add a college.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws DatabaseException the database exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	public static long add(CollegeBean bean) throws DatabaseException, DuplicateRecordException{
		log.debug("student model add start");
		
		Connection con = null;
		PreparedStatement ps= null;
		long l=0;
		 CollegeBean duplicateCollegeName = findByName(bean.getName());

	        if (duplicateCollegeName != null) {
	            throw new DuplicateRecordException("College Name already exists");
	        }
		
		
		try {
			con= JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps=con.prepareStatement("insert into st_college values(?,?,?,?,?,?,?,?,?,?)");
			ps.setLong(1, nextpk());
			 ps.setString(2, bean.getName());
	            ps.setString(3, bean.getAddress());
	            ps.setString(4, bean.getState());
	            ps.setString(5, bean.getCity());
	            ps.setString(6, bean.getPhoneNo());
	            ps.setString(7, bean.getCreatedBy());
	            ps.setString(8, bean.getModifiedBy());
	            ps.setTimestamp(9, bean.getCreatedDatetime());
	            ps.setTimestamp(10, bean.getModifiedDatetime());
	            l=ps.executeUpdate();
	            con.commit();
	            ps.close();// En
			
		} catch (Exception e) {
			log.error("college model add database exception");
			
			e.printStackTrace();
		}
		JDBCDataSource.closeConnection(con);
		log.debug("college model add end");
		return l;
		
	}
	
	/**
	 * Delete a college.
	 *
	 * @param bean the bean
	 * @throws SQLException the SQL exception
	 */
	public static void delete(CollegeBean bean) throws SQLException{
		String err=null;
		log.debug("collegemodel delete start");
		System.out.println("model colleg dllt");
		Connection con= null;
		PreparedStatement ps= null;
	 try {
		con = JDBCDataSource.getConnection();
		con.setAutoCommit(false);
		ps= con.prepareStatement("delete from st_college where id=?");
		ps.setLong(1, bean.getId());
		int i=ps.executeUpdate();
		ps.close();
		con.commit();
		System.out.println("jkk "+i);
		
	} catch (Exception e) {
		
		log.error("clg model catch block ");
		e.printStackTrace();
		if(e.getMessage().equals("MySQLIntegrityConstraintViolationException")){
			err="msg";
		}
		con.rollback();
	}
	 log.debug("colg model delete end");
	 JDBCDataSource.closeConnection(con);
	
	}
	
	/**
	 * Find user by college name.
	 *
	 * @param name the name
	 * @return collgebean
	 */
	public static CollegeBean findByName(String name){
		Connection con =null;
		PreparedStatement ps= null;
	
		CollegeBean bean=null;
		
				try {
					con = JDBCDataSource.getConnection();
					con.setAutoCommit(false);
					 ps = con.prepareStatement("select * from st_college where name=?");
			            ps.setString(1, name);
			           ResultSet rs = ps.executeQuery();
			            while (rs.next()) {
		      bean = new CollegeBean();
			   bean.setId(rs.getLong(1));
			    bean.setName(rs.getString(2));
			      bean.setAddress(rs.getString(3));
			       bean.setState(rs.getString(4));
			        bean.setCity(rs.getString(5));
			       bean.setPhoneNo(rs.getString(6));
			      bean.setCreatedBy(rs.getString(7));
			     bean.setModifiedBy(rs.getString(8));
			    bean.setCreatedDatetime(rs.getTimestamp(9));
			   bean.setModifiedDatetime(rs.getTimestamp(10));}
			            ps.close();
			            rs.close();
			            con.commit();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
            JDBCDataSource.closeConnection(con);
      
		return bean;
		
	}
	 
 	/**
 	 * Find User by College Primary key.
 	 *
 	 * @param pk            : get parameter
 	 * @return bean
 	 * @throws SQLException the SQL exception
 	 */
    public static CollegeBean findByPK(long pk) throws SQLException   {
        log.debug("Model findByPK Started");
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM ST_COLLEGE WHERE ID=?");
        CollegeBean bean = null;
        Connection conn = null;
        try {

            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bean = new CollegeBean();
                bean.setId(rs.getLong(1));
                bean.setName(rs.getString(2));
                bean.setAddress(rs.getString(3));
                bean.setState(rs.getString(4));
                bean.setCity(rs.getString(5));
                bean.setPhoneNo(rs.getString(6));
                bean.setCreatedBy(rs.getString(7));
                bean.setModifiedBy(rs.getString(8));
                bean.setCreatedDatetime(rs.getTimestamp(9));
                bean.setModifiedDatetime(rs.getTimestamp(10));

            }
            
            pstmt.close();
            rs.close();
            conn.commit();
        } catch (Exception e) {
        	conn.rollback();
            log.error("Database Exception..", e);
           
        } 
            JDBCDataSource.closeConnection(conn);
        
        log.debug("Model findByPK End");
        return bean;
    }
    
/**
 * Upadate a college record.
 *
 * @param bean the bean
 * @throws RecordNotFoundException the record not found exception
 * @throws SQLException the SQL exception
 */
public static void update(CollegeBean bean) throws RecordNotFoundException, SQLException{
	log.debug("Model update Started");
    Connection con = null;
    PreparedStatement ps=null;
    ResultSet rs= null;
    CollegeBean bean2= findByName(bean.getName());
//    if(bean2!=null){
//    	throw new RecordNotFoundException("college name doesnt exist");
//    }
    try {
		con= JDBCDataSource.getConnection();
		con.setAutoCommit(false);
		ps=con.prepareStatement("UPDATE ST_COLLEGE SET NAME=?,ADDRESS=?,STATE=?,CITY=?,PHONE_NO=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
		  ps.setString(1, bean.getName());
          ps.setString(2, bean.getAddress());
          ps.setString(3, bean.getState());
          ps.setString(4, bean.getCity());
          ps.setString(5, bean.getPhoneNo());
          ps.setString(6, bean.getCreatedBy());
          ps.setString(7, bean.getModifiedBy());
          ps.setTimestamp(8, bean.getCreatedDatetime());
          ps.setTimestamp(9, bean.getModifiedDatetime());
          ps.setLong(10, bean.getId());
          ps.executeUpdate();
          ps.close();
          con.commit();
	} catch (Exception e) {
		con.rollback();
		log.error("model update catch block");
		e.printStackTrace();
	}
    JDBCDataSource.closeConnection(con);
    log.debug("model update end");
    
    }
    
    /**
     * Search college with pagination.
     *
     * @param bean the bean
     * @param pageNo the page no
     * @param pageSize the page size
     * @return List of user
     * @throws ApplicationException the application exception
     */
    public static List search(CollegeBean bean,int pageNo,int pageSize) throws ApplicationException{
    	   log.debug("Model search Started");
           StringBuffer sql = new StringBuffer(
                   "SELECT * FROM ST_COLLEGE WHERE 1=1");

           if(bean!=null){
        	   if(bean.getId()>0){
        		  sql.append(" AND ID="+bean.getId()) ;
        	   }
        	   if(bean.getName()!=null&&bean.getName().length()>0){
        		   sql.append(" AND NAME LIKE '"+bean.getName()+"%'");
        	   }
//        	   if(bean.getPhoneNo()!=null&&bean.getPhoneNo().length()>0){
//        		   sql.append(" AND PHONE_NO LIKE '"+bean.getPhoneNo()+"'%");
//        	   }
//        	   if(bean.getAddress()!=null&&bean.getAddress().length()>0){
//        		   sql.append(" AND ADDRESS LIKE '"+bean.getAddress()+"%'");
//        	   }
        		   if(bean.getCity()!=null&&bean.getCity().length()>0){
        			   sql.append(" AND CITY LIKE '"+bean.getCity()+"%'");
        		   }
//        		   if(bean.getCreatedBy()!=null&&bean.getCreatedBy().length()>0){
//        			   sql.append(" AND CREATED_BY LIKE '"+bean.getCreatedBy()+"%'");
//        		   }
//        		   
//        		   if(bean.getState()!=null&&bean.getState().length()>0){
//        			   sql.append(" AND STATE LIKE '"+bean.getState()+"%'");
//        		   }
        	   }
           
           if(pageSize>0){
        	   pageNo=(pageNo-1)*pageSize;
        	   sql.append(" LIMIT "+pageNo+","+pageSize);
           }
System.out.println(sql);
           List<CollegeBean> list = new ArrayList<CollegeBean>();
           Connection conn = null;
           try {
               conn = JDBCDataSource.getConnection();
               PreparedStatement pstmt = conn.prepareStatement(sql.toString());
               ResultSet rs = pstmt.executeQuery();
               while (rs.next()) {
                   bean = new CollegeBean();
                   bean.setId(rs.getLong(1));
                   bean.setName(rs.getString(2));
                   bean.setAddress(rs.getString(3));
                   bean.setState(rs.getString(4));
                   bean.setCity(rs.getString(5));
                   bean.setPhoneNo(rs.getString(6));
                   bean.setCreatedBy(rs.getString(7));
                   bean.setModifiedBy(rs.getString(8));
                   bean.setCreatedDatetime(rs.getTimestamp(9));
                   bean.setModifiedDatetime(rs.getTimestamp(10));
                   list.add(bean);
               }
               rs.close();
           } catch (Exception e) {
               log.error("Database Exception..", e);
               throw new ApplicationException(
                       "Exception : Exception in search college");
           } finally {
               JDBCDataSource.closeConnection(conn);
           }

           log.debug("Model search End");
           return list;
       }
    
    /**
     * Search.
     *
     * @param bean the bean
     * @return the list
     * @throws ApplicationException the application exception
     */
    public static List search(CollegeBean bean) throws ApplicationException{
		return search(bean, 0, 0);
    	
    }
    
    /**
     * Get List of College.
     *
     * @return list : List of College
     * @throws ApplicationException the application exception
     * @throws SQLException the SQL exception
     */
    public static List list() throws ApplicationException, SQLException {
        return list(0, 0);
    }

    /**
     * Get List of College with pagination.
     *
     * @param pageNo            : Current Page No.
     * @param pageSize            : Size of Page
     * @return list : List of College
     * @throws ApplicationException the application exception
     * @throws SQLException the SQL exception
     */
    public static List list(int pageNo, int pageSize) throws ApplicationException, SQLException {
        log.debug("Model list Started");
       Connection con =null;
       ResultSet rs= null;
     //  CollegeBean bean= new CollegeBean();
       StringBuffer sql=new StringBuffer("SELECT * FROM ST_COLLEGE ");
       List<CollegeBean> list= new ArrayList<CollegeBean>();
    	if(pageSize>0){
    		pageNo=(pageNo-1)*pageSize;
    		sql.append(" LIMIT  "+pageNo+","+pageSize);
    	}
    	
    	System.out.println(sql);
    	
    	try {
			con=JDBCDataSource.getConnection();
			con.setAutoCommit(false);
		PreparedStatement	ps=con.prepareStatement(sql.toString());
		rs=ps.executeQuery();
		while(rs.next()){
		      CollegeBean bean = new CollegeBean();
              bean.setId(rs.getLong(1));
              bean.setName(rs.getString(2));
              bean.setAddress(rs.getString(3));
              bean.setState(rs.getString(4));
              bean.setCity(rs.getString(5));
              bean.setPhoneNo(rs.getString(6));
              bean.setCreatedBy(rs.getString(7));
              bean.setModifiedBy(rs.getString(8));
              bean.setCreatedDatetime(rs.getTimestamp(9));
              bean.setModifiedDatetime(rs.getTimestamp(10));
              list.add(bean);
		}
			ps.close();
			con.commit();
			rs.close();
		} catch (Exception e) {
			log.error("model list database "+e);
			con.rollback();
			e.printStackTrace();
		}
    	JDBCDataSource.closeConnection(con);
		return list;
    }
}	

