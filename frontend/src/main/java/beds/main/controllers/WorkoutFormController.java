package beds.main.controllers;

import java.io.IOException;
import java.sql.SQLException;

import beds.backend.Workout;
import beds.database.DatabaseConnection;
import beds.main.App;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class WorkoutFormController {

	@FXML private TextField workoutNameField;
	@FXML private CheckBox saveAsRoutineCheck;
	private static Workout workout;

	/**
	 * Sets the workout to be edited
	 * @param workout
	 */
	public void setWorkout(Workout workout) {WorkoutFormController.workout = workout;}

	@FXML
	private void saveWorkout() throws SQLException, IOException {
		WorkoutFormController.workout.setName(workoutNameField.getText());
		WorkoutFormController.workout.setIsRoutine(saveAsRoutineCheck.isSelected());

		DatabaseConnection.storeWorkout(WorkoutFormController.workout); // Store the workout
		App.setRoot("fxmls/dashboard"); // Go back to the dashboard
    }
}

