import java.io.FileInputStream;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.HUD;
import view.RecipeView;

public class RecipeViewTest extends Application {
	private BorderPane theWindow;

    @Override
    public void start( Stage primaryStage ) throws Exception
    {
        primaryStage.setTitle( "Recipe View");
        primaryStage.getIcons().add( new Image( new FileInputStream( "src/main/resources/images/icon.png" ) ) );
        primaryStage.setMinWidth( 1020 );

        // Create main view
        theWindow = new BorderPane();
        Scene theScene = new Scene( theWindow, 1000, 700 );

        // Create views
        HUD theHUD = new HUD(null);
        //StartPage theStartPage = new StartPage();
        RecipeView recipeView = new RecipeView(null);

        // Set alignment
        BorderPane.setAlignment( theHUD, Pos.CENTER );
        BorderPane.setAlignment( recipeView, Pos.CENTER );

        // Create scroll view for page
        ScrollPane mainPageHolder = new ScrollPane();
        mainPageHolder.setContent( recipeView );
        mainPageHolder.setVbarPolicy( ScrollPane.ScrollBarPolicy.ALWAYS );
        mainPageHolder.setFitToWidth( true );
        mainPageHolder.setFitToHeight( true );

        // Set initial view
        theWindow.setTop( theHUD );
        theWindow.setCenter( mainPageHolder );

        theHUD.prefWidthProperty().bind( theScene.widthProperty() );
        theWindow.prefWidthProperty().bind( theScene.widthProperty() );

        primaryStage.setScene( theScene );
        primaryStage.show();
    }


    public static void main( String[] args )
    {
        launch( args );
    }
}
