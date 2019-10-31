import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

import java.util.List;

import model.DatabaseInterface;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import model.Review;
import model.Tag;
import model.User;
import model.DBAccess;


/**
 * DATABASE TEST
 * class to test DBAccess
 * @author Alexander Miller
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {

	// INSTANCE VARS
	private DatabaseInterface dbaccess;
	
	// testing data
	private TestVariable testvar1;
	private TestVariable testvar2;
	
	// extract data from program
	private User user;
	private Recipe recipe;
	private Instruction instruction;
	private List<Recipe> recipes;
	private List<Review> reviews;
	private List<Ingredient> ingredients;
	private List<Instruction> instructions;
	private List<Tag> tags;
	private Ingredient ingredient;
	private Review review;
	private Review compReview1;
	private Review compReview2;
	private Tag tag;
	
	
	// *************************************************************************
	// **************************** TESTING UTILITIES***************************
	/**
	 * TEST VARIABLE
	 * basically a struct to hold variables for testing
	 * @author Alexander Miller
	 */
	private class TestVariable {
		
		// vars for easy direct access to fields
		// user vars
		public String email;
		public String firstName;
		public String lastName;
		public String password;
		// recipe vars
		public String recipeName;
		public int difficulty;
		public int rating;
		// review vars
		public String reviewText;
		// ingredient vars
		public String ingredientName;
		public float amount;
		public String unit;
		// instruction vars
		public String instructionText;
		// tag vars
		public String tagText;
		
		// if you want to make use of other functionality of model classes (i.e. .equals())
		// 		doesnt contain Instruction since id field created by DB so external to testvar instantiation
		//		also doesnt contain Review since this would only be a trivial self-review of not much interest
		public User user;
		public Recipe recipe;
		public Ingredient ingredient;
		public Tag tag;
		
		public TestVariable(String email, String firstName, String lastName, String password,
				String recipeName, int difficulty, int rating,
				String reviewText,
				String ingredientName, float amount, String unit,
				String instructionText,
				String tagText) {
			this.email=email;
			this.firstName=firstName;
			this.lastName=lastName;
			this.password=password;
			this.recipeName=recipeName;
			this.difficulty=difficulty;
			this.rating=rating;
			this.reviewText=reviewText;
			this.ingredientName=ingredientName;
			this.amount=amount;
			this.unit=unit;
			this.instructionText=instructionText;
			this.tagText=tagText;
			
			this.user = new User(email,firstName,lastName,password);
			this.recipe = new Recipe(recipeName,email,difficulty,rating);
			this.ingredient = new Ingredient(ingredientName,recipeName,email,amount,unit);
			this.tag = new Tag(tagText,recipeName,email);
		}
	}

	
	/** 
	 * SETUP
	 * initialize dbaccess, testing data, variables for data extraction, populate db
	 * add invoked: addUser, addRecipe, addInstruction, addIngredient, addReview, addTag
	 */
	@Before
	public void setup() {
		// initialize DBAccess
		dbaccess = new DBAccess();
		
		// create testing data
		testvar1 = new TestVariable("test@gmail.com","testy","mctest","testpass",
			"testRecipe", 4, 3, 
			"test - this meal sucks.",
			"test_peas", 5, "test_lbs",
			"cook for 20 minutes",
			"testy flavor"
		);
		testvar2 = new TestVariable("test1@testmail.com","john","testsohn","password",
			"newRecipe", 2, 5,
			"test - i like it.",
			"test_corn",3,"test_units",
			"bake for 40 minutes",
			"generic meal"
		);
		
		// create containers to retrieve data
		user = null;
		recipe = null;
		instruction = null;
		recipes = null;
		reviews = null;
		ingredients = null;
		instructions = null;
		tags = null;
		ingredient = null;
		review = null;
		compReview1 = new Review(testvar1.email, testvar2.recipeName, testvar2.email, testvar1.reviewText, testvar1.difficulty, testvar1.rating);  
		compReview2 = new Review(testvar2.email, testvar1.recipeName, testvar1.email, testvar2.reviewText, testvar2.difficulty, testvar2.rating);
		tag = null;
	}
	
	/**
	 * TEST ADD 
	 * populates DB with the data and makes sure no exceptions are thrown
	 */
	@Test
	public void test0Add() {
		// POPULATE DATA
		try {
			// ADD
			// add test users 1, 2
			dbaccess.addUser(testvar1.email, testvar1.firstName, testvar1.lastName, testvar1.password);
			dbaccess.addUser(testvar2.email, testvar2.firstName, testvar2.lastName, testvar2.password);
			// 1 makes recipe
			dbaccess.addRecipe(testvar1.recipeName, testvar1.email, testvar1.difficulty, testvar1.rating);
			dbaccess.addInstruction(testvar1.recipeName,testvar1.email,testvar1.instructionText);
			dbaccess.addIngredient(testvar1.ingredientName, testvar1.recipeName, testvar1.email, testvar1.amount, testvar1.unit);
			dbaccess.addTag(testvar1.tagText, testvar1.recipeName, testvar1.email);
			// 2 makes recipe
			dbaccess.addRecipe(testvar2.recipeName, testvar2.email, testvar2.difficulty, testvar2.rating);
			dbaccess.addInstruction(testvar2.recipeName,testvar2.email,testvar2.instructionText);
			dbaccess.addIngredient(testvar2.ingredientName, testvar2.recipeName, testvar2.email, testvar2.amount, testvar2.unit);
			dbaccess.addTag(testvar2.tagText, testvar2.recipeName, testvar2.email);
			// 2 makes review for 1
			dbaccess.addReview(testvar2.email, testvar1.recipeName, testvar1.email, testvar2.reviewText, testvar2.difficulty, testvar2.rating);
			// 1 makes review for 2
			dbaccess.addReview(testvar1.email, testvar2.recipeName, testvar2.email, testvar1.reviewText, testvar1.difficulty, testvar1.rating);
		} catch (Exception x) {
			x.printStackTrace();
			fail("Failed to populate data in database due to exception being thrown");
		}
	}
	
	/**
	 * TEST GET
	 * check get: getUser, getRecipe, getInstruction, getIngredient, getReview, getTag
	 * make sure no errors thrown
	 */
	@Test
	public void test1Get() {
		// GET USER
		// make sure users 1 and 2 added correctly with correct fields
		user = dbaccess.getUser(testvar1.email);
		assertNotNull(user);
		assertTrue(user.equals(testvar1.user));
		user = dbaccess.getUser(testvar2.email);
		assertNotNull(user);
		assertTrue(user.equals(testvar2.user));
		
		// GET RECIPE
		// make sure recipe 1 added correctly with correct fields
		recipe = dbaccess.getRecipe(testvar1.recipeName, testvar1.email);
		assertNotNull(recipe);
		assertTrue(recipe.equals(testvar1.recipe));
		// make sure recipe 2 added correctly with correct fields 
		recipe = dbaccess.getRecipe(testvar2.recipeName, testvar2.email);
		assertNotNull(recipe);
		assertTrue(recipe.equals(testvar2.recipe));
		
		// GET INSTRUCTION
		// make sure instruction 1 added correctly with correct fields
		instruction = dbaccess.getInstruction(null, testvar1.recipeName, testvar1.email, testvar1.instructionText);
		assertNotNull(instruction);
		// don't know instruction ID, so not using equals() method directly
		assertTrue(testvar1.recipeName.equals(instruction.getRecipeName()));
		assertTrue(testvar1.email.equals(instruction.getRecipeCreator()));
		assertTrue(testvar1.instructionText.equals(instruction.getText()));
		// make sure instruction 2 added correctly with correct fields
		instruction = dbaccess.getInstruction(null, testvar2.recipeName, testvar2.email, testvar2.instructionText);
		assertNotNull(instruction);
		// don't know instruction ID, so not using equals() method directly
		assertTrue(testvar2.recipeName.equals(instruction.getRecipeName()));
		assertTrue(testvar2.email.equals(instruction.getRecipeCreator()));
		assertTrue(testvar2.instructionText.equals(instruction.getText()));
		
		// GET INGREDIENT
		// make sure ingredient 1 added correctly with correct fields
		ingredient = dbaccess.getIngredient(testvar1.ingredientName, testvar1.recipeName, testvar1.email);
		assertNotNull(ingredient);
		assertTrue(ingredient.equals(testvar1.ingredient));
		// make sure ingredient 2 added correctly with correct fields
		ingredient = dbaccess.getIngredient(testvar2.ingredientName, testvar2.recipeName, testvar2.email);
		assertNotNull(ingredient);
		assertTrue(ingredient.equals(testvar2.ingredient));
		
		// GET REVIEW
		// make sure review 2 added correctly with correct fields
		review = dbaccess.getReview(testvar1.email, testvar2.recipeName, testvar2.email);
		assertNotNull(review);
		assertTrue(review.equals(compReview1));
		// make sure review 1 added correctly with correct fields
		review = dbaccess.getReview(testvar2.email, testvar1.recipeName, testvar1.email);
		assertNotNull(review);
		assertTrue(review.equals(compReview2));
		
		// GET TAG
		// make sure tag 1 added correctly with correct fields
		tag = dbaccess.getTag(testvar1.tagText, testvar1.recipeName, testvar1.email);
		assertNotNull(tag);
		assertTrue(tag.equals(testvar1.tag));
		// make sure tag 2 added correctly with correct fields
		tag = dbaccess.getTag(testvar2.tagText, testvar2.recipeName, testvar2.email);
		assertNotNull(tag);
		assertTrue(tag.equals(testvar2.tag));
	}
	
	/**
	 * TEST GET-ALL
	 * check get-all: getAllRecipes, getAllRecipesForUser, getAllRecipesByTag,
	 * 		getAllReviewsForRecipe, getAllReviewsForAuthor, getAllReviews,
	 * 		getAllIngredientsForRecipe, getAllInstructionsForRecipe, 
	 * 		getAllTagsByName, getAllTagsForRecipe
	 * verify no errors thrown
	 */
	@Test 
	public void test2GetAll() {

		// GET ALL RECIPES
		recipes = dbaccess.getAllRecipes();
		assertNotNull(recipes);
		// should have 2
		assertTrue(recipes.size()==2);
		// 1 should be testvar1 recipe, other should be testvar 2 recipe
		assertTrue(recipes.get(0).equals(testvar1.recipe) || recipes.get(1).equals(testvar1.recipe));
		assertTrue(recipes.get(0).equals(testvar2.recipe) || recipes.get(1).equals(testvar2.recipe));
		
		// GET ALL RECIPES FOR USER
		// check results for user 1
		recipes = dbaccess.getAllRecipesForUser(testvar1.email);
		assertNotNull(recipes);
		assertTrue(recipes.size()==1);
		assertTrue(recipes.get(0).equals(testvar1.recipe));
		// check results for user 2
		recipes = dbaccess.getAllRecipesForUser(testvar2.email);
		assertNotNull(recipes);
		assertTrue(recipes.size()==1);
		assertTrue(recipes.get(0).equals(testvar2.recipe));
		
		// GET ALL RECIPES BY TAG
		// check results for user 1
		recipes = dbaccess.getAllRecipesByTag(testvar1.tagText);
		assertNotNull(recipes);
		assertTrue(recipes.size()==1);
		assertTrue(recipes.get(0).equals(testvar1.recipe));
		// check results for user 2
		recipes = dbaccess.getAllRecipesByTag(testvar2.tagText);
		assertNotNull(recipes);
		assertTrue(recipes.size()==1);
		assertTrue(recipes.get(0).equals(testvar2.recipe));
		
		// GET ALL REVIEWS FOR RECIPE
		// check results for user 1
		reviews = dbaccess.getAllReviewsForRecipe(testvar1.recipeName, testvar1.email);
		assertNotNull(reviews);
		assertTrue(reviews.size()==1);
		assertTrue(reviews.get(0).equals(compReview2));
		// check results for user 2
		reviews = dbaccess.getAllReviewsForRecipe(testvar2.recipeName, testvar2.email);
		assertNotNull(reviews);
		assertTrue(reviews.size()==1);
		assertTrue(reviews.get(0).equals(compReview1));
		
		// GET ALL REVIEWS FOR AUTHOR
		// check results for user 1
		reviews = dbaccess.getAllReviewsByAuthor(testvar1.email);
		assertNotNull(reviews);
		assertTrue(reviews.size()==1);
		assertTrue(reviews.get(0).equals(compReview1));
		// check results for user 2
		reviews = dbaccess.getAllReviewsByAuthor(testvar2.email);
		assertNotNull(reviews);
		assertTrue(reviews.size()==1);
		assertTrue(reviews.get(0).equals(compReview2));
		
		// GET ALL REVIEWS
		reviews = dbaccess.getAllReviews();
		assertNotNull(reviews);
		assertTrue(reviews.size()==2);
		// 1 should be testvar1 review, other should be testvar 2 review
		assertTrue(reviews.get(0).equals(compReview1) || reviews.get(1).equals(compReview1));
		assertTrue(reviews.get(0).equals(compReview2) || reviews.get(1).equals(compReview2));
		
		// GET ALL INGREDIENTS FOR RECIPE
		// check for user 1
		ingredients = dbaccess.getAllIngredientsForRecipe(testvar1.recipeName,testvar1.email);
		assertNotNull(ingredients);
		assertTrue(ingredients.size()==1);
		assertTrue(ingredients.get(0).equals(testvar1.ingredient));
		// check for user 2
		ingredients = dbaccess.getAllIngredientsForRecipe(testvar2.recipeName,testvar2.email);
		assertNotNull(ingredients);
		assertTrue(ingredients.size()==1);
		assertTrue(ingredients.get(0).equals(testvar2.ingredient));
		
		// GET ALL INSTRUCTIONS FOR RECIPE
		// check for user 1
		instructions = dbaccess.getAllInstructionsForRecipe(testvar1.recipeName, testvar1.email);
		assertNotNull(instructions);
		assertTrue(instructions.size()==1);
		// don't know instruction ID, so not using equals() method directly
		instruction = instructions.get(0);
		assertTrue(testvar1.recipeName.equals(instruction.getRecipeName()));
		assertTrue(testvar1.email.equals(instruction.getRecipeCreator()));
		assertTrue(testvar1.instructionText.equals(instruction.getText()));
		// check for user 2
		instructions = dbaccess.getAllInstructionsForRecipe(testvar2.recipeName, testvar2.email);
		assertNotNull(instructions);
		assertTrue(instructions.size()==1);
		// don't know instruction ID, so not using equals() method directly
		instruction = instructions.get(0);
		assertTrue(testvar2.recipeName.equals(instruction.getRecipeName()));
		assertTrue(testvar2.email.equals(instruction.getRecipeCreator()));
		assertTrue(testvar2.instructionText.equals(instruction.getText()));
		
		// GET ALL TAGS BY NAME
		// check for user 1
		tags = dbaccess.getAllTagsByName(testvar1.tagText);
		assertNotNull(tags);
		assertTrue(tags.size()==1);
		assertTrue(tags.get(0).equals(testvar1.tag));
		// check for user 2
		tags = dbaccess.getAllTagsByName(testvar2.tagText);
		assertNotNull(tags);
		assertTrue(tags.size()==1);
		assertTrue(tags.get(0).equals(testvar2.tag));
		
		// GET ALL TAGS FOR RECIPE
		// check for user 1
		tags = dbaccess.getAllTagsForRecipe(testvar1.recipeName, testvar1.email);
		assertNotNull(tags);
		assertTrue(tags.size()==1);
		assertTrue(tags.get(0).equals(testvar1.tag));
		// check for user 2
		tags = dbaccess.getAllTagsForRecipe(testvar2.recipeName, testvar2.email);
		assertNotNull(tags);
		assertTrue(tags.size()==1);
		assertTrue(tags.get(0).equals(testvar2.tag));
		
		
	}
	
	/**
	 * TEST UPDATE
	 * check update:
	 * verify no errors thrown
	 */
	@Test
	public void test3Update() {
		
	}
	
	/**
	 * TEST DELETE
	 * check delete:
	 * verify no errors thrown
	 */
	@Test
	public void test4Delete() {
		/*
		 * use the following queries to verify deletion in mysql, and to delete manually spam data generated if failed to delete:
		 * 
		 * SELECT * FROM Recipe WHERE RecipeName = 'testRecipe';
		 * SELECT * FROM Ingredient WHERE RecipeName = 'testRecipe';
		 * SELECT * FROM Instruction WHERE RecipeName = 'testRecipe';
		 * SELECT * FROM Review WHERE RecipeName = 'testRecipe';
		 * 
			DELETE FROM Instruction WHERE RecipeName = 'testRecipe';
			DELETE FROM Ingredient WHERE RecipeName = 'testRecipe';
			DELETE FROM Review WHERE RecipeName = 'testRecipe';
			DELETE FROM Tag WHERE RecipeName = 'testRecipe';
			DELETE FROM Recipe WHERE RecipeName = 'testRecipe';
			
			DELETE FROM Instruction WHERE RecipeName = 'newRecipe';
			DELETE FROM Ingredient WHERE RecipeName = 'newRecipe';
			DELETE FROM Review WHERE RecipeName = 'newRecipe';
			DELETE FROM Tag WHERE RecipeName = 'newRecipe';
			DELETE FROM Recipe WHERE RecipeName = 'newRecipe';
			
			DELETE FROM User WHERE Email = 'test@gmail.com';
			DELETE FROM User WHERE Email = 'test1@testmail.com';
		 */
	}
	
	/**
	 * BASIC FUNCTIONALITY TEST
	 * 
	 * 
	 * check getAll:
	 * check update: updateUser, updateRecipe, updateIngredient, updateInstruction, updateReview
	 * check delete: 
	 * 
	 */
	@Test
	public void basicInsertionAndRetrievalTest() {
		

	}
	
	



}
