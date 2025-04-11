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
import beds.enums.SetType;


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
	public static List<Workout> getWorkouts(int offset, int limit) {
        List<Workout> result = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "SELECT Id, Name, CompletionTime, DateTime, IsRoutine FROM Workouts WHERE UserID = ? ORDER BY DateTime DESC LIMIT ? OFFSET ?"
             )) {
			stmt.setInt(1, userID);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(new Workout(
                    rs.getInt("Id"),
                    rs.getString("Name"),
					rs.getInt("CompletionTime"),
                    rs.getTimestamp("DateTime").toLocalDateTime(),
                    rs.getBoolean("IsRoutine")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

	public static void storeWorkout(Workout w) throws SQLException{
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement(
			"INSERT INTO Workouts (UserID, Name, CompletionTime, DateTime, IsRoutine) VALUES(?, ?, ?, ?, ?)",
			PreparedStatement.RETURN_GENERATED_KEYS);

		stmt.setInt(1, userID);
		stmt.setString(2, w.getName());
		stmt.setInt(3, w.getCompletionTime());
		stmt.setTimestamp(4, Timestamp.valueOf(w.getDateTime()));
		stmt.setBoolean(5, w.getIsRoutine());

		stmt.executeUpdate();
		ResultSet workouResultSet = stmt.getGeneratedKeys();
		int workoutID;

		while(workouResultSet.next()){
			workoutID = workouResultSet.getInt("Id");
			stmt = con.prepareStatement(
				"INSERT INTO WorkoutExercise (WorkoutID, ExerciseID, OrderNo) VALUES(?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS);
			ArrayList<CurrentExercise> exercises = w.getExercises();
			for(int i = 0; i < exercises.size(); i++){
				CurrentExercise e = exercises.get(i);
				stmt.setInt(1, workoutID);
				stmt.setInt(2, e.getID());
				stmt.setInt(3, i);
				stmt.addBatch();
			}

			stmt.executeBatch();

			ResultSet workoutExersiResultSet = stmt.getGeneratedKeys();
			int i = 0;
			while(workoutExersiResultSet.next()){
				int workoutExerciseId = workoutExersiResultSet.getInt("Id");
				stmt = con.prepareStatement(
					"INSERT INTO Sets VALUES(?, ?, ?, ?, ?, ?)");
				CurrentExercise ex = exercises.get(i);
				for (Set set : ex.getSets()){
					stmt.setInt(1, ex.getSets().indexOf(set) + 1);
					stmt.setInt(2, workoutExerciseId);
					stmt.setInt(3, set.getMetricA());
					stmt.setInt(4, set.getMetricB());
					stmt.setInt(5, set.getType().getID());
					stmt.setInt(6, set.getRestTime());
					stmt.addBatch();
				}
				i++;
			}
			stmt.executeBatch();
			stmt.close();
			workoutExersiResultSet.close();
		}
		workouResultSet.close();

	}

	public static List<CurrentExercise> getWorkoutExercises(int workoutID) throws SQLException{
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM WorkoutExercise WHERE WorkoutID=? ORDER BY OrderNo");
		stmt.setInt(1, workoutID);
		ResultSet res = stmt.executeQuery();

		List<CurrentExercise> exercises = new ArrayList<CurrentExercise>();
		int exerciseId, workoutExerciseId;
		while (res.next()){
			workoutExerciseId = res.getInt("Id");
			exerciseId = res.getInt("ExerciseID");

			stmt = con.prepareStatement("SELECT * FROM Exercises WHERE Id=?");
			stmt.setInt(1, exerciseId);
			ResultSet res2 = stmt.executeQuery();

			CurrentExercise ex = null;
			while (res2.next()){
				ex = new CurrentExercise(res2.getInt("Id"), 
					res2.getString("Name"),
					MetricType.getByID(res2.getInt("MetricAType")), 
					MetricType.getByID(res2.getInt("MetricBType")), 
					MuscleGroup.getByID(res2.getInt("PrimaryMuscle")), 
					MuscleGroup.getByID(res2.getInt("SecondaryMuscle")), 
					EquipmentType.getByID(res2.getInt("EquipmentType")), 
					res2.getInt("RestTime"));
				
			}

			stmt = con.prepareStatement("SELECT * FROM Sets WHERE WorkoutExerciseID=?");
			stmt.setInt(1, workoutExerciseId);

			ResultSet res3 = stmt.executeQuery();
			while (res3.next()){
				Set set = new Set(res3.getInt("SetNo"), 
					res3.getInt("MetricA"), 
					res3.getInt("MetricB"),
					SetType.getSetType(res3.getInt("SetType")), 
					res3.getInt("RestTime"), 
					true);
				ex.addSet(set);
			}
			exercises.add(ex);
			res2.close();
			res3.close();
			
		}
		stmt.close();
		res.close();
		return exercises;
	}
}
