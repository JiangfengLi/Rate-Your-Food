package view;

import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * VIEW CONTROLLER
 * handles switching out different views
 * @author Alexander Miller
 *
 */
public class ViewController {
	
	// HELPER CLASSES
	private Stage stage;
    private BorderPane theWindow;
    
    // LOG IN CLASSES
    private LogInRoot logInRoot;
    private SignIn signInPage;
    private CreateAccount createAccountPage;
    
    // HOME PAGE CLASSES
    private ScrollPane mainPageHolder;
    private HUD theHUD;
    private StartPage theStartPage;
	
	public void onLaunch(Stage primaryStage) {
		primaryStage.setTitle( "Food 4 Life" );
		try {
			primaryStage.getIcons().add( new Image( new FileInputStream( "src/main/resources/images/icon.png" ) ) );
		} catch (Exception x) {
			System.err.println("Error: no image found as backdrop for homepage.");
		}
		
        primaryStage.setMinWidth( 1020 );

        // Create main view
        theWindow = new BorderPane();
        Scene theScene = new Scene( theWindow, 1000, 700 );

        // Create views
        logInRoot=new LogInRoot(this);
        signInPage = new SignIn(this);
        createAccountPage = new CreateAccount(this);
        theHUD = new HUD(this);
        theStartPage = new StartPage();
        
        // Set alignment
        BorderPane.setAlignment( logInRoot, Pos.CENTER );
        BorderPane.setAlignment( createAccountPage, Pos.CENTER );
        BorderPane.setAlignment( signInPage, Pos.CENTER );
        BorderPane.setAlignment( theHUD, Pos.CENTER );
        BorderPane.setAlignment( theStartPage, Pos.CENTER );

        // Create scroll view for home page
        mainPageHolder = new ScrollPane();
        mainPageHolder.setContent( theStartPage );
        mainPageHolder.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
        mainPageHolder.setFitToWidth( true );
        mainPageHolder.setFitToHeight( true );

        // update dimension properties
        theHUD.prefWidthProperty().bind( theScene.widthProperty() );
        theWindow.prefWidthProperty().bind( theScene.widthProperty() );
        
        // SET LOG IN SCREEN
        moveToLogInRoot();
        
        primaryStage.setScene( theScene );
        primaryStage.show();
	}
	

    // ************************************ NAVIGATING SCREENS ****************************
    
    /**
     * MOVE TO LOG IN ROOT
     * move us to login root
     */
    public void moveToLogInRoot() {
        theWindow.setTop( null );
        theWindow.setCenter( logInRoot );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);
    }
    
    /**
     * MOVE TO CREATE ACCOUNT
     * move us to create account
     */
    public void moveToCreateAccount() {
        theWindow.setTop( null );
        theWindow.setCenter( createAccountPage );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);
    }
    
    /**
     * MOVE TO SIGN IN
     * move us to sign in
     */
    public void moveToSignIn() {
        theWindow.setTop( null );
        theWindow.setCenter( signInPage );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);   
    }
    
    /**
     * MOVE TO HOME PAGE
     * moves us to home page
     */
    public void moveToHomePage() {
        theWindow.setTop( theHUD );
        theWindow.setCenter( mainPageHolder );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);
    }

	
}
