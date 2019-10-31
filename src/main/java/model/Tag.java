package model;

/**
 * INGREDIENT
 * Data object to hold fields for Tags stored in DB
 * @author Alexander Miller
 *
 */
public class Tag extends DataObject {

	private String name;
	private String recipeName;
	private String recipeCreator;
	
	public Tag(String name, String recipeName, String recipeCreator) {
		this.name=name;
		this.recipeName=recipeName;
		this.recipeCreator=recipeCreator;
	}
	
	/**
	 * EQUALS
	 * returns true if both non-null Review instances with all of the instance fields the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Tag) {
			Tag tag = (Tag) obj;
			
			// compare authors
			boolean namesSame = compareTwoStrings(this.name,tag.getName());
			
			// compare recipeNames
			boolean recipeNamesSame = compareTwoStrings(this.recipeName,tag.getRecipeName());
			
			// compare recipeCreators
			boolean recipeCreatorsSame = compareTwoStrings(this.recipeCreator,tag.getRecipeCreator());
			
			return namesSame && recipeNamesSame && recipeCreatorsSame;
			
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
	
}
