import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Ingredient;
import model.Instruction;
import model.Recipe;
import model.Review;
import model.Tag;
import model.User;

public class ModelTest {
	
	
	/**
	 * USER EQUALITY TEST
	 * make sure equals() works for User
	 */
	@Test
	public void userEqualityTest() {
		User userA = new User("jack@gmail.com","jack","jack","jack");
		User userB = new User("jack@gmail.com","jack","jack","john");
		User userC = new User("john@gmail.com","jack","jack","jack");
		User userD = new User("jack@gmail.com","jack","jack","jack");
		assertTrue(userA.equals(userD));
		assertTrue(userD.equals(userA));
		assertFalse(userA.equals(userB));
		assertFalse(userA.equals(userC));
		assertFalse(userB.equals(userC));
		assertFalse(userC.equals(userD));
	}
	
	/**
	 * RECIPE EQUALITY TEST
	 * make sure equals() works for Recipe
	 */
	@Test
	public void recipeEqualityTest() {
		Recipe recA = new Recipe("newRecipe","jack@gmail.com",3,4);
		Recipe recB = new Recipe("newRecipe","john@gmail.com",3,4);
		Recipe recC = new Recipe("oldRecipe","jack@gmail.com",3,4);
		Recipe recD = new Recipe("newRecipe","jack@gmail.com",4,4);
		Recipe recE = new Recipe("newRecipe","jack@gmail.com",3,4);
		assertTrue(recA.equals(recE));
		assertFalse(recA.equals(recB));
		assertFalse(recB.equals(recC));
		assertFalse(recC.equals(recD));
		assertFalse(recC.equals(recE));
		assertFalse(recA.equals(recC));
		assertFalse(recA.equals(recD));
	}
	
	/**
	 * REVIEW EQUALITY TEST
	 * make sure equals() works for Review
	 */
	@Test
	public void reviewEqualityTest() {
		Review revA = new Review("john@gmail.com","myfoods","jack@gmail.com","bad food.",4,4);
		Review revB = new Review("jack@gmail.com","myfoods","jack@gmail.com","bad food.",4,4);
		Review revC = new Review("john@gmail.com","myfoods","jack@gmail.com","good food.",4,4);
		Review revD = new Review("john@gmail.com","myfoods","jack@gmail.com","bad food.",5,4);
		Review revE = new Review("john@gmail.com","myfoods","jack@gmail.com","bad food.",4,5);
		Review revF = new Review("john@gmail.com","yofoods","jack@gmail.com","bad food.",4,4);
		Review revG = new Review("john@gmail.com","myfoods","jack@gmail.com","bad food.",4,4);
		assertTrue(revA.equals(revG));
		assertFalse(revA.equals(revB));
		assertFalse(revA.equals(revC));
		assertFalse(revA.equals(revD));
		assertFalse(revA.equals(revE));
		assertFalse(revA.equals(revF));
		assertFalse(revB.equals(revC));
		assertFalse(revC.equals(revD));
		assertFalse(revD.equals(revE));
		assertFalse(revE.equals(revF));
		assertFalse(revF.equals(revG));
	}
	
	/**
	 * INGREDIENT EQUALITY TEST
	 * make sure equals() works for Ingredient
	 */
	@Test
	public void ingredientEqualityTest() {
		Ingredient ingA = new Ingredient("peas","myrecipe","jack@gmail.com",20,"lbs");
		Ingredient ingB = new Ingredient("beans","myrecipe","jack@gmail.com",20,"lbs");
		Ingredient ingC = new Ingredient("peas","myfoods","jack@gmail.com",20,"lbs");
		Ingredient ingD = new Ingredient("peas","myrecipe","john@gmail.com",20,"lbs");
		Ingredient ingE = new Ingredient("peas","myrecipe","jack@gmail.com",30,"lbs");
		Ingredient ingF = new Ingredient("peas","myrecipe","jack@gmail.com",20,"quarts");
		Ingredient ingG = new Ingredient("peas","myrecipe","jack@gmail.com",20,"lbs");
		assertTrue(ingA.equals(ingG));
		assertFalse(ingA.equals(ingB));
		assertFalse(ingA.equals(ingC));
		assertFalse(ingA.equals(ingD));
		assertFalse(ingA.equals(ingE));
		assertFalse(ingA.equals(ingF));
		assertFalse(ingB.equals(ingC));
		assertFalse(ingC.equals(ingD));
		assertFalse(ingD.equals(ingE));
		assertFalse(ingE.equals(ingF));
	}
	
	/**
	 * INSTRUCTION EQUALITY TEST
	 * make sure equals() works for Instruction
	 */
	@Test
	public void instructionEqualityTest() {
		Instruction instA = new Instruction(1,"myfoods","jack@gmail.com","boil for 2 minutes");
		Instruction instB = new Instruction(2,"myfoods","jack@gmail.com","boil for 2 minutes");
		Instruction instC = new Instruction(1,"yofoods","jack@gmail.com","boil for 2 minutes");
		Instruction instD = new Instruction(1,"myfoods","john@gmail.com","boil for 2 minutes");
		Instruction instE = new Instruction(1,"myfoods","jack@gmail.com","boil for 3 minutes");
		Instruction instF = new Instruction(1,"myfoods","jack@gmail.com","boil for 2 minutes");
		assertTrue(instA.equals(instF));
		assertFalse(instA.equals(instB));
		assertFalse(instA.equals(instC));
		assertFalse(instA.equals(instD));
		assertFalse(instB.equals(instC));
		assertFalse(instC.equals(instD));
		assertFalse(instD.equals(instE));
	}
	
	/**
	 * TAG EQUALITY TEST
	 * make sure equals() works for Tag
	 */
	@Test
	public void tagEqualityTest() {
		Tag tagA = new Tag("pizza plate","myfoods","jack@gmail.com");
		Tag tagB = new Tag("pizza piatto","myfoods","jack@gmail.com");
		Tag tagC = new Tag("pizza plate","yofoods","jack@gmail.com");
		Tag tagD = new Tag("pizza plate","myfoods","john@gmail.com");
		Tag tagE = new Tag("pizza plate","myfoods","jack@gmail.com");
		assertTrue(tagA.equals(tagE));
		assertFalse(tagA.equals(tagB));
		assertFalse(tagA.equals(tagC));
		assertFalse(tagA.equals(tagD));
		assertFalse(tagB.equals(tagC));
		assertFalse(tagC.equals(tagD));
	}

}
