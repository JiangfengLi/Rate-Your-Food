import static org.junit.Assert.*;

import model.DatabaseInterface;
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
	 * BASIC INSERTION TEST
	 * check addRecipe, addInstruction, addIngredient
	 * make sure no errors throw
	 */
	@Test
	public void addRecipeTest() {
		boolean ex = false;
		try {
			dbaccess.addRecipe("newRecipe", "jack@gmail.com", 4, 3);
			dbaccess.addInstruction("newRecipe","jack@gmail.com","this is an instruction.");
			dbaccess.addIngredient("peas", "newRecipe", "jack@gmail.com", 4, "lbs");
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
		 * DELETE FROM Instruction WHERE RecipeName = 'newRecipe';
		 * DELETE FROM Ingredient WHERE RecipeName = 'newRecipe';
		 * DELETE FROM Recipe WHERE RecipeName = 'newRecipe';
		 * 
		 */
	}
	
	



}
