package model;

/**
 * INGREDIENT
 * Data object to hold fields for Tags stored in DB
 * @author Alexander Miller
 *
 */
public class Tag {

	private String name;
	private String recipeName;
	private String recipeCreator;
	
	public Tag(String name, String recipeName, String recipeCreator) {
		this.name=name;
		this.recipeName=recipeName;
		this.recipeCreator=recipeCreator;
	}
	
	// GETTERS
	public String getName() {
		return this.name;
	}
	public String getRecipeName() {
		return this.recipeName;
	}
	public String getRecipeCreator() {
		return this.recipeCreator;
	}
	
}
