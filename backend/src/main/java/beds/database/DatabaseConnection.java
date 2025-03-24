package beds.database;

import java.lang.Thread.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beds.backend.CurrentExercise;
import beds.backend.Exercise;
import beds.backend.Set;
import beds.backend.Workout;
import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;


/** This class handles the singleton connection to the database */
public class DatabaseConnection {
	private static final String URL = "jdbc:hsqldb:file:target/database/mainDB";
	private static Connection mainCon;
	public static int userID;
	/**
	 * Returns the static connection to the database. Initilises the variable if not defined.
	 * It also initilises the database if it doesn't exist.
	 * @return {@link #mainCon} Database connection
	 */
    public static Connection getConnection(){

			try {
				if (mainCon == null || mainCon.isClosed() ){
					/** Initilise the database if it doensn't exist */
					
					try {
						mainCon = DriverManager.getConnection(URL+";ifexists=true", "SA", "");
						
					} catch (Exception e) {
						mainCon = DriverManager.getConnection(URL+";ifexists=false", "SA", "");
						TableCreation.main(mainCon);
						InitiliseDefaultTableData.main(mainCon);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return mainCon;
    }

	public static void setID(int userID){ DatabaseConnection.userID = userID;}

	public static List<Exercise> getAllExercises() throws SQLException{
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM Exercises WHERE UserID=0 OR UserID=? ORDER BY Name");
		stmt.setInt(1, userID);

		ResultSet res = stmt.executeQuery();
		List<Exercise> exercises = new ArrayList<Exercise>();
		int id, metricAType, metricBType, primaryMuscle, secondaryMuscle, equipmentType, restTime;
		String name;
		Exercise ex;
		while (res.next()){
			id = res.getInt("Id");
			name = res.getString("Name");
			metricAType = res.getInt("MetricAType");
			metricBType = res.getInt("MetricBType");
			primaryMuscle = res.getInt("PrimaryMuscle");
			secondaryMuscle = res.getInt("SecondaryMuscle");
			equipmentType = res.getInt("EquipmentType");
			restTime = res.getInt("RestTime");

			ex = new Exercise(id, name, MetricType.getByID(metricAType), 
				MetricType.getByID(metricBType), 
				MuscleGroup.getByID(primaryMuscle), MuscleGroup.getByID(secondaryMuscle), 
				EquipmentType.getByID(equipmentType), restTime);
			
			exercises.add(ex);
		}
		return exercises;
	}

	public static void storeWorkout(Workout w) throws SQLException{
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement(
			"INSERT INTO Workouts (UserID, Name, CompletionTime, DateTime) VALUE(?, ?, ?, ?)",
			PreparedStatement.RETURN_GENERATED_KEYS);

		stmt.setInt(1, userID);
		stmt.setString(2, w.getName());
		stmt.setInt(3, w.getCompletionTime());
		stmt.setTimestamp(4, Timestamp.valueOf(w.getDateTime()));

		ResultSet workouResultSet = stmt.executeQuery();
		stmt.close();
		int workoutID;

		while(workouResultSet.next()){
			workoutID = workouResultSet.getInt("Id");
			stmt = con.prepareStatement(
				"INSERT INTO WorkoutExercise (WorkoutID, ExerciseID, OrderNo) VALUES(?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
			
			for (CurrentExercise e : w.getExercises()){
				stmt.setInt(1, workoutID);
				stmt.setInt(2, e.getID());
				stmt.setInt(3, w.getExercises().indexOf(e));
				stmt.addBatch();
			}

			stmt.executeBatch();

			ResultSet workoutExersiResultSet = stmt.getGeneratedKeys();
			stmt.close();
			while(workoutExersiResultSet.next()){
				int workoutExerciseId = workoutExersiResultSet.getInt("Id");
				stmt = con.prepareStatement(
					"INSERT INTO Sets VALUES(?, ?, ?, ?, ?, ?)");
				for(CurrentExercise ex : w.getExercises()){
					for (Set set : ex.getSets()){
						stmt.setInt(1, ex.getSets().indexOf(set));
						stmt.setInt(2, workoutExerciseId);
						stmt.setInt(3, set.getMetricA());
						stmt.setInt(4, set.getMetricB());
						stmt.setInt(5, set.getSet);
					}
				}
			}
		}

	}
}
