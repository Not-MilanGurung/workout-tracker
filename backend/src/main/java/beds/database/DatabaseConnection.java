package beds.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beds.backend.Workout;

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

	public static void setID(String username){
		Connection con = getConnection();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT UserID FROM Users WHERE Username=?");
			stmt.setString(1, username);
			ResultSet res = stmt.executeQuery();

			if (res.next()){
				userID = res.getInt("UserID");
			}
			stmt.close();
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static List<Workout> getAllWorkouts() throws SQLException{
		List<Workout> tenRecentWorkout = new ArrayList<Workout>();
		Connection con = getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM Workouts WHERE UserID=0 OR UserID = ?");
		stmt.setInt(1, userID);
		ResultSet res = stmt.executeQuery();
	
		Workout w = new Workout();
		while (res.next()){
			w.setName(res.getString("Name"));
			w.setDate(res.getString("DateTime"));
		}
	}
}
