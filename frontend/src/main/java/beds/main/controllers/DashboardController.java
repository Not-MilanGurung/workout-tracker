package beds.main.controllers;

import java.io.IOException;

import beds.backend.Workout;
import beds.main.App;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class DashboardController {
	
	
	@FXML
	private void handleStartEmptyWorkout() throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxmls/currentWorkout.fxml"));
		Parent root = loader.load();

		CurrentWorkoutController controller = loader.getController();
		controller.setWorkout(new Workout());

		App.setRoot(root);
    }

	@FXML
	private void handleWorkoutHistory() throws IOException {
		App.setRoot("fxmls/history/workoutHistoryView");
	}
}
