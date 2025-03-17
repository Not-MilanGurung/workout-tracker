package beds.main.controllers;

import java.io.IOException;

import beds.main.App;
import beds.backend.Workout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;


public class DashboardController {
    @FXML private Button startEmptyWorkoutButton;


    @FXML
    private void handleStartEmptyWorkout() throws IOException {
		FXMLLoader loader = new FXMLLoader(App.class.getResource("fxmls/startWorkout.fxml"));
		CurrentWorkoutController controller = loader.getController();
		Workout emptyWorkout = new Workout();
		controller.setWorkout(emptyWorkout);

		Parent newRoot = loader.load();
        App.setRoot(newRoot);
    }
}
