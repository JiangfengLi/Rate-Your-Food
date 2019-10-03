package view;

/**
 * This is part of a spike to see if we can load the list of accounts from 
 * a web page on a server and access the database.
 * 
 * This class does not have a future in your team project.
 *   
 * @author Rick Mercer
 */

import java.sql.ResultSet;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import model.DataBaseAdapter;

public class AccountView extends VBox {

  private Label heading = new Label("Account List");
  private Label selectedAccount = new Label("Select Account");
      
  private Button selectedAccountButton = new Button("Change the Label below");
  private ListView<String> listOfAccounts;

  /**
   * Construct a view that has a heading, a ListView holding the list of accounts.
   * When a button is clicked, the string is printed
   * 
   * @author Rick Mercer
   */
  public AccountView() {

    // Set the list of Song objects
    populateTheListViewWithAccountObjects();

    // Layout this VBox
    layoutAccountView();
  }

  // This method gets the data from the song table in the database jukebox436
  private void populateTheListViewWithAccountObjects() {
    try {
      // Get the table songs as a ResultSet
      DataBaseAdapter dba = new DataBaseAdapter();
      ResultSet rs = dba.getAllAccounts();

      listOfAccounts = new ListView<>();
      // Add all songs to a view where any element can be selected
      while (rs.next()) {
        listOfAccounts.getItems().add(rs.getString("first_name") + " " + rs.getString("last_name"));
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }

    // Set up the button handler so songs can be added to the PlayList
    selectedAccountButton.setOnAction(ae -> {
       selectedAccount.setText(listOfAccounts.getSelectionModel().getSelectedItem().toString());
    });

  }

  // Layout nodes and style the heading and button
  private void layoutAccountView() {
    // Create a fixed size
    this.setMaxWidth(300);
    this.setMaxHeight(500);
    
    this.setPadding(new Insets(10, 50, 50, 50));
    this.setSpacing(20);

    // Place 10 pixel border
    this.setPadding(new Insets(20, 20, 20, 20));
    // Center all nodes
    this.setAlignment(Pos.CENTER); 
    
    // Style the heading and button with CSS:
    heading.setStyle("-fx-font: bold italic 14pt 'Arial'; -fx-text-fill: darkblue;");

    // Style the button with CSS:
    String style = "-fx-background-color: POWDERBLUE;";
    style += "-fx-font-size: 12pt;";
    style += "-fx-border-style: solid;";
    style += "-fx-border-width: 2px;";
    style += "-fx-border-color: darkblue;";
    style += "-fx-text-fill: darkblue;";
    selectedAccountButton.setStyle(style);
    
    // Style the label with CSS
    selectedAccount.setStyle("-fx-font: bold italic 14pt 'Arial'; -fx-text-fill: green;");
    
    // Add all three nodes to this VBox
    this.getChildren().addAll(heading, listOfAccounts, selectedAccountButton, selectedAccount);
  }
} // End SongLibraryView class