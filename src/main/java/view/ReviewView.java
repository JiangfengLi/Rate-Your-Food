package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Recipe;

public class ReviewView extends VBox{
	private ViewController viewController;
	protected Button Back;
	private Button Submit;
	protected TextArea reviewText;
	protected ImageView foodImg;
	private Label title;
	protected Label Author;
	protected Label RecipeName;
	protected Label RecipeCreator;
	private Label rating;
    private Label difficulty;
    protected Label textAreaTitle;
    protected ChoiceBox<Integer> rateSelection;
    protected ChoiceBox<Integer> difficultySelection;
    private static String returnSite;
    private Recipe reviewRecipe;
    private Label errorMsgPH;

	
	public ReviewView(ViewController viewController2, Recipe theRecipe) {
		this.viewController = viewController2;
		this.reviewRecipe = theRecipe;
				
        Back = new Button("Back");
        Submit = new Button("Submit");
		// create error message placeholder
        errorMsgPH = new Label("");
        errorMsgPH.setStyle( "-fx-font-size: 20px;\n" + "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ff0000;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,0,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );        
        
        // set up text Area
        reviewText = new TextArea();	   
        reviewText.setWrapText(true);
        reviewText.setMaxHeight(150);
        //eviewText.setPrefWidth(50);
        reviewText.setMaxWidth(550);
               
        // create labels
        title = new Label("Review ");
        title.setAlignment(Pos.TOP_CENTER);     
        title.setStyle( "-fx-font-size: 20px;\n" + "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ffff00;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,0,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );        
        
        Author = new Label("Author: " + viewController.getCurrentUser().getEmail());
        Author.setStyle( "-fx-font-size: 14px;\n" + "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ffff00;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );    
        
        RecipeName = new Label("RecipeName: " + reviewRecipe.getRecipeName());
        RecipeName.setStyle( "-fx-font-size: 14px;\n" + "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ffff00;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );
        
        RecipeCreator = new Label("RecipeCreator: " + reviewRecipe.getCreator());
        RecipeCreator.setStyle( "-fx-font-size: 14px;\n" + "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ffff00;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );
        
        rating = new Label("Rating: ");
        rating.setStyle( "-fx-font-size: 14px;\n" + "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ffff00;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );        
        
        difficulty = new Label("Difficulty: ");
        difficulty.setStyle( "-fx-font-size: 14px;\n" + "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ffff00;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );    
        
        textAreaTitle = new Label("Text");
        textAreaTitle.setStyle( "-fx-font-size: 14px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #ffff00;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );
        textAreaTitle.setAlignment(Pos.CENTER_LEFT);
        
        foodImg = setImage("src/main/resources/images/preview.png");
        
        //set up choice box
        rateSelection = new ChoiceBox<>();
        rateSelection.getItems().addAll(1, 2, 3, 4, 5);       
        rateSelection.getSelectionModel().select(reviewRecipe.getRating() - 1 );
        
        difficultySelection = new ChoiceBox<>();       
        difficultySelection.getItems().addAll(1, 2, 3, 4, 5);
        difficultySelection.getSelectionModel().select(reviewRecipe.getDifficulty() - 1 );        

        HBox dialogHbox1 = new HBox(rating, rateSelection);
//        dialogHbox1.setAlignment(Pos.CENTER);
        dialogHbox1.setSpacing(10);
        
        HBox dialogHbox2 = new HBox(difficulty, difficultySelection);
//        dialogHbox2.setAlignment(Pos.CENTER);
        dialogHbox2.setSpacing(10);

        HBox dialogHbox3 = new HBox(Back, Submit);
        dialogHbox3.setAlignment(Pos.BOTTOM_CENTER);
        dialogHbox3.setSpacing(300);
        
        VBox dialogVbox = new VBox(Author, RecipeName, RecipeCreator, dialogHbox1, dialogHbox2, textAreaTitle);
        dialogVbox.setSpacing(20);
        dialogVbox.setAlignment(Pos.CENTER_LEFT);
        
        HBox inFoAndImg = new HBox(dialogVbox, foodImg);
        inFoAndImg.setSpacing(20);
        inFoAndImg.setAlignment(Pos.CENTER);
        
        VBox commentArea = new VBox(inFoAndImg, reviewText);
        commentArea.setSpacing(10);
        commentArea.setAlignment(Pos.CENTER);
       
        this.getChildren().addAll(title, commentArea, dialogHbox3, errorMsgPH);
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        
        //this.setTop(dialogVbox);
        //this.setCenter(reviewText);
        //this.setBottom(dialogHbox3);
        
		// set up the background image
        try
        {
            Image background = new Image( new FileInputStream( "src/main/resources/images/food_review_image.jpg" ) );
            BackgroundSize aSize = new BackgroundSize( 1920, 700, true, true, true, true );
            this.setBackground( new Background( new BackgroundImage( background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, aSize ) ) );
        } catch ( IOException IOE )
        {
        	IOE.printStackTrace();
        }			        
        
        
        // set up click event handler for button
        Back.setOnAction(new SetBackHandler());
        Submit.setOnAction(new SetSubmitHandler());
		
	}

	private ImageView setImage(String url) {
		ImageView imageView = new ImageView();
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(250);
		try {
			imageView.setImage(new Image(new FileInputStream(url)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return imageView;
	}

	/**
	 * BACK HANDLER
	 * inside class to navigate back to log in root
	 */
	protected class SetBackHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			if(returnSite != null) {
				if(returnSite.equals("MyPage"))
					viewController.moveToMyPage();
				else
					viewController.moveToRecipe(reviewRecipe);
			}
		}
	}

	/**
	 * SUBMIT HANDLER
	 * inside class to navigate back to log in root
	 */
	protected class SetSubmitHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			Integer input_rating = rateSelection.getValue();  
			Integer input_difficulty = difficultySelection.getValue();  
			String input_text = reviewText.getText();
			String err_msg = "";
			if(input_rating == null) {
				err_msg += "Invalid rating, please give a valid one\n";
			}
			if(input_difficulty == null) {
				err_msg += "Invalid difficulty, please give a valid one\n";
			}
			if(input_text == null || input_text.isEmpty()) {
				err_msg += "Empty text, please give a valid one\n";
			}
			
			if(err_msg.isEmpty()) {
			    if(returnSite != null) {
				    viewController.updateReviewDB(reviewRecipe.getRecipeName(), reviewRecipe.getCreator(), 
				    		(int)input_rating, (int)input_difficulty, input_text);
				    System.out.println("Review sent to database successfully");
				    if(returnSite.equals("MyPage")) 
				        viewController.moveToMyPage();
			        else 
					    viewController.moveToRecipe(reviewRecipe);
			    }
			} else {
				errorMsgPH.setText(err_msg);
			}
		}
	}

	public void setReturnPoint(String backVeiw) {
		// TODO Auto-generated method stub
		returnSite = backVeiw;
	}

}
