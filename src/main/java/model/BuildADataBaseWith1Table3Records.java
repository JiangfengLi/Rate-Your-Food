package model;

/** As part of a spike, create database teamproject, table accounts,
 * and insert 3 records with encrypted passwords
 * 
 * @author Rick Mercer
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildADataBaseWith1Table3Records {

  //private static Connection conn = null;

  public static void main(String[] args) {
    String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    String DB_URL = "jdbc:mysql://localhost/?characterEncoding=utf8";

    // Database credentials
    String USER = "root";
    String PASS = "csc436zona";

    // Register the JDBC driver (need a mysql-connector...-bin.jar_
    try {
      Class.forName(JDBC_DRIVER);
      Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
      Statement stmt = conn.createStatement();
      String query = "DROP DATABASE if exists teamproject";
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

    DataBaseAdapter dba = DataBaseAdapter.getInstance();
    dba.addAccount("Rick", "Mercer", "mercer@cs.arizona.edu", "one");
    dba.addAccount("Chris", "Miller", "miller@cs.arizona.edu", "two");
    dba.addAccount("Kim", "Baker", "baker@cs.arizona.edu", "three");
  }
}
