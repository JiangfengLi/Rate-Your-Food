package view.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import view.ViewController;

/**
 * CREATE ACCOUNT
 * view class for creating an account
 * @author Alexander Miller
 */
public class CreateAccount extends UserLogInUI {

	// VIEW ELEMENTS
	protected HBox firstNameRow;
	protected Label firstNameLabel;
	protected TextField firstNameField;
	
	protected HBox lastNameRow;
	protected Label lastNameLabel;
	protected TextField lastNameField;
	
	protected HBox confirmationRow;
	protected Label confirmationLabel;
	protected PasswordField confirmationField;
	
	/**
	 * CONSTRUCTOR
	 * creates and stylizes our view; assigns event handlers
	 * @param viewController reference to controller
	 */
	public CreateAccount(ViewController viewController) {
		// set-up that is shared with inheriting classes
		createAccountFactory(viewController);
		
		// add submit event handler
		submitButton.setOnAction(new SubmitHandler());
		
		// put rows in a vertical structure
		verticalStructure.getChildren().add(emailRow);
		verticalStructure.getChildren().add(firstNameRow);
		verticalStructure.getChildren().add(lastNameRow);
		verticalStructure.getChildren().add(passwordRow);
		verticalStructure.getChildren().add(confirmationRow);
		verticalStructure.getChildren().add(buttonRow);
		verticalStructure.getChildren().add(errorMessage);
		
		// put vertical structure in our hbox (so we can center it)
		this.getChildren().add(verticalStructure);
	}
	
	/**
	 * CREATE ACCOUNT
	 * useless constructor to satisfy compiler
	 */
	public CreateAccount() {}
	
	/**
	 * CREATE ACCOUNT FACTORY
	 * provides setup for inheriting classes
	 */
	protected void createAccountFactory(ViewController viewController) {
		// do common grunt initialization in super
		super.factory(viewController);

		// create firstname row
		firstNameRow = new HBox();
		firstNameRow.setSpacing(HORIZONTAL_SPACING);
		firstNameLabel = new Label("First Name: ");
		firstNameLabel.setPrefWidth(LABEL_WIDTH);
		firstNameField = new TextField();
		firstNameRow.getChildren().add(firstNameLabel);
		firstNameRow.getChildren().add(firstNameField);
		
		// create lastname row
		lastNameRow = new HBox();
		lastNameRow.setSpacing(HORIZONTAL_SPACING);
		lastNameLabel = new Label("Last Name: ");
		lastNameLabel.setPrefWidth(LABEL_WIDTH);
		lastNameField = new TextField();
		lastNameRow.getChildren().add(lastNameLabel);
		lastNameRow.getChildren().add(lastNameField);

		// create confirmation row
		confirmationRow = new HBox();
		confirmationRow.setSpacing(HORIZONTAL_SPACING);
		confirmationLabel = new Label("Confirm Password: ");
		confirmationLabel.setPrefWidth(LABEL_WIDTH);
		confirmationField = new PasswordField();
		confirmationRow.getChildren().add(confirmationLabel);
		confirmationRow.getChildren().add(confirmationField);
	}
	
	/**
	 * WIPE VIEW
	 * wipes the text fields, labels, etc. back to defaults
	 */
	@Override
	protected void wipeView() {
		super.wipeView();
		firstNameField.setText("");
		lastNameField.setText("");
		confirmationField.setText("");
	}

	/**
	 * SUBMIT HANDLER
	 * handles creation of accounts - validates inputs, handles switching views
	 */
	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// validate email, name, pass
			String email = emailField.getText();
			String firstName = firstNameField.getText();
			String lastName = lastNameField.getText();
			String pass = passwordField.getText();
			String msg = validateEmail(email);
			if (msg!=null) {
				displayError(msg);
				return;
			}
			msg = validateName(firstName);
			if (msg != null) {
				displayError(msg);
				return;
			}
			msg = validateName(lastName);
			if (msg != null) {
				displayError(msg);
				return;
			}
			msg = validatePassword(pass);
			if (msg != null) {
				displayError(msg);
				return;
			}
			
			// make sure pass matches confirmation pass
			String passConf = confirmationField.getText();
			if (pass.equals(passConf)) {
				msg = viewController.addUser(email, firstName, lastName, pass);
				// move to home page if added user with no hitches
				if (msg == null) {
					wipeView();
					viewController.moveToHomePage();
					viewController.updateMyPage();
				} else {
					displayError(msg);
				}
			} else {
				displayError("Try again! Passwords don't match.");
			}

		}
	}
	
	
}
