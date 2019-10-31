
package model;

/**
 * INGREDIENT
 * Data object to hold fields for Ingredients stored in DB
 * @author Alexander Miller
 *
 */
public class Ingredient extends DataObject {

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
	
	/**
	 * EQUALS
	 * returns true if both non-null Ingredient instances with all of the instance fields the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Ingredient) {
			Ingredient ing = (Ingredient) obj;
			
			// compare names
			boolean namesSame = compareTwoStrings(this.name,ing.getName());
			
			// compare recipeNames
			boolean recipeNamesSame = compareTwoStrings(this.recipeName,ing.getRecipeName());
			
			// compare recipeCreators
			boolean recipeCreatorsSame = compareTwoStrings(this.recipeCreator,ing.getRecipeCreator());
			
			// compare amounts
			boolean amtsSame = this.amount == ing.getAmount();
			
			// compare units
			boolean unitsSame = compareTwoStrings(this.unit,ing.getUnit());
			
			return namesSame && recipeNamesSame && recipeCreatorsSame && amtsSame && unitsSame;
			
		} else {
			return false;
		}
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
	
	// added portion by Joseph Corona
	// ------------------------------------------------------------------------
	
	//SETTERS 
	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}
	
	public String toString() {
		return
			getAmount() + " " +
			getUnit()   + " " +
			getName();
	}
	// ------------------------------------------------------------------------
	
}
