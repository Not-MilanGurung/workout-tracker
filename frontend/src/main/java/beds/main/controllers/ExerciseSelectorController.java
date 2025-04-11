package beds.main.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import beds.backend.CurrentExercise;
import beds.backend.Exercise;
import beds.database.DatabaseConnection;
import beds.enums.EquipmentType;
import beds.enums.MuscleGroup;
import beds.main.App;

public class ExerciseSelectorController {

	@FXML private TextField searchField;
	@FXML private ComboBox<MuscleGroup> muscleGroupFilter;
	@FXML private ComboBox<EquipmentType> equipmentTypeFilter;
	@FXML private ListView<Exercise> exerciseListView;

	private ObservableList<Exercise> exerciseList;

	public void initialize() throws SQLException {
		// Initialize the exercise list from the database
		exerciseList = FXCollections.observableArrayList(DatabaseConnection.getAllExercises());

		// Configure ComboBoxes with enums
		muscleGroupFilter.setItems(FXCollections.observableArrayList(MuscleGroup.values()));
		equipmentTypeFilter.setItems(FXCollections.observableArrayList(EquipmentType.values()));

		// Configure ListView
		exerciseListView.setItems(exerciseList);
		exerciseListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		exerciseListView.setCellFactory(param -> new ListCell<>() {
			@Override
			protected void updateItem(Exercise exercise, boolean empty) {
				super.updateItem(exercise, empty);
				if (empty || exercise == null) {
					setText(null);
				} else {
					setText(exercise.getName()+ " (" +exercise.getEquipmentType()+ ") \t " + exercise.getPrimaryMuscle() );
				}
			}
		});

		// Add listeners to filters
		searchField.textProperty().addListener((obs, oldVal, newVal) -> filterExercises());
		muscleGroupFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterExercises());
		equipmentTypeFilter.valueProperty().addListener((obs, oldVal, newVal) -> filterExercises());
	}

	private void filterExercises() {
		String searchText = searchField.getText().toLowerCase();
		MuscleGroup selectedMuscleGroup = muscleGroupFilter.getValue();
		EquipmentType selectedEquipment = equipmentTypeFilter.getValue();

		exerciseListView.setItems(exerciseList.filtered(exercise ->
				// Searching by name
				(searchText.isEmpty() || exercise.getName().toLowerCase().contains(searchText)) 
			&&	// Searching by muscle group
				(selectedMuscleGroup == null || selectedMuscleGroup == MuscleGroup.ANY || 
					exercise.getPrimaryMuscle() == selectedMuscleGroup || exercise.getSecondaryMuscle() == selectedMuscleGroup) 
			&&
				(selectedEquipment == null || selectedEquipment == EquipmentType.ANY || exercise.getEquipmentType() == selectedEquipment)
		));
	}

	@FXML
	private void addSelectedExercises() throws IOException {
        List<Exercise> selectedExercises = exerciseListView.getSelectionModel().getSelectedItems();
        if (selectedExercises.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select at least one exercise.", ButtonType.OK);
			alert.setTitle("No Exercise Selected");
			alert.showAndWait();
		} else if (selectedExercises.size() > 5) {
			Alert alert = new Alert(Alert.AlertType.WARNING, "You can only select up to 5 exercises.", ButtonType.OK);
			alert.setTitle("Too Many Exercises Selected");
			alert.showAndWait();
        } else {
            for (Exercise e : selectedExercises){
				CurrentWorkoutController.addExercise(new CurrentExercise(e));
			}
				FXMLLoader loader = new FXMLLoader(App.class.getResource("fxmls/currentWorkout.fxml"));
				Parent root = loader.load();

				CurrentWorkoutController controller = loader.getController();
				controller.loadOngoing();
				controller.startWorkoutTimer();

				App.setRoot(root);
        }
    }
}
