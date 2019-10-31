package model;

/**
 * REVIEW
 * Data object to hold fields for Reviews stored in DB
 * @author Alexander Miller
 *
 */
public class Review extends DataObject {

	private String author;
	private String recipeName;
	private String recipeCreator;
	private String text;
	private int difficulty;
	private int rating;
	
	public Review(String author, String recipeName, String recipeCreator, String text, int difficulty, int rating) {
		this.author=author;
		this.recipeName=recipeName;
		this.recipeCreator=recipeCreator;
		this.text=text;
		this.difficulty=difficulty;
		this.rating=rating;
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
		if (obj instanceof Review) {
			Review rev = (Review) obj;
			
			// compare authors
			boolean authorsSame = compareTwoStrings(this.author,rev.getAuthor());
			
			// compare recipeNames
			boolean recipeNamesSame = compareTwoStrings(this.recipeName,rev.getRecipeName());
			
			// compare recipeCreators
			boolean recipeCreatorsSame = compareTwoStrings(this.recipeCreator,rev.getRecipeCreator());
			
			// compare text
			boolean textsSame = compareTwoStrings(this.text,rev.getText());
			
			// compare amounts
			boolean difficultiesSame = this.difficulty == rev.getDifficulty();
			
			// compare units
			boolean ratingsSame = this.rating == rev.getRating();
			
			return authorsSame && recipeNamesSame && recipeCreatorsSame && textsSame && difficultiesSame && ratingsSame;
			
		} else {
			return false;
		}
	}
	
	// GETTERS
	public String getAuthor() {
		return this.author;
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
	public int getDifficulty() {
		return this.difficulty;
	}
	public int getRating() {
		return this.rating;
	}
}
