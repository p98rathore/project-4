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
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.util.EmailBuilder;
import in.co.sunrays.proj4.util.EmailMessage;
import in.co.sunrays.proj4.util.EmailUtility;
import in.co.sunrays.proj4.util.JDBCDataSource;

// TODO: Auto-generated Javadoc
/**
 * JDBC Implementation of User Model.
 *
 * @author Proxy
 * @version 1.0
 * CopyRight (C) Proxy
 */
public class UserModel {
	
	/** The log. */
	private static Logger log = Logger.getLogger(UserModel.class);

	/**
	 * Getting next Primary key.
	 *
	 * @return Next Primary key
	 * @throws DatabaseException the database exception
	 */
	public static Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_USER");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
				System.out.println("Hello");
			}
			rs.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();

		} finally {

			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}

	/**
	 * Add a User.
	 *
	 * @param bean the bean
	 * @return the long
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws SQLException the SQL exception
	 */
	public static long add(UserBean bean) throws ApplicationException, DuplicateRecordException, SQLException {
		// System.out.println("modeladd start");
		log.debug("Model add method start");
		Connection conn = null;
		long i = 0;
		
		
		
		UserBean beanexit = findByLogin(bean.getLogin());
		if (beanexit != null) {
			throw new DuplicateRecordException("login id already exist");
		}
		try {
			java.util.Date date= bean.getDob();
			java.sql.Date sqlDate=new java.sql.Date(date.getTime());
			conn = JDBCDataSource.getConnection();
			System.out.println("connection start");
			//Date d = new Date(bean.getDob().getTime());
			//System.out.println("ate   :" + d);
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO ST_USER VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setInt(1, UserModel.nextPK());
			pstmt.setString(2, bean.getFirstName());
			pstmt.setString(3, bean.getLastName());
			pstmt.setString(4, bean.getLogin());
			pstmt.setString(5, bean.getPassword());
			System.out.println(sqlDate);
			pstmt.setDate(6, sqlDate);
			pstmt.setString(7, bean.getMobileNo());
			pstmt.setLong(8, bean.getRoleId());
			pstmt.setInt(9, bean.getUnSuccessfulLogin());
			pstmt.setString(10, bean.getGender());
			pstmt.setTimestamp(11, bean.getLastLogin());
			pstmt.setString(12, bean.getLock());
			pstmt.setString(13, bean.getRegisteredIp());
			pstmt.setTimestamp(14, bean.getLastLogin());

			pstmt.setString(15, bean.getCreatedBy());
			pstmt.setString(16, bean.getModifiedBy());
			pstmt.setTimestamp(17, bean.getCreatedDatetime());
			pstmt.setTimestamp(18, bean.getModifiedDatetime());
			i = pstmt.executeUpdate();

			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model add End");
		return i;
	}

	/**
	 * Delete a User.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */
	public static void delete(UserBean bean) throws ApplicationException {
		log.debug("Model delete Started");
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			System.out.println("conn start");
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_USER WHERE ID=?");
			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			System.out.println(i);
			conn.commit(); // End transaction
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("Model delete ended");
		}

	}

	/**
	 * Find User by Login.
	 *
	 * @param login            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */

	public static UserBean findByLogin(String login) throws ApplicationException {
		log.debug("Model findByLogin Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE LOGIN=?");
		UserBean bean = null;
		Connection conn = null;
		System.out.println("sql" + sql);

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByLogin End");
		return bean;
	}

	/**
	 * Find User by PK.
	 *
	 * @param pk            : get parameter
	 * @return bean
	 * @throws ApplicationException the application exception
	 */

	public static UserBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findByPk Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE ID=?");
		UserBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
	}

	/**
	 * Update a user.
	 *
	 * @param bean the bean
	 * @throws ApplicationException the application exception
	 */

	public static void update(UserBean bean) throws ApplicationException {
		log.debug("Model update Started");
		Connection conn = null;
		System.out.println(bean.getDob()+"model dob");
		java.util.Date date= bean.getDob();
		java.sql.Date d=new java.sql.Date(date.getTime());

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_USER SET FIRST_NAME=?,LAST_NAME=?,LOGIN=?,PASSWORD=?,DOB=?,MOBILE_NO=?,ROLE_ID=?,UNSUCCESSFUL_LOGIN=?,GENDER=?,LAST_LOGIN=?,USER_LOCK=?,REGISTERED_IP=?,LAST_LOGIN_IP=?,CREATED_BY=?,MODIFIED_BY=?,CREATED_DATETIME=?,MODIFIED_DATETIME=? WHERE ID=?");
			pstmt.setString(1, bean.getFirstName());
			pstmt.setString(2, bean.getLastName());
			pstmt.setString(3, bean.getLogin());
			pstmt.setString(4, bean.getPassword());
			pstmt.setDate(5,  d);
			pstmt.setString(6, bean.getMobileNo());
			pstmt.setLong(7, bean.getRoleId());
			pstmt.setInt(8, bean.getUnSuccessfulLogin());
			pstmt.setString(9, bean.getGender());
			pstmt.setTimestamp(10, bean.getLastLogin());
			pstmt.setString(11, bean.getLock());
			pstmt.setString(12, bean.getRegisteredIp());
			pstmt.setString(13, bean.getLastLoginIp());
			pstmt.setString(14, bean.getCreatedBy());
			pstmt.setString(15, bean.getModifiedBy());
			pstmt.setTimestamp(16, bean.getCreatedDatetime());
			pstmt.setTimestamp(17, bean.getModifiedDatetime());
			pstmt.setLong(18, bean.getId());
			pstmt.executeUpdate();
			conn.commit(); // End transaction
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();

		} finally {
			JDBCDataSource.closeConnection(conn);
			log.debug("Model update End");
		}

	}

	/**
	 * Search of User.
	 *
	 * @param bean the bean
	 * @return List of User
	 * @throws ApplicationException the application exception
	 */
	public static List search(UserBean bean) throws ApplicationException {
		log.debug("Model search(bean) start");

		return search(bean, 0, 0);
	}

	/**
	 * Getting List of User.
	 *
	 * @return List of User
	 */
	public static List list() {
		log.debug("Model list start");
		return list(0, 0);

	}

	/**
	 * Getting List with Pagination.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return List of User
	 */
	public static List list(int pageNo, int pageSize) {
		log.debug("Model list(pgno  pgsize) Started");
		List<UserBean> list = new ArrayList<UserBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		UserBean bean = new UserBean();
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER where true");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

				list.add(bean);
			}
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
		}

		log.debug("model list(no size)  end");
		return list;
	}

	/**
	 * Authenticate a Login.
	 *
	 * @param login the login
	 * @param password the password
	 * @return UserBean
	 */
	public static UserBean authenticate(String login, String password) {
		log.debug("Model authenticate Started");
		UserBean bean = null;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER where login=? and password=?");
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setString(1, login);
			ps.setString(2, password);
			rs = ps.executeQuery();
			while (rs.next()) {
				bean= new UserBean();
				System.out.println( "snot entered");
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));
			}
		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
		}
		log.debug("model authentication end");
		return bean;

	}

	/**
	 * Getting role.
	 *
	 * @param bean1 the bean 1
	 * @return List Of User
	 */
	public static List getRoles(UserBean bean1) {
		log.debug("model get roles start");
		List<UserBean> list = new ArrayList<UserBean>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER where role_id=?");
		try {
			con = JDBCDataSource.getConnection();
			ps = con.prepareStatement(sql.toString());
			ps.setLong(1, bean1.getRoleId());
			rs = ps.executeQuery();
			while (rs.next()) {
				UserBean bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

				list.add(bean);
			}

		} catch (Exception e) {
			log.error("Database Exception..", e);
			e.printStackTrace();
		}

		log.debug("model get role end");
		return list;

	}

	/**
	 * Change password .
	 *
	 * @param id the id
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return Bollean
	 * @throws ApplicationException the application exception
	 * @throws RecordNotFoundException the record not found exception
	 */
	public static boolean changePassword(long id, String oldPassword, String newPassword)
			throws ApplicationException, RecordNotFoundException{
		log.debug("model change pass start");
		Connection con = null;
		int i = 0;
		boolean b = false;
		PreparedStatement ps = null;
		UserBean bean = findByPK(id);
		if (bean != null && bean.getPassword().equals(oldPassword)) 
		{
			System.out.println("modelcheck pass");
			bean.setPassword(newPassword);
			try {
				update(bean);

				b = true;
			}

			catch (Exception e) {
				log.error("Database exception" + e);
				e.printStackTrace();
			}
		} else {
			//throw new RecordNotFoundException("user does not exist");
			b=false;
		}
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());

		String message = EmailBuilder.getChangePasswordMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("SUNARYS ORS Password has been changed Successfully.");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);

		log.debug("user model change pass end");
		return b;

	}

	/**
	 * Lock a user.
	 *
	 * @param login the login
	 * @return true, if successful
	 */
	public static boolean lock(String login) {
		log.debug("user model lock start");
		boolean flag = false;
		UserBean beanExist = null;
		try {
			beanExist = findByLogin(login);
			if (beanExist != null) {
				beanExist.setLock(UserBean.ACTIVE);
				update(beanExist);
				flag = true;
			} else {
				System.out.println("record not found");
			}
		} catch (Exception e) {
			log.error("user model lock database exception" + e);
			e.printStackTrace();

		}
		log.debug("model lock ended");
		return flag;

	}

	/**
	 * Search User With Pagination.
	 *
	 * @param bean the bean
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @return List of user
	 */
	public static List search(UserBean bean, int pageNo, int pageSize) {
		log.debug("user model search(beaan,pgno,pgsize) strt");
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_USER WHERE 1=1");

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
			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" AND LOGIN like '" + bean.getLogin() + "%'");
			}
			if (bean.getPassword() != null && bean.getPassword().length() > 0) {
				sql.append(" AND PASSWORD like '" + bean.getPassword() + "%'");
			}
			if (bean.getDob() != null && bean.getDob().getDate() > 0) {
				sql.append(" AND DOB = " + bean.getGender());
			}
			if (bean.getMobileNo() != null && bean.getMobileNo().length() > 0) {
				sql.append(" AND MOBILE_NO = " + bean.getMobileNo());
			}
			if (bean.getRoleId() > 0) {
				sql.append(" AND ROLE_ID = " + bean.getRoleId());
			}
			if (bean.getUnSuccessfulLogin() > 0) {
				sql.append(" AND UNSUCCESSFUL_LOGIN = " + bean.getUnSuccessfulLogin());
			}
			if (bean.getGender() != null && bean.getGender().length() > 0) {
				sql.append(" AND GENDER like '" + bean.getGender() + "%'");
			}
			if (bean.getLastLogin() != null && bean.getLastLogin().getTime() > 0) {
				sql.append(" AND LAST_LOGIN = " + bean.getLastLogin());
			}
			if (bean.getRegisteredIp() != null && bean.getRegisteredIp().length() > 0) {
				sql.append(" AND REGISTERED_IP like '" + bean.getRegisteredIp() + "%'");
			}
			if (bean.getLastLoginIp() != null && bean.getLastLoginIp().length() > 0) {
				sql.append(" AND LAST_LOGIN_IP like '" + bean.getLastLoginIp() + "%'");
			}
			if (bean.getLock() != null && bean.getLock().length() > 0) {
				sql.append(" AND user_lock like '" + bean.getLock() + "%'");
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
		List<UserBean> list = new ArrayList<UserBean>();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new UserBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setDob(rs.getDate(6));
				bean.setMobileNo(rs.getString(7));
				bean.setRoleId(rs.getLong(8));
				bean.setUnSuccessfulLogin(rs.getInt(9));
				bean.setGender(rs.getString(10));
				bean.setLastLogin(rs.getTimestamp(11));
				bean.setLock(rs.getString(12));
				bean.setRegisteredIp(rs.getString(13));
				bean.setLastLoginIp(rs.getString(14));
				bean.setCreatedBy(rs.getString(15));
				bean.setModifiedBy(rs.getString(16));
				bean.setCreatedDatetime(rs.getTimestamp(17));
				bean.setModifiedDatetime(rs.getTimestamp(18));

				list.add(bean);
			}
			rs.close();
		} catch (Exception e) {
			log.error("user model search Database Exception..", e);
			e.printStackTrace();
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("user model search ended");
		return list;

	}

	/**
	 *  Forget password.
	 *
	 * @param login the login
	 * @return Boolean
	 * @throws Exception the exception
	 */
	public static boolean forgetPassword(String login) throws Exception  {
		UserBean bean = findByLogin(login);
		boolean flag = false;
		if (bean == null) {
			throw new RecordNotFoundException(" user doesnt exist");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());
		map.put("firstName", bean.getFirstName());
		map.put("lastName", bean.getLastName());
		String message = EmailBuilder.getForgetPasswordMessage(map);
		EmailMessage msg = new EmailMessage();
		msg.setTo(login);
		msg.setSubject("SUNARYS ORS Password reset");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);
		EmailUtility.sendMail(msg);
		flag = true;

		return flag;

	}

	/**
	 * Register a User.
	 *
	 * @param bean the bean
	 * @return Long
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws SQLException the SQL exception
	 */
	public static long registerUser(UserBean bean) throws ApplicationException, DuplicateRecordException, SQLException {

		log.debug("Model add Started");

		long pk = add(bean);

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("login", bean.getLogin());
		map.put("password", bean.getPassword());

		String message = EmailBuilder.getUserRegistrationMessage(map);

		EmailMessage msg = new EmailMessage();

		msg.setTo(bean.getLogin());
		msg.setSubject("Registration is successful for ORS Project SunilOS");
		msg.setMessage(message);
		msg.setMessageType(EmailMessage.HTML_MSG);

		EmailUtility.sendMail(msg);
		log.debug("model register complete");
		return pk;
	}

}
