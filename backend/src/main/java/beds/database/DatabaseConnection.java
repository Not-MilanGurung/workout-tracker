package beds.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	/**
	 * Sets the userID for the current session.
	 * This is used to filter the exercises and workouts for the current user.
	 * @param userID
	 */
	public static void setID(int userID){ DatabaseConnection.userID = userID;}
	/**
	 * Returns the userID for the current session.
	 * This is used to filter the exercises and workouts for the current user.
	 * @return {@link #userID} User ID
	 */
	public static int getUserID(){ return userID;}

	/**
	 * Returns the list of all exercises in the database.
	 * This is used to filter the exercises for the current user.
	 * @return {@link List<Exercise>} List of all exercises
	 * @throws SQLException
	 */
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
	
	/**
	 * Returns the list of recent workouts for the current user.
	 * @param num Number of workouts to return
	 * @return {@link List<Workout>} List of recent workouts
	 */
	public static List<Workout> getRecentWorkouts(int num){
		Connection con = getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("SELECT * FROM Workouts WHERE UserID=? ORDER BY DateTime DESC LIMIT ?");
			stmt.setInt(1, DatabaseConnection.userID);
			stmt.setInt(2, num);
			ResultSet res = stmt.executeQuery();
			ArrayList<Workout> workouts = new ArrayList<Workout>();
			while (res.next()){
				Workout w = new Workout();
				w.setName(res.getString("Name"));
				w.setCompletionTime(res.getInt("CompletionTime"));
				w.setDateTime(res.getTimestamp("DateTime").toLocalDateTime());
				workouts.add(w);
			}
			return workouts;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void storeWorkout(Workout w) throws SQLException{
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement(
			"INSERT INTO Workouts (UserID, Name, CompletionTime, DateTime) VALUES(?, ?, ?, ?)",
			PreparedStatement.RETURN_GENERATED_KEYS);

		stmt.setInt(1, userID);
		stmt.setString(2, w.getName());
		stmt.setInt(3, w.getCompletionTime());
		stmt.setTimestamp(4, Timestamp.valueOf(w.getDateTime()));

		stmt.executeUpdate();
		ResultSet workouResultSet = stmt.getGeneratedKeys();
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
						stmt.setInt(5, set.getType().getID());
						stmt.setInt(6, set.getRestTime());
						stmt.addBatch();
					}
				}
			}
			stmt.executeBatch();
			stmt.close();
			workoutExersiResultSet.close();
		}
		workouResultSet.close();

	}
}
