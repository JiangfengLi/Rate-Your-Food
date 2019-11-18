package view;

import java.io.FileInputStream;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.DBAccess;
import model.DatabaseInterface;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import model.Review;
import model.Tag;
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
public class ViewController implements DatabaseInterface {
	
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
    private ReviewView reviewView;
    private RecipeView recipeView;
    private CreateRecipeView createRecipe;
    private AccountView accountView;
    private CartPage cartPage;
    
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
        accountView = new AccountView(this);
        //recipeView = new RecipeView(this, null);
        cartPage = new CartPage(this);
        
        // Set alignment for all views going into main borderpane
        BorderPane.setAlignment( logInRoot, Pos.CENTER );
        BorderPane.setAlignment( createAccountPage, Pos.CENTER );
        BorderPane.setAlignment( signInPage, Pos.CENTER );
        BorderPane.setAlignment( theHUD, Pos.CENTER );
        BorderPane.setAlignment( theStartPage, Pos.CENTER );
        //BorderPane.setAlignment( recipeView, Pos.CENTER);
        BorderPane.setAlignment( accountView, Pos.CENTER);

        // Create scroll view for home page
        createStartPage();
        
        // create scroll view for recipeView
        scrollForRecipe = new ScrollPane();
        //scrollForRecipe.setFitToHeight(true);
        scrollForRecipe.setFitToWidth(true);
        scrollForRecipe.setContent(recipeView);
        
        // create scroll view for createRecipeView
        createRecipeScroll = new ScrollPane();
        createRecipeScroll.setFitToHeight(true);
        createRecipeScroll.setFitToWidth(true);
        createRecipeScroll.setHbarPolicy(ScrollBarPolicy.NEVER);

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
		mypage.upDateReviewLV();
        theWindow.setTop( theHUD );
        theWindow.setCenter( mypage );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);		
	}

    /**
     * UPDATE MYPAGE
     * moves us to 'MyPage' page
     */   	
	public void updateMyPage() {
		mypage.upDateReviewLV();
		
	}
	
	/*
	 * MOVE TO RECIPEVIEW
	 * moves to the recipe view of the 
	 * recipepreview clicked on
	 */	
    public void moveToRecipe( Recipe aRecipe ) {
        theWindow.setTop( theHUD );
        recipeView = new RecipeView( this, aRecipe );
        scrollForRecipe.setContent(recipeView);
        theWindow.setCenter( scrollForRecipe );
        theWindow.setLeft(null);
        theWindow.setRight(null);
        theWindow.setBottom(null);
    }

	/*
	 * MOVE TO CREATE RECIPE
	 * moves to the recipe view of the 
	 * create recipe clicked on
	 */	
	public void moveToCreateRecipe() {
		theWindow.setTop( theHUD );
		
        createRecipe = new CreateRecipeView(this);
        createRecipeScroll.setContent(createRecipe);
	    theWindow.setCenter( createRecipeScroll );
	    theWindow.setLeft(null);
	    theWindow.setRight(null);
	    theWindow.setBottom(null);		
	}
	
	public void moveToEditRecipe(Recipe recipe) {
		theWindow.setTop( theHUD );
		
        EditRecipeView editRecipe = new EditRecipeView(this, recipe);
        createRecipeScroll.setContent(editRecipe);
	    theWindow.setCenter( createRecipeScroll );
	    theWindow.setLeft(null);
	    theWindow.setRight(null);
	    theWindow.setBottom(null);		
	}
	
	public void moveToAccountView() {
		accountView.setUser(getCurrentUser());
		theWindow.setTop( theHUD );
	    theWindow.setCenter( accountView );
	    theWindow.setLeft(null);
	    theWindow.setRight(null);
	    theWindow.setBottom(null);	
	}

	/*
	 * MOVE TO REVIEW
	 * moves to the review view of the 
	 * review clicked on
	 */		
	public void moveToReview(ReviewView reviewView) {
		// TODO Auto-generated method stub
		theWindow.setTop( theHUD );
	    theWindow.setCenter( reviewView );
	    theWindow.setLeft(null);
	    theWindow.setRight(null);
	    theWindow.setBottom(null);		
		
	}

	/*
	 * MOVE TO CART PAGE
	 * moves to the  view of the 
	 * cart page clicked on
	 */			
	public void moveToCartPage() {
	    cartPage.updateCart();
		theWindow.setTop( theHUD );
	    theWindow.setCenter( cartPage );
	    theWindow.setLeft(null);
	    theWindow.setRight(null);
	    theWindow.setBottom(null);		
	
	}

	/*
	 * MOVE TO DELETE PAGE
	 * moves to the  view of the 
	 * cart page clicked on
	 */	
	public void moveToDeletePage(Recipe reviewRecipe, String returnSite, String type) {
		DeletePage deletePage = new DeletePage(this, reviewRecipe, type);
		deletePage.setReturnPoint(returnSite);
		theWindow.setTop( null );
	    theWindow.setCenter( deletePage );
	    theWindow.setLeft(null);
	    theWindow.setRight(null);
	    theWindow.setBottom(null);			
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
	
	
    // ******************************** DATABASE INTERFACE ****************************
	// *************************** WRAPPER METHODS OVER DBACCESS **********************
    // 
	
	// *************************** MANAGING USERS **********************
    /**
     * GET CURRENT USER
     * returns user that's currently logged in or null if none exists
     * @return
     */
    public User getCurrentUser() {
    	return dbaccess.getCurrentUser();
    }
    /**
     * GET USER
     * returns User from db, or null if none exists - based on email
     */
    public User getUser(String email) {
    	return dbaccess.getUser(email);
    }
	/**
	 * ADD USER 
	 * creates the new User, adds a user to database, logs them in
	 * 		validates that the user doesn't already exist before
	 * returns null if successful, or error message if otherwise
	 */
    public String addUser(String email, String firstName, String lastName, String password) {
    	return dbaccess.addUser(email, firstName, lastName, password);
    }
    /**
     * LOG OUT
     * logs current user out
     */
    public void logOut() {
    	dbaccess.logOut();
    }
	/**
	 * LOG IN
	 * logs a user in (verifies password with db, makes them current user if correct)
	 * returns null if successful, or error message if otherwise
	 */
    public String logIn(String email, String password) {
    	return dbaccess.logIn(email, password);
    }
	/**
	 * DELETE USER
	 * deletes user if password correct
	 * deletes all associated recipes, ingredients, reviews, and instructions
	 * returns null if no issues, else error msg
	 */
	public String deleteUser(String email, String password) {
		return dbaccess.deleteUser(email, password);
	}
	/**
	 * UPDATE USER
	 * allows you to update firstname, lastname, or password - then updates currentUser to reflect change
	 * please pass all fields, or it will be rejected
	 * returns null or error msg if problem
	 */
	public String updateUser(String email, String firstName, String lastName, String password) {
		return dbaccess.updateUser(email, firstName, lastName, password);
	}
    
	
	// *************************** MANAGING RECIPES **********************
	/**
	 * GET RECIPE
	 * get a particular recipe based on its creator and the recipe name, or null if none exists
	 * also return null if error occurs - prints stack trace to stdout
	 * @param recipeName
	 * @param creator
	 * @return
	 */
	public Recipe getRecipe(String recipeName, String creator) {
		return dbaccess.getRecipe(recipeName, creator);
	}
	/**
	 * ADD RECIPE 
	 * adds a recipe to database; validates that there isn't already a recipe of this name for the creator
	 * returns null if successful, or error message if otherwise
	 */
	public String addRecipe(String recipeName, String creator, int difficulty, int rating) {
		return dbaccess.addRecipe(recipeName, creator, difficulty, rating);
	}
	/**
	 * GET ALL RECIPES FOR USER
	 * returns all the recipes created by 'creator'
	 * @param creator
	 * @return
	 */
	public List<Recipe> getAllRecipesForUser(String creator) {
		return dbaccess.getAllRecipesForUser(creator);
	}
	/**
	 * GET ALL RECIPES
	 * returns all the recipes in the DB
	 * @return
	 */
	public List<Recipe> getAllRecipes() {
		return dbaccess.getAllRecipes();
	}
	/**
	 * GET ALL RECIPES BY TAG
	 * returns list of recipes with associated tag (may be empty), returns null if sql exception thrown
	 */
	public List<Recipe> getAllRecipesByTag(String name) {
		return dbaccess.getAllRecipesByTag(name);
	}
	/**
	 * SEARCH RECIPES
	 * @param searchKey
	 * @return
	 */
	public List<Recipe> searchRecipes(String searchKey){
		return dbaccess.searchRecipes(searchKey);
	}
	/**
	 * DELETE RECIPE
	 * deletes specific recipe
	 * returns null or error msg if problem encountered
	 */
	public String deleteRecipe(String recipeName, String creator) {
		return dbaccess.deleteRecipe(recipeName, creator);
	}
	/**
	 * UPDATE RECIPE
	 * allows you to update recipename, difficulty, and rating for a recipe
	 * 		if recipename changes, also updates related reviews, ingredients, instructions, and tags to correspond
	 * please provide all params
	 * returns null or error msg if problem
	 */
	public String updateRecipe(String oldRecipeName, String creator, String newRecipeName, int difficulty, int rating) {
		return dbaccess.updateRecipe(oldRecipeName, creator, newRecipeName, difficulty, rating);
	}

	
	// *************************** MANAGING REVIEWS **********************
	/**
	 * GET REVIEW
	 * get a particular review based on its author and the recipe it was created for
	 * @param author
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public Review getReview(String author, String recipeName, String recipeCreator) {
		return dbaccess.getReview(author, recipeName, recipeCreator);
	}
	/**
	 * ADD REVIEW 
	 * adds a review to database; validates that there isn't already a review of this recipe for the author
	 * returns null if successful, or error message if otherwise
	 */
	public String addReview(String author, String recipeName, String recipeCreator, String text, int difficulty, int rating) {
		return dbaccess.addReview(author, recipeName, recipeCreator, text, difficulty, rating);
	}
	/**
	 * GET ALL REVIEWS FOR RECIPE
	 * get all reviews for a particular recipe from the db
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public List<Review> getAllReviewsForRecipe(String recipeName, String recipeCreator) {
		return dbaccess.getAllReviewsForRecipe(recipeName, recipeCreator);
	}
	/**
	 * GET ALL REVIEWS BY AUTHOR
	 * get all the reviews written by a particular author, from db
	 * @param author
	 * @return
	 */
	public List<Review> getAllReviewsByAuthor(String author){
		return dbaccess.getAllReviewsByAuthor(author);
	}
	/**
	 * GET ALL REVIEWS
	 * gets all the reviews from the db
	 * @return
	 */
	public List<Review> getAllReviews() {
		return dbaccess.getAllReviews();
	}
	/**
	 * DELETE REVIEW
	 * deletes a particular review
	 * returns null or error msg if problem encountered
	 */
	public String deleteReview(String author, String recipeName, String recipeCreator) {
		return dbaccess.deleteReview(author, recipeName, recipeCreator);
	}
	/**
	 * UPDATE REVIEW
	 * allows update of recipename, recipecreator, text, difficulty, and rating
	 * please provide all params regardless
	 * returns null or error msg if problem
	 */
	public String updateReview(String author, String oldRecipeName, String oldRecipeCreator, 
			String newRecipeName, String newRecipeCreator, String text, int difficulty, int rating) {
		return dbaccess.updateReview(author, oldRecipeName, oldRecipeCreator, newRecipeName, newRecipeCreator, text, difficulty, rating);
	}
	
	
	// *************************** MANAGING INGREDIENTS **********************
	/**
	 * GET INGREDIENT
	 * get a specific ingredient from DB for a particular recipe
	 * @param name
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public Ingredient getIngredient(String name, String recipeName, String recipeCreator) {
		return dbaccess.getIngredient(name, recipeName, recipeCreator);
	}
	/**
	 * ADD INGREDIENT 
	 * adds a review to database; validates that an ingredient of same name for this recipe doesn't already exist
	 * returns null if successful, or error message if otherwise
	 */
	public String addIngredient(String name, String recipeName, String recipeCreator, float amount, String unit) {
		return dbaccess.addIngredient(name, recipeName, recipeCreator, amount, unit);
	}
	/**
	 * GET ALL INGREDIENTS FOR RECIPE
	 * gets all ingredients for a recipe (identified by its author and its recipe name)
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public List<Ingredient> getAllIngredientsForRecipe(String recipeName, String recipeCreator) {
		return dbaccess.getAllIngredientsForRecipe(recipeName, recipeCreator);
	}
	/**
	 * DELETE INGREDIENT
	 * delete specific ingredient 
	 * returns null or error msg if problem
	 */
	public String deleteIngredient(String name, String recipeName, String recipeCreator) {
		return dbaccess.deleteIngredient(name, recipeName, recipeCreator);
	}
	
	/* TODO: change down
	/**
	 * DELETE ALL INGREDIENT
	 * delete specific ingredient 
	 * returns null or error msg if problem
	 
	public String deleteAllIngredientsForRecipe(String recipeName, String recipeCreator) {
		return dbaccess.deleteAllIngredientsForRecipe(recipeName, recipeCreator);
	}
	*/
	
	/**
	 * UPDATE INGREDIENT
	 * allows update of name, amount and unit
	 * please provide all fields
	 * returns null or error msg if problem
	 */
	public String updateIngredient(String oldName, String recipeName, String recipeCreator, String newName, float amount, String unit) {
		return dbaccess.updateIngredient(oldName, recipeName, recipeCreator, newName, amount, unit);
	}
	
	
	// *************************** MANAGING INSTRUCTIONS **********************
	/**
	 * GET INSTRUCTION
	 * gets an instruction based on relative position, recipe name, recipe creator, and text
	 * if instruction is 'unique', pass relative position as null
	 * otherwise, pass relative position of the 'identical' instructions to indicate which you want, starting at 0
	 * IDs are generated by the DB
	 * @param relativePosition
	 * @param recipeName
	 * @param recipeCreator
	 * @param text
	 * @return
	 */
	public Instruction getInstruction(Integer relativePosition, String recipeName, String recipeCreator, String text) {
		return dbaccess.getInstruction(relativePosition, recipeName, recipeCreator, text);
	}
	/**
	 * ADD INSTRUCTION 
	 * adds a review to database; nothing to validate [duplicate instructions ok]
	 * note: ids generated by database itself
	 * returns null if successful, or error message if otherwise
	 */
	public String addInstruction(String recipeName, String recipeCreator, String text) {
		return dbaccess.addInstruction(recipeName, recipeCreator, text);
	}
	/**
	 * GET ALL INSTRUCTIONS FOR RECIPE
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public List<Instruction> getAllInstructionsForRecipe(String recipeName, String recipeCreator) {
		return dbaccess.getAllInstructionsForRecipe(recipeName, recipeCreator);
	}
	/**
	 * DELETE INSTRUCTION
	 * deletes instruction based on relative position to other instructions of same text
	 * iterate relativePosition starting at 0; if instruction unique, pass 'null'
	 */
	public String deleteInstruction(Integer relativePosition, String recipeName, String recipeCreator, String text) {
		return dbaccess.deleteInstruction(relativePosition, recipeName, recipeCreator, text);
	}
	/**
	 * UPDATE INSTRUCTION
	 * allows update of Text for an instruction
	 * please provide all params
	 * 		if instruction text unique, let relativePosition == null, otherwise provide relative position starting at 0
	 * returns null or error msg if problem
	 */
	public String updateInstruction(Integer relativePosition, String recipeName, String recipeCreator, String oldText, String newText) {
		return dbaccess.updateInstruction(relativePosition, recipeName, recipeCreator, oldText, newText);
	}


	// *************************** MANAGING TAGS **********************
	/**
	 * GET TAG
	 * returns the tag or null if none
	 */
	public Tag getTag(String name, String recipeName, String recipeCreator) {
		return dbaccess.getTag(name, recipeName, recipeCreator);
	}
	/**
	 * ADD TAG
	 * adds tag to database if doesn't already exist
	 * returns null or error msg if problem
	 */
	public String addTag(String name, String recipeName, String recipeCreator) {
		return dbaccess.addTag(name, recipeName, recipeCreator);
	}
	/**
	 * GET ALL TAGS BY NAME
	 * return list of tags [could be empty], null if sql exception thrown
	 */
	public List<Tag> getAllTagsByName(String name) {
		return dbaccess.getAllTagsByName(name);
	}
	/**
	 * GET ALL TAGS FOR RECIPE
	 * return list of tags for recipe [could be empty], null if sql exception thrown
	 */
	public List<Tag> getAllTagsForRecipe(String recipeName, String recipeCreator) {
		return dbaccess.getAllTagsForRecipe(recipeName, recipeCreator);
	}
	/**
	 * DELETE TAG
	 * returns null or error msg if problem
	 */
	public String deleteTag(String name, String recipeName, String recipeCreator) {
		return dbaccess.deleteTag(name, recipeName, recipeCreator);
	}

    
}
