package beds.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import beds.backend.Exercise;
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

	public static ArrayList<Exercise> getAllExercises() throws SQLException{
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM Exercises WHERE UserID=0 OR UserID=? ORDER BY Name");
		stmt.setInt(1, userID);

		ResultSet res = stmt.executeQuery();
		ArrayList<Exercise> exercises = new ArrayList<Exercise>();
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
			restTime = res.getTime("RestTime").toLocalTime().getSecond();

			ex = new Exercise(id, name, MetricType.getByID(metricAType), 
				MetricType.getByID(metricBType), 
				MuscleGroup.getByID(primaryMuscle), MuscleGroup.getByID(secondaryMuscle), 
				EquipmentType.getByID(equipmentType), restTime);
			
			exercises.add(ex);
		}
		return exercises;
	}
}
