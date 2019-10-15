package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
//import model.Ingredient;

public class CreateRecipeView extends GridPane {

	private final String url = "src/main/resources/images/preview.png";
	
	private Label recipeName;
	private TextField recipeNameField;
	private Label recipeSummary;
	private TextField recipeSummaryField;
	private TextField ingredientField;
	private HBox ingredientButtons;
	private Button addIngredientButton;
	private Button deleteIngredientButton;
	private ListView<String> ingredientsList;
	// private TableColumn<Ingredient,String> ingAmount;
	// private TableColumn<Ingredient,String> ingName;
	private Label instructions;
	private TextArea instructionsField;
	private Button submitButton;
	private ImageView image;
	private Button chooseFile;
	private FileChooser fileChooser;

	public CreateRecipeView() {

		try {
			initializeAllNodes();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setNodesToParent();
		setIngredientButtonsHandler();
		setChooseFileButton();
	}

	private void initializeAllNodes() throws FileNotFoundException {

		recipeName = new Label("Recipe Name");
		recipeNameField = new TextField();
		recipeSummary = new Label("Summary");
		image = new ImageView();
		image.setImage(new Image(new FileInputStream(url)));
		recipeSummaryField = new TextField();
		ingredientField = new TextField("1 tomatoe");
		ingredientButtons = new HBox(5);
		addIngredientButton = new Button("Add");
		deleteIngredientButton = new Button("delete");
		ingredientButtons.getChildren().addAll(addIngredientButton, deleteIngredientButton);
		ingredientsList = new ListView<String>();
		// ingAmount = new TableColumn<Ingredient,String>("amount");
		// ingName = new TableColumn<Ingredient,String>("item");
		// ingredientsList.getColumns().addAll(ingAmount,ingName);
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
		this.add(image,2,0,1,3);
		this.add(recipeSummary, 0, 1);
		this.add(recipeSummaryField, 1, 1);
		this.add(ingredientsList, 0, 2, 2, 2);
		this.add(ingredientField, 1, 4);
		this.add(ingredientButtons, 0, 4);
		this.add(chooseFile,2,4);
		this.add(instructions, 0, 5);
		this.add(instructionsField, 1, 5);
		this.add(submitButton, 0, 6, 2, 1);
	}

	private void setIngredientButtonsHandler() {

		addIngredientButton.setOnAction(ae -> {
			String field = ingredientField.getText();
			if (!field.isEmpty())
				ingredientsList.getItems().add(field);

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
	
}
