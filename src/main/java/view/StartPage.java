package view;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.DBAccess;
import model.Recipe;

public class StartPage extends VBox
{
	
	private ViewController viewController;
    private DBAccess dbAccess;
    private TextField theSearchArea;
    private ImageView theSearchIcon;

    public StartPage( ViewController vc)
    {
    	
    	this.viewController = vc;
    	this.dbAccess = new DBAccess();

        theSearchArea = viewController.getHud().getSearchArea();
        theSearchIcon = viewController.getHud().getSearchIcon();

        VBox theFeaturedRecipes = makeFeaturedRecipes();
        VBox theAllRecipes = makeAllRecipes();

        EventHandler searchHandler = event ->
        {
            getChildren().clear();
            getChildren().addAll( makeSearchRecipes() );
            setAlignment( Pos.TOP_CENTER );
        };

        theSearchIcon.setOnMousePressed( searchHandler );
        theSearchArea.setOnAction( searchHandler );

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
    
    /**
     * MAKE FEATURED RECIPES
     * @return
     */
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

        List<Recipe> recipeData = dbAccess.getAllRecipes();

        Random rand = new Random(  );
        ArrayList<Integer> pickedRecipes = new ArrayList<>(  );

        for ( int i = 1; i < 6; i++ )
        {
            int aRecipeIndex = rand.nextInt( recipeData.size() );
            while( pickedRecipes.contains( aRecipeIndex ) )
            {
                aRecipeIndex = rand.nextInt( recipeData.size() );
            }
            pickedRecipes.add( aRecipeIndex );

            // get recipe data
            Recipe rec = recipeData.get(aRecipeIndex);
            String imgPath = viewController.getMainImageForRecipe(rec);
            
            // add preview
            aFeaturedRecipeHolder.getChildren().add( new RecipePreview(
            		viewController,
                    rec.getRecipeName(),
                    imgPath,
                    dbAccess.findRecipeTags(recipeData.get( aRecipeIndex ).getRecipeName()),
                    recipeData.get( aRecipeIndex )) );
        }

        aPane.setPadding( new Insets( 20, 0, 10, 0 ) );
        aPane.setAlignment( Pos.CENTER );
        aPane.setSpacing( 25 );
        aPane.getChildren().addAll( aHeader, aFeaturedRecipeHolder );

        return aPane;
    }

    /**
     * MAKE ALL RECIPES
     * @return
     */
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

        List<Recipe> recipeData = dbAccess.getAllRecipes();

        for( Recipe aRecipe : recipeData )
        {
            allRecipes.getChildren().add(
                    new RecipePreview(
                            viewController,
                            aRecipe.getRecipeName(),
                            viewController.getMainImageForRecipe(aRecipe),
                            dbAccess.findRecipeTags(aRecipe.getRecipeName()),
                            aRecipe ) );
        }

        aPane.setPadding( new Insets( 20, 0, 10, 0 ) );
        aPane.setAlignment( Pos.CENTER );
        aPane.setSpacing( 25 );
        aPane.getChildren().addAll( aHeader, allRecipes );

        return aPane;
    }

    /**
     * MAKE SEARCH RECIPES
     * @return
     */
    private VBox makeSearchRecipes()
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

        List<Recipe> recipeData = dbAccess.searchRecipes( theSearchArea.getText() );

        for( Recipe aRecipe : recipeData )
        {
            allRecipes.getChildren().add(
                    new RecipePreview(
                            viewController,
                            aRecipe.getRecipeName(),
                            viewController.getMainImageForRecipe(aRecipe),
                            dbAccess.findRecipeTags(aRecipe.getRecipeName()),
                            aRecipe ) );
        }

        aPane.setPadding( new Insets( 20, 0, 10, 0 ) );
        aPane.setAlignment( Pos.CENTER );
        aPane.setSpacing( 25 );
        aPane.getChildren().addAll( aHeader, allRecipes );

        return aPane;
    }
}
