package view;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Review extends VBox{
	private ViewController viewController;
	private Button Back;
	private Button Submit;
	private TextArea reviewText;
	private Label title;
	private Label rating;
    private Label difficulty;
    private Label textAreaTitle;
    private ChoiceBox<Integer> rateSelection;
    private ChoiceBox<Integer> difficultySelection;

	
	public Review(ViewController viewController2) {
		this.viewController = viewController2;
		
	    // create a pop up dialog
        //final Stage dialog = new Stage();
        //dialog.setTitle("Review");
        Back = new Button("Back");
        Submit = new Button("Submit");
        
        // set up text Area
        reviewText = new TextArea();	   
        reviewText.setWrapText(true);
        
        // create labels
        title = new Label("Review ");
        rating = new Label("Rating: ");
        difficulty = new Label("Difficulty: ");
        textAreaTitle = new Label("Text ");
        
        //set up choice box
        rateSelection = new ChoiceBox<>();
        difficultySelection = new ChoiceBox<>();	
        
        rateSelection.getItems().addAll(1, 2, 3, 4, 5);
        difficultySelection.getItems().addAll(1, 2, 3, 4, 5);
        
        title.setFont(Font.font(null, FontWeight.BOLD, 30));
        rating.setFont(Font.font(null, FontWeight.BOLD, 14));
        difficulty.setFont(Font.font(null, FontWeight.BOLD, 14));     

        HBox dialogHbox1 = new HBox(rating, rateSelection);
        dialogHbox1.setAlignment(Pos.CENTER_LEFT);
        dialogHbox1.setSpacing(10);
        
        HBox dialogHbox2 = new HBox(difficulty, difficultySelection);
        dialogHbox2.setAlignment(Pos.CENTER_LEFT);
        dialogHbox2.setSpacing(10);

        HBox dialogHbox3 = new HBox(Back, Submit);
        dialogHbox3.setAlignment(Pos.BOTTOM_CENTER);
        dialogHbox3.setSpacing(300);
        
        VBox dialogVbox = new VBox(title, dialogHbox1, dialogHbox2, textAreaTitle, reviewText,
        		dialogHbox3);
        dialogVbox.setSpacing(20);
        dialogVbox.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().add(dialogVbox);

        
        
		// set up the background image
        try
        {
            Image background = new Image( new FileInputStream( "src/main/resources/images/food_review_image.jpg" ) );
            BackgroundSize aSize = new BackgroundSize( 1920, 700, true, true, true, true );
            dialogVbox.setBackground( new Background( new BackgroundImage( background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, aSize ) ) );
        } catch ( IOException IOE )
        {
        	IOE.printStackTrace();
        }			        
        
        
        // set up click event handler for button
        Back.setOnAction(new SetBackHandler());
        Submit.setOnAction(new SetSubmitHandler());
		
	}

	/**
	 * BACK HANDLER
	 * inside class to navigate back to log in root
	 */
	protected class SetBackHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToMyPage();
		}
	}

	/**
	 * SUBMIT HANDLER
	 * inside class to navigate back to log in root
	 */
	protected class SetSubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToMyPage();
		}
	}

}
