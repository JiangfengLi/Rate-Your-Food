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
import model.Review;

public class ReviewPreview extends VBox{
	private Label authorLabel;
	private ViewController vc;
	private String returnSite;
	private Review input_review;
	
    public ReviewPreview( ViewController vc2, String recipeName, String aImagePath, String recipeCreator,
    		Integer rating, Integer difficulty, String reSite){
    	this.vc = vc2;
    	this.returnSite = reSite;
    	
        input_review = vc.getReview(vc.getCurrentUser().getEmail(), recipeName, recipeCreator);

    	
        // Create title
        Label theTitle = new Label( recipeName );
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

        // Create author, recipe creator, rating and difficulties label
        authorLabel = new Label( "Author: ");
        Label recipeCreatorLabel = new Label( "Creator: " + recipeCreator );
        Label evaluationLabel = new Label( "Rating: " + rating + "    Difficulty: " + difficulty );
        
        /*theDescription.setStyle( "-fx-font-size: 12px;\n" +
                "    -fx-text-fill: #333333;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );" );
        theDescription.setMaxWidth( 150 );
        theDescription.setWrapText( true ); */

        // Set up view
        getChildren().addAll( theTitle, aImage, authorLabel, recipeCreatorLabel, evaluationLabel );
        setAlignment( Pos.CENTER );
        setStyle( "-fx-border-color: #2e8b57;\n" +
                "    -fx-border-width: 2px;\n" +
                "    -fx-padding: 10;\n" +
                "    -fx-background-color: snow;\n" +
                "    -fx-spacing: 8;" );
        
    setOnMouseClicked(new EventHandler<MouseEvent>() {
        public void handle(MouseEvent me) {
            System.out.println("Mouse clicked review!! to MyPage");
            if(input_review == null)
            	System.out.println("review is null !!!!");
            else {
                EditReview editReviewView = new EditReview(vc, input_review);
                editReviewView.setReturnPoint(returnSite);
                vc.moveToReview(editReviewView);
            }
        }
    });
    
    }
    
    public Review getRviewClass() {
    	return input_review;
    }
    
    public void updateAuthor() {
        if(vc.getCurrentUser() != null)
            authorLabel.setText( "Author: " + vc.getCurrentUser().getEmail() );
    }
    

}
