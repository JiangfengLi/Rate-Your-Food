package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.List;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.StrokeType;
import javafx.util.Callback;
import model.Ingredient;
import model.Instruction;
import model.Recipe;
import model.Review;

public class RecipeView extends VBox {

	private Label creator;
	private ImageView imageView;
	private Label recipeName;
	private Label tags;
	private Label summary;
	private StackPane ratingLayout;
	private Circle circle1;
	private Label rating;
	private StackPane difficultyLayout;
	private Circle circle2;
	private Label difficulty;
	private Label ingredientsLabel;
	private TableView<Ingredient> ingredientsList;
	private TableColumn<Ingredient, Float> ingAmount;
	private TableColumn<Ingredient, String> ingUnit;
	private TableColumn<Ingredient, String> ingName;
	private Label instructionsLabel;
	private TableView<Instruction> instructions;
	private TableColumn<Instruction, Integer> insNumber;
	private TableColumn<Instruction, String> insText;
	private Label reviewLabel;
	private Button addReviewButton;
	private Button readReviewButton;
	
	private TableView<ReviewPreview> reviewList;
	private TableColumn<ReviewPreview,String> reviewAuthor;
	private TableColumn<ReviewPreview,String> reviewText;
	private TableColumn<ReviewPreview,Integer> reviewRating;
	private TableColumn<ReviewPreview,Integer> reviewDif;
	private ObservableList<ReviewPreview> reviewObList;
	
	private VBox recipeSection;
	private HBox ingredientTop;
	private HBox ingredientBottom;
	private VBox ingredientSection;
	private VBox instructionSection;
	private VBox ingredientInfo;
	private HBox titleAndRating;
	private HBox tagsAndDif;
	private HBox reviewAndButton;

	private Recipe theRecipe;
	
	private ViewController vc;

	//stub method to call the view without any information in it
	public RecipeView(ViewController vc, Recipe aRecipe) {
		this.vc = vc;
		theRecipe = aRecipe;
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(16,16,16,16));
		this.setSpacing(8);
		inititializeAllNodes();

		reviewObList = FXCollections.observableArrayList();
		
		if( theRecipe == null )
		{
			setCreatorLabel("creator");
			setRecipeLabel("Recipe Name");
			setRating(0.0);
			setDifficulty(0);
		}
		else
		{
			setCreatorLabel(theRecipe.getCreator());
			setRecipeLabel(theRecipe.getRecipeName());
			setRating(theRecipe.getRating());
			setDifficulty(theRecipe.getDifficulty());
		}

		setImage("src/main/resources/images/preview.png");
		setTags();
		setSummary("Summary for all this blah blah blah blah blah blah blah blah blah blah blah");
		setIngredientLabel();
		setIngredientList();
		setInstructionsLabel();
		setInstructions();
		setReviewLabel();
		setAddReviewButton();
		readReviewButton();
		setReviewList();
		
		setNodesToParent();
	}
	
	public void setRecipe(Recipe recipe) {
		creator.setText(recipe.getCreator());
		recipeName.setText(recipe.getRecipeName());
		rating.setText(""+recipe.getRating());
		difficulty.setText(""+recipe.getDifficulty());
		
		
		
	}

	private void setReviewList() {
		/*
		reviewAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		reviewText.setCellValueFactory(new PropertyValueFactory<>("text"));
		reviewRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
		reviewDif.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
		
		reviewList.getColumns().addAll(reviewAuthor,reviewRating,reviewDif,reviewText);
		
		reviewAuthor.prefWidthProperty().bind(reviewList.widthProperty().multiply(3.0 / 10.0));
		reviewRating.prefWidthProperty().bind(reviewList.widthProperty().multiply(1.0 / 10.0));
		reviewDif.prefWidthProperty().bind(reviewList.widthProperty().multiply(1.0 / 10.0));
		reviewText.prefWidthProperty().bind(reviewList.widthProperty().multiply(5.0 / 10.0));
        */

		if(!reviewObList.isEmpty())
				reviewObList.removeAll(reviewObList);
		
		List<Review> reviewData = vc.getAllReviewsForRecipe(theRecipe.getRecipeName(), 
				theRecipe.getCreator());
        if(reviewData!= null && !reviewData.isEmpty()) {
            for ( int i = 0; i < reviewData.size(); i++ )
            {
        	    ReviewPreview newReviewPreview = new ReviewPreview(
        			vc,
        			reviewData.get(i).getRecipeName(),
                    "src/main/resources/images/preview.png",
                    reviewData.get(i).getRecipeCreator(),
                    reviewData.get(i).getRating(), reviewData.get(i).getDifficulty(), "RecipeView");
        	    newReviewPreview.updateAuthor();
        	    reviewObList.add(newReviewPreview);
            }         
        
            reviewList.setItems(reviewObList);
        
		    // Create the columns for the TableView object
            reviewAuthor.setCellValueFactory(new Callback<CellDataFeatures<ReviewPreview, String>, ObservableValue<String>>() {
			    public ObservableValue<String> call(CellDataFeatures<ReviewPreview, String> newReviewPreview) {
			    	return new ReadOnlyObjectWrapper<String>(newReviewPreview.getValue().getRviewClass().getAuthor());
			    }
		    });

            reviewRating.setCellValueFactory(new Callback<CellDataFeatures<ReviewPreview, Integer>, ObservableValue<Integer>>() {
			    public ObservableValue<Integer> call(CellDataFeatures<ReviewPreview, Integer> ReviewPreview) {
			    	return new ReadOnlyObjectWrapper<Integer>(ReviewPreview.getValue().getRviewClass().getRating());
			    }
		    });

            reviewDif.setCellValueFactory(new Callback<CellDataFeatures<ReviewPreview, Integer>, ObservableValue<Integer>>() {
			    public ObservableValue<Integer> call(CellDataFeatures<ReviewPreview, Integer> ReviewPreview) {
				    return new ReadOnlyObjectWrapper<Integer>(ReviewPreview.getValue().getRviewClass().getDifficulty());
			    }
		    });

            reviewText.setCellValueFactory(new Callback<CellDataFeatures<ReviewPreview, String>, ObservableValue<String>>() {
			    public ObservableValue<String> call(CellDataFeatures<ReviewPreview, String> newReviewPreview) {
				    return new ReadOnlyObjectWrapper<String>(newReviewPreview.getValue().getRviewClass().getText());
			    }
		    });
		
        }
		// set the columns for the TableView object
        reviewList.getColumns().setAll(reviewAuthor, reviewRating, reviewDif, reviewText);

		
	}

	/**
	 *  Button to take the users into review view and create new review
	 */
	private void setAddReviewButton() {
		addReviewButton.setText("addReview");
		addReviewButton.setOnAction(ae -> {
			ReviewView reviewView = new ReviewView(vc, theRecipe);
			reviewView.setReturnPoint("RcipeView");		
		    vc.moveToReview(reviewView);
		});
	}

	/**
	 *  Button to take the users into read review view and create new review
	 */
	private void readReviewButton() {
		readReviewButton.setText("Read Review");
		readReviewButton.setOnAction(ae -> {
			ReviewPreview newReviewPreview = reviewList.getSelectionModel().getSelectedItem();
			if (newReviewPreview == null)
				return;			
			ReadReview newReadReview = new ReadReview(vc, newReviewPreview.getRviewClass());
			newReadReview.setReturnPoint("RcipeView");
			vc.moveToReview(newReadReview);
		});
	}	
	

	private void setReviewLabel() {
		reviewLabel.setText("Reviews");
	}

	private void setInstructions() {
		instructions.setPrefWidth(450);
		
		insNumber.setText("#");
		insText.setText("instruction");
		
		//insNumber.setCellValueFactory(new PropertyValueFactory<>("")); // no instruction number set up still
		insText.setCellValueFactory(new PropertyValueFactory<>("text"));
		
		instructions.getColumns().addAll(insNumber, insText);
		
		insNumber.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(1.0 / 10.0));
		insText.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(9.0 / 10.0));
	}

	private void setInstructionsLabel() {
		instructionsLabel.setText("Instructions");
	}

	private void setIngredientList() {

		ingredientsList.setPrefWidth(450);
		ingAmount.setText("Amount");
		ingUnit.setText("Unit");
		ingName.setText("Ingredient");
		
		ingAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		ingUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		ingName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		ingredientsList.getColumns().addAll(ingAmount,ingUnit,ingName);
		ingAmount.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(1.0 / 10.0));
		ingUnit.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(2.0 / 10.0));
		ingName.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(7.0 / 10.0));
		
	}

	private void setIngredientLabel() {
		ingredientsLabel.setText("Ingredients");
	}

	private void setDifficulty(int dif) {
		circle2.setFill(Color.valueOf("#f2ff39"));
		circle2.setRadius(26.0);
		circle2.setStroke(Color.TRANSPARENT);
		circle2.setStrokeType(StrokeType.INSIDE);
		circle2.setStrokeWidth(2.0);

		difficulty.setAlignment(javafx.geometry.Pos.CENTER);
		difficulty.setText("" + dif);
		difficulty.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
	}

	private void setRating(double rate) {

		circle1.setFill(Color.valueOf("#f2ff39"));
		circle1.setRadius(26.0);
		circle1.setStroke(Color.TRANSPARENT);
		circle1.setStrokeType(StrokeType.INSIDE);
		circle1.setStrokeWidth(2.0);

		rating.setAlignment(Pos.CENTER);
		rating.setText(String.format("%.02f", rate));
		rating.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
	}

	private void setSummary(String desc) {
		summary.setText(desc);
		summary.setWrapText(true);
	}

	private void setTags() {
		tags.setText("Tags");
	}

	private void setRecipeLabel(String name) {
		recipeName.setText(name);
	}

	private void setImage(String url) {
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(250);
		try {
			imageView.setImage(new Image(new FileInputStream(url)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setCreatorLabel(String text) {
		HBox temp = new HBox();
		temp.getChildren().add(creator);
		temp.setAlignment(Pos.CENTER_RIGHT);
		creator.setAlignment(Pos.CENTER_RIGHT);
		creator.setPadding(new Insets(0.0, 0.0, 10.0, 0.0));
		creator.setText(text);

	}

	private void inititializeAllNodes() {
		creator = new Label();
		imageView = new ImageView();
		recipeName = new Label();
		tags = new Label();
		summary = new Label();
		ratingLayout = new StackPane();
		circle1 = new Circle();
		rating = new Label();
		difficultyLayout = new StackPane();
		circle2 = new Circle();
		difficulty = new Label();
		ingredientsLabel = new Label();
		ingredientsList = new TableView<Ingredient>();
		ingAmount = new TableColumn<Ingredient, Float>();
		ingUnit = new TableColumn<Ingredient, String>();
		ingName = new TableColumn<Ingredient, String>();
		instructionsLabel = new Label();
		instructions = new TableView<Instruction>();
		insNumber = new TableColumn<Instruction, Integer>();
		insText = new TableColumn<Instruction, String>();
		reviewLabel = new Label();
		addReviewButton = new Button();
		readReviewButton = new Button();
		
		//set up columns for a list of reviews
		reviewList = new TableView<ReviewPreview>();
		reviewAuthor = new TableColumn<ReviewPreview,String>("Author");
		reviewText = new TableColumn<ReviewPreview,String>("Review");
		reviewRating = new TableColumn<ReviewPreview,Integer>("Rating");
		reviewDif = new TableColumn<ReviewPreview,Integer>("Dificulty");
				
		recipeSection = new VBox(8);;
		ingredientTop = new HBox(8);
		ingredientBottom = new HBox(8);
		ingredientInfo = new VBox(8);
		ingredientSection = new VBox(4);
		instructionSection = new VBox(4);
		
		titleAndRating = new HBox(8);
		titleAndRating.setAlignment(Pos.CENTER);
		tagsAndDif = new HBox(8);
		tagsAndDif.setAlignment(Pos.CENTER);
		reviewAndButton = new HBox(8);

	}

	private void setNodesToParent() {
		
		ratingLayout.getChildren().add(circle1);
		ratingLayout.getChildren().add(rating);
		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

		titleAndRating.getChildren().addAll(recipeName, region1, ratingLayout);
		titleAndRating.setAlignment(Pos.CENTER_RIGHT);
		
		difficultyLayout.getChildren().add(circle2);
		difficultyLayout.getChildren().add(difficulty);
		Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        
		tagsAndDif.getChildren().addAll(tags, region2, difficultyLayout);
		
		ingredientInfo.getChildren().addAll(titleAndRating,tagsAndDif,summary);
		ingredientTop.getChildren().addAll(imageView,ingredientInfo);
		
		ingredientSection.getChildren().addAll(ingredientsLabel,ingredientsList);
		instructionSection.getChildren().addAll(instructionsLabel,instructions);
		ingredientBottom.getChildren().addAll(ingredientSection,instructionSection);
		
		GridPane recipeLayout = new GridPane();
		recipeLayout.setAlignment(Pos.CENTER);
		recipeLayout.setHgap(8);
		recipeLayout.setVgap(8);
		//recipeLayout.prefWidthProperty().bind(vc.returnStage().widthProperty());
		recipeLayout.add(imageView, 0, 0);
		recipeLayout.add(ingredientInfo, 1, 0);
		recipeLayout.add(ingredientSection, 0, 1);
		recipeLayout.add(instructionSection, 1, 1);
		
		Region region3 = new Region();
        HBox.setHgrow(region3, Priority.ALWAYS);
		reviewAndButton.getChildren().addAll(reviewLabel, region3, readReviewButton, addReviewButton);
		
		
		HBox creatorLayout = new HBox();
		creatorLayout.getChildren().add(creator);
		creatorLayout.setAlignment(Pos.CENTER_RIGHT);
		
		this.getChildren().addAll(creatorLayout, ingredientTop,recipeLayout, reviewAndButton, reviewList);

	}
}
