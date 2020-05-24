/*
 * 
 */
package in.co.sunrays.proj4.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import in.co.sunrays.proj4.bean.StudentBean;
import in.co.sunrays.proj4.model.StudentModel;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentModelTest.
 */
public class StudentModelTest {
	
  /**
   * The main method.
   *
   * @param args the arguments
   * @throws Exception the exception
   */
  public static void main(String[] args) throws Exception{
	  StudentModel model=new StudentModel();
	  System.out.println(model.nextPK());
	  //addTest();
	  //deleteTest();
	 // findByLoginTest();
	  //findByPKTest();
	  //updateTest();
	  //searchTest();
	  testList1();
  }
  
  /**
   * Delete test.
   *
   * @throws Exception the exception
   */
  private static void deleteTest() throws Exception {
	  StudentModel model=new StudentModel();
	  StudentBean bean=new StudentBean();
	  bean.setId(5);
	  model.delete(bean);
	
}
  
  /**
   * Search test.
   */
  private static void searchTest() {
		StudentModel m=new StudentModel();
		StudentBean  bean=new StudentBean();
		bean.setCollegeID(2);
		ArrayList<StudentBean> list=m.search(bean,0,0);
		Iterator<StudentBean> it=list.iterator();
		while(it.hasNext()){
			StudentBean b=(StudentBean)it.next();
			System.out.println(b.getId()+"  "+b.getCollegeID()+" "+b.getCollegeName()+" "+b.getFirstName()+" "+b.getLastName()+" "+b.getDob()+" "
					+b.getMobileNo()+" "+b.getEmail()+" "+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedDatetime()+" "
					+b.getModifiedDatetime());
}
}
  
  /**
   * Test list.
   */
  public static void testList(){
	  StudentBean b=new StudentBean();
	  StudentModel m=new StudentModel();
		ArrayList<StudentBean> list=new ArrayList<StudentBean>();
		list=m.list(1, 10);
		Iterator<StudentBean> it=list.iterator();
		if(list.size()<0){
			System.out.println("TestList fail");
		}
		while(it.hasNext())
		{
			b=(StudentBean)it.next();
			System.out.println(b.getId()+"  "+b.getCollegeID()+" "+b.getCollegeName()+" "+b.getFirstName()+" "+b.getLastName()+" "+b.getDob()+" "
					+b.getMobileNo()+" "+b.getEmail()+" "+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedDatetime()+" "
					+b.getModifiedDatetime());
			
		}
		
	}
	
	/**
	 * Test list 1.
	 */
	public static void testList1(){
		StudentBean b=new StudentBean();
		StudentModel m=new StudentModel();
		ArrayList<StudentBean> list=new ArrayList<StudentBean>();
		list=m.list();
		Iterator<StudentBean> it=list.iterator();
		if(list.size()<0){
			System.out.println("TestList fail");
		}
		while(it.hasNext())
		{
			b=(StudentBean)it.next();
			System.out.println(b.getId()+"  "+b.getCollegeID()+" "+b.getCollegeName()+" "+b.getFirstName()+" "+b.getLastName()+" "+b.getDob()+" "
					+b.getMobileNo()+" "+b.getEmail()+" "+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedDatetime()+" "
					+b.getModifiedDatetime());
		}
	}

		
/**
 * Adds the test.
 *
 * @throws Exception the exception
 */
public static void addTest() throws Exception{
	  
		String str_date="12/10/2001";
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	      Date d=null;
		try {
			d = (Date) formatter.parse(str_date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      java.sql.Timestamp ts = new Timestamp(d.getTime());
	  String date="12/02/1998";
	  SimpleDateFormat sdf=new SimpleDateFormat("dd/mm/yyyy");
	  java.util.Date d1=null;
	try {
		d1 = sdf.parse(date);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  StudentModel model=new StudentModel();
	  StudentBean bean=new StudentBean();
	  bean.setCollegeID(3);
	  bean.setCollegeName("SVIM");
	  bean.setFirstName("Jaya");
	  bean.setLastName("kotiya");
	  bean.setDob(d1);
	  bean.setMobileNo("9977886655");
	  bean.setEmail("jayachouhan2@gmail.com");
	  bean.setCreatedBy("abcd");
	  bean.setModifiedBy("xyz");
	  bean.setCreatedDatetime(ts);
	  bean.setModifiedDatetime(ts);
	  model.add(bean);
  }

/**
 * Find by login test.
 */
public static void findByLoginTest() {
	StudentModel model=new StudentModel();
	StudentBean b=model.findByLogin("jayachouhan2@gmail.com");
	System.out.println(b.getId()+"  "+b.getCollegeID()+" "+b.getCollegeName()+" "+b.getFirstName()+" "+b.getLastName()+" "+b.getDob()+" "
				+b.getMobileNo()+" "+b.getEmail()+" "+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedDatetime()+" "
				+b.getModifiedDatetime());
		
}

/**
 * Find by PK test.
 */
public static void findByPKTest() {
	StudentModel model=new StudentModel();
	StudentBean b=model.findByPK(7l);
	System.out.println(b.getId()+"  "+b.getCollegeID()+" "+b.getCollegeName()+" "+b.getFirstName()+" "+b.getLastName()+" "+b.getDob()+" "
				+b.getMobileNo()+" "+b.getEmail()+" "+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedDatetime()+" "
				+b.getModifiedDatetime());
		
}

/**
 * Update test.
 *
 * @throws Exception the exception
 */
public static void updateTest() throws Exception{
	StudentBean bean=new StudentBean();
	bean.setId(8);
	StudentModel m=new StudentModel();
	bean.setFirstName("Arjun");
	bean.setLastName("panwar");
	m.update(bean);
}
}

