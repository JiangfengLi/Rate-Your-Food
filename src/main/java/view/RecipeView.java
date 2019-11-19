package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;
import model.*;

public class RecipeView extends VBox {

	private Label creator;
	private Button editRecipeButton;
	private ImageView imageView;
	private Label recipeName;
	private Label tagLabel;
	private Label summary;
	private StackPane ratingLayout;
	private Circle circle1;
	private Label rating;
	private StackPane difficultyLayout;
	private Circle circle2;
	private Label difficulty;
	private Label ingredientsLabel;
	private TableView<Ingredient> ingredientsTable;
	private TableColumn<Ingredient, Float> ingAmount;
	private TableColumn<Ingredient, String> ingUnit;
	private TableColumn<Ingredient, String> ingName;
	private Label instructionsLabel;
	private TableView<TempInstruction> instructions;
	private TableColumn<TempInstruction, Integer> insNumber;
	private TableColumn<TempInstruction, String> insText;
	private Label reviewLabel;
	private Button addReviewButton;
	private Button readReviewButton;
	private Button addToCartBtn;
	
	private TableView<Review> reviewList;
	private TableColumn<Review,String> reviewAuthor;
	private TableColumn<Review,String> reviewText;
	private TableColumn<Review,Integer> reviewRating;
	private TableColumn<Review,Integer> reviewDif;
	
	private HBox userRow;
	private HBox ingredientTop;
	private VBox ingredientSection;
	private VBox instructionSection;
	private VBox ingredientInfo;
	private HBox titleAndRating;
	private HBox saveToCart;
	private HBox tagsAndDif;
	private HBox reviewAndButton;

	private Recipe theRecipe;
	
	private ViewController vc;

	private CartList cartList = CartList.getCartList();

	//stub method to call the view without any information in it
	public RecipeView(ViewController vc, Recipe aRecipe) {
		this.vc = vc;
		theRecipe = aRecipe;
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(16,16,16,16));
		this.setSpacing(8);
		inititializeAllNodes();
		
		if( theRecipe == null )
		{
			setCreatorLabel("creator");
			setRecipeLabel("Recipe Name");
			setRating(0.0);
			setDifficulty(0);
		}
		else
		{
			String recipeName = theRecipe.getRecipeName();
			String recipeCreator = theRecipe.getCreator();
			
			List<Tag> recipeTags = this.vc.getAllTagsForRecipe(recipeName, recipeCreator);
			List<Ingredient> ingredientsList = this.vc.getAllIngredientsForRecipe(recipeName, recipeCreator);
			List<Instruction> instructionsList = this.vc.getAllInstructionsForRecipe(recipeName, recipeCreator);
			List<TempInstruction> tempInstructionsList = new LinkedList<TempInstruction>();
			for (int i = 0; i < instructionsList.size(); i++) {
				String insText =  instructionsList.get(i).getText();
				TempInstruction tempIns = new TempInstruction(i+1,insText);
				tempInstructionsList.add(tempIns);
			}
			List<Review> reviewList = this.vc.getAllReviewsForRecipe(recipeName, recipeCreator);
			
			ObservableList<Ingredient> ingredientsObsList  = FXCollections.observableArrayList(ingredientsList);
			ObservableList<TempInstruction> instructionsObsList = FXCollections.observableArrayList(tempInstructionsList);
			ObservableList<Review> reviewObsList = FXCollections.observableArrayList(reviewList);

			setCreatorLabel(theRecipe.getCreator());
			setRecipeLabel(theRecipe.getRecipeName());
			setRating(theRecipe.getRating());
			setDifficulty(theRecipe.getDifficulty());
			setTags(recipeTags);
			ingredientsTable.setItems(ingredientsObsList);
			setIngredientTable();
			setInstructions();
			instructions.setItems(instructionsObsList);;
			setReviewList();
			setEditRecipeButton();
			this.reviewList.setItems(reviewObsList);
			
			
		}

		setImage();
		setIngredientLabel();
		setInstructionsLabel();
		setReviewLabel();
		setAddReviewButton();
		readReviewButton();
		addToCartButton();
		setNodesToParent();
	}
	
	public void setRecipe(Recipe recipe) {
		creator.setText(recipe.getCreator());
		recipeName.setText(recipe.getRecipeName());
		rating.setText(""+recipe.getRating());
		difficulty.setText(""+recipe.getDifficulty());
		
		
		
	}

	@SuppressWarnings("unchecked")
	private void setReviewList() {
		
		reviewAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
		reviewText.setCellValueFactory(new PropertyValueFactory<>("text"));
		reviewRating.setCellValueFactory(new PropertyValueFactory<>("rating"));
		reviewDif.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
		
		reviewList.getColumns().addAll(reviewAuthor,reviewRating,reviewDif,reviewText);
		
		reviewAuthor.prefWidthProperty().bind(reviewList.widthProperty().multiply(3.0 / 10.0));
		reviewRating.prefWidthProperty().bind(reviewList.widthProperty().multiply(1.0 / 10.0));
		reviewDif.prefWidthProperty().bind(reviewList.widthProperty().multiply(1.0 / 10.0));
		reviewText.prefWidthProperty().bind(reviewList.widthProperty().multiply(5.0 / 10.0));
		
		List<Review> reviewData = vc.getAllReviewsForRecipe(theRecipe.getRecipeName(), 
				theRecipe.getCreator());
				
		if(reviewData!= null && !reviewData.isEmpty())
		     reviewList.getItems().addAll(reviewData);		
				
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
			Review newReview = reviewList.getSelectionModel().getSelectedItem();
			if (newReview == null)
				return;					
			
			if(newReview.getAuthor().equals(vc.getCurrentUser().getEmail())) {
				EditReview newReviewSubClass = new EditReview(vc, newReview);
				newReviewSubClass.setReturnPoint("RcipeView");
				vc.moveToReview(newReviewSubClass);
			} else {
				ReadReview newReviewSubClass = new ReadReview(vc, newReview);
				newReviewSubClass.setReturnPoint("RcipeView");
				vc.moveToReview(newReviewSubClass);
			}

		});
	}

	private void addToCartButton() {
		addToCartBtn.setText("Add To Cart");
		addToCartBtn.setOnAction(ae -> {
			String recipeName = theRecipe.getRecipeName();
			String recipeCreator = theRecipe.getCreator();
			List<Ingredient> ingredientsList = this.vc.getAllIngredientsForRecipe(recipeName, recipeCreator);
			cartList.addBulkIngredients( ingredientsList );
		});
	}

	private void setReviewLabel() {
		reviewLabel.setText("Reviews");
	}

	@SuppressWarnings("unchecked")
	private void setInstructions() {
		instructions.prefWidthProperty().bind(vc.returnStage().widthProperty());
		//instructions.setPrefWidth(450);
		
		//instructions.setFixedCellSize(20);
	    //instructions.prefHeightProperty().bind(instructions.fixedCellSizeProperty().multiply(
	    //		Bindings.size(instructions.getItems()).add(1.01)));
	    //instructions.minHeightProperty().bind(instructions.prefHeightProperty());
	    //instructions.maxHeightProperty().bind(instructions.prefHeightProperty());
		
		insNumber.setText("#");
		insText.setText("instruction");
		
		//insNumber.setCellValueFactory(new PropertyValueFactory<>("")); // no instruction number set up still
		insText.setCellValueFactory(new PropertyValueFactory<>("str"));
		insNumber.setCellValueFactory(new PropertyValueFactory<>("index"));
		
		instructions.getColumns().addAll(insNumber, insText);
		
		insNumber.prefWidthProperty().bind(ingredientsTable.widthProperty().multiply(1.0 / 10.0));
		insText.prefWidthProperty().bind(ingredientsTable.widthProperty().multiply(9.0 / 10.0));
	}
	
	

	private void setInstructionsLabel() {
		instructionsLabel.setText("Instructions");
	}

	@SuppressWarnings("unchecked")
	private void setIngredientTable() {

		//ingredientsTable.setPrefWidth(450);
		ingredientsTable.prefWidthProperty().bind(vc.returnStage().widthProperty());

		ingAmount.setText("Amount");
		ingUnit.setText("Unit");
		ingName.setText("Ingredient");
		
		ingAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		ingUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		ingName.setCellValueFactory(new PropertyValueFactory<>("name"));
		
		ingredientsTable.getColumns().addAll(ingAmount,ingUnit,ingName);
		ingAmount.prefWidthProperty().bind(ingredientsTable.widthProperty().multiply(1.0 / 10.0));
		ingUnit.prefWidthProperty().bind(ingredientsTable.widthProperty().multiply(2.0 / 10.0));
		ingName.prefWidthProperty().bind(ingredientsTable.widthProperty().multiply(7.0 / 10.0));
		
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

	/*
	private void setSummary(String desc) {
		summary.setText(desc);
		summary.setWrapText(true);
	}
	*/
	private void setTags(List<Tag> tags) {
		if (tags == null || tags.size() == 0 ) {
			tagLabel.setText("No tags in this recipe");
		} else {
			
			//String result = "";
			StringBuffer result = new StringBuffer();;
			for (int i = 0; i < tags.size(); i++) {
				if (i > 0)
					result.append(", ");
				result.append(tags.get(i).getName());
			}
			tagLabel.setText(result.toString());
		}
		
		
	}

	private void setRecipeLabel(String name) {
		recipeName.setText(name);
		recipeName.setStyle("-fx-font: 24 arial;");
	}

	/**
	 * SET IMAGE
	 * sets image from db if possible, or a default if necessary
	 * @param url
	 */
	private void setImage() {
		// grab 1st image associated with recipe if exists
		String imgPath = vc.getMainImageForRecipe(theRecipe);
		
		imageView.setPreserveRatio(true);
		imageView.setFitHeight(250);
		try {
			imageView.setImage(new Image(new FileInputStream(imgPath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setCreatorLabel(String text) {
		
		creator.setAlignment(Pos.CENTER_RIGHT);
		creator.setPadding(new Insets(0.0, 0.0, 10.0, 0.0));
		creator.setText(text);
		creator.setFont(Font.font(
                "Serif",
                FontPosture.ITALIC,
                Font.getDefault().getSize()));

	}

	private void inititializeAllNodes() {

		creator = new Label();
		editRecipeButton = new Button("Edit recipe");
		imageView = new ImageView();
		recipeName = new Label();
		tagLabel = new Label();
		//tagLabel = new TextFlow();
		summary = new Label();
		ratingLayout = new StackPane();
		circle1 = new Circle();
		rating = new Label();
		difficultyLayout = new StackPane();
		circle2 = new Circle();
		difficulty = new Label();
		ingredientsLabel = new Label();
		ingredientsTable = new TableView<Ingredient>();
		ingAmount = new TableColumn<Ingredient, Float>();
		ingUnit = new TableColumn<Ingredient, String>();
		ingName = new TableColumn<Ingredient, String>();
		instructionsLabel = new Label();
		instructions = new TableView<TempInstruction>();
		insNumber = new TableColumn<TempInstruction, Integer>();
		insText = new TableColumn<TempInstruction, String>();
		reviewLabel = new Label();
		addReviewButton = new Button();
		readReviewButton = new Button();
		addToCartBtn = new Button();
		
		//set up columns for a list of reviews
		reviewList 	 = new TableView<Review>();
		reviewAuthor = new TableColumn<Review, String>("Author");
		reviewText 	 = new TableColumn<Review, String>("Review");
		reviewRating = new TableColumn<Review, Integer>("Rating");
		reviewDif 	 = new TableColumn<Review, Integer>("Dificulty");
				
		userRow 			= new HBox(8);
		ingredientTop 		= new HBox(8);
		ingredientInfo 		= new VBox(8);
		ingredientSection 	= new VBox(4);
		instructionSection 	= new VBox(4);
		
		titleAndRating = new HBox(8);
		titleAndRating.setAlignment(Pos.CENTER);
		saveToCart = new HBox(8);
		saveToCart.setAlignment(Pos.CENTER);
		tagsAndDif = new HBox(8);
		tagsAndDif.setAlignment(Pos.CENTER);
		reviewAndButton = new HBox(8);

	}
	
	private void setEditRecipeButton() {
		editRecipeButton.setOnAction(e -> {
	
			vc.moveToEditRecipe(theRecipe);
	});}

	private void setNodesToParent() {
		
		userRow.setAlignment(Pos.CENTER_RIGHT);
		
		if (vc.getCurrentUser().getEmail().equals(theRecipe.getCreator()))
			userRow.getChildren().addAll(editRecipeButton,creator);
		else
			userRow.getChildren().add(creator);
		
		ratingLayout.getChildren().add(circle1);
		ratingLayout.getChildren().add(rating);
		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

		titleAndRating.getChildren().addAll(recipeName, region1, ratingLayout);
		titleAndRating.setAlignment(Pos.CENTER_RIGHT);

		saveToCart.getChildren().addAll(addToCartBtn);
		saveToCart.setAlignment(Pos.CENTER_RIGHT);
		
		difficultyLayout.getChildren().add(circle2);
		difficultyLayout.getChildren().add(difficulty);
		Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        
		tagsAndDif.getChildren().addAll(tagLabel, region2, difficultyLayout);
		
		ingredientInfo.getChildren().addAll(titleAndRating,tagsAndDif, saveToCart, summary);
		ingredientTop.getChildren().addAll(imageView,ingredientInfo);
		
		ingredientSection.getChildren().addAll(ingredientsLabel,ingredientsTable);
		instructionSection.getChildren().addAll(instructionsLabel,instructions);
		//ingredientBottom.getChildren().addAll(ingredientSection,instructionSection);
		
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
		
		this.getChildren().addAll(userRow, ingredientTop,recipeLayout, reviewAndButton, reviewList);

	}
	
	public class TempInstruction {
		
		private int index;
		private String str;
		
		public TempInstruction(int index, String str) {
			this.index = index;
			this.str = str;
		}			
			
		public void setIndex(int index) {this.index = index;}
		public int getIndex() {return index;}
		public String getStr() {return str;}
	}
}
