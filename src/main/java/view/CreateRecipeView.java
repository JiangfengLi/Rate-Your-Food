package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
//import model.Ingredient;
import model.Ingredient;

public class CreateRecipeView extends GridPane {

	private final String defaultURL = "src/main/resources/images/preview.png";
	private ObservableList<Ingredient> ingredientList;
	
	private Label recipeName;
	private TextField recipeNameField;
	private Label recipeSummary;
	private Label tags;
	private TextField tagsField;
	private TextField recipeSummaryField;
	private HBox ingredientFields;
	private VBox ingredientAmount;
	private VBox ingredientUnit;
	private VBox ingredientName;
	private TextField ingredientAmountField;
	private TextField ingredientUnitField;
	private TextField ingredientNameField;
	private Label ingredientAmountLabel;
	private Label ingredientUnitLabel;
	private Label ingredientNameLabel;
	private HBox ingredientButtons;
	private Button addIngredientButton;
	private Button deleteIngredientButton;
	private TableView<Ingredient> ingredientsList;
	private TableColumn<Ingredient,Integer> ingAmount;
	private TableColumn<Ingredient,String> ingUnit;
	private TableColumn<Ingredient,String> ingName;
	private Label instructions;
	private TextArea instructionsField;
	private Button submitButton;
	private ImageView image;
	private Button chooseFile;
	private FileChooser fileChooser;

	public CreateRecipeView() {
		
		this.setPadding(new Insets(10,10,10,10));

		try {
			initializeAllNodes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setNodesToParent();
		setIngredientButtonsHandler();
		setChooseFileButton();
		setSubmitButton();
	}

	private void initializeAllNodes() throws FileNotFoundException {
		
		ingredientList = FXCollections.observableArrayList();

		recipeName = new Label("Recipe Name");
		recipeNameField = new TextField();
		recipeSummary = new Label("Summary");
		tags = new Label("tags by space");
		tagsField = new TextField();
		image = new ImageView();
		image.setImage(new Image(new FileInputStream(defaultURL)));
		image.setFitWidth(400);
		image.setFitHeight(500);
		recipeSummaryField = new TextField();
		ingredientFields = new HBox(2);
		ingredientAmount = new VBox(2);
		ingredientUnit = new VBox(2);
		ingredientName = new VBox(2);
		ingredientAmountField = new TextField("2");
		ingredientUnitField = new TextField("tbsp");
		ingredientNameField = new TextField("sugar");
		ingredientAmountLabel = new Label("Amount");
		ingredientUnitLabel = new Label("Unit");
		ingredientNameLabel = new Label("Name");	
		ingredientAmount.getChildren().addAll(ingredientAmountField,ingredientAmountLabel);
		ingredientUnit.getChildren().addAll(ingredientUnitField,ingredientUnitLabel);
		ingredientName.getChildren().addAll(ingredientNameField,ingredientNameLabel);
		ingredientFields.getChildren().addAll(ingredientAmount,ingredientUnit,ingredientName);
		ingredientButtons = new HBox(5);
		addIngredientButton = new Button("Add");
		deleteIngredientButton = new Button("delete");
		ingredientButtons.getChildren().addAll(addIngredientButton, deleteIngredientButton);
		ingredientsList = new TableView<Ingredient>();
		ingAmount = new TableColumn<Ingredient,Integer>("amount");
		ingUnit = new TableColumn<Ingredient,String>("unit");
		ingName = new TableColumn<Ingredient,String>("item");
		ingAmount.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(1.0/10.0));
		ingUnit.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(2.0/10.0));
		ingName.prefWidthProperty().bind(ingredientsList.widthProperty().multiply(7.0/10.0));

		ingredientsList.getColumns().addAll(ingAmount,ingUnit,ingName);
		
		instructions = new Label("Instructions");
		instructionsField = new TextArea();
		submitButton = new Button("Submit");
		chooseFile = new Button("choose file");
		fileChooser = new FileChooser();
	}

	private void setNodesToParent() {

		double gap = 10;
		this.setHgap(gap);
		this.setVgap(gap);
		this.add(recipeName, 0, 0);
		this.add(recipeNameField, 1, 0);
		this.add(image,2,0,1,4);
		this.add(recipeSummary, 0, 1);
		this.add(recipeSummaryField, 1, 1);
		this.add(tags,0,2);
		this.add(tagsField,1,2);
		this.add(ingredientsList, 0, 3, 2, 2);
		this.add(ingredientFields, 1, 5);
		this.add(ingredientButtons, 0, 5);
		this.add(chooseFile,2,5);
		this.add(instructions, 0, 6);
		this.add(instructionsField, 1, 6);
		this.add(submitButton, 0, 7, 2, 1);
	}

	private void setIngredientButtonsHandler() {

	
		addIngredientButton.setOnAction(ae -> {
			
			String amount = ingredientAmountField.getText();
			int amountInt = Integer.parseInt(ingredientAmountField.getText());
			String unit = ingredientUnitField.getText();
			String name = ingredientNameField.getText();
			
			Ingredient ingredient = new Ingredient(name,null,null,amountInt,unit);
			if (!amount.isEmpty() && name.isEmpty()) {
				ingredientList.add(ingredient);
				//Ingredient tempIng = new Ingredient()
				//ingredientsList.getItems().add();
			}

		});

		deleteIngredientButton.setOnAction(ae -> {

			final int size = ingredientsList.getItems().size();

			if (size > 0) {
				final int selectedIdx = ingredientsList.getSelectionModel().getSelectedIndex();
				if (selectedIdx != -1) {

					final int newSelectedIdx = 
							(selectedIdx == ingredientsList.getItems().size() - 1) 
							? selectedIdx - 1
							: selectedIdx;

					ingredientsList.getItems().remove(selectedIdx);
					ingredientsList.getSelectionModel().select(newSelectedIdx);
				} else {
					final int lastItem = size - 1;
					ingredientsList.getItems().remove(lastItem);
				}
			}

		});
	}
	
	private void setChooseFileButton() {
		chooseFile.setOnAction(ae -> {
			
			fileChooser.setTitle("Choose Image");
			File file = fileChooser.showOpenDialog(this.getScene().getWindow());
			
			image.setImage(new Image(file.toURI().toString()));
			
			
		});
	}
	
	private void setSubmitButton() {
		
		String name = recipeNameField.getText();
		String summary = recipeSummaryField.getText();
		ObservableList<Ingredient> ingredients = ingredientsList.getItems();
		String instructions = instructionsField.getText();
		
		submitButton.setOnAction(ae -> {

			if (
			!name.isEmpty() &&
			!summary.isEmpty() &&
			!ingredients.isEmpty() &&
			!instructions.isEmpty()
			
			) {
				
				//TODO
				
			}
				
				
		});
	}
	
}
