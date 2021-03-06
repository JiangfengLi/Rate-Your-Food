package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import model.Review;
import model.Tag;
import view.CreateRecipeView.TempInstruction;

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
		try {
			String imgPath = vc.getMainImageForRecipe(recipe);
			image.setImage(new Image(new FileInputStream(imgPath)));
		} catch (FileNotFoundException x) {
			System.out.println("Default Image for Recipe in EditRecipeView not found.");
		}
		
		rateSelection.getSelectionModel().select(recipe.getRating() - 1 );        
        difficultySelection.getSelectionModel().select(recipe.getDifficulty() - 1 );        
		
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
		
		try {
			String imagePath = vc.getMainImageForRecipe(recipe);
			file = new File(imagePath);

			image.setImage(new Image(new FileInputStream(imagePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
				
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
		
		if (!areFieldsValid())
			return;
		
		// setting main recipe variables for update
		String recipeName = recipeNameField.getText();
		String OGrecipeName = recipe.getRecipeName();
		String user = recipe.getCreator();
		
		// updating recipe name if different
		int recipeDif = recipe.getDifficulty();
		int recipeRating = recipe.getRating();
		
		// new method for updating recipe instead of deleting and then
		// creating a new one
		///////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		
		viewController.updateRecipe(OGrecipeName, user, recipeName, recipeDif, recipeRating);
		
		//for (String tagName : tagsField.getText().split("\\W+")) {
		//viewController.update(tagName, recipeName, user);
		//}
		
		
		
		*/
		////////////////////////////////////////////////////////////////////////////////////////////////////
		
		List<Review> reviewList = viewController.getAllReviewsForRecipe(OGrecipeName, user);
				
		String delete = viewController.deleteRecipe(OGrecipeName, user);
		
		int rateSelector = rateSelection.getValue();
		int difSelector = difficultySelection.getValue();
		
		String add = viewController.addRecipe(recipeName, user, rateSelector, difSelector);		
						
		for (Review review : reviewList) {
			String author = review.getAuthor();
			String text = review.getText();
			int dif = review.getDifficulty();
			int rating = review.getRating();
			
			viewController.addReview(author, recipeName, user, text, dif, rating);
		}
		
		for (String tagName : tagsField.getText().split("\\W+")) {
			viewController.addTag(tagName, recipeName, user);
		}
		
		// Updating the new ingredients.
		ObservableList<Ingredient> ingList = ingredientTable.getItems();
		
		// adding the new ingredients
		for (Ingredient ing : ingList) {
			String name = ing.getName();
			float amount = ing.getAmount();
			String unit = ing.getUnit();
			viewController.addIngredient(name, recipeName, user, amount, unit);
		}
				
		// updating the instructions list
		ObservableList<TempInstruction> insList = instructionsTable.getItems();
		
		// adding the new Instructions
		for (TempInstruction tableInstruction : insList) {
			String text = tableInstruction.getStr();
			viewController.addInstruction(recipeName, user, text);			
		}
		
		
		viewController.addImageForRecipe(file.toPath().toString(), recipeName, user);
		
		System.out.println("recipe, ingredients and instructions updated!!");
		
		/////////////////////////////////////////////////////////////////////////////////

		viewController.moveToMyPage();

	} // submit method
	
	private boolean areFieldsValid() {
				
		String name = recipeNameField.getText();
		String tags = tagsField.getText();
		ObservableList<Ingredient> ingredients = ingredientTable.getItems();
		ObservableList<TempInstruction> instructions = instructionsTable.getItems();
		
		if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || tags.isEmpty()) {
			message.setText("Missing info for required field(s), check for red marks");
			
			String redMark = "-fx-border-color: red ; -fx-border-width: 2px ;";
			String noMark = "";
			
			if (name.isEmpty())
				recipeNameField.setStyle(redMark);
			else 
				recipeNameField.setStyle(noMark);
			
			if (tags.isEmpty())
				tagsField.setStyle(redMark);
			else
				tagsField.setStyle(noMark);
			
			if (ingredients.isEmpty())
				ingredientTable.setStyle(redMark);
			else
				ingredientTable.setStyle(noMark);
			
			if (instructions.isEmpty())
				instructionsTable.setStyle(redMark);
			else
				instructionsTable.setStyle(noMark);
			
			return false;

		}
		
		return true;
	}
	
	private void setDeleteRecipeButton(Button button) {
	
	button.setOnAction(e -> {
		
		String name = recipe.getRecipeName();
		String user = recipe.getCreator();
		viewController.deleteRecipe(name, user);
		
		viewController.moveToMyPage();
	});
	
	this.add(button, 1, 11);
	}

} // CreateRecipe class
