package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.CartList;
import model.DBAccess;
import model.Ingredient;

/**
 * MY PAGE
 * provides list view of items that are ready to be purchased.
 * provides common functionality such as input validation and BackButton handling
 * @author Jiangfeng Li, Eric Thomas
 */

public class CartPage extends BorderPane
{
	private ViewController viewController;
	private Button increaseQuantity;
	private Button decreaseQuantity;
	private Button removeItem;

	private DBAccess dbAccess;

	private VBox changeQuantityBox;

	private ObservableList<Ingredient> ingredients;
	private CartList cartList = CartList.getCartList();
	TableView<Ingredient> tableView;


	public CartPage(ViewController viewController) {
		this.viewController = viewController;
        this.dbAccess = new DBAccess();
        ingredients = cartList.getIngredientList();

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
		scrollPane.setFitToHeight( true );
		scrollPane.setFitToWidth( true );
		scrollPane.setHbarPolicy( ScrollPane.ScrollBarPolicy.NEVER );

		tableView = new TableView<>();
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<Ingredient, String> column1 = new TableColumn<>("Ingredient");
		column1.setCellValueFactory(new PropertyValueFactory<>("name"));
		column1.setPrefWidth( 300 );

		TableColumn<Ingredient, String> column2 = new TableColumn<>("Unit");
		column2.setCellValueFactory(new PropertyValueFactory<>("unit"));
		column2.setPrefWidth( 300 );

		TableColumn<Ingredient, String> column3 = new TableColumn<>("Quantity");
		column3.setCellValueFactory(new PropertyValueFactory<>("amount"));
		column3.setPrefWidth( 300 );


		tableView.getColumns().add(column1);
		tableView.getColumns().add(column2);
		tableView.getColumns().add(column3);

		tableView.setItems(ingredients);

		scrollPane.setContent( tableView );

		changeQuantityBox = new VBox();
		changeQuantityBox.getChildren().addAll( increaseQuantity, decreaseQuantity, removeItem );
		changeQuantityBox.setSpacing(20);
		changeQuantityBox.setAlignment( Pos.CENTER_LEFT );

		HBox cartBox = new HBox(  );
		cartBox.setAlignment( Pos.CENTER );
		cartBox.setSpacing( 10 );
		cartBox.getChildren().addAll( scrollPane, changeQuantityBox );

		InnerShadow is = new InnerShadow();
		is.setOffsetX(4.0f);
		is.setOffsetY(4.0f);

		Text header = new Text();
		header.setEffect(is);
		header.setText("Your Cart");
		header.setFill( Color.GOLD);
		header.setFont( Font.font( "DecoType Naskh", FontWeight.BOLD, 100));

		setTop( header );
		setCenter( cartBox );

		setAlignment( header, Pos.CENTER );
		
		increaseQuantity.setOnAction( event -> {
			Ingredient selectedIngredient = tableView.getSelectionModel().getSelectedItem();
			if( selectedIngredient == null )
			{
				return;
			}
			float amount = selectedIngredient.getAmount();
			if( amount == Math.ceil( amount ) )
			{
				selectedIngredient.setAmount( amount + 1 );
			}
			else
			{
				selectedIngredient.setAmount((float) (amount + .1));
			}

			int index = ingredients.indexOf( selectedIngredient );
			ingredients.set( index, selectedIngredient );
		} );

		decreaseQuantity.setOnAction( event -> {
			Ingredient selectedIngredient = tableView.getSelectionModel().getSelectedItem();
			if( selectedIngredient == null )
			{
				return;
			}
			float amount = selectedIngredient.getAmount();
			if( amount == Math.ceil( amount ) )
			{
				selectedIngredient.setAmount( amount - 1 );
			}
			else
			{
				selectedIngredient.setAmount((float) (amount - .1));
			}

			if( selectedIngredient.getAmount() <= 0.0 )
			{
				selectedIngredient = tableView.getSelectionModel().getSelectedItem();
				ingredients.remove( selectedIngredient );
			}
			else
			{
				int index = ingredients.indexOf( selectedIngredient );
				ingredients.set( index, selectedIngredient );
			}
		} );

		removeItem.setOnAction( event -> {
			Ingredient selectedIngredient = tableView.getSelectionModel().getSelectedItem();
			ingredients.remove( selectedIngredient );
		} );

		
	}
//
//	public void build()
//    {
//        ObservableList data = FXCollections.observableArrayList();
//        try{
//            String SQL = "Select * from usermaster Order By UserName";
//            ResultSet rs = con.createStatement().executeQuery(SQL);
//
//            while(rs.next()){
//                Ingredient cm = new Ingredient();
//                cm.userId.set(rs.getInt("UserId"));
//                Image img = new Image("tailoring/UserPhoto/User"+cm.getUserId().toString()+".jpg");
//
//                ImageView mv = new ImageView();
//                mv.setImage(img);
//                mv.setFitWidth(70);
//                mv.setFitHeight(80);
//                cm.userPhoto.set(mv);
//                cm.userName.set(rs.getString("UserName"));
//                cm.userPassword.set(rs.getString("UserPassword"));
//                data.add(cm);
//            }
//            tableview.setItems(data);
//        }
//        catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Error on Building Data");
//        }
//    }

	public void updateCart()
	{
		ingredients = cartList.getIngredientList();
		tableView.setItems( ingredients );
	}

}
