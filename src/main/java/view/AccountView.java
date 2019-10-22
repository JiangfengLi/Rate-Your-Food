package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.User;

public class AccountView extends GridPane {
	
	private Label titlePage;
	private Label name;
	private Label nameLabel;
	private Label email;
	private Label emailLabel;
	private Label password;
	private Label passwordLabel;
	private Button changePassword;
	ViewController viewController;
	private User user;
	
	public AccountView(ViewController vc) {
		this.viewController = vc;
		this.setAlignment(Pos.CENTER);
		this.setHgap(10);
		this.setVgap(12);
		initializeNodes();
		setSize();
		setChildrenToParent();
		setChangePassword();
	}
	
	private void setSize() {
		titlePage.setStyle("-fx-font: 32 arial;");
		name.setStyle("-fx-font: 24 arial;");
		nameLabel.setStyle("-fx-font: 24 arial;");
		email.setStyle("-fx-font: 24 arial;");
		emailLabel.setStyle("-fx-font: 24 arial;");
		password.setStyle("-fx-font: 24 arial;");
		passwordLabel.setStyle("-fx-font: 24 arial;");
		
	}
	
	private void initializeNodes() {
		titlePage = new Label("Account");
		titlePage.setPadding(new Insets(0,0,20,0));
		nameLabel = new Label("Full name: ");
		name = new Label();
		emailLabel = new Label("Email: ");
		email = new Label();
		passwordLabel = new Label("Password: ");
		password = new Label("*******");
		changePassword = new Button("Change Password");
	}
	
	private void setChildrenToParent() {
		this.add(titlePage, 0, 0, 2, 1);
		this.add(nameLabel, 0, 1);
		this.add(name, 1, 1);
		this.add(emailLabel, 0, 2);
		this.add(email, 1, 2);
		this.add(passwordLabel, 0, 3);
		this.add(password, 1, 3);
		//this.add(changePassword, 2, 3);
	}
	
	private void setChangePassword() {
		
		changePassword.setOnMouseClicked(ae -> {
			changePassword.setText("WIP");
		});
	}
	
	public void setUser(User user) {
		this.user = user;
		name.setText(this.user.getFullName());
		email.setText(this.user.getEmail());
		
		
	}
}
