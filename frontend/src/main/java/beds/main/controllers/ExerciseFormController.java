package beds.main.controllers;

import java.sql.SQLException;

import beds.backend.Exercise;
import beds.database.DatabaseConnection;
import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ExerciseFormController {

    @FXML private TextField nameField;
    @FXML private ComboBox<MetricType> metricACombo;
    @FXML private ComboBox<MetricType> metricBCombo;
    @FXML private ComboBox<MuscleGroup> primaryMuscleCombo;
    @FXML private ComboBox<MuscleGroup> secondaryMuscleCombo;
    @FXML private ComboBox<EquipmentType> equipmentTypeCombo;
    @FXML private TextField restTimeField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private Exercise editingExercise = null;

    public void initialize() {
        metricACombo.setItems(FXCollections.observableArrayList(MetricType.values()));
        metricBCombo.setItems(FXCollections.observableArrayList(MetricType.values()));
        primaryMuscleCombo.setItems(FXCollections.observableArrayList(MuscleGroup.values()));
        secondaryMuscleCombo.setItems(FXCollections.observableArrayList(MuscleGroup.values()));
        equipmentTypeCombo.setItems(FXCollections.observableArrayList(EquipmentType.values()));
    }

    public void setExerciseToEdit(Exercise exercise) {
        this.editingExercise = exercise;
        nameField.setText(exercise.getName());
        metricACombo.setValue(exercise.getMetricAType());
        metricBCombo.setValue(exercise.getMetricBType());
        primaryMuscleCombo.setValue(exercise.getPrimaryMuscle());
        secondaryMuscleCombo.setValue(exercise.getSecondaryMuscle());
        equipmentTypeCombo.setValue(exercise.getEquipmentType());
        restTimeField.setText(String.valueOf(exercise.getRestTime()));
    }

    @FXML
    private void handleSave() throws SQLException {
        String name = nameField.getText().trim();
        int restTime = Integer.parseInt(restTimeField.getText());

        MetricType metricA = metricACombo.getValue();
        MetricType metricB = metricBCombo.getValue();
        MuscleGroup primary = primaryMuscleCombo.getValue();
        MuscleGroup secondary = secondaryMuscleCombo.getValue();
        EquipmentType equipment = equipmentTypeCombo.getValue();

        if (name.isEmpty() || metricA == null || primary == null || equipment == null) {
            showAlert("Please fill all required fields.");
            return;
        }

        if (editingExercise == null) {
            Exercise newExercise = new Exercise(name, metricA, metricB, primary, secondary, equipment, restTime);
            DatabaseConnection.addExercise(newExercise);
        } else {
            editingExercise.setName(name);
            editingExercise.setMetricAType(metricA);
            editingExercise.setMetricBType(metricB);
            editingExercise.setPrimaryMuscle(primary);
            editingExercise.setSecondaryMuscle(secondary);
            editingExercise.setEquipmentType(equipment);
            editingExercise.setRestTime(restTime);
            DatabaseConnection.updateExercise(editingExercise);
        }

        // Navigate back to previous scene (implement this in your app's navigation)
    }

    @FXML
    private void handleCancel() {
        // Implement navigation back to previous scene
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Validation Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
