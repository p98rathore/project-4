/*
 * 
 */
package in.co.sunrays.proj4.bean;

import java.util.Date;
import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * User JavaBean encapsulates User attributes.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (c) Proxy
 */

/**
 * @author pradeep
 *
 */
/**
 * @author pradeep
 *
 */
public class UserBean extends BaseBean {
	
	/** Lock Active constant for User. */
	public final static String ACTIVE="Active";
	
	/** Lock INActive constant for User. */
	public final static String INACTIVE="InActive";
	
	/** firstName of User. */
	private String firstName;
	
	/** lastName of user. */
	private String lastName;
	
	/** Login Id of user. */
	private String login;
	
	/** Password of User. */
	private String password;
	
	/** Confirm password of user. */
	private String confirmPassword;
	
	/** Date of birth of user. */
	private Date dob;
	
	/** mobileNo of user. */
	private String mobileNo;
	
	/** roleId of user. */
	private long roleId;
	
	/** unSuccesfullLogin of user. */
	private int unSuccessfulLogin;
	
	/** gender of user. */
	private String gender;
	
	/** lastLogin of user. */
	private Timestamp lastLogin;
	
	/** lock of user. */
	private String lock;
	
	/** register ip of user. */
	private String registeredIp;
	

	/**
	 * accessor.
	 *
	 * @return String
	 */
	public long getId() {
		return id;
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.bean.BaseBean#setId(long)
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Gets the login.
	 *
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	
	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the confirm password.
	 *
	 * @return the confirm password
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	
	/**
	 * Sets the confirm password.
	 *
	 * @param confirmPassword the new confirm password
	 */
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	/**
	 * Gets the dob.
	 *
	 * @return the dob
	 */
	public Date getDob() {
		return dob;
	}
	
	/**
	 * Sets the dob.
	 *
	 * @param dob the new dob
	 */
	public void setDob(Date dob) {
		this.dob =  dob;
	}
	
	/**
	 * Gets the mobile no.
	 *
	 * @return the mobile no
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	
	/**
	 * Sets the mobile no.
	 *
	 * @param mobileNo the new mobile no
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	/**
	 * Gets the role id.
	 *
	 * @return the role id
	 */
	public long getRoleId() {
		return roleId;
	}
	
	/**
	 * Sets the role id.
	 *
	 * @param roleId the new role id
	 */
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * Gets the un successful login.
	 *
	 * @return the un successful login
	 */
	public int getUnSuccessfulLogin() {
		return unSuccessfulLogin;
	}
	
	/**
	 * Sets the un successful login.
	 *
	 * @param unSuccessfulLogin the new un successful login
	 */
	public void setUnSuccessfulLogin(int unSuccessfulLogin) {
		this.unSuccessfulLogin = unSuccessfulLogin;
	}
	
	/**
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	
	/**
	 * Sets the gender.
	 *
	 * @param gender the new gender
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	/**
	 * Gets the last login.
	 *
	 * @return the last login
	 */
	public Timestamp getLastLogin() {
		return lastLogin;
	}
	
	/**
	 * Sets the last login.
	 *
	 * @param lastLogin the new last login
	 */
	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	/**
	 * Gets the lock.
	 *
	 * @return the lock
	 */
	public String getLock() {
		return lock;
	}
	
	/**
	 * Sets the lock.
	 *
	 * @param lock the new lock
	 */
	public void setLock(String lock) {
		this.lock = lock;
	}
	
	/**
	 * Gets the registered ip.
	 *
	 * @return the registered ip
	 */
	public String getRegisteredIp() {
		return registeredIp;
	}
	
	/**
	 * Sets the registered ip.
	 *
	 * @param registeredIp the new registered ip
	 */
	public void setRegisteredIp(String registeredIp) {
		this.registeredIp = registeredIp;
	}
	
	/**
	 * Gets the last login ip.
	 *
	 * @return the last login ip
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	
	/**
	 * Sets the last login ip.
	 *
	 * @param lastLoginIp the new last login ip
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	
	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public String getACTIVE() {
		return ACTIVE;
	}
	
	/**
	 * Gets the inactive.
	 *
	 * @return the inactive
	 */
	public String getINACTIVE() {
		return INACTIVE;
	}
	
	/** The last login ip. */
	private String lastLoginIp;
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.bean.BaseBean#getKey()
	 */
	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return id+"";
	}
	
	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.bean.BaseBean#getValue()
	 */
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return firstName+""+lastName;
	}
}
