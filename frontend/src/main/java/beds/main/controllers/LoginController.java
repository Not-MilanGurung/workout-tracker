package beds.main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import java.io.IOException;

import beds.main.App;
import beds.security.Authentication;

public class LoginController {
	@FXML private Label loginTitleLable;
	@FXML private Label usernameLabel;
	@FXML private TextField usernameField;
	@FXML private Label passwordLabel;
	@FXML private PasswordField passwordField;
	@FXML private Hyperlink hyperlinkToRegister;
	@FXML private Label errorLabel = new Label();

	@FXML
	public void handleLogin() {
		String username = usernameField.getText();
		String password = passwordField.getText();

		if (Authentication.authenticateUser(username, password)) {
			errorLabel.setText("Login successful!");
			// Redirect to Dashboard
		} else {
			errorLabel.setText("Invalid username or password.");
		}
	}

	@FXML
	public void toRegister() throws IOException {
		App.setRoot("fxmls/register");
	}
}
