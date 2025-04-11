package beds.main.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import beds.backend.Goals;
import beds.backend.HeaviestSetGoals;
import beds.backend.WeeklyFrequencyGoal;
import beds.database.DatabaseConnection;
import beds.database.GoalDAO;
import beds.enums.GoalType;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


public class GoalsController implements Initializable {

    @FXML private ComboBox<GoalType> goalTypeCombo;
    @FXML private DatePicker deadlinePicker;
    @FXML private TextField goalValueField;
    @FXML private TableView<Goals> goalsTable;
    @FXML private TableColumn<Goals, String> typeCol, valueCol, deadlineCol, statusCol;
	@FXML private TableColumn<Goals, Void> actionCol;

	private ObservableList<Goals> goalList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        goalTypeCombo.setItems(FXCollections.observableArrayList(GoalType.values()));
        // Set up table columns, etc.
		typeCol.setCellValueFactory(cellData ->
			new SimpleStringProperty(cellData.getValue().getGoalType().toString()));

		valueCol.setCellValueFactory(cellData -> {
			if (cellData.getValue() instanceof HeaviestSetGoals h) {
				return new SimpleStringProperty(h.getHeaviestWeightGoal() + " kg");
			} else if (cellData.getValue() instanceof WeeklyFrequencyGoal w) {
				return new SimpleStringProperty(w.getConsistencyGoal() + "x/week");
			}
			return new SimpleStringProperty("Unknown");
		});

		deadlineCol.setCellValueFactory(cellData ->
			new SimpleStringProperty(cellData.getValue().getGoalDeadLine().toLocalDate().toString()));

		statusCol.setCellValueFactory(cellData -> {
			Goals g = cellData.getValue();
			String status = g.isGoalAchieved() ? "Achieved" :
							g.isGoalExpired() ? "Expired" : "Active";
			return new SimpleStringProperty(status);
		});

		goalsTable.setItems(goalList);
		try {
			loadGoals();
		} catch (SQLException e) {
			showAlert("Error", "Could not load goals: " + e.getMessage());
		}

		goalTypeCombo.setOnAction(event -> {
			GoalType selectedType = goalTypeCombo.getValue();
			if (selectedType == GoalType.HEAVIESTSET) {
				goalValueField.setPromptText("Enter weight in kg");
			} else if (selectedType == GoalType.WEEKLYFREQUENCY) {
				goalValueField.setPromptText("Enter frequency per week");
			}
		});

		deadlinePicker.setOnAction(event -> {
			LocalDateTime selectedDate = deadlinePicker.getValue().atStartOfDay();
			if (selectedDate.isBefore(LocalDateTime.now())) {
				showAlert("Invalid Date", "Deadline cannot be in the past.");
				deadlinePicker.setValue(null);
			}
		});

		actionCol.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Mark Achieved");

			{
				btn.setStyle("-fx-size: 10px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
				btn.setOnAction(event -> {
					Goals goal = getTableView().getItems().get(getIndex());
					goal.setGoalAchievedDateTime(LocalDateTime.now());
					try {
						GoalDAO.updateGoal(goal);
					} catch (SQLException e) {
						showAlert("Error", "Could not update goal: " + e.getMessage());
					}
					goalsTable.refresh(); // reflect status change
					// Optional: update DB here
				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || getTableRow().getItem() == null || ((Goals) getTableRow().getItem()).isGoalAchieved()) {
					setGraphic(null);
				} else {
					setGraphic(btn);
				}
			}
		});
		goalsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
		goalsTable.getColumns().forEach(col -> {
				col.setStyle("-fx-font-size: 14px; -fx-background-color:rgb(20, 18, 18);");
		});

		goalsTable.setStyle("-fx-font-size: 14px; -fx-background-color:rgb(20, 18, 18);");
	}


    @FXML
    private void handleSaveGoal() {
        try {
            GoalType selectedType = goalTypeCombo.getValue();
            LocalDateTime deadline = deadlinePicker.getValue().atStartOfDay();
            double value = Double.parseDouble(goalValueField.getText());

            Goals newGoal = switch (selectedType) {
                case HEAVIESTSET -> new HeaviestSetGoals(deadline, value);
                case WEEKLYFREQUENCY -> new WeeklyFrequencyGoal(deadline, value);
                default -> null;
            };

            if (newGoal != null) {
                newGoal.setUserID(DatabaseConnection.getUserID());
                GoalDAO.saveGoal(newGoal);
                showAlert("Goal Saved", "Your goal has been successfully saved!");
				loadGoals(); // Refresh table
                // Refresh table
            }

        } catch (Exception e) {
            showAlert("Error", "Could not save goal: " + e.getMessage());
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

	private void loadGoals() throws SQLException {
		// Load goals from the database and add them to the goalList
		// This is a placeholder. You should implement the actual loading logic.
		goalList.clear();
		for (Goals goal : GoalDAO.getGoalsByUserID(DatabaseConnection.getUserID())) {
			goalList.add(goal);
		}
		goalsTable.refresh();
	}

}

