package model;

/**
 * RECIPE
 * Data object to hold fields for Recipes stored in DB
 * @author Alexander Miller
 *
 */
public class Recipe {

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
