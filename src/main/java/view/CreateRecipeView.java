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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.DBAccess;
//import model.Ingredient;
import model.Ingredient;

public class CreateRecipeView extends GridPane {

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
	private TableView<Ingredient> ingredientTable;
	private TableColumn<Ingredient, Integer> ingAmount;
	private TableColumn<Ingredient, String> ingUnit;
	private TableColumn<Ingredient, String> ingName;
	private Label instructions;
	private TextArea instructionsField;
	private Button submitButton;
	private ImageView image;
	private Button chooseFile;
	private FileChooser fileChooser;

	private ViewController viewController;
	private final String defaultURL = "src/main/resources/images/preview.png";
	private ObservableList<Ingredient> ingredientList;

	public CreateRecipeView(ViewController vc) {

		this.viewController = vc;
		this.setPadding(new Insets(10, 10, 10, 10));

		try {
			initializeAllNodes();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		setNodesToParent();
		setCellColumn();
		setIngredientButtonsHandler();
		setChooseFileButton();
		setSubmitButton();
	}

	private void setCellColumn() {
		ingAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		ingUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		ingName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ingredientTable.setItems(ingredientList);
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
		ingredientAmount.getChildren().addAll(ingredientAmountField, ingredientAmountLabel);
		ingredientUnit.getChildren().addAll(ingredientUnitField, ingredientUnitLabel);
		ingredientName.getChildren().addAll(ingredientNameField, ingredientNameLabel);
		ingredientFields.getChildren().addAll(ingredientAmount, ingredientUnit, ingredientName);
		ingredientButtons = new HBox(5);
		addIngredientButton = new Button("Add");
		deleteIngredientButton = new Button("delete");
		ingredientButtons.getChildren().addAll(addIngredientButton, deleteIngredientButton);
		ingredientTable = new TableView<Ingredient>();
		ingAmount = new TableColumn<Ingredient, Integer>("amount");
		ingUnit = new TableColumn<Ingredient, String>("unit");
		ingName = new TableColumn<Ingredient, String>("item");
		ingAmount.prefWidthProperty().bind(ingredientTable.widthProperty().multiply(1.0 / 10.0));
		ingUnit.prefWidthProperty().bind(ingredientTable.widthProperty().multiply(2.0 / 10.0));
		ingName.prefWidthProperty().bind(ingredientTable.widthProperty().multiply(7.0 / 10.0));

		ingredientTable.getColumns().addAll(ingAmount, ingUnit, ingName);

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
		this.add(image, 2, 0, 1, 4);
		this.add(recipeSummary, 0, 1);
		this.add(recipeSummaryField, 1, 1);
		this.add(tags, 0, 2);
		this.add(tagsField, 1, 2);
		this.add(ingredientTable, 0, 3, 2, 2);
		this.add(ingredientFields, 1, 5);
		this.add(ingredientButtons, 0, 5);
		this.add(chooseFile, 2, 5);
		this.add(instructions, 0, 6);
		this.add(instructionsField, 1, 6);
		this.add(submitButton, 0, 7, 2, 1);
		// this.setPrefWidth(this.widthProperty().doubleValue());
	}

	private void setIngredientButtonsHandler() {

		addIngredientButton.setOnAction(ae -> {

			String amount = ingredientAmountField.getText();
			int amountInt = Integer.parseInt(amount);
			String unit = ingredientUnitField.getText();
			String name = ingredientNameField.getText();
			String user = "";
			if (viewController != null) {
				user = viewController.getCurrentUser().getEmail();
				//System.out.println(user);
			}
			else
				user = null;

			Ingredient ingredient = new Ingredient(name, null, user, amountInt, unit);
			if (!amount.isEmpty() && !name.isEmpty()) {
				ingredientList.add(ingredient);
				System.out.println("added ingredient " + ingredientList.size());
				// Ingredient tempIng = new Ingredient()
				// ingredientsList.getItems().add();
			}

		});

		deleteIngredientButton.setOnAction(ae -> {

			final int size = ingredientTable.getItems().size();

			if (size > 0) {
				final int selectedIdx = ingredientTable.getSelectionModel().getSelectedIndex();
				if (selectedIdx != -1) {

					final int newSelectedIdx = (selectedIdx == ingredientTable.getItems().size() - 1) ? selectedIdx - 1
							: selectedIdx;

					ingredientTable.getItems().remove(selectedIdx);
					ingredientTable.getSelectionModel().select(newSelectedIdx);
				} else {
					final int lastItem = size - 1;
					ingredientTable.getItems().remove(lastItem);
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

		submitButton.setOnAction(ae -> {
			
			String name = recipeNameField.getText();
			String summary = recipeSummaryField.getText();
			ObservableList<Ingredient> ingredients = ingredientTable.getItems();
			String instructions = instructionsField.getText();
			String user = viewController.getCurrentUser().getEmail();

			if (!name.isEmpty() && !summary.isEmpty() && !ingredients.isEmpty() && !instructions.isEmpty()) {
				
				//viewController.addRecipe(name, user);
				viewController.addInstruction(name, user, instructions);

				for (int i = 0; i < ingredients.size(); i++) {
					
					Ingredient ingredient = ingredientList.get(i);
					ingredient.setRecipeName(name);
					String ingName = ingredient.getName();
					String ingRecipeName = ingredient.getRecipeName();
					String ingRecipeCreator = ingredient.getRecipeCreator();
					float ingAmount = ingredient.getAmount();
					String ingUnit = ingredient.getUnit();

					viewController.addIngredient(ingName, ingRecipeName, ingRecipeCreator, ingAmount, ingUnit);
				}

				viewController.moveToMyPage();
			}
		});
	}
}
