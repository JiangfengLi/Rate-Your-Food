package view;

import javafx.collections.ObservableList;
import model.Ingredient;
import model.Instruction;
import model.Recipe;

public class EditRecipeView extends CreateRecipeView {

	public EditRecipeView(ViewController vc, Recipe recipe) {
		super(vc);
		
		ObservableList<Ingredient> ingList = (ObservableList<Ingredient>) 
				vc.getAllIngredientsForRecipe(recipe.getRecipeName(), recipe.getCreator());
		
		ingredientTable.setItems(ingList);
		
		ObservableList<Instruction> insList = (ObservableList<Instruction>) 
				vc.getAllInstructionsForRecipe(recipe.getRecipeName(), recipe.getCreator());
		
		//instructionsTable.setItems(insList);
		recipeNameField.setText(recipe.getRecipeName());
	}

}
