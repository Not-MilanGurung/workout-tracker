package beds.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


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
}
