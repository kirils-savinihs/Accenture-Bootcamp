package jtm.activity14;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import jtm.activity13.Teacher;

public class StudentManager {

	protected Connection conn;

	public StudentManager() {
		// TODO #1 When new StudentManager is created, create connection to the
		// database server:
		// url = "jdbc:mysql://localhost/?autoReconnect=true&useSSL=false"
		// user = "root"
		// pass = "abcd1234"
		// Hints:
		// 1. Do not pass database name into url, because some statements
		// for tests need to be executed server-wise, not just database-wise.
		// 2. Set AutoCommit to false and use conn.commit() where necessary in
		// other methods
		
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost/?autoReconnect=true&useSSL=false", "root", "abcd1234");
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Returns a Student instance represented by the specified ID.
	 * 
	 * @param id
	 *            the ID of teacher
	 * @return a Student object
	 */
	public Student findStudent(int id) {
		// TODO #2 Write an sql statement that searches teacher by ID.
		// If teacher is not found return Student object with zero or null in
		// its fields!
		// Hint: Because default database is not set in connection,
		// use full notation for table "database_activity.Student"
		try {
			java.sql.Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from database_activity.Student where id=" + id);
			rs.next();
			Student stdnt = new Student(id, rs.getString(2), rs.getString(3));
			return stdnt;

		} catch (SQLException e) {

			e.printStackTrace();
			return new Student(new Integer(null),new String(),new String());
		}

	}

	/**
	 * Returns a list of Student object that contain the specified first name
	 * and last name. This will return an empty List of no match is found.
	 * 
	 * @param firstName
	 *            the first name of teacher.
	 * @param lastName
	 *            the last name of teacher.
	 * @return a list of Student object.
	 */
	public List<Student> findStudent(String firstName, String lastName) {
		// TODO #3 Write an sql statement that searches teacher by first and
		// last name and returns results as ArrayList<Student>.
		// Note that search results of partial match
		// in form ...like '%value%'... should be returned
		// Note, that if nothing is found return empty list!
		
		List<Student> stdnts = new ArrayList<Student>();

		try {
			java.sql.Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from database_activity.Student where firstname like '%" + firstName
					+ "%' and lastname like '%" + lastName + "%'");

			while (rs.next()) {
				stdnts.add(new Student(rs.getInt(1), rs.getString(2), rs.getString(3)));

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return stdnts;
	}

	/**
	 * This method will attempt to insert an new teacher (first name and last
	 * name) into the repository.
	 * 
	 * @param firstName
	 *            the first name of teacher
	 * @param lastName
	 *            the last name of teacher
	 * @return true if insert, else false.
	 */

	public boolean insertStudent(String firstName, String lastName) {
		// TODO #4 Write an sql statement that inserts teacher in database.
		try {
			java.sql.Statement st = conn.createStatement();
			int rs = st.executeUpdate("insert into database_activity.Student values (NULL,'" + firstName + "','" + lastName + "')");
			conn.commit();
		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;
	}

	/**
	 * Try to insert Student in database
	 * 
	 * @param student
	 * @return true on success, false on error (e.g. non-unique id)
	 */
	public boolean insertStudent(Student student) {
		// TODO #5 Write an sql statement that inserts teacher in database.
		try {
			java.sql.Statement st = conn.createStatement();
			int rs = st.executeUpdate("insert into database_activity.Student values ('" + student.getID() + "','" + student.getFirstName()
					+ "','" + student.getLastName() + "')");
			conn.commit();
		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;
	}

	/**
	 * Updates an existing Student in the repository with the values represented
	 * by the Student object.
	 * 
	 * @param student
	 *            a Student object, which contain information for updating.
	 * @return true if row was updated.
	 */
	public boolean updateStudent(Student student) {
		try {
			java.sql.Statement st = conn.createStatement();
			String query = "update database_activity.Student set firstname='" + student.getFirstName() + "',lastname='"
					+ student.getLastName() + "' where id=" + student.getID();

			int rs = st.executeUpdate(query);
			System.out.println("rs=" + rs);
			if (rs == 0) {

				return false;
			}
			conn.commit();

		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Delete an existing Student in the repository with the values represented
	 * by the ID.
	 * 
	 * @param id
	 *            the ID of teacher.
	 * @return true if row was deleted.
	 */
	public boolean deleteStudent(int id) {
		// TODO #7 Write an sql statement that deletes teacher from database.
		try {
			java.sql.Statement st = conn.createStatement();
			int rs = st.executeUpdate("delete from database_activity.Student where id=" + id);
			if (rs == 0) {

				return false;
			}
			conn.commit();

		} catch (SQLException e) {

			e.printStackTrace();
			return false;

		}

		return true;
	}

	public void closeConnecion() {
		// TODO Close connection if and reset it to release connection to the
		// database server
		
		try {
			conn.commit();
			conn.close();
			conn = null;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
