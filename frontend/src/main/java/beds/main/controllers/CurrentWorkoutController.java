package beds.main.controllers;

import java.io.IOException;

import beds.backend.CurrentExercise;
import beds.backend.Workout;
import beds.main.App;
import beds.nodes.ExerciseNode;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class CurrentWorkoutController {
	private static Workout workout;
	private Timeline workoutTimer;

	@FXML private Button finishButton;
	@FXML private VBox exercisesVBox;
	@FXML private Button addExerciseButton;
	@FXML private Label headerLabel;
	@FXML private Label noOfSetsLabel;
	@FXML private Label durationLabel;



	public void setWorkout(Workout workout){ 
		CurrentWorkoutController.workout = workout;
		this.headerLabel.setText("Workout: " + workout.getName());
		this.durationLabel.setText(formatTime(workout.getCompletionTime()));
		this.loadOngoing();
		updateStats();
	}

	public void hideFinishButton(){
		this.finishButton.setVisible(false);
		this.finishButton.setManaged(false);
	}

	public void updateStats() {
		noOfSetsLabel.setText("Total Sets: " + workout.getTotalSets());
	}

	public void loadOngoing(){
		if (workout != null)
			for (CurrentExercise e : workout.getExercises()){
				this.loadExercises(new ExerciseNode(e));
			}
			updateStats();
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
	private void handleBackOut() throws IOException{
		App.setRoot("fxmls/dashboard");
	}
	
	@FXML
	private void handleFinishWorkout() throws IOException{
		FXMLLoader loader = new FXMLLoader((App.class.getResource("fxmls/workoutForm.fxml")));
		Parent newRoot = loader.load();

		WorkoutFormController controller = loader.getController();
		workoutTimer.stop();
		controller.setWorkout(workout);

		App.setRoot(newRoot);
	}
	
	public static void addExercise(CurrentExercise e){
		CurrentWorkoutController.workout.addExercise(e);
	}

	public void startWorkoutTimer() {
		workoutTimer = new Timeline(
			new KeyFrame(Duration.seconds(1), e -> {
				workout.setCompletionTime(workout.getCompletionTime() + 1);
				durationLabel.setText(formatTime(workout.getCompletionTime()));
			})
		);
		workoutTimer.setCycleCount(Animation.INDEFINITE);
		workoutTimer.play();
	}

	private String formatTime(int totalSeconds) {
		int hours = totalSeconds / 3600;
		int minutes = (totalSeconds % 3600) / 60;
		int seconds = totalSeconds % 60;
		return String.format("Duration : %02d:%02d:%02d", hours, minutes, seconds);
	}
}
