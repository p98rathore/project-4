/*
 * 
 */
package in.co.sunrays.proj4.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.sunrays.proj4.bean.CourseBean;
import in.co.sunrays.proj4.bean.SubjectBean;
import in.co.sunrays.proj4.bean.TimetableBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 * JDBC Implementation of Timetable Model.
 *
 * @author Proxy
 * @version 1.0 Copyright (c) Proxy
 */
public class TimetableModel 
{
	
	/**
	 * Find next PK of Timetable.
	 *
	 * @return the long
	 * @throws ApplicationException the application exception
	 */
  public static long nextPK() throws ApplicationException
  {
	  long pk=0;
	  Connection con=null;
	  PreparedStatement ps=null;
	  ResultSet rs=null;
	  
	  try {
		con = JDBCDataSource.getConnection();
		con.setAutoCommit(false);
		ps = con.prepareStatement("SELECT MAX(ID) FROM ST_TIMETABLE");
		rs= ps.executeQuery();
		while(rs.next())
		{
			pk = rs.getInt(1);
		}
	} catch (Exception e) {
		e.printStackTrace();
		throw new ApplicationException("Exception in timeTable model nextPk..."+e.getMessage());
	} 
	return pk+1;
  }

  /**
   * Add a Timetable.
   *
   * @param bean the bean
   * @throws ApplicationException the application exception
   */
   public static void add(TimetableBean bean) throws ApplicationException
   {
	   Connection con=null;
	   PreparedStatement ps=null;
	   Date examDate = new Date(bean.getExamDate().getTime());
	   System.out.println("add date is "+bean.getExamDate());
	   System.out.println("add date is new  "+examDate);
	   
	   //get course Name and Subject Name by id
	   CourseModel Cmodel = new CourseModel();
	   CourseBean Cbean = null;
	   Cbean = Cmodel.findByPK(bean.getCourseId());
	   bean.setCourseName(Cbean.getName());
	   
	   SubjectModel Smodel = new SubjectModel();
	   SubjectBean Sbean = Smodel.findByPK(bean.getSubId());
	   bean.setSubName(Sbean.getName());
	   
		
	   try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement("INSERT INTO ST_TIMETABLE VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps.setLong(1, TimetableModel.nextPK());
			ps.setInt(2, bean.getSubId());
			ps.setString(3, bean.getSubName());
			ps.setLong(4, bean.getCourseId());
			ps.setString(5, bean.getCourseName());
			ps.setString(6, bean.getSemester());
			ps.setDate(7, examDate);
			ps.setString(8, bean.getExamTime());
			ps.setString(9, bean.getDescription());
			ps.setString(10, bean.getCreatedBy());
			ps.setString(11, bean.getModifiedBy());
			ps.setTimestamp(12, bean.getCreatedDatetime());
			ps.setTimestamp(13, bean.getModifiedDatetime());

			ps.executeUpdate();
			con.commit();
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model add..."+e.getMessage());
		}   
   }
  
   /**
    * Delete a Timetable.
    *
    * @param bean the bean
    * @throws ApplicationException the application exception
    */
   public static void delete(TimetableBean bean) throws ApplicationException 
	{
		System.out.println("TimeTableMode  Delete id is  -"+bean.getId());
		
		try {	
			Connection con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement("DELETE FROM ST_TIMETABLE WHERE ID=?");
			ps.setLong(1, bean.getId());
			ps.executeUpdate();
			con.commit();
			ps.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model delete..."+e.getMessage());
		}
	}
   
   /**
    * Update a Timetable.
    *
    * @param bean the bean
    * @throws ApplicationException the application exception
    */
   public static void update(TimetableBean bean) throws ApplicationException
   {

   	 Connection con=null;
   	 PreparedStatement ps=null;
   	 Date examDate = new Date(bean.getExamDate().getTime());
   	
       //get course Name and Subject Name by id
	   CourseModel Cmodel = new CourseModel();
	   CourseBean Cbean = Cmodel.findByPK(bean.getCourseId());
	   bean.setCourseName(Cbean.getName());
	   
	   SubjectModel Smodel = new SubjectModel();
	   SubjectBean Sbean = Smodel.findByPK(bean.getSubId());
	   bean.setSubName(Sbean.getName());
   	 
   	 try{
   	 con = JDBCDataSource.getConnection();
   	 con.setAutoCommit(false);
   	 ps = con.prepareStatement("UPDATE ST_TIMETABLE SET SUBJECT_ID=?,SUBJECT_NAME=?,"
   	 		+ "COURSE_ID=?,COURSE_NAME=?,SEMESTER=?,EXAM_DATE=?,EXAM_TIME=?,description=?,CREATED_BY=?"
   	  		+ ",MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
   	     	
	ps.setInt(1, bean.getSubId());
	ps.setString(2, bean.getSubName());
	ps.setLong(3, bean.getCourseId());
	ps.setString(4, bean.getCourseName());
	ps.setString(5, bean.getSemester());
	ps.setDate(6, examDate);
	ps.setString(7, bean.getExamTime());
	ps.setString(8, bean.getDescription());
	ps.setString(9, bean.getCreatedBy());
	ps.setString(10, bean.getModifiedBy());
	ps.setTimestamp(11, bean.getCreatedDatetime());
	ps.setTimestamp(12, bean.getModifiedDatetime());
	ps.setLong(13, bean.getId());
	  ps.executeUpdate();
	  con.commit();
			ps.close();
   	}catch (Exception e) 
   	{
   		throw new ApplicationException("Exception in timeTable model update..."+e.getMessage());
	}
   }
   
   /**
    * Find Timetable by PK.
    *
    * @param pk            : get parameter
    * @return bean
    * @throws ApplicationException the application exception
    */
	public static TimetableBean findByPK(long pk) throws ApplicationException 
	{
		TimetableBean tb = null;
		
		Connection con=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		try {
			con = JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement("SELECT * FROM ST_TIMETABLE WHERE ID=?");
			ps.setLong(1, pk);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				tb = new TimetableBean();
			  tb.setId(rs.getLong(1));
			  tb.setSubId(rs.getInt(2));
			  tb.setSubName(rs.getString(3));
			  tb.setCourseId(rs.getInt(4));
			  tb.setCourseName(rs.getString(5));
			  tb.setSemester(rs.getString(6));
			  tb.setExamDate(rs.getDate(7));
			  tb.setExamTime(rs.getString(8));
			  tb.setDescription(rs.getString(9));
			  tb.setCreatedBy(rs.getString(10));
			  tb.setModifiedBy(rs.getString(11));
			  tb.setCreatedDatetime(rs.getTimestamp(12));
			  tb.setModifiedDatetime(rs.getTimestamp(13));  
			}	
			con.commit();
			ps.close();
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model findByPK..."+e.getMessage());
		}
		
		JDBCDataSource.closeConnection(con);
		return tb;
	}
 
	/**
	 * Get List of Timetable.
	 *
	 * @param bean the bean
	 * @return list : List of Timetable
	 * @throws ApplicationException the application exception
	 */
	public static List list(TimetableBean bean) throws ApplicationException
	{
		return list(bean, 0, 0);
	}
	
	/**
	 * Get List of Timetable with pagination.
	 *
	 * @param bean the bean
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of Timetable
	 * @throws ApplicationException the application exception
	 */
	public static List list(TimetableBean bean,int pageNo,int pageSize) throws ApplicationException
	{
		List<TimetableBean> list = new ArrayList<TimetableBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE 1=1");
		
		if(pageSize>0)
		{
			pageNo = (pageNo-1)*pageSize;
			sql.append(" LIMIT "+pageNo+","+pageSize);
		}
		
		try {
			con=JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps=con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while(rs.next())
			{
			  
			  TimetableBean tb = new TimetableBean();
			  tb.setId(rs.getLong(1));
			  tb.setSubId(rs.getInt(2));
			  tb.setSubName(rs.getString(3));
			  tb.setCourseId(rs.getInt(4));
			  tb.setCourseName(rs.getString(5));
			  tb.setSemester(rs.getString(6));
			  tb.setExamDate(rs.getDate(7));
			  tb.setExamTime(rs.getString(8));
			  tb.setDescription(rs.getString(9));
			  tb.setCreatedBy(rs.getString(10));
			  tb.setModifiedBy(rs.getString(11));
			  tb.setCreatedDatetime(rs.getTimestamp(12));
			  tb.setModifiedDatetime(rs.getTimestamp(13));
			  list.add(tb);
			}
			con.commit();
			ps.close();
			rs.close();
			JDBCDataSource.closeConnection(con);
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model list..."+e.getMessage());
		}
		return list;
	}
	
	/**
	 * Search Timetable.
	 *
	 * @param bean            : Search Parameters
	 * @return the list
	 * @throws ApplicationException the application exception
	 */
	public static List search(TimetableBean bean) throws ApplicationException
    {
      return search(bean, 0, 0);
    }
    
	/**
	 * Search Timetable with pagination.
	 *
	 * @param bean            : Search Parameters
	 * @param pageNo            : Current Page No.
	 * @param pageSize            : Size of Page
	 * @return list : List of Timetable
	 * @throws ApplicationException the application exception
	 */
    public static List search(TimetableBean bean,int pageNo,int pageSize) throws ApplicationException
    {
    	List<TimetableBean> list = new ArrayList<TimetableBean>();
    	Connection con=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE true");

    	System.out.println("DAte Search   "+bean.getExamDate());
    	System.out.println("Sub Search   "+bean.getSubId());
    	if(bean!=null)
    	{
    		System.out.println("timetable examtime  "+bean.getExamTime());
    		if(bean.getId()>0)
    		{
    		  sql.append(" AND ID="+bean.getId());
    		}
    	    
//    	    if (bean.getSemester() != null && bean.getSemester().length() > 0) {
//                sql.append(" AND SEMISTER like '" + bean.getSemester() + "%'");
//            }
            if (bean.getSubId() > 0) {
                sql.append(" AND SUBJECT_ID = " + bean.getSubId());
            }
            if (bean.getSubName() != null) {
                sql.append(" AND SUBJECT_NAME like '" + bean.getSubName()+"%'");
            }
            if (bean.getExamDate() != null) 
            {
            	Date date = new Date(bean.getExamDate().getTime());
            
            	System.out.println("After formate  "+date);
            	
                sql.append(" AND EXAM_DATE = '" + date+"'");
            }
            
            if (bean.getCourseId() > 0) {
                sql.append(" AND COURSE_ID = " + bean.getCourseId());
            }
            if (bean.getCourseName() != null) {
                sql.append(" AND COURSE_NAME like'" + bean.getCourseName()+"%'");
            }
    	}
    	if(pageSize>0)
    	{
    		pageNo=(pageNo-1)*pageSize;
    		sql=sql.append(" LIMIT "+pageNo+","+pageSize);
    	}  	
    	System.out.println("FINAL SQL OF TimeTable list : "+sql);
    	try {
			con=JDBCDataSource.getConnection();
			con.setAutoCommit(false);
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			TimetableBean tb;
			while(rs.next())
			{
				System.out.println("while");
				  tb = new TimetableBean();
				  tb.setId(rs.getLong(1));
				  tb.setSubId(rs.getInt(2));
				  tb.setSubName(rs.getString(3));
				  tb.setCourseId(rs.getInt(4));
				  tb.setCourseName(rs.getString(5));
				  tb.setSemester(rs.getString(6));
				  tb.setExamDate(rs.getDate(7));
				  tb.setExamTime(rs.getString(8));
				  tb.setDescription(rs.getString(9));
				  tb.setCreatedBy(rs.getString(10));
				  tb.setModifiedBy(rs.getString(11));
				  tb.setCreatedDatetime(rs.getTimestamp(12));
				  tb.setModifiedDatetime(rs.getTimestamp(13));
				  list.add(tb);
			}
			con.commit();
			ps.close();
			rs.close();
			JDBCDataSource.closeConnection(con);
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model search..."+e.getMessage());
		}
    	System.out.println("List ki Size  "+list.size());
		return list;
    }
    
    /**
     * Check by exam time.
     *
     * @param CourseId the course id
     * @param SubjectId the subject id
     * @param semester the semester
     * @param ExamDAte the exam D ate
     * @param ExamTime the exam time
     * @return the timetable bean
     * @throws ApplicationException the application exception
     */
    public static TimetableBean checkByExamTime(long CourseId,int SubjectId,String semester,java.util.Date ExamDAte,String ExamTime)
   throws ApplicationException {
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	TimetableBean bean=null;
    	Date ExDate = new Date(ExamDAte.getTime());
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND"
    			+ " SEMESTER=? AND EXAM_DATE=? AND EXAM_TIME=?");
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			ps.setString(5, ExamTime);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getInt(2));
				bean.setSubName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByexamTime..."+e.getMessage());
		}
		return bean;
    }

    /**
     * Check by course name.
     *
     * @param CourseId the course id
     * @param ExamDate the exam date
     * @return the timetable bean
     * @throws ApplicationException the application exception
     */
    public static TimetableBean checkByCourseName(long CourseId,java.util.Date ExamDate)
    throws ApplicationException{
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	TimetableBean bean=null;
    	
    	Date Exdate = new Date(ExamDate.getTime());
    	
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? "
    			+ "AND EXAM_DATE=?");
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setDate(2, Exdate);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getInt(2));
				bean.setSubName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkByCourseName..."+e.getMessage());
		}
		return bean;
    }
    
    /**
     * Check by subject name.
     *
     * @param CourseId the course id
     * @param SubjectId the subject id
     * @param ExamDAte the exam D ate
     * @return the timetable bean
     * @throws ApplicationException the application exception
     */
    public static TimetableBean checkBySubjectName(long CourseId,int SubjectId,java.util.Date ExamDAte)
    throws ApplicationException{
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	TimetableBean bean=null;
    	Date ExDate = new Date(ExamDAte.getTime());
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND"
    			+ " EXAM_DATE=?");
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setDate(3, ExDate);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getInt(2));
				bean.setSubName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..."+e.getMessage());
		}
		return bean;
    }
    
    /**
     * Check bysemester.
     *
     * @param CourseId the course id
     * @param SubjectId the subject id
     * @param semester the semester
     * @param ExamDAte the exam D ate
     * @return the timetable bean
     * @throws ApplicationException the application exception
     */
    public static TimetableBean checkBysemester(long CourseId,long SubjectId,String semester,java.util.Date ExamDAte)
    throws ApplicationException{
    	PreparedStatement ps=null;
    	ResultSet rs = null;
    	TimetableBean bean=null;
    	
    	Date ExDate = new Date(ExamDAte.getTime());
    	
    	StringBuffer sql = new StringBuffer("SELECT * FROM ST_TIMETABLE WHERE COURSE_ID=? AND SUBJECT_ID=? AND"
    			+ " SEMESTER=? AND EXAM_DATE=?");
    	
    	try {
			Connection con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, CourseId);
			ps.setLong(2, SubjectId);
			ps.setString(3, semester);
			ps.setDate(4, ExDate);
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				bean = new TimetableBean();
				bean.setId(rs.getLong(1));
				bean.setSubId(rs.getInt(2));
				bean.setSubName(rs.getString(3));
				bean.setCourseId(rs.getLong(4));
				bean.setCourseName(rs.getString(5));
				bean.setSemester(rs.getString(6));
				bean.setExamDate(rs.getDate(7));
				bean.setExamTime(rs.getString(8));
				bean.setDescription(rs.getString(9));
				bean.setCreatedBy(rs.getString(10));
				bean.setModifiedBy(rs.getString(11));
				bean.setCreatedDatetime(rs.getTimestamp(12));
				bean.setModifiedDatetime(rs.getTimestamp(13));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception in timeTable model checkBySubjectName..."+e.getMessage());
		}
		return bean;
    }
    
}
