/**
 * Test the accounts table. It uses @Before to create a data base with one table.
 * @After exist to drop the table.  Inbetween, some DataBaseAdapter methods are tested.
 * 
 * @author Rick Mercer
 */

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.DataBaseAdapter;
import model.SecureIt;

import static org.junit.Assert.assertEquals;

public class DataBaseTest {

  private static Connection conn = null;

  // This function will be called first.
  @Before
  public void buildDataBase() {
    // The .cj is new
     String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
     // String JDBC_DRIVER = "com.mysql.jdbc.Driver";
     String DB_URL = "jdbc:mysql://localhost/?characterEncoding=utf8";

    // Database credentials
    String USER = "root";
    String PASS = "csc436zona";

    // Register the JDBC driver (need a mysql-connector...-bin.jar_
    try {
      Class.forName(JDBC_DRIVER);
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      
      Statement stmt = conn.createStatement();
      String query = "DROP DATABASE IF EXISTS teamproject;";
      stmt.executeUpdate(query);

      stmt = conn.createStatement();
      query = "CREATE DATABASE teamproject";
      stmt.executeUpdate(query);
      query = "USE teamproject;";
      stmt.executeUpdate(query);
      String createTable = "CREATE TABLE accounts (id int NOT NULL AUTO_INCREMENT, "
          + "  first_name VARCHAR(64) NOT NULL, " + "  last_name  VARCHAR(64) NOT NULL, "
          + "  account_id VARCHAR(64) NOT NULL, " + "  password  VARCHAR(64) NOT NULL, " + "  PRIMARY KEY(id) " + ");";

      stmt.executeUpdate(createTable);

    } catch (ClassNotFoundException e) {
      System.out.println("Class.forName(JDBC_DRIVER)" + e.toString());
    } catch (SQLException e) {
      System.out.println("getConnection threw: " + e.toString());
    }
  }

  @Test
  public void insertNewAccount() {
    DataBaseAdapter dba = DataBaseAdapter.getInstance();
    dba.addAccount("Rick", "Mercer", "mercer@cs.arizona.edu", "one");
    dba.addAccount("Chris", "Miller", "miller@cs.arizona.edu", "two");
    dba.addAccount("Kim", "Baker", "baker@cs.arizona.edu", "three");

    try {
      ResultSet rs = dba.getAllAccounts();

      // Update cursor to the first record
      rs.next();
      assertEquals(1, rs.getInt("id"));
      assertEquals("Rick", rs.getString("first_name"));
      assertEquals("one", SecureIt.decrypt(rs.getString("password")));

      // Update cursor to the next record
      rs.next();
      assertEquals("Miller", rs.getString("last_name"));
      assertEquals("miller@cs.arizona.edu", rs.getString("account_Id"));
      assertEquals("two", SecureIt.decrypt(rs.getString("password")));

      // Update cursor to the 3rd and final record
      rs.next();
      assertEquals(3, rs.getInt("id"));
      assertEquals("Kim", rs.getString("first_name"));
      assertEquals("Baker", rs.getString("last_name"));
      assertEquals("three", SecureIt.decrypt(rs.getString("password")));

    } catch (SQLException e) {
    }
  }
}
