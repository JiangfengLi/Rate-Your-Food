package view;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Ingredient;

/**
 * MY PAGE
 * provides list view of items that are ready to be purchased.
 * provides common functionality such as input validation and BackButton handling
 * @author Jiangfeng Li, Eric Thomas
 */

public class CartPage extends HBox
{
	private ViewController viewController;
	private Button increaseQuantity;
	private Button decreaseQuantity;
	private Button removeItem;
	

	private VBox changeQuantityBox;


	public CartPage(ViewController viewController) {
		setAlignment(Pos.CENTER);
		setSpacing( 10 );
		this.viewController = viewController;

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
        increaseQuantity = new Button("+");
        increaseQuantity.setPrefSize( 100, 100 );
        increaseQuantity.setStyle( "-fx-font-size:40; -fx-text-fill: #ffffff; -fx-background-color: #f59b42; -fx-border-color: #f57e42; -fx-border-width: 2px;" );

        decreaseQuantity = new Button("-");
        decreaseQuantity.setPrefSize( 100, 100 );
        decreaseQuantity.setStyle( "-fx-font-size:40; -fx-text-fill: #ffffff; -fx-background-color: #f59b42; -fx-border-color: #f57e42; -fx-border-width: 2px;" );

        removeItem = new Button( "Remove" );
        removeItem.setStyle( "-fx-font-size:20; -fx-text-fill: #ffffff; -fx-background-color: #f03030; -fx-border-color: #000000; -fx-border-width: 1px;" );

		ScrollPane scrollPane = new ScrollPane(  );
		scrollPane.setMaxHeight( 750 );
		scrollPane.setFitToHeight(true);
		scrollPane.prefWidth( 500 );


		TableView tableView = new TableView();

		TableColumn<String, Ingredient> column1 = new TableColumn<>("Ingredient");
		column1.setCellValueFactory(new PropertyValueFactory<>("name"));
		column1.setPrefWidth( 300 );

		TableColumn<String, Ingredient> column2 = new TableColumn<>("Unit");
		column2.setCellValueFactory(new PropertyValueFactory<>("unit"));
		column2.setPrefWidth( 300 );

		TableColumn<String, Ingredient> column3 = new TableColumn<>("Quantity");
		column3.setCellValueFactory(new PropertyValueFactory<>("amount"));
		column3.setPrefWidth( 300 );


		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);

		for( int i = 0; i < 20; i++ )
		{
			tableView.getItems().add(new Ingredient("test " + i, "test", "test", 1, "test") );
		}

		scrollPane.setContent( tableView );

		changeQuantityBox = new VBox();
		changeQuantityBox.getChildren().addAll( increaseQuantity, decreaseQuantity, removeItem );
		changeQuantityBox.setSpacing(20);
		changeQuantityBox.setAlignment( Pos.CENTER_LEFT );


		getChildren().addAll( scrollPane, changeQuantityBox );
		
		//set createRecipe button to move to CreateTRecipeView
		//createRecipe.setOnAction(ae -> {
		//	viewController.moveToCreateRecipe();
		//});
		
	}

}
