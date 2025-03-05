package beds.database;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.SQLException;

public class TableCreation {
    public static void main(String[] args) {
        DatabaseConnection.main(args);
        Connection con = DatabaseConnection.con;
        Statement stmt = null;
        int result = -1;
        // Creating the statement
		try{
			stmt = con.createStatement();

			// Creating table that stores the metric type of set of a exercise (weights, reps, etc)
			result = stmt.executeUpdate("CREATE TABLE MetricType ("+
					"MetricTypeID int PRIMARY KEY,"+ 
					"MetricType varchar(15) unique not null"+
					")");
				System.out.println(result);
					
			// Creating table that stores the string representing different muscle groups
			result = stmt.executeUpdate("CREATE TABLE MuscleGroup ("+
					"MuscleGroupID int PRIMARY KEY,"+ 
					"MuscleGroup varchar(15) unique not null"+
					")");
				System.out.println(result);

			// Creating table that stores the different equipment type
			result = stmt.executeUpdate("CREATE TABLE EquipmentType ("+
					"EquipmentID int PRIMARY KEY,"+ 
					"EquipmentType varchar(15) unique not null"+
					")");
				System.out.println(result);

					
			// Creating table that stores the different exercises that can be added to a workout
			// Missing reference to highest record sets
			result = stmt.executeUpdate("CREATE TABLE Exercises ("+
						"Id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),"+
						"Name varchar(80) not null," +
						"MetricAType int not null,"+
						"MetricBType int," +
						"PrimaryMuscle int,"+ 
						"SecondaryMuscle int,"+
						"EquipmentType int,"+
						"RestTime time,"+
						"CONSTRAINT FK_MetricType_Exercises_MetricA FOREIGN KEY (MetricAType) references MetricType(MetricTypeID),"+
						"CONSTRAINT FK_MetricType_Exercises_MetricB FOREIGN KEY (MetricBType) references MetricType(MetricTypeID),"+
						"CONSTRAINT FK_MuscleGroup_Exercises_PrimaryMuscle FOREIGN KEY (PrimaryMuscle) references MuscleGroup(MuscleGroupID),"+
						"CONSTRAINT FK_MuscleGroup_Exercises_SecondaryMuscle FOREIGN KEY (SecondaryMuscle) references MuscleGroup(MuscleGroupID),"+
						"CONSTRAINT FK_EquipmentType FOREIGN KEY (EquipmentType) references EquipmentType(EquipmentID)"+
						")");
						System.out.println(result);
			con.commit();
			con.close();
		}
		catch (SQLException e){
			e.printStackTrace(System.out);
		}
    }
}
