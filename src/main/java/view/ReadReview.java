package view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Recipe;
import model.Review;
import view.ReviewView.SetBackHandler;
import view.ReviewView.SetSubmitHandler;

public class ReadReview extends ReviewView{

	
	public ReadReview(ViewController viewController2, Review theReview2) {
		super(viewController2, new Recipe(theReview2.getRecipeName(), theReview2.getRecipeCreator(),
				theReview2.getDifficulty(), theReview2.getRating()));
        Label rating_Label = new Label("rating: " + theReview2.getRating());
       
        Label difficulty_Label = new Label("difficulty: " + theReview2.getDifficulty());      

        Label textContent = new Label(theReview2.getText());

        VBox dialogVbox = new VBox(Author, RecipeName, RecipeCreator, rating_Label, difficulty_Label, textAreaTitle);
        dialogVbox.setSpacing(20);
        dialogVbox.setAlignment(Pos.CENTER_LEFT);
        
        HBox inFoAndImg = new HBox(dialogVbox, foodImg);
        inFoAndImg.setSpacing(20);
        inFoAndImg.setAlignment(Pos.CENTER);
        
        VBox commentArea = new VBox(inFoAndImg, textContent);
        commentArea.setSpacing(10);
        commentArea.setAlignment(Pos.CENTER);
        
		super.getChildren().remove(1);
		super.getChildren().add(1, commentArea);
		
	}

}
