package beds.nodes;

import beds.backend.CurrentExercise;
import beds.backend.Set;
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
	public final CurrentExercise exercise;
	private TableView<Set> setsTable = new TableView<Set>();
	private final Button addSetButton;
	private final Button removeSetButton;
	
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

		this.add(new Label(e.getName() + " (" +e.getEquipmentType() +")"), 0, 0);
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
		TableColumn<Set, Integer> metricB;
		try {
			metricB = new TableColumn<>(e.getMetricBType().toString());
			metricB.setCellValueFactory(data -> data.getValue().getMetricBProperty().asObject());
			metricB.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
			metricB.setOnEditCommit(event -> event.getRowValue().setMetricB(event.getNewValue()));
		} catch (Exception e1) {
			metricB = new TableColumn<>("");
		}

		// Completed Column (Checkbox)
        TableColumn<Set, Boolean> completedColumn = new TableColumn<>("Completed");
        completedColumn.setCellValueFactory(data -> data.getValue().getIsCompleteProperty());
        completedColumn.setCellFactory(CheckBoxTableCell.forTableColumn(completedColumn));
		// Rest time column
		TableColumn<Set, Integer> restTimeColumn = new TableColumn<>("Rest Time");
		restTimeColumn.setCellValueFactory(data -> data.getValue().getRestTimProperty().asObject());
		restTimeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		restTimeColumn.setOnEditCancel(event -> event.getRowValue().setRestTime(event.getNewValue()));

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
