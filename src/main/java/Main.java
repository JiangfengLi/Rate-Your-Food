import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewController;

public class Main extends Application {
	
	private ViewController viewController;

    /**
     * MAIN Jiangfeng Branch sprint2
     * @param args
     */
    public static void main( String[] args ) {
        launch( args );
    }
    
    /**
     * START
     * what to run on startup - post log-in screen
     */
    @Override
    public void start( Stage primaryStage ) throws Exception {
        viewController = new ViewController();
        viewController.onLaunch(primaryStage);
    }
}