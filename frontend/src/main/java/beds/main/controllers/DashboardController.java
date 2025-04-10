package beds.main.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import beds.backend.Workout;
import beds.main.App;
import beds.security.Authentication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class DashboardController {
	@FXML private TextField newUsernameField;
	@FXML private PasswordField confirmUsernamePasswordField;

	@FXML private PasswordField oldPasswordField;
	@FXML private PasswordField newPasswordField;

	@FXML private Label currentUsernameLabel;
	
	@FXML
	private void handleStartEmptyWorkout() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxmls/currentWorkout.fxml"));
		Parent root = loader.load();

		CurrentWorkoutController controller = loader.getController();
		controller.setWorkout(new Workout());

		App.setRoot(root);
    }

	@FXML
	public void initialize() {
		currentUsernameLabel.setText(Authentication.getUsername());
	}

	@FXML
	private void handleWorkoutHistory() throws IOException {
		App.setRoot("fxmls/history/workoutHistoryView");
	}

	@FXML
	private void handleGoals() {
		System.err.println("Goals not implemented yet");
	}

	@FXML
	private void handleUpdateUsername() {
		String newUsername = newUsernameField.getText();
		String confirmPassword = confirmUsernamePasswordField.getText();

		if (newUsername.isEmpty() || confirmPassword.isEmpty()) {
			return;
		}

		int res = Authentication.updateUser(newUsername, confirmPassword);
		System.out.println(res);
		if (res == 0) {
			alert("Succesful", "Username updated succesfully", false);
			currentUsernameLabel.setText(newUsername);
			newUsernameField.clear();
			confirmUsernamePasswordField.clear();
		} else if (res == -1) {
			alert("Username Conflict", "Username already exists", true);
		} else if (res == -2) {
			alert("Incorrect password", "Password doesn't match", true);
		} else {
			alert("Error", "Unknown error. Try logging out and loggin back in", true);
		}
	}

	private void alert(String title, String content, boolean isError) {
		Alert alert;
		if (isError) {
			alert = new Alert(Alert.AlertType.ERROR);
		} else {
			alert = new Alert(Alert.AlertType.INFORMATION);
		}
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	@FXML
	private void handleUpdatePassword() {
		String oldPassword = oldPasswordField.getText();
		String newPassword = newPasswordField.getText();
		if (oldPassword.isEmpty() || newPassword.isEmpty()) {
			return;
		}
		int res = Authentication.updatePassword(oldPassword, newPassword);
		if (res == 0) {
			alert("Succesful", "Password updated succesfully", false);
			oldPasswordField.clear();
			newPasswordField.clear();
		} else if (res == -1) {
			alert("Incorrect password", "Old password doesn't match", true);
		} else {
			alert("Error", "Unknown error. Try logging out and loggin back in", true);
		}
	}

	@FXML
	private void handleUpdateMeasurements() {
		System.err.println("Update measurements not implemented yet");
	}
}
