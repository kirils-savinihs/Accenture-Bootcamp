/**
 * 
 */
package jtm.activity14;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import org.junit.Assert.*;
import org.junit.runners.MethodSorters;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import java.sql.*;
import java.util.List;

/**
 * @author student
 *
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseUnitTest1 {
	
	public static StudentManager manager;
	
	public static void main(String[] args) {
	    DatabaseUnitTest dbUnitTest = new DatabaseUnitTest();
	    dbUnitTest.test();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		manager = new StudentManager();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	
		//manager.conn.commit();
		//manager.conn.close();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

//		String workspace = System.getProperty("user.dir");
//		executeCmd("mysql -h 127.0.0.1 --protocol=tcp -s -uroot -pabcd1234 < " + workspace
//				+ "/src/main/java/jtm/activity13/database.sql 2>/dev/null");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {

	}
	

	/**
	 * Test method for {@link jtm.activity14.StudentManager#StudentManager()}.
	 */
	@Test
	public final void testStudentManager() {
		assertNotEquals("Connection is not initialized", null, manager.conn);
		
	}

	/**
	 * Test method for {@link jtm.activity14.StudentManager#findStudent(int)}.
	 */
	@Test
	public final void testFindStudentById() {
		Student result = manager.findStudent(1);
		assertEquals("firstname comparisson error",result.getFirstName(),"Anna");
		assertEquals("lastname comparisson error",result.getLastName(),"Tress");
		assertNotEquals("lastname comparisson error",result.getFirstName(),"Ann");
		assertNotEquals("lastname comparisson error",result.getLastName(),"Tres");
	}

	/**
	 * Test method for {@link jtm.activity14.StudentManager#findStudent(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testFindStudentByNameSurname() {
		List <Student> result = manager.findStudent("Anna","Tress");
		assertEquals("array size not correct",result.size(),1);
		assertEquals ("ID comparisson error",result.get(0).getID(),1);
		
	}

	/**
	 * Test method for {@link jtm.activity14.StudentManager#insertStudent(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testInsertStudentStringString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link jtm.activity14.StudentManager#insertStudent(jtm.activity14.Student)}.
	 */
	@Test
	public final void testInsertStudentStudent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link jtm.activity14.StudentManager#updateStudent(jtm.activity14.Student)}.
	 */
	@Test
	public final void testUpdateStudent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link jtm.activity14.StudentManager#deleteStudent(int)}.
	 */
	@Test
	public final void testDeleteStudent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link jtm.activity14.StudentManager#closeConnecion()}.
	 */
	@Test
	public final void testCloseConnecion() {
		fail("Not yet implemented"); // TODO
	}

}
