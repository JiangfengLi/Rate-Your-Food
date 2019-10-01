package view.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.ViewController;

/**
 * USER LOG IN UI
 * provides common set up and cosmetic aspects to log in UIs
 * provides common functionality such as input validation and BackButton handling
 * @author Alexander Miller
 */
public abstract class UserLogInUI extends HBox {

	// CONSTANTS
	protected static int BUTTON_WIDTH = 90;
	protected static int VERTICAL_SPACING = 30;
	protected static int HORIZONTAL_SPACING = 30;
	protected static int LABEL_WIDTH = 120;
	
	// CONTROLLER REFERENCE
	protected ViewController viewController;
	
	// VIEW ELEMENTS
	protected VBox verticalStructure;
	
	protected HBox emailRow;
	protected Label emailLabel;
	protected TextField emailField;
	
	protected HBox passwordRow;
	protected Label passwordLabel;
	protected PasswordField passwordField;
	
	protected HBox buttonRow;
	protected Button backButton;
	protected Button submitButton;
	
	protected Label errorMessage;
	
	/**
	 * FACTORY
	 * stub for settings of inheriting constructors; creates and stylizes our view; assigns event handlers
	 * implements a BackHandler, but not a SubmitHandler
	 * @param viewController reference to controller
	 */
	protected void factory (ViewController viewController) {
		// pass in reference to controller
		this.viewController = viewController;
		
		// create email row
		emailRow = new HBox();
		emailRow.setSpacing(HORIZONTAL_SPACING);
		emailLabel = new Label("Email: ");
		emailLabel.setPrefWidth(LABEL_WIDTH);
		emailField = new TextField();
		emailRow.getChildren().add(emailLabel);
		emailRow.getChildren().add(emailField);
		
		// create password row
		passwordRow = new HBox();
		passwordRow.setSpacing(HORIZONTAL_SPACING);
		passwordLabel = new Label("Password: ");
		passwordLabel.setPrefWidth(LABEL_WIDTH);
		passwordField = new PasswordField();
		passwordRow.getChildren().add(passwordLabel);
		passwordRow.getChildren().add(passwordField);
		
		// create button row
		buttonRow = new HBox();
		buttonRow.setSpacing(2*HORIZONTAL_SPACING);
		backButton=new Button("Back");
		backButton.setPrefWidth(BUTTON_WIDTH);
		backButton.setOnAction(new BackHandler());
		submitButton = new Button("Submit");
		submitButton.setPrefWidth(BUTTON_WIDTH);
		buttonRow.getChildren().add(backButton);
		buttonRow.getChildren().add(submitButton);
		
		// create error message placeholder
		errorMessage=new Label("");
		
		// vertical structure configs
		verticalStructure = new VBox();
		verticalStructure.setAlignment(Pos.CENTER);
		verticalStructure.setSpacing(VERTICAL_SPACING);

		// horizontal structure configs
		this.setAlignment(Pos.CENTER);
	}
	
	/**
	 * WIPE VIEW
	 * wipes the text fields, labels, etc. back to defaults
	 */
	protected void wipeView() {
		emailField.setText("");
		passwordField.setText("");
		errorMessage.setText("");
	}
	
	/**
	 * DISPLAY ERROR
	 * display error message
	 */
	protected void displayError(String error) {
		errorMessage.setText(error);
	}
	
	/**
	 * BACK HANDLER
	 * inside class to navigate back to log in root
	 */
	protected class BackHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// wipe display, move to root
			wipeView();
			viewController.moveToLogInRoot();
		}
	}
	
	/**
	 * VALIDATE EMAIL
	 * validates email
	 * requires it to contain @ and end in .com, .net, or .org
	 * max 30 chars, min 7 chars
	 * if ok, returns null, else returns error msg
	 */
	protected String validateEmail(String email) {
		if (email.length()<7) {
			return "Email invalid. Must contain be at least 7 characters";
		}
		if (email.length()>30) {
			return "Email invalid. Must contain be at most 30 characters";
		}
		String last4Chars = email.substring(email.length()-4);
		if (!email.contains("@")) {
			return "Email invalid. Must contain '@'";
		}
		if (!last4Chars.equals(".com") && !last4Chars.equals(".org") && !last4Chars.equals(".net") ) {
			return "Email invalid. Must end with .com, .org, or .net";
		}
		return null;
	}
	
	/**
	 * VALIDATE PASSWORD
	 * validates password
	 * minimum length of 3 chars, max 20 chars
	 * if ok, returns null, else returns error msg
	 */
	protected String validatePassword(String password) {
		if (password.length()<3) {
			return "Password invalid. Must contain be at least 3 chars";
		}
		if (password.length()>20) {
			return "Password invalid. Must contain be no more than 20 chars";
		}
		return null;
	}
	
	/**
	 * VALIDATE NAME
	 * validates that name is at least 3 chars, no more than 15
	 */
	protected String validateName(String name) {
		if (name.length()<3) {
			return "Name invalid. Must contain be at least 3 chars";
		}
		if (name.length()>15) {
			return "Name invalid. Must contain no more than 15 chars";
		}
		return null;
	}
	
	
}
