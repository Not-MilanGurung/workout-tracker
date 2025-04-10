package beds.main.controllers.history;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;

import beds.backend.Workout;
import beds.database.DatabaseConnection;
import beds.main.App;
import beds.main.controllers.CurrentWorkoutController;

/**
 * The WorkoutHistoryController class manages the workout history view in the application.
 * It handles loading workouts from the database and displaying them in a ListView.
 */
public class WorkoutHistoryController {

    @FXML private ListView<Workout> workoutListView;
    @FXML private Button loadMoreButton;
	@FXML private Button loadPreviousButton;

    private ObservableList<Workout> workouts = FXCollections.observableArrayList();
    private int offset = 0;
    private final int limit = 20;

	/**
	 * Initializes the workout history view by setting up the ListView and loading workouts.
	 * This method is called after the FXML file has been loaded.
	 */
    public void initialize() {
        workoutListView.setItems(workouts);
        workoutListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(Workout workout, boolean empty) {
                super.updateItem(workout, empty);
                if (empty || workout == null) {
                    setText(null);
                } else {
                    setText(workout.getName() + " - " + workout.getDateTime());
                }
            }
        });

        workoutListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Workout selected = workoutListView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    try {
						openWorkoutDetailView(selected);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        });

        loadMoreWorkouts();
    }

	/**
	 * Loads more workouts from the database and adds them to the list view.
	 */
    @FXML
    private void loadMoreWorkouts() {
        List<Workout> nextPage = DatabaseConnection.getWorkouts(offset, limit);
		if (!nextPage.isEmpty()) {
			workouts.clear();
			// Add the new workouts to the list view
			workouts.addAll(nextPage);
			// Update the offset for the next page
			offset += limit;
		}
	}

	/**
	 * Loads the previous page of workouts.
	 * This method is called when the "Load Previous" button is clicked.
	 */
	@FXML
	private void loadPreviousWorkouts() {
		if (offset - limit < 0) {
			// Disable the button or show a message that there are no previous workouts
			return;
		}
		offset -= limit;
		workouts.clear();
		// Load the previous page of workouts
		List<Workout> previousPage = DatabaseConnection.getWorkouts(offset, limit);
		workouts.addAll(previousPage);
	}

	/**
	 * Opens the workout detail view for the selected workout.
	 * @param workout
	 * @throws IOException
	 */
    private void openWorkoutDetailView(Workout workout) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("fxmls/currentWorkout.fxml"));
		Parent root = loader.load();

		CurrentWorkoutController controller = loader.getController();
		// To do: load the exercises and sets for the selected workout
		controller.setWorkout(workout);

		App.setRoot(root);
    }

	@FXML
	private void handleBack() throws IOException {
		App.setRoot("fxmls/dashboard");
	}
}
