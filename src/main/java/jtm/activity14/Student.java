package jtm.activity14;

// This class is used as a definition for persistent objects
// Do not change this class

public class Student {

	private int id;
	private String firstName;
	private String lastName;

	public Student(int id, String firstName, String lastName) {

		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;

	}

	public int getID() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

}
