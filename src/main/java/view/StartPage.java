package view;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class StartPage extends VBox
{
    public StartPage( )
    {
        VBox theFeaturedRecipes = makeFeaturedRecipes();
        VBox theAllRecipes = makeAllRecipes();

        setAlignment( Pos.CENTER );
        getChildren().addAll( theFeaturedRecipes, theAllRecipes );
        try
        {
            Image background = new Image( new FileInputStream( "src/main/resources/images/wallpaper.jpeg" ) );
            BackgroundSize aSize = new BackgroundSize( 1920, 700, true, true, true, true );
            setBackground( new Background( new BackgroundImage( background, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, aSize ) ) );
        } catch ( IOException e )
        {
            e.printStackTrace();
        }
    }

    private VBox makeFeaturedRecipes( )
    {
        VBox aPane = new VBox();
        Label aHeader = new Label( "Featured Recipes" );
        aHeader.setStyle( "-fx-font-size: 30px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #000000;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: true;" );

        FlowPane aFeaturedRecipeHolder = new FlowPane();
        aFeaturedRecipeHolder.setOrientation( Orientation.HORIZONTAL );
        aFeaturedRecipeHolder.setHgap( 5 );
        aFeaturedRecipeHolder.setVgap( 5 );
        aFeaturedRecipeHolder.setAlignment( Pos.CENTER );

        for ( int i = 0; i < 5; i++ )
        {
            aFeaturedRecipeHolder.getChildren().add( new RecipePreview(
                    "This is a title",
                    "src/main/resources/images/preview.png",
                    "A pretty fairly long description to test how long a description can be without looking weird." ) );
        }

        aPane.setPadding( new Insets( 20, 0, 10, 0 ) );
        aPane.setAlignment( Pos.CENTER );
        aPane.setSpacing( 25 );
        aPane.getChildren().addAll( aHeader, aFeaturedRecipeHolder );

        return aPane;
    }

    private VBox makeAllRecipes( )
    {
        VBox aPane = new VBox();
        Label aHeader = new Label( "Recipes" );
        aHeader.setStyle( "-fx-font-size: 30px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #000000;\n" +
                "    -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );\n" +
                "    -fx-underline: true;" );

        FlowPane allRecipes = new FlowPane();
        allRecipes.setOrientation( Orientation.HORIZONTAL );
        allRecipes.setHgap( 5 );
        allRecipes.setVgap( 5 );
        allRecipes.setAlignment( Pos.CENTER );

        for ( int i = 0; i < 20; i++ )
        {
            allRecipes.getChildren().add(
                    new RecipePreview(
                            "This is a title",
                            "src/main/resources/images/preview.png",
                            "A pretty fairly long description to test how long a description can be without looking weird." ) );
        }

        aPane.setPadding( new Insets( 20, 0, 10, 0 ) );
        aPane.setAlignment( Pos.CENTER );
        aPane.setSpacing( 25 );
        aPane.getChildren().addAll( aHeader, allRecipes );

        return aPane;
    }
}
