package beds.nodes;

import java.util.concurrent.atomic.AtomicInteger;

import beds.backend.CurrentExercise;
import beds.backend.Set;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.Button;

public class ExerciseNode extends GridPane {
	public final CurrentExercise exercise;
	private TableView<Set> setsTable = new TableView<Set>();
	private final Button addSetButton;
	private final Button removeSetButton;
	private Label restTimerLabel = new Label("Rest: --");
	private Timeline restCountdown;
	
	@SuppressWarnings("unchecked")
	public ExerciseNode(CurrentExercise e){
		this.exercise = e;


		this.setPadding(new Insets(20));
        this.setVgap(10);
        this.setHgap(10);

		ColumnConstraints col = new ColumnConstraints();
        col.setHgrow(Priority.ALWAYS);  // Allow full width
        this.getColumnConstraints().add(col);
		
		// Configure GridPane Row Constraints for vertical growth
        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.NEVER);   // Label row won't grow
		
        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.SOMETIMES);  // TableView row expands
		
        RowConstraints row3 = new RowConstraints();
        row3.setVgrow(Priority.NEVER);   // Button row won't grow
		
        this.getRowConstraints().addAll(row1, row2, row3);
		
		this.setsTable.setEditable(true);

		HBox titleBox = new HBox(20); // spacing between name and timer
		Label titleLabel = new Label(e.getName() + " (" + e.getEquipmentType() + ")");
		titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
		restTimerLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #00ff99;");

		titleBox.getChildren().addAll(titleLabel, restTimerLabel);
		this.add(titleBox, 0, 0);
		// Set no column
		TableColumn<Set, Integer> setsNoColumn = new TableColumn<>("Set");
		setsNoColumn.setCellValueFactory(data -> data.getValue().getSetNoProperty().asObject());
		setsNoColumn.setPrefWidth(50);
		// Metric A column
		TableColumn<Set, Integer> metricA = new TableColumn<>(e.getMetricAType().toString());
		metricA.setCellValueFactory(data -> data.getValue().getMetricAProperty().asObject());
		metricA.setCellFactory(coll -> autoCommitIntegerCell());


		
		// Metric B column
		TableColumn<Set, Integer> metricB;
		try {
			metricB = new TableColumn<>(e.getMetricBType().toString());
			metricB.setCellValueFactory(data -> data.getValue().getMetricBProperty().asObject());
			metricB.setCellFactory(coll -> autoCommitIntegerCell());

		} catch (Exception e1) {
			metricB = new TableColumn<>("");
		}

		// Completed Column (Checkbox)
        TableColumn<Set, Boolean> completedColumn = new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(data -> data.getValue().getIsCompleteProperty());
		completedColumn.setCellFactory(coll -> {
			CheckBoxTableCell<Set, Boolean> cell = new CheckBoxTableCell<>();
			cell.setSelectedStateCallback(index -> {
				Set set = setsTable.getItems().get(index);
				set.getIsCompleteProperty().addListener((obs, oldVal, newVal) -> {
					if (newVal) startRestTimer(set.getRestTime());
				});
				return set.getIsCompleteProperty();
			});
			return cell;
		});

		// Rest time column
		TableColumn<Set, Integer> restTimeColumn = new TableColumn<>("Rest Time");
		restTimeColumn.setCellValueFactory(data -> data.getValue().getRestTimProperty().asObject());
		restTimeColumn.setCellFactory(coll -> autoCommitIntegerCell());


		setsTable.getColumns().addAll(setsNoColumn, metricA, metricB, completedColumn, restTimeColumn);
		setsTable.setItems(exercise.getSets());

		
		setsTable.setPrefWidth(250.0);
        setsTable.setMaxWidth(Double.MAX_VALUE);  // Allow expansion
        setsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

		this.add(setsTable, 0, 1, 2, 1);
		
		addSetButton = new Button("Add Set");
		addSetButton.setOnAction(event -> addSet());
		this.add(this.addSetButton, 0, 2, 1, 1);

		removeSetButton = new Button("Remove Set");
		removeSetButton.setOnAction(event -> removeSelectedSet());
		this.add(this.removeSetButton, 1, 2, 1, 1);

		setsTable.getColumns().forEach(coll -> {
			coll.setStyle("-fx-font-size: 14px; -fx-background-color:rgb(20, 18, 18);");
		});

	}

	private TextFieldTableCell<Set, Integer> autoCommitIntegerCell() {
		IntegerStringConverter converter = new IntegerStringConverter();
		TextFieldTableCell<Set, Integer> cell = new TextFieldTableCell<>(converter);

		cell.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
			if (!isNowFocused && cell.isEditing()) {
				cell.commitEdit(converter.fromString(cell.getText()));
			}
		});

		return cell;
	}

	private void startRestTimer(int initialSeconds) {
		if (restCountdown != null) {
			restCountdown.stop();
		}

		AtomicInteger secondsLeft = new AtomicInteger(initialSeconds);
		restTimerLabel.setText("Rest: " + secondsLeft.get() + "s");

		restCountdown = new Timeline(
			new KeyFrame(Duration.seconds(1), event -> {
				int remaining = secondsLeft.decrementAndGet();
				restTimerLabel.setText("Rest: " + remaining + "s");
			})
		);
		restCountdown.setCycleCount(initialSeconds);
		restCountdown.setOnFinished(e -> restTimerLabel.setText("Rest over!"));
		restCountdown.play();
	}



	private void addSet(){
		int nextSetNo = exercise.getSets().size() + 1;
		exercise.getSets().add(new Set(nextSetNo, exercise.getRestTime()));
	}

	private void removeSelectedSet() {
        Set selected = setsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            exercise.getSets().remove(selected);
        }
    }

}
