package beds.nodes;

import beds.backend.CurrentExercise;
import beds.backend.Exercise;
import beds.backend.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.Button;

public class ExerciseNode extends GridPane {
	public CurrentExercise exercise;
	private TableView<Set> sets = new TableView<Set>();
	private final ObservableList<Set> data;
	private final Button addSetButton;
	private final Button removeSetButton;
	
	@SuppressWarnings("unchecked")
	public ExerciseNode(Exercise e){
		this.exercise = new CurrentExercise(e);

		this.data = FXCollections.observableArrayList();

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
		
		this.sets.setEditable(true);

		this.add(new Label(e.getName()), 0, 0);
		// Set no column
		TableColumn<Set, Integer> setsNoColumn = new TableColumn<>("Set");
		setsNoColumn.setCellValueFactory(data -> data.getValue().getSetNoProperty().asObject());
		setsNoColumn.setPrefWidth(50);
		// Metric A column
		TableColumn<Set, Integer> metricA = new TableColumn<>(e.getMetricAType().toString());
		metricA.setCellValueFactory(data -> data.getValue().getMetricAProperty().asObject());
		metricA.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		metricA.setOnEditCommit(event -> event.getRowValue().setMetricA(event.getNewValue()));
		// Metric B column
		TableColumn<Set, Integer> metricB = new TableColumn<>(e.getMetricBType().toString());
		metricB.setCellValueFactory(data -> data.getValue().getMetricBProperty().asObject());
		metricB.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		metricB.setOnEditCommit(event -> event.getRowValue().setMetricB(event.getNewValue()));

		// Completed Column (Checkbox)
        TableColumn<Set, Boolean> completedColumn = new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(data -> data.getValue().getIsCompleteProperty());
        completedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(completedColumn));
		// Rest time column
		TableColumn<Set, Integer> restTimeColumn = new TableColumn<>("Rest Time s");
		restTimeColumn.setCellValueFactory(data -> data.getValue().getRestTimProperty().asObject());
		restTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		restTimeColumn.setOnEditCancel(event -> event.getRowValue().setRestTime(event.getNewValue()));

		sets.getColumns().addAll(setsNoColumn, metricA, metricB, completedColumn, restTimeColumn);
		sets.setItems(data);

		
		sets.setPrefWidth(250.0);
        sets.setMaxWidth(Double.MAX_VALUE);  // Allow expansion
        sets.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

		add(sets, 0, 1, 2, 1);
		setHgrow(sets, Priority.SOMETIMES);
		
		addSetButton = new Button("Add Set");
		addSetButton.setOnAction(event -> addSet());
		add(this.addSetButton, 0, 2, 1, 1);

		removeSetButton = new Button("Remove Set");
		removeSetButton.setOnAction(event -> removeSelectedSet());
		add(this.removeSetButton, 0, 2, 1, 1);

	}

	private void addSet(){
		int nextSetNo = data.size() + 1;
		data.add(new Set(nextSetNo, exercise.getRestTime()));
	}

	private void removeSelectedSet() {
        Set selected = sets.getSelectionModel().getSelectedItem();
        if (selected != null) {
            data.remove(selected);
        }
    }

}
