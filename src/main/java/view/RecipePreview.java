package view;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import model.Recipe;

public class RecipePreview extends VBox
{
    Recipe theRecipe;

    public RecipePreview( ViewController vc, String aTitle, String aImagePath, String tags, Recipe aRecipe )
    {
        theRecipe = aRecipe;

        // Create title
        Label theTitle = new Label( aTitle );
        theTitle.setStyle( "-fx-font-size: 12px;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );" );

        // Create image
        ImageView aImage = new ImageView();
        aImage.setFitWidth( 150 );
        aImage.setFitHeight( 150 );

        try
        {
            aImage.setImage( new Image( new FileInputStream( aImagePath ) ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        // Create description
        Label theDescription = new Label( tags );
        theDescription.setStyle( "-fx-font-size: 12px;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );" );
        theDescription.setMaxWidth( 150 );
        theDescription.setWrapText( true );

        // Set up view
        getChildren().addAll( theTitle, aImage, theDescription );
        setAlignment( Pos.CENTER );
        setStyle( "-fx-border-color: #2e8b57;\n" +
                "    -fx-border-width: 2px;\n" +
                "    -fx-padding: 10;\n" +
                "    -fx-background-color: snow;\n" +
                "    -fx-spacing: 8;" );
        
    setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
          //  System.out.println("Mouse clicked!!");
            vc.moveToRecipe( theRecipe );
        }
    });
    
    }
    
    
}