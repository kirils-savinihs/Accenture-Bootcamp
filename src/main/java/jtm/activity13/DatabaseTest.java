
package jtm.activity13;

import static jtm.TestUtils.checkMySQL;
import static jtm.TestUtils.executeCmd;
import static jtm.TestUtils.getDate;
import static jtm.TestUtils.handleErrorAndFail;
import static jtm.TestUtils.randomLatinName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import jtm.TestUtils;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(5);

	// Common trigger to reset database only once per test suite
	private static boolean dirtyDatabase = true;
	private static Logger logger = Logger.getLogger(DatabaseTest.class);

	private static Connection conn;
	private static TeacherManager manager;

	private static String fnameToDelete = "To be";
	private static String lnameToDelete = "Deleted";
	private static int idToDelete = 5;

	@BeforeClass
	public static void setUpBeforeClass() {
		String password = "abcd1234";
		try {
			checkMySQL();

//			assertTrue("Password '" + password + "' for root user of MySQL doesn't work.",
//					"0\n".equals(executeCmd("mysql -h 127.0.0.1 --protocol=tcp -uroot -p" + password + " --execute=quit 2>/dev/null; echo $?")));

//			if ("".equals(executeCmd(
//					"mysql -s -h 127.0.0.1 --protocol=tcp -uroot -p" + password + " -e \"show databases;\" 2>/dev/null|grep database_activity")))
//				logger.warn("There was no database 'database_activity' when test was started;");

			resetDatabase();
			logger.info("Database was dropped and recreated");

//			assertTrue("Database 'database_activity' is not created.", "0\n".equals(TestUtils
//					.executeCmd("mysql -h 127.0.0.1 --protocol=tcp -uroot -pabcd1234 database_activity --execute=\"quit\" 2>/dev/null; echo $?")));

			manager = new TeacherManager();
			System.out.println(manager);
			conn = manager.conn;
			assertNotEquals("TeacherManager connection is not initialized", null, conn);
			logger.debug("Connection successfully established!");

			logger.info("OK");
		} catch (

		Exception e) {
			handleErrorAndFail(e);
		}
	}

	@Test
	public void test01FindTeacherByID() {
		try {
			manager = new TeacherManager();
			// Positive tests
			Teacher result = manager.findTeacher(1);
			checkTeacher(result, "Mara", "Ett", 1l);
			result = manager.findTeacher(2);
			checkTeacher(result, "Sara", "Du", 2l);
			// Negative test
			result = manager.findTeacher(-128);
			checkTeacher(result, null, null, 0l);
			logger.info("OK");
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
	}

	@Test
	public void test02FindTeachersByNames() {
		try {
			// Positive tests
			List<Teacher> results = manager.findTeacher("RA", "E");
			assertEquals("findTeacher() error.", 2, results.size());
			checkTeachers(results, "Mara", "Ett", 1l, 0);
			checkTeachers(results, "Lara", "Tre", 3l, 1);
			results = manager.findTeacher("ARA", "DU");
			assertEquals("findTeacher() result size error.", 1, results.size());
			checkTeachers(results, "Sara", "Du", 2l, 0);
			// Negative tests
			results = manager.findTeacher("This teacher", "should NOT be found");
			assertEquals("findTeacher() error for negative search.", 0, results.size());
			logger.info("OK");
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
	}

	@Test
	public void test03InsertTeacher() {
		try {
			// Only positive test
			String fname = "testF";
			String lname = "testL";
			boolean result = manager.insertTeacher(fname, lname);
			assertTrue(result);
			List<Teacher> results = manager.findTeacher(fname, lname);
			assertEquals("findTeacher() result size error.", 1, results.size());
			checkTeachers(results, fname, lname);
			logger.info("OK");
		} catch (Exception e) {
			handleErrorAndFail(e);
		}

	}

	@Test
	public void test04InsertTeacherAll() {
		try {
			// Positive test
			String fname = "Insert";
			String lname = "All";
			int id = 5;
			Teacher teacher = new Teacher(id, fname, lname);
			boolean result = manager.insertTeacher(teacher);
			assertTrue(result);
			// Search for inserted
			List<Teacher> results = manager.findTeacher(fname, lname);
			assertEquals("insertTeacherAll() results size error.", 1, results.size());
			checkTeachers(results, fname, lname, (long) id, 0);

			// Negative tests
			// Insert again the same values
			result = manager.insertTeacher(teacher);
			assertFalse(result);
			// Search again
			results = manager.findTeacher(fname, lname);
			assertEquals("insertTeacherAll() results size error.", 1, results.size());
			checkTeachers(results, fname, lname, (long) id, 0);

			// Insert with different values but the same ID
			fname = "Insert1";
			lname = "All1";
			Teacher teacher1 = new Teacher(5, fname, lname);
			result = manager.insertTeacher(teacher1);
			assertFalse(result);
			// Search
			results = manager.findTeacher(fname, lname);
			assertEquals("insertTeacherAll() results size error.", 0, results.size());
			logger.info("OK");
		} catch (Exception e) {
			handleErrorAndFail(e);
		}

	}

	@Test
	public void test05UpdateTeacher() {
		try {
			// Positive test with existing teacher
			Teacher teacher = new Teacher(5, fnameToDelete, lnameToDelete);
			boolean result = manager.updateTeacher(teacher);
			assertTrue(result);
			List<Teacher> results = manager.findTeacher(fnameToDelete, lnameToDelete);
			assertEquals("updateTeacher() results size error.", 1, results.size());
			checkTeachers(results, fnameToDelete, lnameToDelete, (long) idToDelete, 0);

			// Negative test with non existing teacher
			String fname = "This teacher";
			String lname = "Should not be updated";
			teacher = new Teacher(123, fname, lname);
			result = manager.updateTeacher(teacher);
			assertFalse(result);
			results = manager.findTeacher(fname, lname);
			assertEquals("updateTeacher() results size error.", 0, results.size());

			logger.info("OK");
		} catch (Exception e) {
			handleErrorAndFail(e);
		}

	}

	@Test
	public void test06DeleteTeacher() {
		try {
			// Delete
			boolean result = manager.deleteTeacher(idToDelete);
			assertTrue(result);
			// Check
			List<Teacher> results = manager.findTeacher(fnameToDelete, lnameToDelete);
			assertEquals("deleteTeacher() results size error.", 0, results.size());
			// Delete again
			result = manager.deleteTeacher(idToDelete);
			assertFalse(result);
			logger.info("OK");
		} catch (Exception e) {
			handleErrorAndFail(e);
		}

	}

	@Test
	public void test07InjectionTests() {
		try {
			boolean result = manager.insertTeacher("A','A');" + "drop database database_activity;"
					+ "insert into database_activity.Teacher (firstname, lastname) VALUES ('B", "B");
			assertFalse("Negative test with SQL injection passed", result);
			logger.info("OK");
		} catch (Exception e) {
			handleErrorAndFail(e);
		}

	}

	@Test
	public void test08TransactionCommit() {
		String name1 = "Transaction1_" + getDate();
		String name2 = "Transaction2_" + getDate();
		logger.debug("Initial name: " + name1);
		int id;
		try {
			TeacherManager manager1 = new TeacherManager();
			TeacherManager manager2 = new TeacherManager();
			manager2.conn.setAutoCommit(true); // Don't use transactions for
												// this connection
			assertTrue("Different connections are not created. Connection1: " + manager1 + " Connection2: " + manager2,
					!manager1.equals(manager2));

			// Add teacher
			manager1.insertTeacher(name1, name1);
			List<Teacher> teachers = manager2.findTeacher(name1, name1);
			assertNotNull(
					"List of teachers for commited transaction test is empty. Check that transaction is commited, when teacher is added.",
					teachers);
			checkTeachers(teachers, name1, name1, null, null, "Check that transaction is commited, when teacher is added.");

			// Update Teacher
			id = teachers.get(0).getID();
			name1 = randomLatinName();
			logger.debug("Updated name: " + name1);
			manager1.updateTeacher(new Teacher(id, name2, name2));
			Teacher findTeacher = manager2.findTeacher(id);
			checkTeacher(findTeacher, name2, name2, (long) id, "Check that transaction is commited, when teacher is updated.");

			// Delete teacher
			manager1.deleteTeacher(id);
			findTeacher = manager2.findTeacher(id);
			checkTeacher(findTeacher, null, null, 0l, "Check that transaction is commited, when teacher is deleted.");

			// Close connections
			manager1.closeConnecion();
			manager2.closeConnecion();
			logger.info("OK");

		} catch (Exception e) {
			handleErrorAndFail(e);
		}

	}

	@Test
	public void test10closeConnection() {
		try {
			if (manager != null)
				manager.closeConnecion();
			assertNull("Connection is not set to null when closed.", manager.conn);
		} catch (Exception e) {
			handleErrorAndFail(e);
		}
	}

	public static synchronized void resetDatabase() {
		if (dirtyDatabase) {
			try {
				String workspace = System.getProperty("user.dir");
				executeCmd("mysql -h 127.0.0.1 --protocol=tcp -s -uroot -pabcd1234 < " + workspace
						+ "/src/main/java/jtm/activity13/database.sql 2>/dev/null");
				logger.info("Database dump restored");
				dirtyDatabase = false;
			} catch (Exception e) {
				handleErrorAndFail(e);
			}
		} else
			logger.info("Database is clean");
	}

	private void checkTeachers(List<Teacher> results, String firstname, String lastname) {
		checkTeachers(results, firstname, lastname, null, null, null);
	}

	private void checkTeachers(List<Teacher> results, String firstname, String lastname, Long id, Integer recordno) {
		checkTeachers(results, firstname, lastname, id, recordno, null);
	}

	private void checkTeachers(List<Teacher> results, String firstname, String lastname, Long id, Integer recordno,
			String additionalMessage) {
		if (recordno == null)
			recordno = 0;
		if (additionalMessage == null)
			additionalMessage = "";
		else
			additionalMessage = " " + additionalMessage;
		assertNotNull("List of teachers is null. If no teachers are found, empty list should be returned.", results);
		assertEquals("Firstname comparison error." + additionalMessage, firstname, results.get(recordno).getFirstName());
		assertEquals("Lastname comparison error." + additionalMessage, lastname, results.get(recordno).getLastName());
		if (id != null)
			assertEquals("ID comparison error.", (long) id, results.get(recordno).getID());
	}

	private void checkTeacher(Teacher result, String firstname, String lastname, Long id) {
		checkTeacher(result, firstname, lastname, id, null);
	}

	private void checkTeacher(Teacher result, String firstname, String lastname, Long id, String additionalMessage) {
		if (additionalMessage == null)
			additionalMessage = "";
		else
			additionalMessage = " " + additionalMessage;

		assertEquals("Firstname comparison error." + additionalMessage, firstname, result.getFirstName());
		assertEquals("Lastname comparison error." + additionalMessage, lastname, result.getLastName());
		if (id != null)
			assertEquals("ID comparison error." + additionalMessage, (long) id, result.getID());
	}

}
