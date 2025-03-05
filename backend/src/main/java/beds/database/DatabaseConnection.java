package beds.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	public static Connection con;
    public static void main(String[] args) {
		// Connetction creation
        try {
            //Registering the HSQLDB JDBC driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            //Creating the connection with HSQLDB
            con = DriverManager.getConnection("jdbc:hsqldb:file:backend/target/database/testdb", "SA", "");
            if (con!= null){
               System.out.println("Connection created successfully");
               
            }else{
               System.out.println("Problem with creating connection");
            }
            
		}  
		catch (Exception e) {
            e.printStackTrace(System.out);
        }
		
    }
}
