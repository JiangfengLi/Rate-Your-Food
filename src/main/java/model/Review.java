package model;

/**
 * REVIEW
 * Data object to hold fields for Reviews stored in DB
 * @author Alexander Miller
 *
 */
public class Review {

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
