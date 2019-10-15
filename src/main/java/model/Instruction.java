package model;

/**
 * INSTRUCTION
 * Data object to hold fields for Instructions stored in DB
 * @author Alexander Miller
 *
 */
public class Instruction {
	
	private int ID;
	private String recipeName;
	private String recipeCreator;
	private String text;
	
	public Instruction(int id, String recipeName, String recipeCreator, String text) {
		this.ID = id;
		this.recipeName = recipeName;
		this.recipeCreator = recipeCreator;
		this.text=text;
	}
	
	// GETTERS
	public int getID() {
		return this.ID;
	}
	public String getRecipeName() {
		return this.recipeName;
	}
	public String getRecipeCreator() {
		return this.recipeCreator;
	}
	public String getText() {
		return this.text;
	}
	
}
