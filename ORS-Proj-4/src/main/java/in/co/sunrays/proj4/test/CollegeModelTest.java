/*
 * 
 */
package in.co.sunrays.proj4.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.sunrays.proj4.bean.CollegeBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DatabaseException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.model.CollegeModel;
import in.co.sunrays.proj4.util.DataUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class CollegeModelTest.
 */
public class CollegeModelTest {

	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws SQLException the SQL exception
	 * @throws DatabaseException the database exception
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws RecordNotFoundException the record not found exception
	 * @throws ApplicationException the application exception
	 */
	public static void main(String[] args) throws SQLException, DatabaseException, DuplicateRecordException, RecordNotFoundException, ApplicationException {
		//testNextPk();
		//testAdd();
		//testDelete();
		//testFindByName();
		//findByPk();
		//testUpdate();
		//testsearch();
		//testsearchBean();
		testList();
		//testlistbn();
	}

	/**
	 * Testlistbn.
	 *
	 * @throws ApplicationException the application exception
	 * @throws SQLException the SQL exception
	 */
	private static void testlistbn() throws ApplicationException, SQLException {
		CollegeBean bean= new CollegeBean();
		List<CollegeBean> list = new ArrayList<CollegeBean>();
		list=CollegeModel.list(1, 4);
		Iterator it = list.iterator();
		while(it.hasNext()){
			bean= (CollegeBean) it.next();
			System.out.print("\t"+bean.getAddress());
			System.out.println("\t"+bean.getName());
		}
		
				
		
		
	}

	/**
	 * Test list.
	 *
	 * @throws ApplicationException the application exception
	 * @throws SQLException the SQL exception
	 */
	private static void testList() throws ApplicationException, SQLException
 {
		CollegeBean bean =new  CollegeBean();
		List<CollegeBean> list = new ArrayList<CollegeBean>();
		
				list=CollegeModel.list(1, 2);
				Iterator it = list.iterator();
				while(it.hasNext()){
					bean=(CollegeBean) it.next();
					System.out.println(bean.getName());
					System.out.println(bean.getModifiedBy());
					
				}
		
		
		
	}

	/**
	 * Testsearch bean.
	 *
	 * @throws ApplicationException the application exception
	 */
	private static void testsearchBean() throws ApplicationException {
		CollegeBean bean= new CollegeBean();
		List<CollegeBean> list= new ArrayList<CollegeBean>();
		list=CollegeModel.search(bean);
		Iterator it =list .listIterator();
		while(it.hasNext()){
			bean=(CollegeBean) it.next();
			System.out.println(bean.getName());
			
		}
		
	}

	/**
	 * Testsearch.
	 *
	 * @throws ApplicationException the application exception
	 */
	private static void testsearch() throws ApplicationException {
		CollegeBean bean = new CollegeBean();
		bean.setCreatedBy("pradeep");
		List<CollegeBean> list= new ArrayList<CollegeBean>();
		list=CollegeModel.search(bean, 1, 3);
		
		Iterator it= list.iterator();
		while(it.hasNext()){
		bean=(CollegeBean) it.next();
		System.out.println(bean.getName());
		}
	}

	/**
	 * Test update.
	 *
	 * @throws RecordNotFoundException the record not found exception
	 * @throws SQLException the SQL exception
	 */
	private static void testUpdate() throws RecordNotFoundException, SQLException {
		CollegeBean bean= new CollegeBean();
		bean.setId(18);
		bean.setName("jjjjj");
		bean.setCity("hbd");
		bean.setAddress("hbd");
		bean.setCreatedBy("pradeep");
		bean.setCreatedDatetime(DataUtility.getTimestamp("30/10/1997"));
		bean.setModifiedBy("pradeep");
		bean.setPhoneNo("88888888888");
		bean.setState("mp");
		bean.setModifiedDatetime(DataUtility.getCurrentTimestamp());
		CollegeModel.update(bean);
		
	}

	/**
	 * Find by pk.
	 *
	 * @throws SQLException the SQL exception
	 */
	private static void findByPk() throws SQLException {
		CollegeBean bean;
		try {
			bean = CollegeModel.findByPK(13);
			System.out.println(bean.getAddress());
			System.out.println(bean.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Test find by name.
	 */
	private static void testFindByName() {
		CollegeBean bean=new CollegeBean();
		bean= CollegeModel.findByName("rahul");
		System.out.println(bean.getAddress());
		System.out.println(bean.getCreatedBy());
		
	}

	/**
	 * Test delete.
	 *
	 * @throws SQLException the SQL exception
	 */
	private static void testDelete() throws SQLException {
		CollegeBean bean= new CollegeBean();
		bean.setId(15);
		CollegeModel.delete(bean);
				
		
	}

	/**
	 * Test add.
	 *
	 * @throws DatabaseException the database exception
	 * @throws DuplicateRecordException the duplicate record exception
	 */
	private static void testAdd() throws DatabaseException, DuplicateRecordException {
		CollegeBean bean = new CollegeBean();
		 bean.setName("rahul");
		 bean.setAddress("lapataa ganj");
		 bean.setCity("indore");
		 bean.setCreatedBy("pr");
		 bean.setPhoneNo("8109888971");
		 bean.setState("mh");
		 bean.setCreatedDatetime(DataUtility.getTimestamp(new Date().getTime()));
		 CollegeModel.add(bean);
		
	}

	/**
	 * Test next pk.
	 *
	 * @throws SQLException the SQL exception
	 */
	private static void testNextPk() throws SQLException {
		long i=CollegeModel.nextpk();
		System.out.println(i);
	}
}
