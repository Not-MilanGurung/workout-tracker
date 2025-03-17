package beds.main.controllers;

import java.io.IOException;
import java.util.List;


import beds.backend.Workout;
import beds.main.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;


public class DashboardController {
	@FXML private ListView<Workout> workoutListView;
    @FXML private Button addWorkoutButton;

    @FXML
    public void initialize() {
        loadWorkouts();
        workoutListView.setOnMouseClicked(event -> openWorkoutDetails());
    }

    private void loadWorkouts() {
        List<Workout> workouts = DatabaseManager.getAllWorkouts();
        workoutListView.getItems().setAll(workouts);
    }

    @FXML
    private void handleAddWorkout() throws IOException {
        App.setRoot("fxmls/addWorkout");
    }

    private void openWorkoutDetails() {
        Workout selectedWorkout = workoutListView.getSelectionModel().getSelectedItem();
        if (selectedWorkout != null) {
            try {
				FXMLLoader loader = new FXMLLoader(App.class.getResource("fxmls/WorkoutDetails.fxml"));
                App.setRoot("fxmls/workoutDetails");
				WorkoutDetailsController controller = loader.getController();
				controller.setWorkout(selectedWorkout);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
