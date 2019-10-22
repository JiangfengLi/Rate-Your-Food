
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.LinkedList;

/**
 * DB ACCESS
 * utility class for accessing database
 * @author Alexander Miller
 *
 */
public class DBAccess implements DatabaseInterface{

	// JDBC driver, database URL, credentials
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost/foodrecipe436?characterEncoding=utf8";
	private static final String USER = "root";
	private static final String PASS = "csc436zona";
	
	// User Queries
	private static final String GET_USER_BY_EMAIL = "SELECT * FROM User WHERE Email=?;";
	private static final String ADD_USER = "INSERT INTO User(Email, FirstName, LastName, Password) VALUES(?,?,?,?);";
	
	// Recipe Queries
	private static final String GET_RECIPE = "SELECT * FROM Recipe WHERE RecipeName=? AND Creator=?;";
	private static final String ADD_RECIPE = "INSERT INTO Recipe(RecipeName, Creator, Difficulty, Rating) VALUES(?,?,?,?);";
	private static final String GET_ALL_RECIPES_FOR_USER = "SELECT * FROM Recipe WHERE Creator=?;";
	private static final String GET_ALL_RECIPES = "SELECT * FROM Recipe;";
	private static final String SEARCH_RECIPES = "SELECT * FROM Recipe WHERE RecipeName LIKE ?;";
	 
	// Review Queries
	private static final String GET_REVIEW = "SELECT * FROM Review WHERE Author=? AND RecipeName=? AND RecipeCreator=?;";
	private static final String ADD_REVIEW = "INSERT INTO Review(Author, RecipeName, RecipeCreator, Text, Difficulty, Rating) VALUES(?,?,?,?,?,?);";
	private static final String GET_ALL_REVIEWS_FOR_RECIPE = "SELECT * FROM Review WHERE RecipeName=? AND RecipeCreator=?;";
	private static final String GET_ALL_REVIEWS_BY_AUTHOR = "SELECT * FROM Review WHERE Author=?;";
	private static final String GET_ALL_REVIEWS = "SELECT * FROM Review;";
	
	// Ingredient Queries
	private static final String GET_INGREDIENT = "SELECT * FROM Ingredient WHERE Name=? AND RecipeName=? AND RecipeCreator=?;";
	private static final String ADD_INGREDIENT = "INSERT INTO Ingredient(Name, RecipeName, RecipeCreator, Amount, Unit) VALUES(?,?,?,?,?);";
	private static final String GET_ALL_INGREDIENTS_FOR_RECIPE = "SELECT * FROM Ingredient WHERE RecipeName=? and RecipeCreator=?;";
	
	// Instruction Queries
	private static final String GET_INSTRUCTION = "SELECT * FROM Instruction WHERE ID=? AND RecipeName=? AND RecipeCreator=?;";
	private static final String ADD_INSTRUCTION = "INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES (?,?,?);";
	private static final String GET_ALL_INSTRUCTIONS_FOR_RECIPE = "SELECT * FROM Instruction WHERE RecipeName=? AND RecipeCreator=? ORDER BY ID ASC;";
	
	// current user (who's logged in?)
	private User currentUser;
	
	// CONSTRUCTOR
	public DBAccess() {
		currentUser = null;
	}
	

	// ***********************************************************************
	// ************************** DATABASE UTILITY METHODS *******************
	/**
	 * ESTABLISH CONNECTION
	 * @throws Exception if db fails to connect
	 */
    private Connection establishConnection() throws Exception {
        Class.forName(JDBC_DRIVER);
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        return conn;
    }
    
    
    
    // **************************************************************************
	// ************************** USER MANAGEMENT INTERFACE *********************
    /**
     * GET CURRENT USER
     * who's logged in right now?
     */
    public User getCurrentUser() {
    	return this.currentUser;
    }
    
    /**
     * GET USER
     * returns User from db, or null if none exists - based on email
     */
    public User getUser(String email) {
    	try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_USER_BY_EMAIL);
    		stmt.setString(1, email);
    		ResultSet rs = stmt.executeQuery();
    		User newUser = null;
    		// should only return 1 result, since ID is a primary key
    		while (rs.next()) {
    			newUser = new User(rs.getString(1),rs.getString(2),rs.getString(3),SecureIt.decrypt(rs.getString(4)));
    		}
    		stmt.close();
    		conn.close();
    		return newUser;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
    }
    
	/**
	 * ADD USER 
	 * creates the new User, adds a user to database, logs them in
	 * 		assumes that you have already validated that the user doesn't already exist before
	 * returns null if successful, or error message if otherwise
	 */
	public String addUser(String email, String firstName, String lastName, String password) {
		// add user to the DB
		try {
			Connection conn = establishConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_USER);
			stmt.setString(1, email);
			stmt.setString(2, firstName);
			stmt.setString(3, lastName);
			stmt.setString(4, SecureIt.encrypt(password));
			stmt.executeUpdate();
			
			User newUser = new User(email,firstName,lastName,password);
			currentUser = newUser;
			
			stmt.close();
			conn.close();
		} catch (Exception x) {
			x.printStackTrace();
			return "ERROR with database encountered. Please try again.";
		}
		return null;
	}
	
	/**
	 * LOG OUT
	 * logs a user out (sets current user to null)
	 */
	public void logOut() {
		currentUser = null;
	}
	
	/**
	 * LOG IN
	 * logs a user in (verifies password with db, makes them current user if correct)
	 * returns null if successful, or error message if otherwise
	 */
	public String logIn(String email, String password) {
		User existingUser = getUser(email);
		// if user doesn't exist, that's a problem
		if (existingUser == null) {
			return "No account found for that user!";
		} 
		// otherwise verify password is correct
		if (password.equals(existingUser.getPassword())) {
			currentUser = existingUser;
		} else {
			return "Incorrect password!";
		}
		return null;
	}
	
	
	
	// *****************************************************************
	// ************************** RECIPE INTERFACE *********************
	/**
	 * GET RECIPE
	 * get a particular recipe based on its creator and the recipe name, or null if none exists
	 * also return null if error occurs - prints stack trace to stdout
	 * @param recipeName
	 * @param creator
	 * @return
	 */
	public Recipe getRecipe(String recipeName, String creator) {
    	try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_RECIPE);
    		stmt.setString(1, recipeName);
    		stmt.setString(2, creator);
    		ResultSet rs = stmt.executeQuery();
    		Recipe recipe = null;
    		// should only return 1 result, since search by primary key
    		while (rs.next()) {
    			recipe = new Recipe(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
    		}
    		stmt.close();
    		conn.close();
    		return recipe;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}
	
	/**
	 * ADD RECIPE 
	 * adds a recipe to database; validates that there isn't already a recipe of this name for the creator
	 * returns null if successful, or error message if otherwise
	 */
	public String addRecipe(String recipeName, String creator, int difficulty, int rating) {
		// validate that there isn't already a recipe of this name for the creator
		if (getRecipe(recipeName, creator) != null) {
			return "A recipe of this name already exists for this user!";
		}
		try {
			Connection conn = establishConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_RECIPE);
			stmt.setString(1, recipeName);
			stmt.setString(2, creator);
			stmt.setInt(3, difficulty);
			stmt.setInt(4, rating);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (Exception x) {
			x.printStackTrace();
			return "ERROR with database encountered. Please try again.";
		}
		return null;
	}

	/**
	 * GET ALL RECIPES FOR USER
	 * returns all the recipes created by 'creator'
	 * @param creator
	 * @return
	 */
	public List<Recipe> getAllRecipesForUser(String creator) {
		return getAllRecipesHelper(creator);
	}
	
	/**
	 * GET ALL RECIPES
	 * returns all the recipes in the DB
	 * @return
	 */
	public List<Recipe> getAllRecipes() {
		return getAllRecipesHelper(null);
	}
	
	/**
	 * GET ALL RECIPES HELPER
	 * handles common logic for getAllRecipesForUser and getAllRecipes
	 * @param creator
	 * @return
	 */
	private List<Recipe> getAllRecipesHelper(String creator) {
    	try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt;
    		if (creator != null) {
        		stmt = conn.prepareStatement(GET_ALL_RECIPES_FOR_USER);
        		stmt.setString(1, creator);
    		} else {
    			stmt = conn.prepareStatement(GET_ALL_RECIPES);
    		}
    		ResultSet rs = stmt.executeQuery();
    		LinkedList<Recipe> recipeList = new LinkedList<Recipe>();
    		while (rs.next()) {
    			Recipe recipe = new Recipe(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
    			recipeList.add(recipe);
    		}
    		stmt.close();
    		conn.close();
    		return recipeList;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}

	/**
	 * SEARCH RECIPES HELPER
	 * handles common logic for searchRecipes
	 * @param searchKey
	 * @return
	 */
	public List<Recipe> searchRecipes(String searchKey) {
		try {
			Connection conn = establishConnection();
			PreparedStatement stmt;
			stmt = conn.prepareStatement( SEARCH_RECIPES );
			stmt.setString( 1, "%" + searchKey + "%" );
			ResultSet rs = stmt.executeQuery();
			LinkedList<Recipe> recipeList = new LinkedList<Recipe>();
			while (rs.next()) {
				Recipe recipe = new Recipe(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getInt(4));
				recipeList.add(recipe);
			}
			stmt.close();
			conn.close();
			return recipeList;
		} catch (Exception x) {
			x.printStackTrace();
			return null;
		}
	}
	
	// *****************************************************************
	// ************************** REVIEW INTERFACE *********************
	/**
	 * GET REVIEW
	 * get a particular review based on its author and the recipe it was created for
	 * @param author
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public Review getReview(String author, String recipeName, String recipeCreator) {
    	try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_REVIEW);
    		stmt.setString(1, author);
    		stmt.setString(2, recipeName);
    		stmt.setString(3, recipeCreator);
    		ResultSet rs = stmt.executeQuery();
    		Review review = null;
    		// should only return 1 result, since search by primary key
    		while (rs.next()) {
    			review = new Review(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
    		}
    		stmt.close();
    		conn.close();
    		return review;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}
	
	/**
	 * ADD REVIEW 
	 * adds a review to database; validates that there isn't already a review of this recipe for the author
	 * returns null if successful, or error message if otherwise
	 */
	public String addReview(String author, String recipeName, String recipeCreator, String text, int difficulty, int rating) {
		// validate that there isn't already a recipe of this name for the creator
		if (getReview(author, recipeName, recipeCreator) != null) {
			return "A review by this author for this recipe already exists!";
		}
		try {
			Connection conn = establishConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_REVIEW);
			stmt.setString(1, author);
			stmt.setString(2, recipeName);
			stmt.setString(3, recipeCreator);
			stmt.setString(4, text);
			stmt.setInt(5, difficulty);
			stmt.setInt(6, rating);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (Exception x) {
			x.printStackTrace();
			return "ERROR with database encountered. Please try again.";
		}
		return null;
	}
	
	/**
	 * GET ALL REVIEWS FOR RECIPE
	 * get all reviews for a particular recipe from the db
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public List<Review> getAllReviewsForRecipe(String recipeName, String recipeCreator) {
		return getAllReviewsHelper(recipeName,recipeCreator);
	}
	
	/**
	 * GET ALL REVIEWS BY AUTHOR
	 * get all the reviews written by a particular author, from db
	 * @param author
	 * @return
	 */
	public List<Review> getAllReviewsByAuthor(String author) {
		return getAllReviewsHelper(author,null);
	}
	
	/**
	 * GET ALL REVIEWS
	 * gets all the reviews from the db
	 * @return
	 */
	public List<Review> getAllReviews() {
		return getAllReviewsHelper(null,null);
	}
	
	/**
	 * GET ALL REVIEWS HELPER
	 * helper function to handle common logic to getAllReviewsForRecipe, getAllReviewsByAuthor, and getAllReviews
	 * @param param1
	 * @param param2
	 * @return
	 */
	private List<Review> getAllReviewsHelper(String param1, String param2) {
		try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt;
    		// getAllReviewsForRecipe
    		if (param1 != null && param2 != null) {
        		stmt = conn.prepareStatement(GET_ALL_REVIEWS_FOR_RECIPE);
        		stmt.setString(1, param1);
        		stmt.setString(2, param2);
    		} // getAllReviewsForAuthor 
    		else if (param1 != null && param2 == null) {
    			stmt = conn.prepareStatement(GET_ALL_REVIEWS_BY_AUTHOR);
    			stmt.setString(1, param1);
    		} // getAllReviews 
    		else {
    			stmt = conn.prepareStatement(GET_ALL_REVIEWS);
    		}

    		ResultSet rs = stmt.executeQuery();
    		List<Review> reviewList = new LinkedList<Review>();
    		// should only return 1 result, since search by primary key
    		while (rs.next()) {
    			Review review = new Review(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
    			reviewList.add(review);
    		}
    		stmt.close();
    		conn.close();
    		return reviewList;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}
	
	
	
	// *********************************************************************
	// ************************** INGREDIENT INTERFACE *********************
	/**
	 * GET INGREDIENT
	 * get a specific ingredient from DB for a particular recipe
	 * @param name
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public Ingredient getIngredient(String name, String recipeName, String recipeCreator) {
    	try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_INGREDIENT);
    		stmt.setString(1, name);
    		stmt.setString(2, recipeName);
    		stmt.setString(3, recipeCreator);
    		ResultSet rs = stmt.executeQuery();
    		Ingredient ingredient = null;
    		// should only return 1 result, since search by primary key
    		while (rs.next()) {
    			ingredient = new Ingredient(rs.getString(1),rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getString(5));
    		}
    		stmt.close();
    		conn.close();
    		return ingredient;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}
	

	/**
	 * ADD INGREDIENT 
	 * adds a review to database; validates that an ingredient of same name for this recipe doesn't already exist
	 * returns null if successful, or error message if otherwise
	 */
	public String addIngredient(String name, String recipeName, String recipeCreator, float amount, String unit) {
		// validate that there isn't already a recipe of this name for the creator
		if (getIngredient(name, recipeName, recipeCreator) != null) {
			return "An ingredient of this name already exists for this recipe!";
		}
		try {
			Connection conn = establishConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_INGREDIENT);
			stmt.setString(1, name);
			stmt.setString(2, recipeName);
			stmt.setString(3, recipeCreator);
			stmt.setFloat(4, amount);
			stmt.setString(5, unit);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (Exception x) {
			x.printStackTrace();
			return "ERROR with database encountered. Please try again.";
		}
		return null;
	}
		
	/**
	 * GET ALL INGREDIENTS FOR RECIPE
	 * gets all ingredients for a recipe (identified by its author and its recipe name)
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public List<Ingredient> getAllIngredientsForRecipe(String recipeName, String recipeCreator) {
		try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_ALL_INGREDIENTS_FOR_RECIPE);
    		stmt.setString(1, recipeName);
    		stmt.setString(2, recipeCreator);
    		ResultSet rs = stmt.executeQuery();
    		List<Ingredient> ingredientList = new LinkedList<Ingredient>();
    		// should only return 1 result, since search by primary key
    		while (rs.next()) {
    			Ingredient ingredient = new Ingredient(rs.getString(1),rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getString(5));
    			ingredientList.add(ingredient);
    		}
    		stmt.close();
    		conn.close();
    		return ingredientList;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}
	
	// **********************************************************************
	// ************************** INSTRUCTION INTERFACE *********************
	/**
	 * GET INSTRUCTION
	 * get a particular instruction from DB based on ID, recipe name, and recipe creator
	 * IDs are generated by the DB
	 * @param ID
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public Instruction getInstruction(int ID, String recipeName, String recipeCreator) {
    	try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_INSTRUCTION);
    		stmt.setInt(1, ID);
    		stmt.setString(2, recipeName);
    		stmt.setString(3, recipeCreator);
    		ResultSet rs = stmt.executeQuery();
    		Instruction instruction = null;
    		// should only return 1 result, since search by primary key
    		while (rs.next()) {
    			instruction = new Instruction(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
    		}
    		stmt.close();
    		conn.close();
    		return instruction;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}
	
	
	/**
	 * ADD INSTRUCTION 
	 * adds a review to database; nothing to validate [duplicate instructions ok]
	 * note: ids generated by database itself
	 * returns null if successful, or error message if otherwise
	 */
	public String addInstruction(String recipeName, String recipeCreator, String text) {
		try {
			Connection conn = establishConnection();
			PreparedStatement stmt = conn.prepareStatement(ADD_INSTRUCTION);
			stmt.setString(1, recipeName);
			stmt.setString(2, recipeCreator);
			stmt.setString(3, text);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
		} catch (Exception x) {
			x.printStackTrace();
			return "ERROR with database encountered. Please try again.";
		}
		return null;
	}
	
	/**
	 * GET ALL INSTRUCTIONS FOR RECIPE
	 * @param recipeName
	 * @param recipeCreator
	 * @return
	 */
	public List<Instruction> getAllInstructionsForRecipe(String recipeName, String recipeCreator) {
		try {
    		Connection conn = establishConnection();
    		PreparedStatement stmt = conn.prepareStatement(GET_ALL_INSTRUCTIONS_FOR_RECIPE);
    		stmt.setString(1, recipeName);
    		stmt.setString(2, recipeCreator);
    		ResultSet rs = stmt.executeQuery();
    		List<Instruction> instructionList = new LinkedList<Instruction>();
    		// should only return 1 result, since search by primary key
    		while (rs.next()) {
    			Instruction instruction = new Instruction(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
    			instructionList.add(instruction);
    		}
    		stmt.close();
    		conn.close();
    		return instructionList;
    	} catch (Exception x) {
    		x.printStackTrace();
    		return null;
    	}
	}
	
}
