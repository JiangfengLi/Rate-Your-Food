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
	public String deleteUser(String email, String password);
	public String updateUser(String email, String firstName, String lastName, String password);
	
	// MANAGING RECIPES
	public Recipe getRecipe(String recipeName, String creator);
	public String addRecipe(String recipeName, String creator, int difficulty, int rating);
	public List<Recipe> getAllRecipesForUser(String creator);
	public List<Recipe> getAllRecipes();
	public List<Recipe> getAllRecipesByTag(String name);
	public List<Recipe> searchRecipes(String searchKey);
	public String deleteRecipe(String recipeName, String creator);
	public String updateRecipe(String recipeName, String creator, int difficulty, int rating);
	
	// MANAGING REVIEWS
	public Review getReview(String author, String recipeName, String recipeCreator);
	public String addReview(String author, String recipeName, String recipeCreator, String text, int difficulty, int rating);
	public List<Review> getAllReviewsForRecipe(String recipeName, String recipeCreator);
	public List<Review> getAllReviewsByAuthor(String author);
	public List<Review> getAllReviews();
	public String deleteReview(String author, String recipeName, String recipeCreator);
	public String updateReview(String author, String recipeName, String recipeCreator, String text, int difficulty, int rating);
	
	// MANAGING INGREDIENTS
	public Ingredient getIngredient(String name, String recipeName, String recipeCreator);
	public String addIngredient(String name, String recipeName, String recipeCreator, float amount, String unit);
	public List<Ingredient> getAllIngredientsForRecipe(String recipeName, String recipeCreator);
	public String deleteIngredient(String name, String recipeName, String recipeCreator);
	public String updateIngredient(String name, String recipeName, String recipeCreator, float amount, String unit);
	
	// MANAGING INSTRUCTIONS
	public Instruction getInstruction(Integer relativePosition, String recipeName, String recipeCreator, String text);
	public String addInstruction(String recipeName, String recipeCreator, String text);
	public List<Instruction> getAllInstructionsForRecipe(String recipeName, String recipeCreator);
	public String deleteInstruction(Integer relativePosition, String recipeName, String recipeCreator, String text);
	public String updateInstruction(Integer relativePosition, String recipeName, String recipeCreator, String text);
	
	// MANAGING TAGSg
	public Tag getTag(String name, String recipeName, String recipeCreator);
	public String addTag(String name, String recipeName, String recipeCreator);
	public List<Tag> getAllTagsByName(String name);
	public List<Tag> getAllTagsForRecipe(String recipeName, String recipeCreator);
	public String deleteTag(String name, String recipeName, String recipeCreator);
	
}
