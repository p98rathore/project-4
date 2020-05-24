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

import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.util.JDBCDataSource;


// TODO: Auto-generated Javadoc
/**
 * JDBC Implementation of Role Model.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (c) proxy
 */ 
public class RoleModel {
	
	/** The conn. */
	Connection conn=null;
	
	/** The rs. */
	ResultSet rs=null;
	
	/** The stmt. */
	PreparedStatement stmt=null;
    
	
/**
 * Getting next primary key.
 *
 * @return primary key
 */
public int nextPK(){
    	int id=0;
    	try{
    		try {
				conn=JDBCDataSource.getConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		stmt=conn.prepareStatement("select max(ID) from st_role");
    		rs=stmt.executeQuery();
    		while(rs.next()){
    			id=rs.getInt(1);
    		}
    	}catch(SQLException e){
    		e.printStackTrace();
    	}
    	return id+1;
    }

/**
 *  Add a role.
 *
 * @param b the b
 * @return long
 * @throws DuplicateRecordException the duplicate record exception
 */
public long add(RoleBean b) throws DuplicateRecordException{
	int id=nextPK();
	RoleBean duplicataRole = findByName(b.getName());
    // Check if create Role already exist
    
	if (duplicataRole != null) {
        throw new DuplicateRecordException("role already exist");
    }
	try{
		
		try {
			conn=JDBCDataSource.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stmt=conn.prepareStatement("insert into st_role values(?,?,?,?,?,?,?)");
	    stmt.setInt(1, id);
	    stmt.setString(2, b.getName());
	    stmt.setString(3,b.getDescription());
	    stmt.setString(4, b.getCreatedBy());
	    stmt.setString(5, b.getModifiedBy());
	    stmt.setTimestamp(6, b.getCreatedDatetime());
	    stmt.setTimestamp(7, b.getModifiedDatetime());
	   id= stmt.executeUpdate();
	}catch(SQLException e){
		e.printStackTrace();
	}
	JDBCDataSource.closeConnection(conn);
	return id;
}

/**
 *   Delete a role.
 *
 * @param bean the bean
 */
public void delete(RoleBean bean){
	 try{
		 try {
			conn=JDBCDataSource.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 stmt=conn.prepareStatement("delete from st_role where id=?");
		 stmt.setLong(1, bean.getId());
		 stmt.executeUpdate();
		 
	 }catch(SQLException e){
		 e.printStackTrace();
	 }
	 JDBCDataSource.closeConnection(conn);
}

/**
 * find a Role By name.
 *
 * @param name the name
 * @return RoleBean
 */
public RoleBean findByName(String name){
//	log.debug("model findbylogin starts");
	RoleBean b=new RoleBean();
	try{
		conn=JDBCDataSource.getConnection();
		stmt=conn.prepareStatement("select * from st_user where email=?");
		
		stmt.setString(1,name);
		rs=stmt.executeQuery();
		while(rs.next()){
			System.out.println("ok");
			b.setId(rs.getInt(1));
			b.setName(rs.getString(2));
			b.setDescription(rs.getString(3));
			b.setCreatedBy(rs.getString(4));
			b.setModifiedBy(rs.getString(5));
			b.setCreatedDatetime(rs.getTimestamp(6));
			b.setModifiedDatetime(rs.getTimestamp(7));
		}
		
	}catch(Exception e)
	{
		
		e.printStackTrace();
	}

	JDBCDataSource.closeConnection(conn);
	//log.debug("model findbylogin end");
	if(b.getName()==null)
		return null;
	else
	return b;
}

/**
 * Find Role By PrimaryKey.
 *
 * @param pk the pk
 * @return RoleBean
 */
public RoleBean findByPK(long pk){
		//log.debug("model findbyPk started");
		RoleBean b=new RoleBean();
	    	try{
	    		conn=JDBCDataSource.getConnection();
	    		stmt=conn.prepareStatement("select * from st_role where id=?");
	    		stmt.setLong(1, pk);
	    		rs=stmt.executeQuery();
	    		while(rs.next()){
	    			b.setId(rs.getInt(1));
	    			b.setName(rs.getString(2));
	    			b.setDescription(rs.getString(3));
	    			b.setCreatedBy(rs.getString(4));
	    			b.setModifiedBy(rs.getString(5));
	    			b.setCreatedDatetime(rs.getTimestamp(6));
	    			b.setModifiedDatetime(rs.getTimestamp(7));
	    			
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
 *  Update a ROLE.
 *
 * @param bean the bean
 */
public void update(RoleBean bean){
		//log.debug("model update started");
		try {
			try {
				conn=JDBCDataSource.getConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			stmt=conn.prepareStatement("update  st_role set name=?,description=? where id=?");
			stmt.setString(1, bean.getName());
			stmt.setString(2, bean.getDescription());
			//stmt.setString(3, bean.getPassword());
			stmt.setLong(3, bean.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			//log.error("update database error",e);
			e.printStackTrace();
		}
		JDBCDataSource.closeConnection(conn);
		//log.debug("model update ended");
	}

/**
 * Search a role with pagination.
 *
 * @param bean the bean
 * @param pageNo the page no
 * @param pageSize the page size
 * @return List of Role
 */
public ArrayList<RoleBean> search(RoleBean bean, int pageNo, int pageSize) {
	    //log.debug("Model search Started");
	    StringBuffer sql = new StringBuffer("select * from st_role where 1=1");

	    if (bean != null) {
	        if (bean.getId() > 0) {
	            sql.append(" AND id = " + bean.getId());
	        }
	        if (bean.getName() != null && bean.getName().length() > 0) {
	            sql.append(" AND NAME like '" + bean.getName() + "%'");
	        }
	        if (bean.getDescription() != null && bean.getDescription().length() > 0) {
	            sql.append(" AND description like '" + bean.getDescription() + "%'");
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
	    ArrayList<RoleBean> list = new ArrayList<RoleBean>();
	    Connection conn = null;
	    try {
	        conn = JDBCDataSource.getConnection();
	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
	        ResultSet rs = pstmt.executeQuery();
	        while (rs.next()) {
	            bean = new RoleBean();
	            bean.setId(rs.getInt(1));
	            bean.setName(rs.getString(2));
	            bean.setDescription(rs.getString(3));
	            bean.setCreatedBy(rs.getString(4));
	            bean.setModifiedBy(rs.getString(5));
	            bean.setCreatedDatetime(rs.getTimestamp(6));
	            bean.setModifiedDatetime(rs.getTimestamp(7));

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
 * Getting list of role with pagination.
 *
 * @param pageNo the page no
 * @param pageSize the page size
 * @return List of Role
 */
public List list(int pageNo,int pageSize){
		ArrayList<RoleBean> list=new ArrayList<RoleBean>();
		RoleBean bean=null;
		StringBuffer sql=new StringBuffer("select * from st_role where 1=1");
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
			    	bean = new RoleBean();
		            bean.setId(rs.getInt(1));
		            bean.setName(rs.getString(2));
		            bean.setDescription(rs.getString(3));
		            bean.setCreatedBy(rs.getString(4));
		            bean.setModifiedBy(rs.getString(5));
		            bean.setCreatedDatetime(rs.getTimestamp(6));
		            bean.setModifiedDatetime(rs.getTimestamp(7));

	            list.add(bean);
			    }
			}catch(SQLException e){
				//log.error("search list database error",e);
			}
		 JDBCDataSource.closeConnection(conn);
	return list;
	}

	/**
	 *  getting list of Role.
	 *
	 * @return list of role
	 */
	public List list(){
		return list(0,0);
	}



}
