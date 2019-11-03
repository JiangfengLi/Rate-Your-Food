package view;

import model.Recipe;
import model.Review;

public class EditReview extends ReviewView{
	private Review review;

	public EditReview(ViewController viewController2, Review theReview2) {
		super(viewController2, new Recipe(theReview2.getRecipeName(), theReview2.getRecipeCreator(),
				theReview2.getDifficulty(), theReview2.getRating()));
		this.review = theReview2;
		System.out.println("Come to this EditReview");
        rateSelection.getSelectionModel().select(theReview2.getRating() - 1 );
        difficultySelection.getSelectionModel().select(theReview2.getDifficulty() - 1 );        

        reviewText.setText(theReview2.getText());
	}

}
