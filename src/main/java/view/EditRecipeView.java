package view;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import model.Review;
import model.Tag;

public class EditRecipeView extends CreateRecipeView {
	
	private Recipe recipe;
	//private ObservableList<Ingredient> DBIngList;
	//private ObservableList<TempInstruction> DBInsList;
	//private List<Tag> DBTagList;

	public EditRecipeView(ViewController vc, Recipe recipe) {
		super(vc);
		this.recipe = recipe;
		
		String recipeName = recipe.getRecipeName();
		String recipeUser = recipe.getCreator();
		
		recipeNameField.setText(recipeName);
		
		List<Tag> tagList = viewController.getAllTagsForRecipe(recipeName, recipeUser);
		//DBTagList = new LinkedList<Tag>(tagList);
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0 ; i < tagList.size(); ++i) {
			if (i != 0)
				stringBuffer.append(" ");
			String tagName = tagList.get(i).getName();
			stringBuffer.append(tagName);
		}
		
		tagsField.setText(stringBuffer.toString());

		
		List<Ingredient> list = vc.getAllIngredientsForRecipe(recipeName, recipeUser);
		ObservableList<Ingredient> ingList = FXCollections.observableArrayList(list);

		ingredientTable.setItems(ingList);
		//DBIngList = FXCollections.observableArrayList(ingList);
		
		List<Instruction> insList = vc.getAllInstructionsForRecipe(recipeName, recipeUser);
		ObservableList<TempInstruction> tempInsList = FXCollections.observableArrayList();
		for (int i = 0; i < insList.size(); ++i) {
			String insText = insList.get(i).getText();
			TempInstruction tempIns = new TempInstruction(i,insText);
			tempInsList.add(tempIns);
		}
		instructionsTable.setItems(tempInsList);
		//DBInsList = FXCollections.observableArrayList(tempInsList);	
		
		Button deleteRecipeButton = new Button("Delete Recipe");
		setDeleteRecipeButton(deleteRecipeButton);
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
		int recipeDif = recipe.getDifficulty();
		int recipeRating = recipe.getRating();
		//if (!recipeName.equals(OGrecipeName)) {
		//	viewController.addRecipe(OGrecipeName, user, recipeName, dif, rating);
		//}
		
		
		List<Review> reviewList = viewController.getAllReviewsForRecipe(OGrecipeName, user);
		
		String delete = viewController.deleteRecipe(OGrecipeName, user);
		String add = viewController.addRecipe(recipeName, user, recipeDif, recipeRating);		
		
		System.out.println("result: "+delete);
		System.out.println("result: "+add);
		
		for (Review review : reviewList) {
			String author = review.getAuthor();
			String text = review.getText();
			int dif = review.getDifficulty();
			int rating = review.getRating();
			
			viewController.addReview(author, recipeName, user, text, dif, rating);
		}
		// ^ need to create the new recipe and then destroy the old recipe to prevent any row from referencing the old one
		// causing a foreign table exception
		
		/*
		for (Tag tag : DBTagList) {
			String name = tag.getName();
			viewController.deleteTag(name, OGrecipeName, user);
		}
		*/
		for (String tagName : tagsField.getText().split("\\W+")) {
			viewController.addTag(tagName, recipeName, user);
		}
		
		// Updating the new ingredients.
		ObservableList<Ingredient> ingList = ingredientTable.getItems();
		
		// deleting all ingredients
		/*
		for (Ingredient DBing : DBIngList) {
			String DBname = DBing.getName();
			viewController.deleteIngredient(DBname, OGrecipeName, user);
		}
		*/
		
		// adding the new ingredients
		for (Ingredient ing : ingList) {
			String name = ing.getName();
			float amount = ing.getAmount();
			String unit = ing.getUnit();
			viewController.addIngredient(name, recipeName, user, amount, unit);
		}
				
		// updating the instructions list
		ObservableList<TempInstruction> insList = instructionsTable.getItems();
		
		// deleting all old instructions
		/*
		for (TempInstruction DBInstruction : DBInsList) {
			String DBtext = DBInstruction.getStr();
			viewController.deleteInstruction(null, OGrecipeName, user, DBtext);
		}
		*/
		// adding the new Instructions
		for (TempInstruction tableInstruction : insList) {
			String text = tableInstruction.getStr();
			viewController.addInstruction(recipeName, user, text);			
		}
		
		/*
		List<Review> reviewList = viewController.getAllReviewsForRecipe(OGrecipeName, user);
		for (Review review : reviewList) {
			String author = review.getAuthor();
			String text = review.getText();
			int dif = review.getDifficulty();
			int rating = review.getRating();
			
			viewController.addReview(author, recipeName, user, text, dif, rating);
		}
		*/
		
		System.out.println("recipe, ingredients and instructions updated!!");

		viewController.moveToMyPage();

	} // submit method
	
	private void setDeleteRecipeButton(Button button) {
	
	button.setOnAction(e -> {
		
		String name = recipe.getRecipeName();
		String user = recipe.getCreator();
		viewController.deleteRecipe(name, user);
		
		viewController.moveToMyPage();
	});
	
	this.add(button, 1, 10);
	}

} // CreateRecipe class
