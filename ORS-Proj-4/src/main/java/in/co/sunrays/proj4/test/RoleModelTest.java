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
import java.util.List;

import in.co.sunrays.proj4.bean.RoleBean;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.model.RoleModel;
import in.co.sunrays.proj4.util.ServletUtility;



// TODO: Auto-generated Javadoc
/**
 * The Class RoleModelTest.
 */
public class RoleModelTest {
  
  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args){
	  RoleModel m=new RoleModel();
	  int i=m.nextPK();
	  System.out.println(i);
	  //addTest();
	  //deleteTest();
  }
  
  /**
   * Adds the test.
   */
  public  static void addTest() {
	 
	  String s="10/10/2001";
	  DateFormat formatter= new SimpleDateFormat("MM/dd/yyyy");
	  Date date=null;
	  try {
		date=(Date)formatter.parse(s);
	} catch (ParseException e) {
		
		e.printStackTrace();
	}
	  java.sql.Timestamp ts=new Timestamp(date.getTime());
	  RoleModel m=new RoleModel();
	  RoleBean b=new RoleBean();
	  b.setName("admin");
	  b.setDescription("admin");
	  b.setCreatedBy("xyz");
	  b.setModifiedBy("abc");
	  b.setCreatedDatetime(ts);
	  b.setModifiedDatetime(ts);
	  try {
		m.add(b);
	} catch (Exception e) {
		
		e.printStackTrace();
	}
  }
  
  /**
   * Delete test.
   */
  private static void deleteTest() {
	  RoleModel model=new RoleModel();
	  RoleBean bean=new RoleBean();
	  bean.setId(5);
	  model.delete(bean);
	
}
  
  /**
   * Search test.
   */
  private static void searchTest() {
		RoleModel m=new RoleModel();
		RoleBean  bean=new RoleBean();
		bean.setId(2);
		ArrayList<RoleBean> list=m.search(bean,0,0);
		Iterator<RoleBean> it=list.iterator();
		while(it.hasNext()){
			RoleBean b=(RoleBean)it.next();
			System.out.println(b.getId()+"  "+b.getName()+" "+b.getDescription()+" "
			+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedBy()+" "
					+b.getModifiedDatetime());
}
}
  
  /**
   * Test list.
   */
  public static void testList(){
	  RoleBean b=new RoleBean();
	  RoleModel m=new RoleModel();
		List list;
		list= m.list(1, 10);
		Iterator<RoleBean> it=list.iterator();
		if(list.size()<0){
			System.out.println("TestList fail");
		}
		while(it.hasNext())
		{
			b=(RoleBean)it.next();
			System.out.println(b.getId()+"  "+b.getName()+" "+b.getDescription()+" "
					+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedBy()+" "
							+b.getModifiedDatetime());
			
		}
		
	}
	
	/**
	 * Test list 1.
	 */
	public static void testList1(){
		RoleBean b=new RoleBean();
		RoleModel m=new RoleModel();
		List list;
		list=m.list();
		Iterator<RoleBean> it=list.iterator();
		if(list.size()<0){
			System.out.println("TestList fail");
		}
		while(it.hasNext())
		{
			b=(RoleBean)it.next();
			System.out.println(b.getId()+"  "+b.getName()+" "+b.getDescription()+" "
					+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedBy()+" "
							+b.getModifiedDatetime());
		}
	}

		
/**
 * Find by name test.
 */
public static void findByNameTest() {
	RoleModel m=new RoleModel();
	RoleBean b=m.findByName("admin");
	System.out.println(b.getId()+"  "+b.getName()+" "+b.getDescription()+" "
			+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedBy()+" "
					+b.getModifiedDatetime());
		
}

/**
 * Find by PK test.
 */
public static void findByPKTest() {
	in.co.sunrays.proj4.model.RoleModel model=new RoleModel();
RoleBean b=model.findByPK(7);
	System.out.println(b.getId()+"  "+b.getName()+" "+b.getDescription()+" "
			+b.getCreatedBy()+" "+b.getModifiedBy()+" "+b.getCreatedBy()+" "
					+b.getModifiedDatetime());
		
}

/**
 * Update test.
 */
public static void updateTest(){
	RoleBean bean=new RoleBean();
	bean.setId(8);
	RoleModel m=new RoleModel();
	bean.setName("Arjun");
	
	m.update(bean);
}
}
