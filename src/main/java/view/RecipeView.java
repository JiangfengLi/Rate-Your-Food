package view;

import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.geometry.*;
import javafx.scene.control.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.layout.*;

public class RecipeView extends AnchorPane {
	
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
	private TableView ingredientsList;
	private TableColumn ingAmount;
	private TableColumn ingName;
	private Label instructionsLabel;
	private TextArea instructions;
	private Label reviewLabel;
	private Button addReviewButton;
	private ListView reviewList;

	//stub method to call the view without any information in it
	public RecipeView() {

		intitializeAllNodes();

		setMaxHeight(USE_PREF_SIZE);
		setMaxWidth(USE_PREF_SIZE);
		setMinHeight(USE_PREF_SIZE);
		setMinWidth(USE_PREF_SIZE);
		setPrefHeight(1000.0);
		setPrefWidth(700.0);

		setCreatorLabel("creator");

		setImage("src/main/resources/images/preview.png");

		setRecipeLabel("Recipe Name");

		setTags();

		setSummary("Summary for all this blah blah blah  blah blah blah blah blah blah blah blah");

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
		AnchorPane.setBottomAnchor(reviewList, 77.0);
		AnchorPane.setLeftAnchor(reviewList, 65.0);
		AnchorPane.setRightAnchor(reviewList, 42.0);
		reviewList.setLayoutX(65.0);
		reviewList.setLayoutY(679.0);
		reviewList.setPrefHeight(244.0);
		reviewList.setPrefWidth(593.0);
	}

	private void setAddReviewButton() {
		AnchorPane.setRightAnchor(addReviewButton, 52.0);
		AnchorPane.setTopAnchor(addReviewButton, 636.0);
		addReviewButton.setLayoutX(570.0);
		addReviewButton.setLayoutY(630.0);
		addReviewButton.setMnemonicParsing(false);
		addReviewButton.setText("addReview");
	}

	private void setReviewLabel() {
		AnchorPane.setLeftAnchor(reviewLabel, 73.0);
		AnchorPane.setTopAnchor(reviewLabel, 636.0);
		reviewLabel.setLayoutX(73.0);
		reviewLabel.setLayoutY(636.0);
		reviewLabel.setPrefHeight(27.0);
		reviewLabel.setPrefWidth(183.0);
		reviewLabel.setText("Reviews");
	}

	private void setInstructions() {
		AnchorPane.setRightAnchor(instructions, 39.0);
		AnchorPane.setTopAnchor(instructions, 376.0);
		instructions.setLayoutX(350.0);
		instructions.setLayoutY(376.0);
		instructions.setPrefHeight(236.0);
		instructions.setPrefWidth(311.0);
	}

	private void setInstructionsLabel() {
		AnchorPane.setTopAnchor(instructionsLabel, 339.0);
		instructionsLabel.setLayoutX(350.0);
		instructionsLabel.setLayoutY(339.0);
		instructionsLabel.setPrefHeight(27.0);
		instructionsLabel.setPrefWidth(311.0);
		instructionsLabel.setText("Instructions");
	}

	private void setIngredientList() {
		AnchorPane.setLeftAnchor(ingredientsList, 73.0);
		AnchorPane.setTopAnchor(ingredientsList, 376.0);
		ingredientsList.setLayoutX(73.0);
		ingredientsList.setLayoutY(376.0);
		ingredientsList.setPrefHeight(226.0);
		ingredientsList.setPrefWidth(256.0);

		ingAmount.setPrefWidth(50.0);
		ingAmount.setText("amount");

		ingName.setPrefWidth(205.0);
		ingName.setText("ingredient");
	}

	private void setIngredientLabel() {
		AnchorPane.setLeftAnchor(ingredientsLabel, 73.0);
		AnchorPane.setTopAnchor(ingredientsLabel, 339.0);
		ingredientsLabel.setLayoutX(73.0);
		ingredientsLabel.setLayoutY(339.0);
		ingredientsLabel.setPrefHeight(27.0);
		ingredientsLabel.setPrefWidth(256.0);
		ingredientsLabel.setText("Ingredients");
	}

	private void setDifficulty(int dif) {
		AnchorPane.setRightAnchor(difficultyLayout, 52.0);
		AnchorPane.setTopAnchor(difficultyLayout, 121.0);
		difficultyLayout.setLayoutX(596.0);
		difficultyLayout.setLayoutY(121.0);
		difficultyLayout.setPrefHeight(50.0);
		difficultyLayout.setPrefWidth(50.0);

		circle2.setFill(javafx.scene.paint.Color.valueOf("#f2ff39"));
		circle2.setRadius(26.0);
		circle2.setStroke(javafx.scene.paint.Color.TRANSPARENT);
		circle2.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
		circle2.setStrokeWidth(2.0);

		difficulty.setAlignment(javafx.geometry.Pos.CENTER);
		difficulty.setText("" + dif);
		difficulty.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
	}

	private void setRating(double rate) {
		AnchorPane.setRightAnchor(ratingLayout, 52.0);
		AnchorPane.setTopAnchor(ratingLayout, 53.0);
		ratingLayout.setLayoutX(596.0);
		ratingLayout.setLayoutY(53.0);
		ratingLayout.setPrefHeight(50.0);
		ratingLayout.setPrefWidth(50.0);

		circle1.setFill(javafx.scene.paint.Color.valueOf("#f2ff39"));
		circle1.setRadius(26.0);
		circle1.setStroke(javafx.scene.paint.Color.TRANSPARENT);
		circle1.setStrokeType(javafx.scene.shape.StrokeType.INSIDE);
		circle1.setStrokeWidth(2.0);

		rating.setAlignment(javafx.geometry.Pos.CENTER);
		rating.setText(String.format("%.02f", rate));
		rating.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
	}

	private void setSummary(String desc) {
		AnchorPane.setRightAnchor(summary, 39.0);
		AnchorPane.setTopAnchor(summary, 186.0);
		summary.setAlignment(javafx.geometry.Pos.TOP_LEFT);
		summary.setLayoutX(350.0);
		summary.setLayoutY(186.0);
		summary.setPrefHeight(136.0);
		summary.setPrefWidth(311.0);
		summary.setText(desc);
		summary.setWrapText(true);
	}

	private void setTags() {
		AnchorPane.setRightAnchor(tags, 167.0);
		AnchorPane.setTopAnchor(tags, 91.0);
		tags.setLayoutX(350.0);
		tags.setLayoutY(91.0);
		tags.setPrefHeight(63.0);
		tags.setPrefWidth(183.0);
		tags.setText("Tags");
	}

	private void setRecipeLabel(String name) {
		AnchorPane.setTopAnchor(recipeName, 53.0);
		recipeName.setLayoutX(350.0);
		recipeName.setLayoutY(53.0);
		recipeName.setPrefHeight(27.0);
		recipeName.setPrefWidth(183.0);
		recipeName.setText(name);
	}

	private void setImage(String url) {
		AnchorPane.setLeftAnchor(imageView, 73.0);
		AnchorPane.setTopAnchor(imageView, 51.0);
		imageView.setFitHeight(270.0);
		imageView.setFitWidth(267.0);
		imageView.setLayoutX(73.0);
		imageView.setLayoutY(51.0);
		imageView.setPickOnBounds(true);
		imageView.setPreserveRatio(true);
		try {
			imageView.setImage(new Image(new FileInputStream(url)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void setCreatorLabel(String text) {
		AnchorPane.setRightAnchor(creator, 14.0);
		AnchorPane.setTopAnchor(creator, 14.0);
		creator.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
		creator.setLayoutX(286.0);
		creator.setLayoutY(14.0);
		creator.setPrefHeight(37.0);
		creator.setPrefWidth(355.0);
		creator.setPadding(new Insets(0.0, 0.0, 10.0, 0.0));
		creator.setText(text);

	}

	private void intitializeAllNodes() {
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
		ingredientsList = new TableView();
		ingAmount = new TableColumn();
		ingName = new TableColumn();
		instructionsLabel = new Label();
		instructions = new TextArea();
		reviewLabel = new Label();
		addReviewButton = new Button();
		reviewList = new ListView();

	}

	private void setNodesToParent() {

		getChildren().add(creator);
		getChildren().add(imageView);
		getChildren().add(recipeName);
		getChildren().add(tags);
		getChildren().add(summary);
		ratingLayout.getChildren().add(circle1);
		ratingLayout.getChildren().add(rating);
		getChildren().add(ratingLayout);
		difficultyLayout.getChildren().add(circle2);
		difficultyLayout.getChildren().add(difficulty);
		getChildren().add(difficultyLayout);
		getChildren().add(ingredientsLabel);
		ingredientsList.getColumns().add(ingAmount);
		ingredientsList.getColumns().add(ingName);
		getChildren().add(ingredientsList);
		getChildren().add(instructionsLabel);
		getChildren().add(instructions);
		getChildren().add(reviewLabel);
		getChildren().add(addReviewButton);
		getChildren().add(reviewList);
	}
}
