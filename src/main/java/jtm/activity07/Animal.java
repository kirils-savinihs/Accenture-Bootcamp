/**
 * 
 */
package jtm.activity07;

/**
 * @author student
 *
 */
public class Animal {

	/**
	 * 
	 */
	private int age;

	/**
	 * 
	 */
	public Animal() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public void setAge(int age) {
		if (age > 0) {
			this.age = age;
		}
		else
		{
			this.age=0;
		}
	}

	/**
	 * 
	 */
	public int getAge() {
		return this.age;
	}

}
