package view.login;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import model.User;
import view.ViewController;

public class EditAccount extends CreateAccount {

	// UNIQUE NODES
	private HBox oldPasswordRow;
	private Label oldPasswordLabel;
	private TextField oldPasswordField;
	
	
	public EditAccount (ViewController viewController) {
		// do grunt set-up work from super
		super.createAccountFactory(viewController);
		
		// adjust the text slightly
		passwordLabel.setText("New Password: ");
		confirmationLabel.setText("Confirm New Password: ");
		
		// add new nodes
		oldPasswordLabel = new Label("Old Password: ");
		oldPasswordLabel.setPrefWidth(LABEL_WIDTH);
		oldPasswordField = new TextField();
		oldPasswordRow = new HBox();
		oldPasswordRow.setSpacing(HORIZONTAL_SPACING);
		oldPasswordRow.getChildren().add(oldPasswordLabel);
		oldPasswordRow.getChildren().add(oldPasswordField);
		
		// add button handlers
		submitButton.setOnAction(new SubmitHandler());
		backButton.setOnAction(new EditAccountBackHandler());
		
		// put rows in a vertical structure
		verticalStructure.getChildren().add(emailRow);
		verticalStructure.getChildren().add(firstNameRow);
		verticalStructure.getChildren().add(lastNameRow);
		verticalStructure.getChildren().add(oldPasswordRow);
		verticalStructure.getChildren().add(passwordRow);
		verticalStructure.getChildren().add(confirmationRow);
		verticalStructure.getChildren().add(buttonRow);
		verticalStructure.getChildren().add(errorMessage);
		
		
		// stylize everything
		oldPasswordLabel.setStyle(UserLogInUI.headerStyle);
		
		setBackground();
		
		// put vertical structure in our hbox (so we can center it)
		this.getChildren().add(verticalStructure);
		
	}
	
	
	/**
	 * POPULATE DATA
	 * pre-poplates the textfields with existing data
	 */
	public void populateData() {
		User currentUser = viewController.getCurrentUser();
		firstNameField.setText(currentUser.getFirstName());
		lastNameField.setText(currentUser.getLastName());
		emailField.setText(currentUser.getEmail());
	}
	
	/**
	 * WIPE VIEW
	 * wipes the text fields, labels, etc. back to defaults
	 */
	@Override
	protected void wipeView() {
		super.wipeView();
		oldPasswordField.setText("");
	}
	
	/**
	 * EDIT ACCOUNT BACK HANDLER
	 * inside class to navigate back to AccountSummary
	 * 		do not confuse with BackHandler which moves you to login root because of inheritence from UserLogInUI
	 */
	protected class EditAccountBackHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// wipe display, move to root
			wipeView();
			viewController.moveToAccountSummary();
		}
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
			String oldPass = oldPasswordField.getText();
			String newPass = passwordField.getText();
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
			msg = validatePassword(oldPass);
			if (msg != null) {
				displayError(msg);
				return;
			}
			msg = validatePassword(newPass);
			if (msg != null) {
				displayError(msg);
				return;
			}
			
			// make sure old pass is correct
			User user = viewController.getUser(email);
			if (user == null) {
				displayError("Error: Unable to access your account in database right now. Try again later.");
				return;
			}
			if (!user.getPassword().equals(oldPass)) {
				displayError("Incorrect old password.");
				return;
			}
			
			// make sure pass matches confirmation pass
			String passConf = confirmationField.getText();
			if (newPass.equals(passConf)) {
				msg = viewController.updateUser(email, firstName, lastName, newPass);
				// move to home page if added user with no hitches
				if (msg == null) {
					wipeView();
					viewController.moveToHomePage();
					viewController.updateMyPage();
				} else {
					displayError(msg);
				}
			} else {
				displayError("Try again! Password and confirmation password don't match.");
			}

		}
	}
}
