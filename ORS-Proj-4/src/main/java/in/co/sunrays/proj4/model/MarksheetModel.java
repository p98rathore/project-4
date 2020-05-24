/*
 * 
 */
package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.MarksheetBean;
import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 * JDBC Implementation of Markseet Model.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (c) proxy
 */
public class MarksheetModel {

	/** The log 2. */
	private static Logger log2 = Logger.getLogger(MarksheetModel.class);
	
	/**
	 * Geting next primary key of Marksheet.
	 *
	 * @return next primary key of Marksheet
	 */
	public static Integer nextPK()
	{
		Integer pk=0;
		ResultSet rs;
		try {
			Connection con= JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("SELECT MAX(ID) FROM ST_MARKSHEET");
		    rs=ps.executeQuery();
		    con.commit();
		    while(rs.next())
		    {
		    	pk = rs.getInt(1);
		    }
           ps.close();
           rs.close();
           JDBCDataSource.closeConnection(con);
 		} catch (Exception e) {
			e.printStackTrace();
		}
		return pk+1;
	}

	/**
	 *  
	 * Add a marksheet.
	 *
	 * @param bean the bean
	 * @return long
	 * @throws Exception the exception
	 */
	public static long add(MarksheetBean bean) throws Exception
	{
		log2.debug("add started");
		Connection con=null;
		PreparedStatement ps =null;
		  // get Student Name
        StudentModel sModel = new StudentModel();
        StudentBean studentbean = sModel.findByPK(bean.getStudentId());
        bean.setName(studentbean.getFirstName() + " "
                + studentbean.getLastName());
        System.out.println(bean.getName());
        System.out.println(bean.getRollNo());
        MarksheetBean duplicateMarksheet=null;
         duplicateMarksheet = findByRollNo(bean.getRollNo());
        int pk = 0;

        if (duplicateMarksheet != null) {
            throw new DuplicateRecordException("Roll Number already exists");
        }
		long l =0;
		
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement("INSERT INTO ST_MARKSHEET VALUES(?,?,?,?,?,?,?,?,?,?,?)");
			ps.setInt(1, MarksheetModel.nextPK());
			ps.setString(2, bean.getRollNo());
			ps.setLong(3, bean.getStudentId());
			ps.setString(4, bean.getName());
			ps.setInt(5, bean.getPhysics());
			ps.setInt(6, bean.getChemistry());
			ps.setInt(7, bean.getMaths());
			ps.setString(8, bean.getCreatedBy());
			ps.setString(9, bean.getModifiedBy());
			ps.setTimestamp(10, bean.getCreatedDatetime());
			ps.setTimestamp(11, bean.getModifiedDatetime());
			l = ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
		ps.close();
		log2.debug("add completed");
		return l;	
	}
	
	/**
	 * Delete a marksheet.
	 *
	 * @param bean the bean
	 * @throws Exception the exception
	 */
	public static void Delete(MarksheetBean bean) throws Exception
	{
      log2.debug("delete started");
      Connection con=null;
      PreparedStatement ps = null;
      try {
		con = JDBCDataSource.getConnection();
		con.setAutoCommit(false);
		ps = con.prepareStatement("DELETE FROM ST_MARKSHEET WHERE ID=?");
		ps.setLong(1, bean.getId());
		ps.executeUpdate();
		con.commit();
	} catch (Exception e) {
		e.printStackTrace();
		con.rollback();
	}
      ps.close();
      JDBCDataSource.closeConnection(con);
      log2.debug("Record Deleted");
 }

	  /**
  	 * find Marksheet by roll no.
  	 *
  	 * @param rollNo the roll no
  	 * @return MarksheetBean
  	 * @throws ApplicationException the application exception
  	 */
	public static MarksheetBean findByRollNo(String rollNo) throws ApplicationException
	    {
	    	log2.debug("findByRollNo started");
	    	MarksheetBean bean=null;
	    	Connection con=null;
	    	PreparedStatement ps=null;
	    	ResultSet rs=null;
	    	try {
				con = JDBCDataSource.getConnection();
				con.setAutoCommit(false);
				ps = con.prepareStatement("SELECT * FROM ST_MARKSHEET WHERE ROLL_NO=?");
				ps.setString(1, rollNo);
				rs = ps.executeQuery();
				while(rs.next())
				{
					bean = new MarksheetBean();
					bean.setId(rs.getInt(1));
					bean.setRollNo(rs.getString(2));
					bean.setStudentId(rs.getLong(3));
					bean.setName(rs.getString(4));
					bean.setPhysics(rs.getInt(5));
					bean.setChemistry(rs.getInt(6));
					bean.setMaths(rs.getInt(7));
					bean.setCreatedBy(rs.getString(8));
					bean.setModifiedBy(rs.getString(9));
					bean.setCreatedDatetime(rs.getTimestamp(10));
					bean.setModifiedDatetime(rs.getTimestamp(11));
				}
				con.commit();
				rs.close();
				ps.close();
				log2.debug("findByRollNo done");
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException("exception in marksheet model  findByRollNo"+e.getMessage());
			}
	        JDBCDataSource.closeConnection(con);
	        log2.debug("findByRollNo completed");
	     return bean;
	    }
		
	
    /**
     * Find Marksheet by Pk.
     *
     * @param pk the pk
     * @return MarksheetBean
     * @throws Exception the exception
     */
    public static MarksheetBean findByPK(long pk) throws Exception
    {
    	log2.debug("findByPK started");
    	MarksheetBean bean = new MarksheetBean();
    	Connection con=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement("SELECT * FROM ST_MARKSHEET WHERE ID=?");
			ps.setLong(1, pk);
			rs = ps.executeQuery();
			while(rs.next())
			{
				bean.setId(rs.getInt(1));
				bean.setRollNo(rs.getString(2));
				bean.setStudentId(rs.getLong(3));
				bean.setName(rs.getString(4));
				bean.setPhysics(rs.getInt(5));
				bean.setChemistry(rs.getInt(6));
				bean.setMaths(rs.getInt(7));
				bean.setCreatedBy(rs.getString(8));
				bean.setModifiedBy(rs.getString(9));
				bean.setCreatedDatetime(rs.getTimestamp(10));
				bean.setModifiedDatetime(rs.getTimestamp(11));
			}
			con.commit();
			log2.debug("findByPK done");
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
        rs.close();
        ps.close();
        JDBCDataSource.closeConnection(con);
        log2.debug("findByPK completed");
    	return bean;
    }
    
    /**
     * Update a Marksheet.
     *
     * @param bean the bean
     * @throws Exception the exception
     */
    public static void update(MarksheetBean bean) throws Exception
    {
    	 StudentModel sModel = new StudentModel();
         StudentBean studentbean = sModel.findByPK(bean.getStudentId());
         bean.setName(studentbean.getFirstName() + " "
                 + studentbean.getLastName());
//    	MarkSheetBean b = findByRollNo(bean.getRollNo());
//    	if(b!=null)
//    	{
//    		throw new DuplicateRecordException("Roll No is already exist");	
//    	}
    	Connection con=null;
    	PreparedStatement ps=null;
    	try{
    	con = JDBCDataSource.getConnection();
    	con.setAutoCommit(false);
    	ps = con.prepareStatement("UPDATE ST_MARKSHEET SET ROLL_NO=?,STUDENT_ID=?,NAME=?,PHYSICS=?,CHEMISTRY=?,MATHS=?,CREATED_BY=?"
    	  		+ ",MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
    	    ps.setString(1, bean.getRollNo());
			ps.setLong(2, bean.getStudentId());
			ps.setString(3, bean.getName());
			ps.setInt(4, bean.getPhysics());
			ps.setInt(5, bean.getChemistry());
			ps.setInt(6, bean.getMaths());
			ps.setString(7, bean.getCreatedBy());
			ps.setString(8, bean.getModifiedBy());
			ps.setTimestamp(9, bean.getCreatedDatetime());
			ps.setTimestamp(10, bean.getModifiedDatetime());
			ps.setLong(11, bean.getId());
			ps.executeUpdate();
			con.commit();
			ps.close();
    	}catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
    }
    
    /**
     * search Marksheet.
     *
     * @param bean the bean
     * @return list of Marksheet
     * @throws Exception the exception
     */
    public static List search(MarksheetBean bean) throws Exception
    {
      return search(bean, 0, 0);
    }
    
    /**
     *  
     * Search Marksheet with pagination.
     *
     * @param bean the bean
     * @param pageNo the page no
     * @param pageSize the page size
     * @return list of Marksheet
     * @throws Exception the exception
     */
    public static List search(MarksheetBean bean,int pageNo,int pageSize) throws Exception
    {
    	List<MarksheetBean> list = new ArrayList<MarksheetBean>();
    	Connection con=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE true");

    	if(bean!=null)
    	{
    		if(bean.getId()>0)
    		{
    		  sql.append(" AND ID="+bean.getId());
    		}
    	    if(bean.getRollNo()!=null && bean.getRollNo().length()>0)
    	    {
    	      sql.append(" AND ROLL_NO LIKE '"+bean.getRollNo()+"%'");	
    	    }
    	    if (bean.getName() != null && bean.getName().length() > 0) {
                sql.append(" AND name like '" + bean.getName() + "%'");
            }
            if (bean.getPhysics() != null && bean.getPhysics() > 0) {
                sql.append(" AND physics = " + bean.getPhysics());
            }
            if (bean.getChemistry() != null && bean.getChemistry() > 0) {
                sql.append(" AND chemistry = " + bean.getChemistry());
            }
            if (bean.getMaths() != null && bean.getMaths() > 0) {
                sql.append(" AND maths = '" + bean.getMaths());
            }
    	}
    	if(pageSize>0)
    	{
    		pageNo=(pageNo-1)*pageSize;
    		sql=sql.append(" LIMIT "+pageNo+","+pageSize);
    		System.out.println(sql);
    	}  	
    	try {
			con=JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				MarksheetBean mb = new MarksheetBean();
				mb.setId(rs.getLong(1));
				mb.setRollNo(rs.getString(2));
				mb.setStudentId(rs.getLong(3));
				mb.setName(rs.getString(4));
				mb.setPhysics(rs.getInt(5));
				mb.setChemistry(rs.getInt(6));
				mb.setMaths(rs.getInt(7));
				mb.setCreatedBy(rs.getString(8));
				mb.setModifiedBy(rs.getString(9));
				mb.setCreatedDatetime(rs.getTimestamp(10));
				mb.setModifiedDatetime(rs.getTimestamp(11));
				list.add(mb);
			}
			con.commit();
			ps.close();
			rs.close();
			JDBCDataSource.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}
    	
		return list;
    }
    
    /**
     *  Getting list of Marksheet.
     *
     * @return List of Marksheet
     * @throws Exception the exception
     */
    public static List list() throws Exception
    {
    	return list(0, 0);
    }
    
    /**
     * Getting list with pagination.
     *
     * @param pageNo the page no
     * @param pageSize the page size
     * @return list of Marksheet
     * @throws Exception the exception
     */
    public static List list(int pageNo,int pageSize) throws Exception
    {
    	List<MarksheetBean> list = new ArrayList<MarksheetBean>();
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_MARKSHEET WHERE true");
    	
    	if(pageSize>0)
    	{
    		pageNo = (pageNo-1)*pageSize;
    		sql.append(" LIMIT "+pageNo+","+pageSize);
    	}
    	Connection con = null;
    	PreparedStatement ps=null;
    	ResultSet rs =null;
    	try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
				MarksheetBean mb = new MarksheetBean();
				mb.setId(rs.getLong(1));
				mb.setRollNo(rs.getString(2));
				mb.setStudentId(rs.getLong(3));
				mb.setName(rs.getString(4));
				mb.setPhysics(rs.getInt(5));
				mb.setChemistry(rs.getInt(6));
				mb.setMaths(rs.getInt(7));
				mb.setCreatedBy(rs.getString(8));
				mb.setModifiedBy(rs.getString(9));
				mb.setCreatedDatetime(rs.getTimestamp(10));
				mb.setModifiedDatetime(rs.getTimestamp(11));
				list.add(mb);
			}
			con.commit();
			ps.close();
			rs.close();
			JDBCDataSource.closeConnection(con);
		} catch (Exception e) {
			e.printStackTrace();
			con.rollback();
		}	
    	return list;
    }
    
    /**
     *  
     * Getting marit list.
     *
     * @param pageNo the page no
     * @param pageSize the page size
     * @return list of Marksheet
     * @throws Exception the exception
     */
    
    public static List getMeritList(int pageNo, int pageSize) throws Exception
    {
    	List<MarksheetBean> list = new ArrayList<MarksheetBean>();
     StringBuffer sql = new StringBuffer
     ("SELECT `ID`,`ROLL_NO`, `NAME`, `PHYSICS`, `CHEMISTRY`, `MATHS`,"
      + "(PHYSICS+CHEMISTRY+MATHS)/3 as merit FROM ST_MARKSHEET where PHYSICS>33 AND CHEMISTRY>33 AND MATHS>33 ORDER BY"
      + " MERIT DESC ");
    	
      if(pageSize>0)
 	  {
 		pageNo = (pageNo-1)*pageSize;
 		sql.append(" LIMIT "+pageNo+","+pageSize);
 	  }
 	  Connection con = null;
 	  PreparedStatement ps=null;
 	  ResultSet rs =null;	
 	  
 	  try {
		con = JDBCDataSource.getConnection();
		con.setAutoCommit(false);
		ps = con.prepareStatement(sql.toString());
		rs = ps.executeQuery();
		while(rs.next())
		{
			MarksheetBean bean = new MarksheetBean();
            bean.setId(rs.getLong(1));
            bean.setRollNo(rs.getString(2));
            bean.setName(rs.getString(3));
            bean.setPhysics(rs.getInt(4));
            bean.setChemistry(rs.getInt(5));
            bean.setMaths(rs.getInt(6));
            list.add(bean);
		}
		con.commit();
		ps.close();
		rs.close();
		JDBCDataSource.closeConnection(con);
	} catch (Exception e) {
		e.printStackTrace();
		con.rollback();
	}
		return list;
    }
}
