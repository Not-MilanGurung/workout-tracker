package beds.main.controllers;

import beds.backend.Workout;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CurrentWorkoutController {
	private static Workout workout;
	@FXML private Button finishWorkoutButton;
	@FXML private VBox exercisesVBox;

	public void setWorkout(Workout workout){ CurrentWorkoutController.workout = workout;}

	@FXML
	private void handleAddExercise() {
		System.out.println("Adding a exercise");
	}

	@FXML
	private void handleFinishWorkout(){
		System.out.println("Finished workout");
	}
	
}
