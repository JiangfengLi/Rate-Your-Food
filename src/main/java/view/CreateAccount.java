package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * CREATE ACCOUNT
 * view class for creating an account
 * @author Alexander Miller
 * 
 *
 */
public class CreateAccount extends VBox {
	
	private ViewController viewController;
	
	private HBox emailLine;
	private Label emailLabel;
	private TextField emailField;
	
	private HBox firstNameLine;
	private Label firstNameLabel;
	private TextField firstNameField;
	
	private HBox lastNameLine;
	private Label lastNameLabel;
	private TextField lastNameField;
	
	private HBox passwordLine;
	private Label passwordLabel;
	private PasswordField passwordField;
	
	private HBox confirmationLine;
	private Label confirmationLabel;
	private PasswordField confirmationField;
	
	private HBox buttonLine;
	private Button backButton;
	private Button submitButton;
	
	public CreateAccount(ViewController viewController) {
		this.viewController = viewController;
		
		emailLine = new HBox();
		emailLabel = new Label("Email: ");
		emailField = new TextField();
		emailLine.getChildren().add(emailLabel);
		emailLine.getChildren().add(emailField);
		
		firstNameLine = new HBox();
		firstNameLabel = new Label("First Name: ");
		firstNameField = new TextField();
		firstNameLine.getChildren().add(firstNameLabel);
		firstNameLine.getChildren().add(firstNameField);
		
		lastNameLine = new HBox();
		lastNameLabel = new Label("Last Name: ");
		lastNameField = new TextField();
		lastNameLine.getChildren().add(lastNameLabel);
		lastNameLine.getChildren().add(lastNameField);
		
		passwordLine = new HBox();
		passwordLabel = new Label("Password: ");
		passwordField = new PasswordField();
		passwordLine.getChildren().add(passwordLabel);
		passwordLine.getChildren().add(passwordField);
		
		confirmationLine = new HBox();
		confirmationLabel = new Label("Confirm Password: ");
		confirmationField = new PasswordField();
		confirmationLine.getChildren().add(confirmationLabel);
		confirmationLine.getChildren().add(confirmationField);
		
		buttonLine = new HBox();
		backButton=new Button("Back");
		backButton.setOnAction(new BackHandler());
		submitButton = new Button("Submit");
		submitButton.setOnAction(new SubmitHandler());
		buttonLine.getChildren().add(backButton);
		buttonLine.getChildren().add(submitButton);
		
		this.setAlignment(Pos.CENTER);
		this.getChildren().add(emailLine);
		this.getChildren().add(firstNameLine);
		this.getChildren().add(lastNameLine);
		this.getChildren().add(passwordLine);
		this.getChildren().add(confirmationLine);
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
