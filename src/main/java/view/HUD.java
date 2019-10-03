package view;

import java.io.FileInputStream;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class HUD extends FlowPane
{
	private ViewController viewController;

    public HUD(ViewController viewController ) {
    	this.viewController = viewController;

        setOrientation( Orientation.HORIZONTAL );
        setPrefWidth( 1000 );
        setPrefHeight( 75 );
        setHgap( 10 );
        setStyle( "-fx-background-color: linear-gradient(to bottom, #0087CB, #000000);" );
        setAlignment( Pos.CENTER );
        ImageView searchIcon = null;
        try {
        	searchIcon = new ImageView( new Image( new FileInputStream( "src/main/resources/images/search.png" ) ) );
        } catch (Exception x) {
        	System.err.println("Error: unable to find search icon image.");
        }
        if (searchIcon != null) {
            searchIcon.setFitHeight( 50 );
            searchIcon.setFitWidth( 50 );
            searchIcon.setSmooth( true );
            searchIcon.setCache( true );
        }

        FlowPane searchPane = new FlowPane( searchIcon, makeSearchArea() );
        searchPane.setOrientation( Orientation.HORIZONTAL );
        searchPane.setAlignment(Pos.CENTER);

        // Create menu
        ImageView menuIcon = null;
        try {
            menuIcon = new ImageView( new Image( new FileInputStream( "src/main/resources/images/menu_icon.png" ) ) );
        } catch (Exception x) {
            System.err.println("Error: Unable to find menu icon image.");
        }
        if ( menuIcon != null ) {
            menuIcon.setFitHeight( 50 );
            menuIcon.setFitWidth( 25 );
            menuIcon.setSmooth( true );
            menuIcon.setCache( true );
        }

        Menu theMenu = new Menu();
        theMenu.setStyle("-fx-background-color: transparent");
        theMenu.setGraphic(menuIcon);

        // Create menu items
        MenuItem accountItem = new MenuItem("Account");
        MenuItem logoutItem = new MenuItem("Sign Out");

        // add menu items to menu
        theMenu.getItems().add(accountItem);
        theMenu.getItems().add(logoutItem);

        // Add event
        accountItem.setOnAction(null); //TODO: Link to account page
        logoutItem.setOnAction( new SignOutHandler() );

        // Create a menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.setBackground(Background.EMPTY);
        menuBar.getMenus().add(theMenu);

        // Create holder for menu
        VBox menuBox = new VBox(menuBar);
        menuBox.setMaxWidth(25);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setBackground(Background.EMPTY);

        getChildren().addAll( makeHomeBtn(), searchPane, makeAccountBtn(), makeCartBtn(), menuBox );

        setAlignment( Pos.CENTER );
    }

    private Button makeHomeBtn( )
    {
        Button homeBtn = new Button();
        homeBtn.setText( "Home" );
        homeBtn.setPrefSize( 100, 50 );
        homeBtn.setOnAction( new HomePageHandler() );
        setButtonStyle( homeBtn );
        return homeBtn;
    }

    private TextField makeSearchArea( )
    {
        TextField searchArea = new TextField();
        searchArea.setPromptText( "Search for recipes" );
        searchArea.setPrefSize( 345, 50 );
        return searchArea;
    }

    private Button makeAccountBtn( )
    {
        Button accountBtn = new Button();
        accountBtn.setText( "My Page" );
        accountBtn.setPrefSize( 150, 50 );
        accountBtn.setOnAction( new MyPageHandler() );
        setButtonStyle( accountBtn );
        return accountBtn;
    }

    private Button makeCartBtn( )
    {
        Button cartBtn = new Button();
        cartBtn.setText( "Cart" );
        cartBtn.setPrefSize( 150, 50 );
        setButtonStyle( cartBtn );
        return cartBtn;
    }

    private void setButtonStyle( Button aButton )
    {
        String style = "-fx-background-color: cornflowerblue;";
        style += "-fx-font-size: 12pt;";
        style += "-fx-border-style: solid;";
        style += "-fx-border-width: 2px;";
        style += "-fx-border-color: darkblue;";
        style += "-fx-text-fill: snow;";
        aButton.setStyle( style );
    }
 
	// CREATE ACCOUNT HANDLER
	private class SignOutHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.logOut();
			viewController.moveToLogInRoot();
		}
	}

	// CREATE HOMEPAGE HANDLER
	private class HomePageHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToHomePage();;
		}
	}

	
	// CREATE MYPAGE HANDLER
	private class MyPageHandler implements EventHandler<ActionEvent> {
		public void handle(ActionEvent e) {
			viewController.moveToMyPage();
		}
	}

}