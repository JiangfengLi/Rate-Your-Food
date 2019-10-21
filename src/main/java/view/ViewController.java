package view;

import java.io.FileInputStream;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DBAccess;
import model.User;
import view.login.CreateAccount;
import view.login.LogInRoot;
import view.login.SignIn;


/**
 * VIEW CONTROLLER
 * handles switching out different views, filters views' access to model components
 * @author Alexander Miller
 *
 */
public class ViewController {
	
	// DISPLAY HELPER CLASSES
	private Stage stage;
    private BorderPane theWindow;
    
    // LOG IN VIEW CLASSES
    private LogInRoot logInRoot;
    private SignIn signInPage;
    private CreateAccount createAccountPage;
    
    // HOME PAGE VIEW CLASSES
    private ScrollPane mainPageHolder;
    private ScrollPane scrollForRecipe;
    private ScrollPane createRecipeScroll;
    private HUD theHUD;
    private StartPage theStartPage;
    private MyPage mypage;
    private RecipeView recipeView;
    private CreateRecipeView createRecipe;
    
    // DATABASE MODEL CLASSES
    private DBAccess dbaccess;
	
    /**
     * ON LAUNCH
     * things to do on start up: pull up starter view, create views, create model components
     * @param primaryStage
     */
	public void onLaunch(Stage primaryStage) {
		// stage setup
		this.stage = primaryStage;
		primaryStage.setTitle( "Food 4 Life" );
		try {
			primaryStage.getIcons().add( new Image( new FileInputStream( "src/main/resources/images/icon.png" ) ) );
		} catch (Exception x) {
			System.err.println("Error: no image found as backdrop for homepage.");
		}
        primaryStage.setMinWidth( 1020 );
        
        // create model
        dbaccess = new DBAccess();
        
        // Create main view
        theWindow = new BorderPane();
        Scene theScene = new Scene( theWindow, 1000, 700 );

        // Create auxiliary views
        logInRoot=new LogInRoot(this);
        signInPage = new SignIn(this);
        createAccountPage = new CreateAccount(this);
        theHUD = new HUD(this);
        theStartPage = new StartPage(this);
        mypage = new MyPage(this);
        recipeView = new RecipeView();
        createRecipe = new CreateRecipeView();
        
        // Set alignment for all views going into main borderpane
        BorderPane.setAlignment( logInRoot, Pos.CENTER );
        BorderPane.setAlignment( createAccountPage, Pos.CENTER );
        BorderPane.setAlignment( signInPage, Pos.CENTER );
        BorderPane.setAlignment( theHUD, Pos.CENTER );
        BorderPane.setAlignment( theStartPage, Pos.CENTER );
        BorderPane.setAlignment( recipeView, Pos.CENTER);

        // Create scroll view for home page
        createStartPage();
        
        // create scroll view for recipeView
        scrollForRecipe = new ScrollPane();
        scrollForRecipe.setContent(recipeView);
        
        // create scroll view for createRecipeView
        createRecipeScroll = new ScrollPane();
        createRecipeScroll.setContent(createRecipe);
        createRecipeScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
        //createRecipeScroll.setFitToWidth( true );
        //createRecipeScroll.setFitToHeight( true );

        // update dimension properties
        theHUD.prefWidthProperty().bind( theScene.widthProperty() );
        theWindow.prefWidthProperty().bind( theScene.widthProperty() );
        
        // set current view to login screen
        moveToLogInRoot();
        
        primaryStage.setScene( theScene );
        primaryStage.show();
	}

	private void createStartPage()
    {
        theStartPage = new StartPage( this );
        mainPageHolder = new ScrollPane();
        mainPageHolder.setContent( theStartPage );
        mainPageHolder.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
        mainPageHolder.setFitToWidth( true );
        mainPageHolder.setFitToHeight( true );
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
        createStartPage();
        theWindow.setTop( theHUD );
        theWindow.setCenter( mainPageHolder );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);
    }

    /**
     * MOVE TO MYPAGE
     * moves us to 'MyPage' page
     */   
	public void moveToMyPage() {
        theWindow.setTop( theHUD );
        theWindow.setCenter( mypage );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);		
	}
	
	/*
	 * MOVE TO RECIPEVIEW
	 * moves to the recipe view of the 
	 * recipepreview clicked on
	 */
	
	public void moveToRecipe() {
		 theWindow.setTop( theHUD );
	     theWindow.setCenter( scrollForRecipe );
	     theWindow.setLeft(null);
	     theWindow.setRight(null);
	     theWindow.setBottom(null);		
		
	}
	
	public void moveToCreateRecipe() {
		theWindow.setTop( theHUD );
	     theWindow.setCenter( createRecipeScroll );
	     theWindow.setLeft(null);
	     theWindow.setRight(null);
	     theWindow.setBottom(null);		
	}
    
    
    // ******************************** INTERFACE FOR VIEWS ACCESSING THE MODEL ****************************
    /**
     * GET CURRENT USER
     * wrapper over DBAccess method
     * returns user that's currently logged in or null if none exists
     * @return
     */
    public User getCurrentUser() {
    	return dbaccess.getCurrentUser();
    }
	
    /**
     * ADD USER
     * wrapper over DBAccess method
     * returns null if successful or error message otherwise
     */
    public String addUser(String email, String firstName, String lastName, String password) {
    	User existingUser = dbaccess.getUser(email);
    	if (existingUser == null) {
    		return dbaccess.addUser(email, firstName, lastName, password);
    	} else {
    		return "User account for this email already exists!";
    	}
    	 
    }
    
    /**
     * LOG OUT
     * wrapper over DBAccess method
     * logs current user out
     */
    public void logOut() {
    	dbaccess.logOut();
    }
    
    /**
     * LOG IN
     * wrapper over DBAccess method
     * returns null if successful or error message otherwise
     */
    public String logIn(String email, String password) {
    	return dbaccess.logIn(email, password);
    }


    /**
     * returnStage
     * 
     * returns stage
     */    
	public Stage returnStage() {
		return this.stage;
	}
    

	public HUD getHud()
    {
        return theHUD;
    }
    
}
