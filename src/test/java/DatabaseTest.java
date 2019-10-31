import static org.junit.Assert.*;

import java.util.List;

import model.DatabaseInterface;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import model.Review;
import model.DBAccess;
import org.junit.Test;
import org.junit.Before;

/**
 * DATABASE TEST
 * class to test DBAccess
 * @author Alexander Miller
 *
 */
public class DatabaseTest {

	DatabaseInterface dbaccess;
	
	/*
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	*/
	
	/** 
	 * SETUP
	 */
	@Before
	public void setup() {
		dbaccess = new DBAccess();
	}
	
	/**
	 * BASIC INSERTION AND RETRIEVAL TEST
	 * check addRecipe, addInstruction, addIngredient
	 * make sure no errors throw
	 */
	@Test
	public void basicInsertionAndRetrievalTest() {
		boolean ex = false;
		try {
			dbaccess.addRecipe("newRecipe", "jack@gmail.com", 4, 3);
			dbaccess.addInstruction("newRecipe","jack@gmail.com","this is an instruction.");
			dbaccess.addIngredient("peas", "newRecipe", "jack@gmail.com", 4, "lbs");
			dbaccess.addReview("john@gmail.com", "newRecipe", "jack@gmail.com", "this meal sucks.", 3, 3);
			Recipe recipe = null;
			List<Instruction> instructions = null;
			Ingredient ingredient = null;
			Review review = null;
			recipe = dbaccess.getRecipe("newRecipe", "jack@gmail.com");
			assertNotNull(recipe);
			instructions = dbaccess.getAllInstructionsForRecipe("newRecipe","jack@gmail.com");
			assertNotNull(instructions);
			assertFalse(instructions.isEmpty());
			ingredient = dbaccess.getIngredient("peas", "newRecipe", "jack@gmail.com");
			assertNotNull(ingredient);
			review = dbaccess.getReview("john@gmail.com", "newRecipe", "jack@gmail.com");
			assertNotNull(review);
			//Recipe rec = ;
		} catch (Exception x) {
			ex = true;
		}
		assertFalse(ex);
		
		/*
		 * use the following queries to verify result in mysql, then delete the spam data generated:
		 * 
		 * SELECT * FROM Recipe WHERE RecipeName = 'newRecipe';
		 * SELECT * FROM Ingredient WHERE RecipeName = 'newRecipe';
		 * SELECT * FROM Instruction WHERE RecipeName = 'newRecipe';
		 * SELECT * FROM Review WHERE RecipeName = 'newRecipe';
		 * DELETE FROM Instruction WHERE RecipeName = 'newRecipe';
		 * DELETE FROM Ingredient WHERE RecipeName = 'newRecipe';
		 * DELETE FROM Review WHERE RecipeName = 'newRecipe';
		 * DELETE FROM Recipe WHERE RecipeName = 'newRecipe';
		 * 
		 */
	}
	
	



}
