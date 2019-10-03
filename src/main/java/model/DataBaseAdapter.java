package model;

/**
 * When this Singleton is created in the first call to getInstance, 
 * it uses JDBC to read from table songs from database jukebox436. 
 * The database managements system is mysql that must have an admin
 * name root with the password "password". The password can be changed.
 * 
 * @author Rick Mercer
 */
import java.sql.*;

public class DataBaseAdapter {

  private ResultSet rs;

  // Make this a singleton
  private static DataBaseAdapter single_instance = null;

  synchronized public static DataBaseAdapter getInstance() {
    if (single_instance == null) {
      try {
        single_instance = new DataBaseAdapter();
      } catch (Exception e) {
        System.out.println("getInstance threw an exception ");
      }
    }
    return single_instance;
  }

  // The adapters connection to a MySQL data base on this computer
  private static Connection conn = null;

  public DataBaseAdapter()  {
    // Register the JDBC driver (need an Oracle account and
    // mysql-connector...-bin.jar
    // JDBC driver name and database URL
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    // Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
    String DB_URL = "jdbc:mysql://localhost/teamproject?characterEncoding=utf8";
    // Database credentials
    String USER = "root";
    String PASS = "csc436zona";
      try {
      Class.forName(JDBC_DRIVER);
      // Open a connection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  } // end constructor

  // Return all columns of all rows from the table
  public ResultSet getAllAccounts() {
    try {
      // Store the SQL command:
      String query = "SELECT * FROM accounts";
      Statement stmt = conn.createStatement();
      // executeQuery makes a real query on the real database
      rs = stmt.executeQuery(query);
      return rs;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public void addAccount(String firstname, String lastname, String accountname, String password) {
  
    String encryptedPassword = SecureIt.encrypt(password);
    try {
      String query = "insert into accounts(first_name, last_name, account_id, password) " 
       + "values ('" + firstname + "', '" + lastname + "', '" + accountname + "', '" 
       + encryptedPassword + "');";
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // This is used in the StarterCode. 
  public void dropDatabase(String db) {
    String query = "DROP DATABASE " + db;
    try {
      Statement stmt = conn.createStatement();
      stmt.executeUpdate(query);
   } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }    
  }
}