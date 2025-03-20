package beds.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import beds.main.App;
import beds.security.Authentication;

public class LoginController {
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;

	@FXML
	public void handleLogin() throws IOException {
		String username = usernameField.getText();
		String password = passwordField.getText();

		if (Authentication.authenticateUser(username, password)) {
			App.setRoot("fxmls/dashboard");
			// Redirect to Dashboard
		} else {
			System.err.println("Invalid credentials");
		}
	}

	@FXML
	public void toRegister() throws IOException {
		App.setRoot("fxmls/register");
	}
}
