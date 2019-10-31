package model;

/**
 * INSTRUCTION
 * Data object to hold fields for Instructions stored in DB
 * @author Alexander Miller
 *
 */
public class Instruction extends DataObject {
	
	private int ID;
	private String recipeName;
	private String recipeCreator;
	private String text;
	
	public Instruction() {}
	
	public Instruction(int id, String recipeName, String recipeCreator, String text) {
		this.ID = id;
		this.recipeName = recipeName;
		this.recipeCreator = recipeCreator;
		this.text=text;
	}
	
	/**
	 * EQUALS
	 * returns true if both non-null Instruction instances with all of the instance fields the same, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof Instruction) {
			Instruction inst = (Instruction) obj;
			
			// compare IDs
			boolean IDsSame = this.ID == inst.getID();
			
			// compare recipeNames
			boolean recipeNamesSame = compareTwoStrings(this.recipeName,inst.getRecipeName());
			
			// compare recipeCreators
			boolean recipeCreatorsSame = compareTwoStrings(this.recipeCreator,inst.getRecipeCreator());
			
			// compare texts
			boolean textsSame = compareTwoStrings(this.text,inst.getText());
			
			return IDsSame && recipeNamesSame && recipeCreatorsSame && textsSame;
			
		} else {
			return false;
		}
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
