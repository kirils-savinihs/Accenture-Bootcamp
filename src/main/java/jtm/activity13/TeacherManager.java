package jtm.activity13;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysql.jdbc.Statement;

public class TeacherManager {

	protected Connection conn;

	public TeacherManager() {
		// TODO #1 When new TeacherManager is created, create connection to the
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
	 * Returns a Teacher instance represented by the specified ID.
	 * 
	 * @param id the ID of teacher
	 * @return a Teacher object
	 */
	public Teacher findTeacher(int id) {
		// TODO #2 Write an sql statement that searches teacher by ID.
		// If teacher is not found return Teacher object with zero or null in
		// its fields!
		// Hint: Because default database is not set in connection,
		// use full notation for table "database_activity.Teacher"
		try {
			java.sql.Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from database_activity.Teacher where id=" + id);
			rs.next();
			Teacher tch = new Teacher(id, rs.getString(2), rs.getString(3));
			return tch;

		} catch (SQLException e) {
			System.out.println("EXCEPTION");
			e.printStackTrace();
			return new Teacher();
		}

	}

	/**
	 * Returns a list of Teacher object that contain the specified first name and
	 * last name. This will return an empty List of no match is found.
	 * 
	 * @param firstName the first name of teacher.
	 * @param lastName  the last name of teacher.
	 * @return a list of Teacher object.
	 */
	public List<Teacher> findTeacher(String firstName, String lastName) {
		// TODO #3 Write an sql statement that searches teacher by first and
		// last name and returns results as ArrayList<Teacher>.
		// Note that search results of partial match
		// in form ...like '%value%'... should be returned
		// Note, that if nothing is found return empty list!

		List<Teacher> tchrs = new ArrayList<Teacher>();

		try {
			java.sql.Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from database_activity.Teacher where firstname like '%" + firstName
					+ "%' and lastname like '%" + lastName + "%'");

			while (rs.next()) {
				tchrs.add(new Teacher(rs.getInt(1), rs.getString(2), rs.getString(3)));

			}

		} catch (SQLException e) {
			System.out.println("EXCEPTION");
			e.printStackTrace();

		}

		return tchrs;

	}

	/**
	 * Insert an new teacher (first name and last name) into the repository.
	 * 
	 * @param firstName the first name of teacher
	 * @param lastName  the last name of teacher
	 * @return true if success, else false.
	 */

	public boolean insertTeacher(String firstName, String lastName) {
		// TODO #4 Write an sql statement that inserts teacher in database.
		try {
			java.sql.Statement st = conn.createStatement();
			int rs = st.executeUpdate("insert into database_activity.Teacher values (NULL,'" + firstName + "','" + lastName + "')");
			conn.commit();
		} catch (SQLException e) {
			System.out.println("INSERT TEACHER EXCEPTION");
			e.printStackTrace();
			return false;

		}

		return true;
	}

	/**
	 * Insert teacher object into database
	 * 
	 * @param teacher
	 * @return true on success, false on error (e.g. non-unique id)
	 */
	public boolean insertTeacher(Teacher teacher) {
		// TODO #5 Write an sql statement that inserts teacher in database.
		try {
			java.sql.Statement st = conn.createStatement();
			int rs = st.executeUpdate("insert into database_activity.Teacher values ('" + teacher.getID() + "','" + teacher.getFirstName()
					+ "','" + teacher.getLastName() + "')");
			conn.commit();
		} catch (SQLException e) {
			System.out.println("INSERT TEACHER 2 EXCEPTION");
			e.printStackTrace();
			return false;

		}

		return true;
	}

	/**
	 * Updates an existing Teacher in the repository with the values represented by
	 * the Teacher object.
	 * 
	 * @param teacher a Teacher object, which contain information for updating.
	 * @return true if row was updated.
	 */
	public boolean updateTeacher(Teacher teacher) {

		// TODO #6 Write an sql statement that updates teacher information.

		try {
			java.sql.Statement st = conn.createStatement();
			String query = "update database_activity.Teacher set firstname='" + teacher.getFirstName() + "',lastname='"
					+ teacher.getLastName() + "' where id=" + teacher.getID();
			System.out.println(query);
			int rs = st.executeUpdate(query);
			System.out.println("rs=" + rs);
			if (rs == 0) {
				System.out.println("updateTeacher returned false");
				return false;
			}
			conn.commit();

		} catch (SQLException e) {
			System.out.println("UPDATE TEACHER EXCEPTION");
			e.printStackTrace();
			return false;
		}

		System.out.println("updateTeacher returned true");
		return true;

	}

	/**
	 * Delete an existing Teacher in the repository with the values represented by
	 * the ID.
	 * 
	 * @param id the ID of teacher.
	 * @return true if row was deleted.
	 */
	public boolean deleteTeacher(int id) {

		// TODO #6 Write an sql statement that updates teacher information.

		try {
			java.sql.Statement st = conn.createStatement();
			int rs = st.executeUpdate("delete from database_activity.Teacher where id=" + id);
			if (rs == 0) {
				System.out.println("updateTeacher returned false");
				return false;
			}
			conn.commit();

		} catch (SQLException e) {
			System.out.println("DELETE TEACHER EXCEPTION");
			e.printStackTrace();
			return false;

		}
		System.out.println("deleteTeacher returned true");
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
