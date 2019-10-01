package view.login;

import view.ViewController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * SIGN IN
 * view class for signing in
 * @author Alexander Miller
 *
 */
public class SignIn extends UserLogInUI{

	
	/**
	 * CONSTRUCTOR
	 * makes use of super's factory method. still need to implement and assign submit handler
	 * @param viewController
	 */
	public SignIn(ViewController viewController) {
		// do common grunt initialization in super
		super.factory(viewController);
		
		// add submit event handler
		submitButton.setOnAction(new SubmitHandler());
		
		// put rows in a vertical structure
		verticalStructure.getChildren().add(emailRow);
		verticalStructure.getChildren().add(passwordRow);
		verticalStructure.getChildren().add(buttonRow);
		verticalStructure.getChildren().add(errorMessage);
		
		// put vertical structure in our hbox (so we can center it)
		this.getChildren().add(verticalStructure);
	}
	

	
	/**
	 * SUBMIT HANDLER
	 * handles signing in - validates inputs, handles switching views
	 */
	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// validate email, password
			String email = emailField.getText();
			String password = passwordField.getText();
			String msg = validateEmail(email);
			if (msg != null) {
				displayError(msg);
				return;
			}
			msg = validatePassword(password);
			if (msg != null) {
				displayError(msg);
				return;
			}
			// attempt log in
			msg = viewController.logIn(email, password);
			// if no error msg returned, good to go; wipe fields, continue
			if (msg == null) {
				wipeView();
				viewController.moveToHomePage();
			} // otherwise, display error message
			else {
				displayError(msg);
			}
		}
	}
	
	
	
}
