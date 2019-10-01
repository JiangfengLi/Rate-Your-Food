package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * DB ACCESS
 * utility class for accessing database
 * @author Alexander Miller
 *
 */
public class DBAccess {

	// JDBC driver, database URL, credentials
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/foodrecipe436?characterEncoding=utf8";
	static final String USER = "root";
	static final String PASS = "csc436zona";
	
	// Queries
	static final String GET_USER_BY_EMAIL = "SELECT * FROM User WHERE Email=?;";
	static final String ADD_USER = "INSERT INTO User(Email, FirstName, LastName, Password) VALUES(?,?,?,?);";
	
	// current user (who's logged in?)
	private User currentUser;
	
	// CONSTRUCTOR
	public DBAccess() {
		currentUser = null;
	}
	
	// ************************** DATABASE UTILITY METHODS *******************
	/**
	 * ESTABLISH CONNECTION
	 * @throws Exception if db fails to connect
	 */
    private Connection establishConnection() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        return conn;
    }
    
	// ************************** EXTERNAL INTERFACE *********************
    /**
     * GET CURRENT USER
     * who's logged in right now?
     */
    public User getCurrentUser() {
    	return this.currentUser;
    }
    
    /**
     * GET USER FROM DB
     * returns User, or null if none exists - based on ID
     */
    public User getUser(String email) {
    	try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_USER_BY_EMAIL);
    		stmt.setString(1, email);
    		ResultSet rs = stmt.executeQuery();
    		User newUser = null;
    		// should only return 1 result, since ID is a primary key
    		while (rs.next()) {
    			newUser = new User(rs.getString(1),rs.getString(2),rs.getString(3),SecureIt.decrypt(rs.getString(4)));
    		}
    		stmt.close();
    		conn.close();
    		return newUser;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
    }
    
	/**
	 * ADD USER 
	 * creates the new User instance and instantiating a new ID for it, adds a user to database, logs them in
	 * 		assumes that you have already validated that the user doesn't already exist before
	 * returns null if successful, or error message if otherwise
	 */
	public String addUser(String email, String firstName, String lastName, String password) {
		// add user to the DB
		try {
			Connection conn = establishConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_USER);
			stmt.setString(1, email);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName);
			stmt.setString(4, SecureIt.encrypt(password));
			stmt.executeUpdate();
			
			User newUser = new User(email,firstName,lastName,password);
			currentUser = newUser;
			
			stmt.close();
			conn.close();
		} catch (Exception x) {
			x.printStackTrace();
			return "ERROR with database encountered. Please try again.";
		}
		return null;
	}
	
	/**
	 * LOG OUT
	 * logs a user out (sets current user to null)
	 */
	public void logOut() {
		currentUser = null;
	}
	
	/**
	 * LOG IN
	 * logs a user in (verifies password with db, makes them current user if correct)
	 * returns null if successful, or error message if otherwise
	 */
	public String logIn(String email, String password) {
		User existingUser = getUser(email);
		// if user doesn't exist, that's a problem
		if (existingUser == null) {
			return "No account found for that user!";
		} 
		// otherwise verify password is correct
		if (password.equals(existingUser.getPassword())) {
			currentUser = existingUser;
		} else {
			return "Incorrect password!";
		}
		return null;
	}
	
}
