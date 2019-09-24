package view;

/**
 * For this app to run, you need to execute the program in
 * 
 *     BuildADataBaseWith1Table3Records.java
 * 
 * This GUI is a spike, which is the simplest possible program to explore
 * potential solutions. It is used to determine how much work will be 
 * required to solve or work around a software issue. In this case it is
 * the issue of creating a database with encrypted passwords and showing
 * all three records in a ListView.
 * 
 * This class does not have a future in your team project.
 * 
 * @author Rick Mercer
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
  
public class Spike1View extends Application {

  public static void main(String[] args) {
    launch(args);
  }
 
  private AccountView selector;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
     
    BorderPane sprint1 = new BorderPane();
    sprint1.setTop(new Heading());
    selector = new AccountView();  
    sprint1.setCenter(selector);
    
    // Add a BorderPane to the Scene
    Scene scene = new Scene(sprint1, 1400, 800);
    primaryStage.setScene(scene);
    // Don't forget to show the running application:
    primaryStage.show();
  }
}