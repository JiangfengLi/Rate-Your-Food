package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

/**
 * SIGN IN
 * view class for signing in
 * @author Alexander Miller
 *
 */
public class SignIn extends VBox{

	private ViewController viewController;
	
	private HBox emailLine;
	private Label emailLabel;
	private TextField emailField;
	
	private HBox passLine;
	private Label passwordLabel;
	private PasswordField passwordField;
	
	private HBox buttonLine;
	private Button backButton;
	private Button submitButton;
	
	public SignIn (ViewController viewController) {
		this.viewController = viewController;
		
		emailLine=new HBox();
		emailLabel=new Label("Email: ");
		emailField=new TextField();
		emailLine.getChildren().add(emailLabel);
		emailLine.getChildren().add(emailField);
		
		passLine=new HBox();
		passwordLabel = new Label("Password: ");
		passwordField = new PasswordField();
		passLine.getChildren().add(passwordLabel);
		passLine.getChildren().add(passwordField);
		
		buttonLine = new HBox();
		backButton=new Button("Back");
		backButton.setOnAction(new BackHandler());
		submitButton = new Button("Submit");
		submitButton.setOnAction(new SubmitHandler());
		buttonLine.getChildren().add(backButton);
		buttonLine.getChildren().add(submitButton);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(emailLine);
		this.getChildren().add(passLine);
		this.getChildren().add(buttonLine);
	}
	
	// CREATE ACCOUNT HANDLER
	private class BackHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToLogInRoot();
		}
	}
	
	// SIGN IN HANDLER
	private class SubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToHomePage();
		}
	}
	
	
	
}
