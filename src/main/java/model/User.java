package model;

/**
 * simple class to hold metadata about a particular user
 * @author Alexander Miller
 *
 */
public class User {
	
	// INSTANCE VARIABLES
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	
	// ***************************** CONSTRUCTORS *************************
	/**
	 * this constructor is for use to hold data on Users already in DB
	 * 		thus the serialID has already been generated
	 * if ID is not known, just pass null
	 */
	public User(String email, String firstName, String lastName, String password) {
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}
	
	// ***************************** GETTERS *************************
	public String getEmail() {
		return this.email;
	}
	public String getFirstName() {
		return this.firstName;
	}
	public String getLastName() {
		return this.lastName;
	}
	public String getPassword() {
		return this.password;
	}
	
}