/*
 * 
 */
package in.co.sunrays.proj4.bean;

// TODO: Auto-generated Javadoc
/**
 * subject javaBean enapsulates subject attribute.
 *
 * @author Proxy
 * @version 1.0
 * Copyright (C) Proxy
 */
public class SubjectBean extends BaseBean {
	
	/** Default serial version Id. */
	private static final long serialVersionUID = 1L;

	/** Name of Subject. */
	private String name;
	
	/** Course id of Subject. */
	private long courseId;
	
	/** Course name of Subject. */
	private String courseName;
	
	/** Description of Subject. */
	private String description;

	/**
	 * accessor.
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the course id.
	 *
	 * @return the course id
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets the course id.
	 *
	 * @param courseId the new course id
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Gets the course name.
	 *
	 * @return the course name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name.
	 *
	 * @param courseName the new course name
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.bean.BaseBean#getKey()
	 */
	public String getKey() {
		// TODO Auto-generated method stub
		return id + "";
	}

	/* (non-Javadoc)
	 * @see in.co.sunrays.proj4.bean.BaseBean#getValue()
	 */
	public String getValue() {
		// TODO Auto-generated method stub
		return name;
	}

}
