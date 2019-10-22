package view;

import javafx.scene.shape.*;
import model.Ingredient;
import model.Instruction;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

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
	private TableView<Review> reviewList;
	private TableColumn<Review,String> reviewAuthor;
	private TableColumn<Review,String> reviewText;
	private TableColumn<Review,Integer> reviewRating;
	private TableColumn<Review,Integer> reviewDif;
	
	private VBox recipeSection;
	private HBox ingredientTop;
	private HBox ingredientBottom;
	private VBox ingredientSection;
	private VBox instructionSection;
	private VBox ingredientInfo;
	private HBox titleAndRating;
	private HBox tagsAndDif;
	private HBox reviewAndButton;	
	
	private ViewController vc;
	

	//stub method to call the view without any information in it
	public RecipeView(ViewController vc) {
		
		this.vc = vc;
		
		this.setAlignment(Pos.CENTER);
		this.setPadding(new Insets(16,16,16,16));
		this.setSpacing(8);
		inititializeAllNodes();
		
		setCreatorLabel("creator");
		setImage("src/main/resources/images/preview.png");
		setRecipeLabel("Recipe Name");
		setTags();
		setSummary("Summary for all this blah blah blah blah blah blah blah blah blah blah blah");
		setRating(0.0);
		setDifficulty(0);
		setIngredientLabel();
		setIngredientList();
		setInstructionsLabel();
		setInstructions();
		setReviewLabel();
		setAddReviewButton();
		setReviewList();
		
		setNodesToParent();

	}

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

		
	}

	private void setAddReviewButton() {
		addReviewButton.setText("addReview");
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
		reviewList = new TableView<Review>();
		reviewAuthor = new TableColumn<Review,String>("Author");
		reviewText = new TableColumn<Review,String>("Review");
		reviewRating = new TableColumn<Review,Integer>("Rating");
		reviewDif = new TableColumn<Review,Integer>("Dificulty");
		
		
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
		reviewAndButton.getChildren().addAll(reviewLabel, region3, addReviewButton);
		
		
		HBox creatorLayout = new HBox();
		creatorLayout.getChildren().add(creator);
		creatorLayout.setAlignment(Pos.CENTER_RIGHT);
		
		this.getChildren().addAll(creatorLayout, ingredientTop,recipeLayout, reviewAndButton, reviewList);

	}
}
