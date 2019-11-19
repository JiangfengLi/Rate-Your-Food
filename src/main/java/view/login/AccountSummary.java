package view.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;
import view.ViewController;

/**
 * ACCOUNT VIEW
 * view class for the manage-account page accessed thru the drop down menu on the HUD
 * @author Jiangfeng and Alexander Miller
 *
 */
public class AccountSummary extends HBox {
	
	// CONTROLLER
	private ViewController viewController;

	// STRUCTURE
	private VBox verticalStructure;
	private HBox buttonRow;
	
	// CURRENT DATA NODES
	private Label title;
	private Label name;
	private Label email;
	private Label password;
	
	// BUTTON NODES
	private Button editButton;
	private Button backButton;
	
	// MISC
	private Label errorMessage;
	
	
	/**
	 * CONSTRUCTOR
	 */
	public AccountSummary(ViewController viewController) {
		// pass in reference to controller
		this.viewController = viewController;

		// set constant strings
		title = new Label("Account");
		password = new Label("Password: *******");
		
		// create data-specific placeholders
		name = new Label("");
		email = new Label("");
		
		// create error message placeholder
		errorMessage=new Label("");
		
		// set up buttons
		editButton = new Button("Edit");
		backButton = new Button("Back");
		editButton.setOnAction(new EditHandler());
		backButton.setOnAction(new BackHandler());
		
		// set up button row
		buttonRow = new HBox();
		buttonRow.setAlignment(Pos.CENTER);
		buttonRow.setSpacing(UserLogInUI.HORIZONTAL_SPACING);
		buttonRow.getChildren().add(backButton);
		buttonRow.getChildren().add(editButton);
		
		// set up vertical structure
		verticalStructure = new VBox();
		verticalStructure.setAlignment(Pos.CENTER);
		verticalStructure.setSpacing(UserLogInUI.VERTICAL_SPACING);
		verticalStructure.getChildren().add(title);
		verticalStructure.getChildren().add(email);
		verticalStructure.getChildren().add(name);
		verticalStructure.getChildren().add(password);
		verticalStructure.getChildren().add(buttonRow);
		
		// horizontal structure configs
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(verticalStructure);
	}
	
	
	/**
	 * POPULATE DATA
	 * handles population of data, s.t. it might be repeated every time the page is called
	 */
	public void populateData() {
		// populate specific data
		User currentUser = viewController.getCurrentUser();
		name.setText("Full name: "+currentUser.getFullName());
		email.setText("Email: "+currentUser.getEmail());
	}
	
	
	/**
	 * BACK HANDLER
	 * inside class to navigate back to log in root
	 */
	private class BackHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// move to home page
			viewController.moveToHomePage();
		}
	}
	
	/**
	 * EDIT HANDLER
	 * inside class to navigate back to log in root
	 */
	private class EditHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToEditAccount();
		}
	}
	

}
