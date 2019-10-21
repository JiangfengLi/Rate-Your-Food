package model;

import java.util.List;

/**
 * DATABASE INTERFACE
 * short list of methods used to access database / manage model
 * @author Alexander Miller
 *
 */
public interface DatabaseInterface {

	// MANAGING USERS
    public User getCurrentUser();
    public User getUser(String email);
	public String addUser(String email, String firstName, String lastName, String password);
	public void logOut();
	public String logIn(String email, String password);
	
	// MANAGING RECIPES
	public Recipe getRecipe(String recipeName, String creator);
	public String addRecipe(String recipeName, String creator, int difficulty, int rating);
	public List<Recipe> getAllRecipesForUser(String creator);
	public List<Recipe> getAllRecipes();
	public List<Recipe> searchRecipes(String searchKey);
	
	// MANAGING REVIEWS
	public Review getReview(String author, String recipeName, String recipeCreator);
	public String addReview(String author, String recipeName, String recipeCreator, String text, int difficulty, int rating);
	public List<Review> getAllReviewsForRecipe(String recipeName, String recipeCreator);
	public List<Review> getAllReviewsByAuthor(String author);
	public List<Review> getAllReviews();
	
	// MANAGING INGREDIENTS
	public Ingredient getIngredient(String name, String recipeName, String recipeCreator);
	public String addIngredient(String name, String recipeName, String recipeCreator, float amount, String unit);
	public List<Ingredient> getAllIngredientsForRecipe(String recipeName, String recipeCreator);
	
	// MANAGING INSTRUCTIONS
	public Instruction getInstruction(int ID, String recipeName, String recipeCreator);
	public String addInstruction(String recipeName, String recipeCreator, String text);
	public List<Instruction> getAllInstructionsForRecipe(String recipeName, String recipeCreator);
	
}
