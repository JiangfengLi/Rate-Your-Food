package view.login;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import view.ViewController;
import javafx.geometry.Pos;

import java.io.FileInputStream;
import java.io.IOException;

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
	
		setBackground();
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
	
	
	/**
	 * SET BACKGROUND
	 * stylizes background w image
	 */
	protected void setBackground() {
        try
        {
            Image background = new Image( new FileInputStream( "src/main/resources/images/wallpaper.jpeg" ) );
            BackgroundSize aSize = new BackgroundSize( 1920, 700, true, true, true, true );
            setBackground( new Background( new BackgroundImage( background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, aSize ) ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
	}
	
}
