package beds.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Query {
    public static void main(String[] args) {
        try {
            Connection con = DatabaseConnection.getConnection();
            Statement stmt = con.createStatement();

            ResultSet result = stmt.executeQuery("Select * from Exercises");
            while (result.next()){
                System.out.printf("%d %s %d %d %d %d %d %s\n",result.getInt(1), result.getString(2),
                result.getInt(3),result.getInt(4),result.getInt(5),result.getInt(6),result.getInt(7),result.getTime(8));
            }
        }
        catch (SQLException e){
            e.printStackTrace(System.out);
        }

    }
}
