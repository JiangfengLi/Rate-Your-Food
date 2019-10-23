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
 * provides list view of items that are ready to be purchased.
 * provides common functionality such as input validation and BackButton handling
 * @author Jiangfeng Li
 */

public class CartPage extends HBox{
	private ViewController viewController;
	private Label myRecipes;
	private Label myReviews;
	private Button confirmOrder;
	private Button cancelOrder;
	private Button deleteItem;
	private Button setLocation;
	private Button setDeliverTime;	
	
	//private VBox shoppingItemsColum;
	private VBox decisionColum;
	
	private ObservableList<RecipePreview> recipeObList;
	private ListView<RecipePreview> recipeListView;

	public CartPage(ViewController viewController2) {
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
        confirmOrder = new Button("Confirm Order");
        cancelOrder = new Button("Cancel Order");
        deleteItem = new Button("Delete Item");
        setLocation = new Button("Select Deliever Address");
        setDeliverTime = new Button("Select Deliever Date");
		
		//set the main two column of this page
		//shoppingItemsColum = new VBox();
		decisionColum = new VBox();
		decisionColum.getChildren().addAll( confirmOrder, cancelOrder, deleteItem, setLocation, setDeliverTime);
		decisionColum.setSpacing(10);

		recipeObList = FXCollections.observableArrayList();
		recipeListView = new ListView<RecipePreview>();
		
        for ( int i = 0; i < 10; i++ )
        {
        	recipeObList.add( new RecipePreview(
        			viewController,
                    "This is a title",
                    "src/main/resources/images/preview.png",
                    "A pretty fairly long description to test how long a description can be without looking weird.",
					null ) );
        }
		
		recipeListView.setItems(recipeObList);		
		
		this.getChildren().addAll(recipeListView, decisionColum);
		
		//set createRecipe button to move to CreateTRecipeView
		//createRecipe.setOnAction(ae -> {
		//	viewController.moveToCreateRecipe();
		//});
		
	}

}
