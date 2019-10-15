
package model;

/**
 * INGREDIENT
 * Data object to hold fields for Ingredients stored in DB
 * @author Alexander Miller
 *
 */
public class Ingredient {

	private String name;
	private String recipeName;
	private String recipeCreator;
	private float amount;
	private String unit;
	
	public Ingredient(String name, String recipeName, String recipeCreator, float amount, String unit) {
		this.name=name;
		this.recipeName=recipeName;
		this.recipeCreator=recipeCreator;
		this.amount=amount;
		this.unit=unit;
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
	public float getAmount() {
		return this.amount;
	}
	public String getUnit() {
		return this.unit;
	}
	
}
