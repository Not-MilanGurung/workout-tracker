package beds.main.controllers;

import java.io.IOException;

import beds.backend.CurrentExercise;
import beds.backend.Workout;
import beds.main.App;
import beds.nodes.ExerciseNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CurrentWorkoutController {
	private static Workout workout;
	@FXML private Button finishWorkoutButton;
	@FXML private VBox exercisesVBox;
	@FXML private Button addExerciseButton;

	public void setWorkout(Workout workout){ CurrentWorkoutController.workout = workout;}

	public void loadOngoing(){
		if (workout != null)
			for (CurrentExercise e : workout.getExercises()){
				this.loadExercises(new ExerciseNode(e));
			}
	}

	@FXML
	private void handleAddExercise() throws IOException {
		App.setRoot("fxmls/addExercise");
	}

	private void loadExercises (ExerciseNode eNode){
		VBox.setVgrow(eNode, Priority.SOMETIMES);
		exercisesVBox.getChildren().add(exercisesVBox.getChildren().indexOf(addExerciseButton), eNode);
	}
	
	@FXML
	private void handleFinishWorkout() throws IOException{
		FXMLLoader loader = new FXMLLoader((App.class.getResource("fxmls/workoutForm.fxml")));
		Parent newRoot = loader.load();

		WorkoutFormController controller = loader.getController();
		controller.setWorkout(workout);

		App.setRoot(newRoot);
	}
	
	public static void addExercise(CurrentExercise e){
		CurrentWorkoutController.workout.addExercise(e);
	}
}
