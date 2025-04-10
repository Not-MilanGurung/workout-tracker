package beds.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;

import beds.main.App;
import beds.security.Authentication;

public class RegisterController {
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private PasswordField confirmPasswordField;
	@FXML private Label errorLabel;

	@FXML 
	private void handleRegister() {
		String username = usernameField.getText();
		String password = passwordField.getText();
		String confirmPassword = confirmPasswordField.getText();

		if (!password.equals(confirmPassword)) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Registeration Failed");
			alert.setHeaderText("Passwords do not match");
			alert.setContentText("Please ensure both password fields match.");
			alert.showAndWait();
			return;
		}

		if (Authentication.registerUser(username, password)) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Registeration Successful");
			alert.setHeaderText("Account created");
			alert.setContentText("You can now log in with your credentials.");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Registeration Failed");
			alert.setHeaderText("Username already exists");
			alert.setContentText("Please choose a different username.");
			alert.showAndWait();
		}
	}

	@FXML
	private void toLogin() throws IOException {
		App.setRoot("fxmls/login");
	}
}

