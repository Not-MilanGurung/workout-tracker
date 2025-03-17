package beds.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import beds.database.DatabaseConnection;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StoredWorkout extends Workout {
	private IntegerProperty id;
	private StringProperty datetime;
	/**
	 * Constructor for retriving workout from the database
	 * @param id of the workout
	 * @param name of the workout
	 * @param completionTime how long it took to complete the workout
	 * @param datetime When the workout was recorded
	 */
	public StoredWorkout(int id, String name, long completionTime, Timestamp datetime){
		this.id = new SimpleIntegerProperty(id);
		setName(name);
		this.completionTime = new SimpleLongProperty(completionTime);
		this.datetime = new SimpleStringProperty(datetime.toString());
	}

	public IntegerProperty getIdProperty() {return this.id;}
	public int getId() {return this.id.get();}

	public String getDateTime() {return this.datetime.get();}
	public StringProperty getDateTimeProperty() {return this.datetime;}

	public void loadExercises() throws SQLException{
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM WorkoutExercise WHERE WorkoutID = ? ORDER BY OrderNo");
		stmt.setInt(1, this.id.get());

		ResultSet res = stmt.executeQuery();

		while (res.next()){

		}
	}
}
