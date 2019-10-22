package view;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * MY PAGE
 * provides list view of recipes and reviews created by users.
 * provides common functionality such as input validation and BackButton handling
 * @author Jiangfeng Li
 */

public class MyPage extends HBox {
	private ViewController viewController;
	private Label myRecipes;
	private Label myReviews;
	private Button createRecipe;
//	private Button createReview;
	
	private VBox recipeColum;
	private VBox reviewColum;
	
	private ObservableList<RecipePreview> recipeObList;
	private ObservableList<ReviewPreview> reviewObList;
	private ListView<RecipePreview> recipeListView;
	private ListView<ReviewPreview> reviewListView;

	public MyPage(ViewController viewController2) {
		setAlignment(Pos.CENTER);
		this.setSpacing(100);		
		this.viewController = viewController2;

		// set up the background image
        try
        {
            Image background = new Image( new FileInputStream( "src/main/resources/images/wallpaper.jpeg" ) );
            BackgroundSize aSize = new BackgroundSize( 1920, 700, true, true, true, true );
            setBackground( new Background( new BackgroundImage( background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, aSize ) ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }		
		
		//set titles and buttons
		myRecipes = new Label("My Recipes");
		myReviews = new Label("My Reviews");
		createRecipe = new Button("Create Recipe");
		// createReview = new Button("Create Review");
		
		/*set createReview button to move to Review
		createReview.setOnAction(ae -> {
			viewController.moveToReview("MyPage");
		});
		*/
		
		// set up style of label
		myRecipes.setStyle( "-fx-font-size: 30px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #000000;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );
		myReviews.setStyle( "-fx-font-size: 30px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #000000;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: false;" );

		
		//set the main two column of this page
		recipeColum = new VBox();
		reviewColum = new VBox();

		recipeObList = FXCollections.observableArrayList();
		recipeListView = new ListView<RecipePreview>();
		
		reviewObList = FXCollections.observableArrayList();
		reviewListView = new ListView<ReviewPreview>();
		
        for ( int i = 0; i < 10; i++ )
        {
        	recipeObList.add( new RecipePreview(
        			viewController,
                    "This is a title",
                    "src/main/resources/images/preview.png",
                    "A pretty fairly long description to test how long a description can be without looking weird." ) );
        	reviewObList.add(    			
        			new ReviewPreview(
                			viewController,
                			"This is review " + (i + 1),
                            "src/main/resources/images/preview.png",
                            "David",
                            5, 5));
        }
		
		recipeListView.setItems(recipeObList);
		reviewListView.setItems(reviewObList);
		
		reviewListView.minHeight(recipeListView.getHeight() + 15);
		
		//set the alignments and spacing of components;
		myRecipes.setAlignment(Pos.CENTER);
		myRecipes.setLineSpacing(5);
		myReviews.setAlignment(Pos.CENTER);
		myReviews.setLineSpacing(5);
		createRecipe.setAlignment(Pos.CENTER);
		createRecipe.setLineSpacing(5);
		//createReview.setAlignment(Pos.CENTER);
		//createReview.setLineSpacing(5);
		recipeColum.setAlignment(Pos.CENTER);
		recipeColum.setSpacing(10);
		reviewColum.setAlignment(Pos.CENTER);
		reviewColum.setSpacing(10);
		
		recipeColum.getChildren().addAll(myRecipes, createRecipe, recipeListView);
		reviewColum.getChildren().addAll(myReviews, reviewListView);
		
		this.getChildren().addAll(recipeColum, reviewColum);
		
		//set createRecipe button to move to CreateRecipeView
		createRecipe.setOnAction(ae -> {
			viewController.moveToCreateRecipe();
		});
		
	}

	public void upDateReviewLV() {
		for ( int i = 0; i < 10; i++ )
			reviewObList.get(i).updateAuthor(viewController);
		reviewListView.setItems(reviewObList);
	}
	
	
	/**
	 * BACK HANDLER
	 * inside class to navigate back to log in root
	 
	protected class SetReviewDialogHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			// create a pop up dialog
	        final Stage dialog = new Stage();
	        dialog.setTitle("Review");
	        Button Back = new Button("Back");
	        Button Submit = new Button("Submit");
	        
	        // set up text Area
	        TextArea textArea = new TextArea();	        
	        
	        // create labels
	        Label title = new Label("Review ");
	        Label rating = new Label("Rating: ");
	        Label difficulty = new Label("Difficulty: ");
	        Label textAreaTitle = new Label("Text ");
	        
	        //set up choice box
	        ChoiceBox<Integer> rateSelection = new ChoiceBox<>();
	        ChoiceBox<Integer> difficultySelection = new ChoiceBox<>();	
	        
	        rateSelection.getItems().addAll(1, 2, 3, 4, 5);
	        difficultySelection.getItems().addAll(1, 2, 3, 4, 5);
	        
	        title.setFont(Font.font(null, FontWeight.BOLD, 30));
	        rating.setFont(Font.font(null, FontWeight.BOLD, 14));
	        difficulty.setFont(Font.font(null, FontWeight.BOLD, 14));
	        

	        dialog.initModality(Modality.NONE);
	        dialog.initOwner((Stage) viewController.returnStage());

	        HBox dialogHbox1 = new HBox(rating, rateSelection);
	        dialogHbox1.setAlignment(Pos.CENTER_LEFT);
	        dialogHbox1.setSpacing(10);
	        
	        HBox dialogHbox2 = new HBox(difficulty, difficultySelection);
	        dialogHbox2.setAlignment(Pos.CENTER_LEFT);
	        dialogHbox2.setSpacing(10);

	        HBox dialogHbox3 = new HBox(Back, Submit);
	        dialogHbox3.setAlignment(Pos.BOTTOM_CENTER);
	        dialogHbox3.setSpacing(300);
	        
	        
	        VBox dialogVbox = new VBox(title, dialogHbox1, dialogHbox2, textAreaTitle, textArea,
	        		dialogHbox3);
	        dialogVbox.setSpacing(20);
	        dialogVbox.setAlignment(Pos.CENTER_LEFT);

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

	        Back.addEventHandler(MouseEvent.MOUSE_CLICKED,
	                new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent e) {
	                        // inside here you can use the minimize or close the previous stage//
	                        dialog.close();
	                    }
	                });
	        Submit.addEventHandler(MouseEvent.MOUSE_CLICKED,
	                new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent e) {
	                        dialog.close();
	                    }
	                });

//	        dialogVbox.getChildren().addAll(dialogVbox);
	        Scene dialogScene = new Scene(dialogVbox, 500, 600);
//	        dialogScene.getStylesheets().add("//style sheet of your choice");
	        dialog.setScene(dialogScene);
	        dialog.show();		
	     }
	}*/
	

}
