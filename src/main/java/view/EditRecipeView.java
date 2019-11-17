package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Ingredient;
import model.Instruction;
import model.Recipe;

public class EditRecipeView extends CreateRecipeView {
	
	private Recipe recipe;
	private ObservableList<Ingredient> DBIngList;
	private ObservableList<TempInstruction> DBInsList;

	public EditRecipeView(ViewController vc, Recipe recipe) {
		super(vc);
		this.recipe = recipe;
		
		List<Ingredient> list = vc.getAllIngredientsForRecipe(recipe.getRecipeName(), recipe.getCreator());
		ObservableList<Ingredient> ingList = FXCollections.observableArrayList(list);

		ingredientTable.setItems(ingList);
		DBIngList = FXCollections.observableArrayList(ingList);
		
		List<Instruction> insList = vc.getAllInstructionsForRecipe(recipe.getRecipeName(), recipe.getCreator());
		ObservableList<TempInstruction> tempInsList = FXCollections.observableArrayList();
		for (int i = 0; i < insList.size(); ++i) {
			String insText = insList.get(i).getText();
			TempInstruction tempIns = new TempInstruction(i,insText);
			tempInsList.add(tempIns);
		}
		instructionsTable.setItems(tempInsList);
		DBInsList = FXCollections.observableArrayList(tempInsList);
		
		recipeNameField.setText(recipe.getRecipeName());
		//setSubmitButton();
	}
	
	@Override
	protected void setSubmitButton() {
		submitButton.setOnAction(ae -> {
			submit();
		});
	}
	
	private void submit() {
		
		// setting main recipe variables for update
		String recipeName = recipeNameField.getText();
		String OGrecipeName = recipe.getRecipeName();
		String user = recipe.getCreator();
		
		// updating recipe name if different
		int dif = recipe.getDifficulty();
		int rating = recipe.getRating();
		if (!recipeName.equals(OGrecipeName))
			viewController.updateRecipe(OGrecipeName, user, recipeName, dif, rating);
		
		//TODO: tags update
		
		// deleting all ingredients and setting the new ones
		ObservableList<Ingredient> ingList = ingredientTable.getItems();
		for (int i = 0; i < ingList.size(); ++i) {
						
			if (i < DBIngList.size()) {

				Ingredient DBIngredient = DBIngList.get(i);
				String DBname = DBIngredient.getName();
				viewController.deleteIngredient(DBname, OGrecipeName, user);
			}
			
			Ingredient tableIngredient = ingList.get(i);
			String name = tableIngredient.getName();
			float amount = tableIngredient.getAmount();
			String unit = tableIngredient.getUnit();
			viewController.addIngredient(name, recipeName, user, amount, unit);

		}
				
		// deleting all ingredients and setting the new ones
		ObservableList<TempInstruction> insList = instructionsTable.getItems();
		for (int i = 0; i < insList.size(); ++i) {
						
			if (i < DBInsList.size()) {
				
				TempInstruction DBInstruction = DBInsList.get(i);
				String DBtext = DBInstruction.getStr();
				viewController.deleteInstruction(null, OGrecipeName, user, DBtext);
			}
			
			TempInstruction tableInstruction = insList.get(i);
			String text = tableInstruction.getStr();
			viewController.addInstruction(recipeName, user, text);			

		}
		
		System.out.println("recipe, ingredients and instructions updated!!");

		viewController.moveToMyPage();

	} // submit method
} // CreateRecipe class
