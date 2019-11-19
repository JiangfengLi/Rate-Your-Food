package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import model.DBAccess;
import model.Ingredient;
import model.Instruction;
import model.Recipe;

public class CreateRecipeView extends GridPane {

	private Label message;
	private Label recipeName;
	private Label tags;
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
	private TableColumn<Ingredient, Integer> ingAmount;
	private TableColumn<Ingredient, String> ingUnit;
	private TableColumn<Ingredient, String> ingName;
	private Label instructions;
	private TableColumn<TempInstruction, Integer> insStep;
	private TableColumn<TempInstruction, String> insString;
	private HBox instructionButtons;
	private Button addInstructionButton;
	private Button deleteInstructionButton;
	private TextField instructionField;
	protected ImageView image;
	private Button chooseFile;
	private FileChooser fileChooser;
	private File file;

	private DBAccess database;
	protected Button submitButton;
	protected final String defaultURL = "src/main/resources/images/preview.png";
	//protected ObservableList<Ingredient> ingredientList;
	//protected ObservableList<TempInstruction> tempInstructionlist;
	
	protected TextField tagsField;
	protected TextField recipeNameField;	
	protected TableView<Ingredient> ingredientTable;
	protected TableView<TempInstruction> instructionsTable;
	protected ObservableList<Instruction> instructionList; 

	protected ViewController viewController;



	public CreateRecipeView(ViewController vc) {

		constructorInitializer(vc);
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
	
	private void constructorInitializer(ViewController vc) {
		database = new DBAccess();
		this.viewController = vc;
		this.setPadding(new Insets(10, 10, 10, 10));
	
	}
	
	@SuppressWarnings("unchecked")
	private void initializeAllNodes() throws FileNotFoundException {

		message = new Label();
		recipeName = new Label("Recipe Name*");
		recipeNameField = new TextField();
		tags = new Label("tags by space*");
		tagsField = new TextField();
		image = new ImageView();
		image.setImage(new Image(new FileInputStream(defaultURL)));
		image.setFitWidth(400);
		image.setFitHeight(500);
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
		ingredientTable.getColumns().addAll(ingAmount, ingUnit, ingName);

		ingAmount.prefWidthProperty().bind(ingredientTable.widthProperty().multiply(0.1));
		ingUnit.prefWidthProperty().bind(ingredientTable.widthProperty().multiply(0.2));
		ingName.prefWidthProperty().bind(ingredientTable.widthProperty().multiply(0.69));
		ingAmount.setResizable(false);
        ingName.setResizable(false);
        ingUnit.setResizable(false);


		instructions = new Label("Instructions*");
		instructionsTable = new TableView<TempInstruction>();
		insStep = new TableColumn<TempInstruction, Integer>("#");
		insString = new TableColumn<TempInstruction, String>("instruction");
		instructionsTable.getColumns().addAll(insStep, insString);
		insStep.prefWidthProperty().bind(instructionsTable.widthProperty().multiply(0.1));
		insString.prefWidthProperty().bind(instructionsTable.widthProperty().multiply(0.89));
		
		addInstructionButton = new Button("Add");
		deleteInstructionButton = new Button("Delete");
		instructionButtons = new HBox(8);
		instructionButtons.getChildren().addAll(addInstructionButton, deleteInstructionButton);
		instructionField = new TextField("Instruction steps");
		submitButton = new Button("Submit");
		chooseFile = new Button("choose file");
		fileChooser = new FileChooser();
	}

	private void setCellColumn() {
		ingAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
		ingUnit.setCellValueFactory(new PropertyValueFactory<>("unit"));
		ingName.setCellValueFactory(new PropertyValueFactory<>("name"));
		//ingredientTable.setItems(ingredientList);
		
		insStep.setCellValueFactory(new PropertyValueFactory<>("index"));
		insString.setCellValueFactory(new PropertyValueFactory<>("str"));
		//instructionsTable.setItems(tempInstructionlist);			
	}

	private void setNodesToParent() {

		double gap = 10;
		this.setHgap(gap);
		this.setVgap(gap);
		this.prefWidthProperty().bind(viewController.returnStage().widthProperty());
		//ColumnConstraints col = new ColumnConstraints();
		//col.setHgrow(Priority.NEVER);
		//col.setFillWidth(false);
		//col.setPrefWidth(400);
		//col.prefWidthProperty().bind(col.prefWidthProperty());
	    this.getColumnConstraints().addAll( new ColumnConstraints( 100 ));
	    this.setAlignment(Pos.TOP_CENTER);


		
		this.add(recipeName, 0, 0);
		this.add(recipeNameField, 1, 0);
		this.add(image, 2, 1, 1, 7);
		this.add(tags, 0, 1);
		this.add(tagsField, 1, 1);
		this.add(ingredientLabel,0,2);
		this.add(ingredientTable, 0, 3, 2, 2);
		this.add(ingredientFields, 1, 5);
		this.add(ingredientButtons, 0, 5);
		this.add(chooseFile, 2, 8);
		this.add(instructions, 0, 6);
		this.add(instructionsTable, 0, 7, 2, 2);
		this.add(instructionButtons,0,9);
		this.add(instructionField,1,9);
		this.add(submitButton, 0, 10);
		this.add(message, 1,11);
		


	}

	private void setIngredientButtonsHandler() {

		addIngredientButton.setOnAction(ae -> {
			
			String 	name 		= ingredientNameField.getText();
			String 	user 		= "";
			String 	amount 		= ingredientAmountField.getText();
			int 	amountInt 	= Integer.parseInt(amount);
			String 	unit 		= ingredientUnitField.getText();
			
			if (viewController != null)
				user = viewController.getCurrentUser().getEmail();
			else
				user = null;
			
			if (amount.isEmpty() || name.isEmpty()) {
				return; // exit method
			}
			
			ObservableList<Ingredient> ingredientList = ingredientTable.getItems();
			for (Ingredient ing : ingredientList) {

				if	(ing.getName().equals(name)) {
					System.out.println(name+" already exists");
					message.setText("no repeating Ingredients");
					return; //exit method
				}
			}
			
			Ingredient ingredient = new Ingredient(name, "", user, amountInt, unit);

			ingredientList.add(ingredient);

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
			
			if (instructionString.isEmpty())
				return; // fail
			
			ObservableList<TempInstruction> tempInstructionlist = instructionsTable.getItems();
			for (TempInstruction ins : tempInstructionlist) {
				if	(ins.getStr().equals(instructionString)) {
					message.setText("No repeating Instructions");
					return; //exit method
				}
			}
								
			TempInstruction instruction = new TempInstruction(tempInstructionlist.size()+1,instructionString);
			tempInstructionlist.add(instruction);
		});

		deleteInstructionButton.setOnAction(ae -> {

			ObservableList<TempInstruction> tempInstructionlist = instructionsTable.getItems();
			final int size = tempInstructionlist.size();

			if (size > 0) {
				
				final int selectedIdx = instructionsTable.getSelectionModel().getSelectedIndex();
				if (selectedIdx != -1) {

					final int newSelectedIdx = (selectedIdx == tempInstructionlist.size() - 1) ? selectedIdx - 1
							: selectedIdx;

					for (int i = selectedIdx+1; i < size; i++) {
						tempInstructionlist.get(i).setIndex(i);
					}
					tempInstructionlist.remove(selectedIdx);
					instructionsTable.getSelectionModel().select(newSelectedIdx);
					
				} else {
					final int lastItem = size - 1;
					tempInstructionlist.remove(lastItem);
				}
			}
		});
	}

	private void setChooseFileButton() {
		fileChooser.setTitle("Choose Image");
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
		
        chooseFile.setOnAction(ae -> {
			file = fileChooser.showOpenDialog(this.getScene().getWindow());
			if (file != null)
				image.setImage(new Image(file.toURI().toString()));
		});
	}

	protected void setSubmitButton() {
		submitButton.setOnAction(ae -> {
			
			String user = viewController.getCurrentUser().getEmail();
			String name = recipeNameField.getText();
			//String summary = recipeSummaryField.getText();
			String tags = tagsField.getText();
			ObservableList<Ingredient> ingredients = ingredientTable.getItems();
			ObservableList<TempInstruction> instructions = instructionsTable.getItems();

			if (name.isEmpty() || ingredients.isEmpty() || instructions.isEmpty() || tags.isEmpty()) {
				message.setText("Missing info for required field(s), check for red marks");
				
				String redMark = "-fx-border-color: red ; -fx-border-width: 2px ;";
				String noMark = "";
				
				if (name.isEmpty())
					recipeNameField.setStyle(redMark);
				else 
					recipeNameField.setStyle(noMark);
				
				if (tags.isEmpty())
					tagsField.setStyle(redMark);
				else
					tagsField.setStyle(noMark);
				
				if (ingredients.isEmpty())
					ingredientTable.setStyle(redMark);
				else
					ingredientTable.setStyle(noMark);
				
				if (instructions.isEmpty())
					instructionsTable.setStyle(redMark);
				else
					instructionsTable.setStyle(noMark);
				
				return;

			}
			
			List<Recipe> userRecipes = database.getAllRecipesForUser(user);
			for (Recipe recipe : userRecipes) {
				if (recipe.getRecipeName().equals(name)) {
					message.setText(name+" already exits, please choose another name for recipe");
					return;
				}
			}
			
			viewController.addRecipe(name, user, 1, 1);
			
			for (String tag : tags.split("\\W+")) {
				database.addTag(tag, name, user);
			}
			
			for (Ingredient ingredient : ingredientTable.getItems()) {
				
				ingredient.setRecipeName(name);
				String ingName 			= ingredient.getName();
				String ingRecipeName 	= ingredient.getRecipeName();
				String ingRecipeCreator = ingredient.getRecipeCreator();
				float  ingAmount 		= ingredient.getAmount();
				String ingUnit 			= ingredient.getUnit();

				viewController.addIngredient(ingName, ingRecipeName, ingRecipeCreator, ingAmount, ingUnit);
			}
			
			ObservableList<TempInstruction> tempInstructionlist = instructionsTable.getItems();
			for (TempInstruction instruction : tempInstructionlist) {
				database.addInstruction(name, user, instruction.getStr());
			}
			
			/* 
			 * make the image file be uploaded into the database here
			 */
			viewController.addImageForRecipe(file.toURI().toString(), name, user);

			viewController.moveToMyPage();
			
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
} // CreaterecipeView class
