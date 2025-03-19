package beds.main.controllers;

import java.io.IOException;

import beds.backend.Workout;
import beds.main.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class DashboardController {
	
	@FXML
	private Button startEmptyWorkoutButton;
	
	@FXML
	private void handleStartEmptyWorkout() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxmls/currentWorkout-test.fxml"));
		Parent root = loader.load();

		CurrentWorkoutController controller = loader.getController();
		controller.setWorkout(new Workout());

		App.setRoot(root);
    }
}
