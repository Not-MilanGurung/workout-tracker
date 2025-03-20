package beds.main.controllers;

import beds.backend.Exercise;
import beds.backend.Workout;
import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;
import beds.nodes.ExerciseNode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CurrentWorkoutController {
	private static Workout workout;
	@FXML private Button finishWorkoutButton;
	@FXML private VBox exercisesVBox;

	public void setWorkout(Workout workout){ CurrentWorkoutController.workout = workout;}

	@FXML
	private void handleAddExercise() {
		ExerciseNode ex = new ExerciseNode(new Exercise(1, "Test", MetricType.REPS, 
			MetricType.WEIGHTS, MuscleGroup.BICEPS, MuscleGroup.ABDOMINAL, EquipmentType.BARBELL, 60000));
		VBox.setVgrow(ex, Priority.ALWAYS);
		exercisesVBox.getChildren().add(0, ex);
	}

	@FXML
	private void handleFinishWorkout(){
		System.out.println("Finished workout");
	}
	
}
