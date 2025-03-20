package beds.nodes;

import beds.backend.CurrentExercise;
import beds.backend.Exercise;
import beds.backend.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.Button;

public class ExerciseNode extends GridPane {
	public CurrentExercise exercise;
	private TableView<Set> sets = new TableView<Set>();
	private final ObservableList<Set> data;
	private final Button addSetButton;

	public ExerciseNode(Exercise e){
		exercise = new CurrentExercise(e);
		setMaxWidth(Double.MAX_VALUE);

		ColumnConstraints col = new ColumnConstraints();
        col.setHgrow(Priority.ALWAYS);  // Allow full width
        getColumnConstraints().add(col);

		// Configure GridPane Row Constraints for vertical growth
        RowConstraints row1 = new RowConstraints();
        row1.setVgrow(Priority.NEVER);   // Label row won't grow

        RowConstraints row2 = new RowConstraints();
        row2.setVgrow(Priority.SOMETIMES);  // TableView row expands

        RowConstraints row3 = new RowConstraints();
        row3.setVgrow(Priority.NEVER);   // Button row won't grow

        getRowConstraints().addAll(row1, row2, row3);

		sets.setEditable(true);
		data = FXCollections.observableArrayList(new Set(exercise.getRestTime()));

		TableColumn<Set, Integer> setsNo = new TableColumn<Set, Integer>("Set");
		
		TableColumn<Set, Integer> metricA = new TableColumn<Set, Integer>(e.getMetricAType().toString());
		metricA.setCellValueFactory(new PropertyValueFactory<Set, Integer>("metricA"));
		
		TableColumn<Set, Integer> metricB = new TableColumn<Set, Integer>(e.getMetricBType().toString());
		metricB.setCellValueFactory(new PropertyValueFactory<Set, Integer>("metricB"));

		sets.setItems(data);
		sets.getColumns().addAll(setsNo, metricA, metricB);
		
		sets.setPrefWidth(250.0);
        sets.setMaxWidth(Double.MAX_VALUE);  // Allow expansion
        sets.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

		add(new Label(e.getName()), 0, 0);
		add(sets, 0, 1, 2, 1);
		setHgrow(sets, Priority.SOMETIMES);
		
		addSetButton = new Button("Add Set");
		addSetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e){
				data.add(new Set(exercise.getRestTime()));
			}
		});
		sets.setPrefWidth(250.0);
		add(addSetButton, 0, 2, 2, 1);
		
	}
}
