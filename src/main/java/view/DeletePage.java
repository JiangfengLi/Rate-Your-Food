package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import model.Recipe;
import view.ReviewView.SetDeletePageHandler;

public class DeletePage extends ReviewView {
	private String removeType = "";

	public DeletePage(ViewController viewController2, Recipe theRecipe, String deleteType) {
		super(viewController2, theRecipe);
		// TODO Auto-generated constructor stub
		title.setText("Are you sure to delete this" + deleteType + "?");
		Submit = new Button("OK");
		Back.setText("Cancel");
		
		this.removeType = deleteType;
		
		super.getChildren().remove(0, 3);
		super.getChildren().add(0, title);
		
        HBox dialogHbox3 = new HBox(Back, Submit);
        dialogHbox3.setAlignment(Pos.BOTTOM_CENTER);
        dialogHbox3.setSpacing(400);		
        super.getChildren().add(1, dialogHbox3);
        Submit.setOnAction(new SetDeleteHandler());
	}

	/**
	 * DELETE HANDLER
	 * inside class to navigate back to log in root
	 */
	protected class SetDeleteHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(removeType.equals("Review")) {
			     viewController.deleteReview(viewController.getCurrentUser().getEmail(), reviewRecipe.getRecipeName(), 
			    		 reviewRecipe.getCreator());
			     
			} else if(removeType.equals("Recipe")) {
				viewController.deleteRecipe(reviewRecipe.getRecipeName(), reviewRecipe.getCreator());
			}
			if(returnSite != null) {
				if(returnSite.equals("MyPage"))
					viewController.moveToMyPage();
				else
					viewController.moveToRecipe(reviewRecipe);
			}		
		}
	}
}
