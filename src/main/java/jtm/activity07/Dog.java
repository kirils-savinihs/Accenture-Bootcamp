/**
 * 
 */
package jtm.activity07;

/**
 * @author student
 *
 */
public class Dog extends Mammal {

	/**
	 * 
	 */
	private java.lang.String name;

	/**
	 * 
	 */
	public Dog() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	 public void setName(String name) {
		 if (Character.isUpperCase(name.charAt(0)))
		this.name=name;
		 else 
			 this.name="";
	}

	/**
	 * 
	 */
	 public String getName() {
		return this.name;
	}

}
