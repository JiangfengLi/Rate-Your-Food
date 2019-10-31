package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
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
import javafx.util.Callback;
//import model.Ingredient;
import model.Ingredient;
import model.Instruction;

public class CreateRecipeView extends GridPane {

	private Label message;
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
	private Label ingredientLabel;
	private TableView<Ingredient> ingredientTable;
	private TableColumn<Ingredient, Integer> ingAmount;
	private TableColumn<Ingredient, String> ingUnit;
	private TableColumn<Ingredient, String> ingName;
	private Label instructions;
	private TableView<TempInstruction> instructionsTable;
	private TableColumn<TempInstruction, Integer> insStep;
	private TableColumn<TempInstruction, String> insString;
	private HBox instructionButtons;
	private Button addInstructionButton;
	private Button deleteInstructionButton;
	private TextField instructionField;
	private Button submitButton;
	private ImageView image;
	private Button chooseFile;
	private FileChooser fileChooser;

	private ViewController viewController;
	private final String defaultURL = "src/main/resources/images/preview.png";
	private ObservableList<Ingredient> ingredientList;
	private ObservableList<TempInstruction> instructionlist;


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
		setInstructionButtonsHandler();
		setChooseFileButton();
		setSubmitButton();
	}

	private void setCellColumn() {
		ingAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		ingUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		ingName.setCellValueFactory(new PropertyValueFactory<>("name"));
		ingredientTable.setItems(ingredientList);
		
		insStep.setCellValueFactory(new PropertyValueFactory<>("index"));
		insString.setCellValueFactory(new PropertyValueFactory<>("str"));
		instructionsTable.setItems(instructionlist);
				

	}

	private void initializeAllNodes() throws FileNotFoundException {

		ingredientList = FXCollections.observableArrayList();
		instructionlist = FXCollections.observableArrayList();

		message = new Label();
		recipeName = new Label("Recipe Name*");
		recipeNameField = new TextField();
		recipeSummary = new Label("Summary");
		tags = new Label("tags by space*");
		tagsField = new TextField();
		image = new ImageView();
		image.setImage(new Image(new FileInputStream(defaultURL)));
		image.setFitWidth(400);
		image.setFitHeight(500);
		recipeSummaryField = new TextField();
		ingredientLabel = new Label("Ingredients");
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

		instructions = new Label("Instructions*");
		instructionsTable = new TableView<TempInstruction>();
		insStep = new TableColumn<TempInstruction, Integer>("#");
		insString = new TableColumn<TempInstruction, String>("instruction");
		insStep.prefWidthProperty().bind(instructionsTable.widthProperty().multiply(0.9 / 10.0));
		insString.prefWidthProperty().bind(instructionsTable.widthProperty().multiply(9.0 / 10.0));
		instructionsTable.getColumns().addAll(insStep, insString);
		addInstructionButton = new Button("Add");
		deleteInstructionButton = new Button("Delete");
		instructionButtons = new HBox(8);
		instructionButtons.getChildren().addAll(addInstructionButton, deleteInstructionButton);
		instructionField = new TextField("Instruction steps");
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
		this.add(image, 2, 1, 1, 5);
		this.add(recipeSummary, 0, 1);
		this.add(recipeSummaryField, 1, 1);
		this.add(tags, 0, 2);
		this.add(tagsField, 1, 2);
		this.add(ingredientLabel,0,3);
		this.add(ingredientTable, 0, 4, 2, 2);
		this.add(ingredientFields, 1, 6);
		this.add(ingredientButtons, 0, 6);
		this.add(chooseFile, 2, 8);
		this.add(instructions, 0, 7);
		this.add(instructionsTable, 0, 8, 2, 2);
		this.add(instructionButtons,0,10);
		this.add(instructionField,1,10);
		this.add(submitButton, 0, 11);
		this.add(message, 1,12);
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
	
	private void setInstructionButtonsHandler() {

		addInstructionButton.setOnAction(ae -> {

			String instructionString = instructionField.getText();
			if (!instructionString.isEmpty()) {
					
				TempInstruction instruction = new TempInstruction(instructionlist.size()+1,instructionString);
				instructionlist.add(instruction);
			}

		});

		deleteInstructionButton.setOnAction(ae -> {

			final int size = instructionlist.size();

			if (size > 0) {
				
				final int selectedIdx = instructionsTable.getSelectionModel().getSelectedIndex();
				if (selectedIdx != -1) {

					final int newSelectedIdx = (selectedIdx == instructionlist.size() - 1) ? selectedIdx - 1
							: selectedIdx;

					for (int i = selectedIdx+1; i < size; i++) {
						instructionlist.get(i).setIndex(i);
					}
					instructionlist.remove(selectedIdx);
					instructionsTable.getSelectionModel().select(newSelectedIdx);
					
				} else {
					final int lastItem = size - 1;
					instructionlist.remove(lastItem);
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
			
			String user = viewController.getCurrentUser().getEmail();
			String name = recipeNameField.getText();
			//String summary = recipeSummaryField.getText();
			String[] tags = tagsField.getText().split(" ");
			ObservableList<Ingredient> ingredients = ingredientTable.getItems();
			ObservableList<TempInstruction> instructions = instructionsTable.getItems();

			if (!name.isEmpty() && !ingredients.isEmpty() && !instructions.isEmpty() && tags.length > 0) {
				
				//viewController.addRecipe(name, user);
				
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
