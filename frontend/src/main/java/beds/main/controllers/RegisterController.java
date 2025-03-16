package beds.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
			errorLabel.setText("Passwords do not match!");
			return;
		}

		if (Authentication.registerUser(username, password)) {
			errorLabel.setText("Registration successful! Go to login.");
		} else {
			errorLabel.setText("Username already exists.");
		}
	}

	@FXML
	private void toLogin() throws IOException {
		App.setRoot("fxmls/login");
	}
}

