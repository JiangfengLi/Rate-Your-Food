package model;

/**
 * simple class to hold metadata about a particular user
 * @author Alexander Miller
 *
 */
public class User extends DataObject {
	
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
	
	/**
	 * EQUALS
	 * returns true if both non-null User instances with all of the instance fields the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof User) {
			User userObj = (User) obj;
			
			// compare emails
			boolean emailsSame = compareTwoStrings(this.email,userObj.getEmail());
			
			// compare first names
			boolean firstNamesSame = compareTwoStrings(this.firstName,userObj.getFirstName());
			
			// compare last names
			boolean lastNamesSame = compareTwoStrings(this.lastName,userObj.getLastName());
			
			// compare passwords
			boolean passwordsSame = compareTwoStrings(this.password,userObj.getPassword());
			
			return emailsSame && firstNamesSame && lastNamesSame && passwordsSame;
		} else {
			return false;
		}
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
	
	// changes by Joseph Corona
	// ------------------------------------------------------------------------
	public String getFullName() {
		return firstName+" "+lastName;
	}
	// ------------------------------------------------------------------------

	
}