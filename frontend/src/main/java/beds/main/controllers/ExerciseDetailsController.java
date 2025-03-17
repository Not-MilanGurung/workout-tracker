package beds.main.controllers;

import beds.backend.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;

public class ExerciseDetailsController {
	@FXML private ListView<Set> setListView;
	@FXML
	private void handleCompleteSet(ActionEvent event) {
		Set selectedSet = setListView.getSelectionModel().getSelectedItem();
		if (selectedSet != null) {
			selectedSet.setCompleted(!selectedSet.isCompleted());
			setListView.refresh(); // Update UI
		}
	}
}
