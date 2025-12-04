//we will write person details first here from which we will employee
//we couldve also written in employee only, but it will cause confusion

package model;

public class Person {
	protected String name;
	protected String email;
	protected String phone;
	
	//constructor
	public Person(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	//next we need get and set
	//we are using eclpise only to generate getter and seettter
	

}
