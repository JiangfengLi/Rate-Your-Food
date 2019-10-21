import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.CreateRecipeView;

public class RecipeFormViewTest extends Application {

	public static void main( String[] args ) {
        launch( args );
    }
	
	private ScrollPane scroll;
    private BorderPane window;
    private CreateRecipeView view;
    

	@Override
	public void start(Stage primaryStage) throws Exception {

		primaryStage.setTitle("Recipe Form Test");
		window = new BorderPane();
		scroll = new ScrollPane();
		view = new CreateRecipeView(null);
		
		window.setCenter(view);
		scroll.setContent(window);
		scroll.setFitToHeight(true);
		scroll.setFitToWidth(true);
		//scroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		Scene scene = new Scene( scroll, 1000, 600 );
        
        primaryStage.setScene(scene);
        primaryStage.show();
	}
}
