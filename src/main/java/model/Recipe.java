package model;

/**
 * RECIPE
 * Data object to hold fields for Recipes stored in DB
 * @author Alexander Miller
 *
 */
public class Recipe extends DataObject {

	private String recipeName;
	private String creator;
	private int difficulty;
	private int rating;
	
	public Recipe(String recipeName, String creator, int difficulty, int rating) {
		this.recipeName = recipeName;
		this.creator = creator;
		this.difficulty=difficulty;
		this.rating=rating;
	}
	
	/**
	 * EQUALS
	 * returns true if both non-null Recipe instances with all of the instance fields the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Recipe) {
			Recipe rec = (Recipe) obj;
			
			// compare recipeNames
			boolean recipeNamesSame = compareTwoStrings(this.recipeName,rec.getRecipeName());
			
			// compare recipeCreators
			boolean recipeCreatorsSame = compareTwoStrings(this.creator,rec.getCreator());
			
			// compare amounts
			boolean difficultiesSame = this.difficulty == rec.getDifficulty();
			
			// compare units
			boolean ratingsSame = this.rating == rec.getRating();
			
			return recipeNamesSame && recipeCreatorsSame && difficultiesSame && ratingsSame;
			
		} else {
			return false;
		}
	}
	
	// GETTERS
	public String getRecipeName() {
		return this.recipeName;
	}
	public String getCreator() {
		return this.creator;
	}
	public int getDifficulty() {
		return this.difficulty;
	}
	public int getRating() {
		return this.rating;
	}
	
}
