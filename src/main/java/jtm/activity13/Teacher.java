package jtm.activity13;

public class Teacher {

	private int id;
	private String firstName;
	private String lastName;

	// TODO process passed values

	public Teacher(int id, String firstName, String lastName) {
		this.firstName = firstName;
		this.id = id;
		this.lastName = lastName;

	}

	public Teacher() {

	}

	public int getID() {
		// TODO return required value
		return this.id;
	}

	public String getFirstName() {
		// TODO return required value
		return this.firstName;
	}

	public String getLastName() {
		// TODO return required value
		return this.lastName;
	}

	// TODO
	// Override toString() method which returns teacher in form "Name Surname"
	@Override
	public String toString() {
		return this.firstName + " " + this.lastName;
	}
}
