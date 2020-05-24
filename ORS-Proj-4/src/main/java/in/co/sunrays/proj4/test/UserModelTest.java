/*
 * 
 */
package in.co.sunrays.proj4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;



import in.co.sunrays.proj4.bean.UserBean;
import in.co.sunrays.proj4.exception.ApplicationException;
import in.co.sunrays.proj4.exception.DuplicateRecordException;
import in.co.sunrays.proj4.exception.RecordNotFoundException;
import in.co.sunrays.proj4.model.UserModel;

// TODO: Auto-generated Javadoc
/**
 * The Class UserModelTest.
 */
public class UserModelTest {

	/**
	 * Tests add a User.
	 *
	 * @throws Exception the exception
	 */
	public static void testAdd() throws Exception {

		try {
			UserBean bean = new UserBean();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");

			// bean.setId(5234L);
			bean.setFirstName("cjhsghbdswv");
			bean.setLastName("kumevbebawat");
			bean.setLogin("abhi125@gmail.com");
			bean.setPassword("pass1234");
			bean.setDob(sdf.parse("31-12-1990"));
			bean.setRoleId(1L);
			bean.setUnSuccessfulLogin(2);
			bean.setGender("Male");
			bean.setLastLogin(new Timestamp(new Date().getTime()));
			bean.setLock("Yes");
			bean.setConfirmPassword("pass1234");
			bean.setLastLoginIp("2222");

			UserModel.add(bean);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Testdelete.
	 *
	 * @throws ApplicationException the application exception
	 */
	public static void testdelete() throws ApplicationException
		{
			UserBean bean = new UserBean();
			bean.setId(1l);
			UserModel.delete(bean);
		}
	

	/**
	 * Test findby login.
	 *
	 * @throws ApplicationException the application exception
	 */
	public static void testFindbyLogin() throws ApplicationException {
		{
			UserBean bean = new UserBean();

			bean = UserModel.findByLogin("abhi12@g.com");

			System.out.println(bean.getFirstName());
			System.out.println(bean.getId());
			System.out.println(bean.getDob());

		}
	}

	/**
	 * Test findby pk.
	 *
	 * @throws ApplicationException the application exception
	 */
	public static void testFindbyPk() throws ApplicationException {
		UserBean bean = new UserBean();

		bean = UserModel.findByPK(2);

		System.out.println(bean.getFirstName());
		System.out.println(bean.getId());
		System.out.println(bean.getDob());

	}

	/**
	 * Test updatte.
	 *
	 * @throws ApplicationException the application exception
	 * @throws DuplicateRecordException the duplicate record exception
	 * @throws ParseException the parse exception
	 */
	public static void testUpdatte() throws ApplicationException, DuplicateRecordException, ParseException {
		UserBean bean = new UserBean();
		Date d = new Date();
		Timestamp t = new Timestamp(d.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		bean.setId(2);
		bean.setFirstName("Kaushik");
		bean.setLastName("rathore");
		bean.setLogin("sss");

		bean.setCreatedBy("pr");
		bean.setModifiedDatetime(t);
		bean.setPassword("pass1234");
		bean.setDob(sdf.parse("30/10/1997"));
		bean.setRoleId(1L);
		bean.setUnSuccessfulLogin(2);
		bean.setGender("Male");
		bean.setLastLogin(t);
		bean.setLock("Yes");
		bean.setConfirmPassword("pass1234");
		bean.setLastLoginIp("2222");
		bean.setCreatedDatetime(t);
		bean.setMobileNo("1111111");
		bean.setModifiedBy("p");

		UserModel.update(bean);
	}

	/**
	 * Test search.
	 *
	 * @throws ApplicationException the application exception
	 */
	public static void testSearch() throws ApplicationException {
		UserBean bean = new UserBean();
		List<UserBean> list = new ArrayList<UserBean>();
		list = UserModel.search(bean);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getFirstName());
			System.out.println(bean.getGender());
		}
	}

	/**
	 * Test list.
	 */
	public static void testList() {
		UserBean bean = new UserBean();
		List<UserBean> list = new ArrayList<UserBean>();
		list = UserModel.list();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getFirstName());
		}
	}

	/**
	 * Test list.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 */
	public static void testList(int pageNo, int pageSize) {
		UserBean bean = new UserBean();
		List<UserBean> list = new ArrayList<UserBean>();
		list = UserModel.list(pageNo, pageSize);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.println(bean.getFirstName());
		}
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		UserBean bean = new UserBean();
		int pageNo = 1;
		int pageSize = 2;
		// int i = UserModel.nextPK();
		testAdd();
		// testdelete();
		// testFindbyLogin();
		// testFindbyPk();
		// testUpdatte();

		// testSearch();
		// testList();
		// testList(1,3);
		// testAuthenticate();
		// testGetRole();
		// testChangePass();
		// testSearch( bean,pageNo,pageSize);
		// testLock();
		// testForgetPass();
		// testRegister();
		System.out.println("insetr ");
	}

	/**
	 * Test register.
	 *
	 * @throws Exception the exception
	 */
	private static void testRegister() throws Exception {
		UserBean bean = new UserBean();

		Date d = new Date();
		Timestamp t = new Timestamp(d.getTime());
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		bean.setFirstName("vijay");
		bean.setLastName("sahu");
		bean.setLogin("jayachohan2@gmail.com");

		bean.setCreatedBy("pradeep");
		bean.setModifiedDatetime(t);
		try {
			bean.setPassword("pass1234");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bean.setDob(sdf.parse("9/10/1996"));
		bean.setRoleId(1L);
		bean.setUnSuccessfulLogin(2);
		bean.setGender("Male");
		bean.setLastLogin(t);
		bean.setLock("Yes");
		bean.setConfirmPassword("pass1234");
		bean.setLastLoginIp("2222");
		bean.setCreatedDatetime(t);
		bean.setMobileNo("8878545664");
		bean.setModifiedBy("pr");

		UserModel.registerUser(bean);

	}

	/**
	 * Test forget pass.
	 *
	 * @throws Exception the exception
	 */
	private static void testForgetPass() throws Exception {
		UserModel.forgetPassword("rahulsirwani181989@gmail.com");

		System.out.println("Message sent");

	}

	/**
	 * Test search.
	 *
	 * @param bean the bean
	 * @param pageNo the page no
	 * @param pageSize the page size
	 */
	private static void testSearch(UserBean bean, int pageNo, int pageSize) {
		bean.setLock("Active");
		List<UserBean> list = new ArrayList<UserBean>();
		list = UserModel.search(bean, pageNo, pageSize);
		Iterator it = list.iterator();
		while (it.hasNext()) {
			bean = (UserBean) it.next();
			System.out.print(bean.getFirstName());
			System.out.print(bean.getLastName());
			System.out.print(bean.getGender());
			System.out.println(bean.getCreatedBy());

		}

	}

	/**
	 * Test lock.
	 */
	private static void testLock() {
		UserBean bean = new UserBean();
		boolean b = UserModel.lock("dd");
		if (b) {
			System.out.println("succes");
		} else {
			System.out.println("notsucces");
		}
	}

	/**
	 * Test change pass.
	 *
	 * @throws ApplicationException the application exception
	 * @throws RecordNotFoundException the record not found exception
	 */
	private static void testChangePass() throws ApplicationException, RecordNotFoundException  {
		{
			UserBean bean = new UserBean();
			boolean b = UserModel.changePassword(1, "1234", "mota bhai");
			if (b) {
				System.out.println("pass change");
			} else {
				System.out.println("pass not change");
			}

		}
	}

	/**
	 * Test get role.
	 */
	private static void testGetRole() {
		List<UserBean> list = new ArrayList<UserBean>();
		UserBean bean = new UserBean();
		bean.setRoleId(2);

		list = UserModel.getRoles(bean);

		Iterator<UserBean> it = list.iterator();
		while (it.hasNext()) {
			bean = it.next();
			System.out.print(bean.getFirstName());
			System.out.println(bean.getGender());
		}

	}

	/**
	 * Test authenticate.
	 */
	private static void testAuthenticate() {
		UserBean bean = new UserBean();
		bean = UserModel.authenticate("dd", "1234");
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
	}

}