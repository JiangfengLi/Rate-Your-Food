package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
import model.Recipe;
import model.Review;


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
		
		//set the alignments and spacing of components;
		myRecipes.setAlignment(Pos.CENTER);
		myRecipes.setLineSpacing(5);
		myReviews.setAlignment(Pos.CENTER);
		myReviews.setLineSpacing(5);
		createRecipe.setAlignment(Pos.CENTER);
		createRecipe.setLineSpacing(5);
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
		//for ( int i = 0; i < 10; i++ )
		//	reviewObList.get(i).updateAuthor(viewController);
		//reviewListView.setItems(reviewObList);
		if(!recipeObList.isEmpty())
			recipeObList.removeAll(recipeObList);
		if(!reviewObList.isEmpty())
			reviewObList.removeAll(reviewObList);
		
		List<Recipe> recipeData = viewController.getAllRecipesForUser(viewController.getCurrentUser().getEmail());
		List<Review> reviewData = viewController.getAllReviewsByAuthor(viewController.getCurrentUser().getEmail());

        for ( int i = 0; i < recipeData.size(); i++ )
        {
        	recipeObList.add( new RecipePreview(
        			viewController,
        			recipeData.get(i).getRecipeName(),
                    "src/main/resources/images/preview.png",
                    recipeData.get(i).toString(),
                    recipeData.get(i)) );
        }  
	
        for ( int i = 0; i < reviewData.size(); i++ )
        {
        	ReviewPreview newReviewPreview = new ReviewPreview(
        			viewController,
        			reviewData.get(i).getRecipeName(),
                    "src/main/resources/images/preview.png",
                    reviewData.get(i).getRecipeCreator(),
                    reviewData.get(i).getRating(), reviewData.get(i).getDifficulty(), "MyPage");
        	newReviewPreview.updateAuthor();
        	reviewObList.add(newReviewPreview);
        			
        }         
        
		recipeListView.setItems(recipeObList);
		reviewListView.setItems(reviewObList);
		
		recipeListView.setMinHeight(300);
		reviewListView.setMinHeight(450);
		
	}
	

}
