package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * 
 * LOG IN ROOT
 * starting point for users; requires them to log in or create account
 * @author ale
 *
 */
public class LogInRoot extends HBox {
	
	private ViewController viewController;
	private Button createAccount;
	private Button signIn;
	
	public LogInRoot(ViewController viewController) {
		this.viewController=viewController;
		
		this.createAccount = new Button("Create Account");
		createAccount.setPrefWidth(250);
		createAccount.setPrefHeight(75);
		createAccount.setOnAction(new CreateAccountHandler());
		
		this.signIn = new Button("Sign In");
		signIn.setPrefWidth(250);
		signIn.setPrefHeight(75);
		signIn.setOnAction(new SignInHandler());
		
		this.setAlignment(Pos.CENTER);
		this.setSpacing(100);
		
		this.getChildren().add(createAccount);
		this.getChildren().add(signIn);
	}
	
	// CREATE ACCOUNT HANDLER
	private class CreateAccountHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToCreateAccount();
		}
	}
	
	// SIGN IN HANDLER
	private class SignInHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToSignIn();
		}
	}
	
	
}
