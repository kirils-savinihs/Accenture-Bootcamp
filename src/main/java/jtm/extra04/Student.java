package jtm.extra04;

public class Student {
	private int ID;
	private String firstName;
	private String lastName;
	private int phoneNumber;

	public Student() {
	};

	public Student(int ID, String firstname, String lastName, int phoneNumber) {
		// TODO #1 Create new student, assign him ID, first name, last name and
		// phone number from passed values
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
