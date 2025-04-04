package beds.database;

import java.sql.Statement;


import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is responsible for creating the tables in the database.
 * It creates the following tables:
 * <ul>
 * <li>MetricType</li>
 * <li>MuscleGroup</li>
 * <li>EquipmentType</li>
 * <li>Users</li>
 * <li>GoalType</li>
 * <li>Goals</li>
 * <li>Exercises</li>
 * <li>Workouts</li>
 * <li>WorkoutExercise</li>
 * <li>SetType</li>
 * <li>Sets</li>
 * </ul>
 */
public class TableCreation {
    public static void main(Connection mainCon) {
        Connection con = mainCon;
        Statement stmt = null;
        // Creating the statement
		try{
			stmt = con.createStatement();

			// Creating table that stores the metric type of set of a exercise (weights, reps, etc)
			stmt.executeUpdate("CREATE TABLE MetricType ("+
					"MetricTypeID int PRIMARY KEY,"+ 
					"MetricType varchar(15) unique not null"+
					")");
					
			// Creating table that stores the string representing different muscle groups
			stmt.executeUpdate("CREATE TABLE MuscleGroup ("+
				"MuscleGroupID int PRIMARY KEY,"+ 
				"MuscleGroup varchar(15) unique not null"+
				")");
			
			// Creating table that stores the different equipment type
			stmt.executeUpdate("CREATE TABLE EquipmentType ("+
				"EquipmentID int PRIMARY KEY,"+ 
				"EquipmentType varchar(15) unique not null"+
				")");
			
			stmt.executeUpdate("CREATE TABLE Users ("+
					"UserID INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,"+
					"Username VARCHAR(200) UNIQUE," +
					"Hash VARCHAR(255) NOT NULL"+
					")");
			
			stmt.executeUpdate("CREATE TABLE GoalType ("+
				"GoalTypeID int PRIMARY KEY,"+ 
				"GoalType varchar(30) unique not null"+
				")");
			
			stmt.executeUpdate("CREATE TABLE Goals ("+
					"GoalID INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,"+
					"UserID INT DEFAULT 0 NOT NULL,"+
					"GoalTypeID INT NOT NULL,"+
					"DeadLine DateTime," +
					"AchievedDateTime DateTime," +
					"StartDateTime DateTime NOT NULL," +
					"GoalValue DECIMAL(5,2) NOT NULL,"+
					"CONSTRAINT FK_Users_Goals FOREIGN KEY (UserID) references Users(UserID) ON DELETE CASCADE,"+
					"CONSTRAINT FK_GoalType_Goals FOREIGN KEY (GoalTypeID) references GoalType(GoalTypeID)"+
					")");
				
				// Creating table that stores the different exercises that can be added to a workout
			// Missing reference to highest record sets
			stmt.executeUpdate("CREATE TABLE Exercises ("+
						"Id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),"+
						"UserID INT DEFAULT 0 not null ," +
						"Name varchar(80) not null," +
						"MetricAType int not null,"+
						"MetricBType int," +
						"PrimaryMuscle int,"+ 
						"SecondaryMuscle int,"+
						"EquipmentType int,"+
						"RestTime INT DEFAULT 0,"+
						"CONSTRAINT FK_Users_Exercises FOREIGN KEY (UserID) references Users(UserID) ON DELETE CASCADE,"+
						"CONSTRAINT FK_MetricType_Exercises_MetricA FOREIGN KEY (MetricAType) references MetricType(MetricTypeID),"+
						"CONSTRAINT FK_MetricType_Exercises_MetricB FOREIGN KEY (MetricBType) references MetricType(MetricTypeID),"+
						"CONSTRAINT FK_MuscleGroup_Exercises_PrimaryMuscle FOREIGN KEY (PrimaryMuscle) references MuscleGroup(MuscleGroupID),"+
						"CONSTRAINT FK_MuscleGroup_Exercises_SecondaryMuscle FOREIGN KEY (SecondaryMuscle) references MuscleGroup(MuscleGroupID),"+
						"CONSTRAINT FK_EquipmentType FOREIGN KEY (EquipmentType) references EquipmentType(EquipmentID)"+
						")");
	

			stmt.executeUpdate("CREATE TABLE Workouts ("+
					"Id INT PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1),"+
					"UserID INT DEFAULT 0 not null,"+
					"Name varchar(80) not null," +
					"CompletionTime INT DEFAULT 0 not null,"+
					"DateTime DateTime not null,"+ 
					"IsRoutine BOOLEAN DEFAULT FALSE NOT NULL,"+
					"CONSTRAINT FK_Users_Workout FOREIGN KEY (UserID) references Users(UserID) ON DELETE CASCADE"+ 
					")");

					
			stmt.executeUpdate("CREATE TABLE WorkoutExercise ("+
					"Id int PRIMARY KEY GENERATED BY DEFAULT AS IDENTITY,"+
					"WorkoutID INT,"+
					"ExerciseID INT NOT NULL," +
					"OrderNo INT,"+
					"CONSTRAINT FK_Workouts_WorkoutExercise FOREIGN KEY (WorkoutID) REFERENCES Workouts(Id) ON DELETE CASCADE,"+
					"CONSTRAINT FK_Exercises_WorkoutExercise FOREIGN KEY (ExerciseID) REFERENCES Exercises(Id) ON DELETE CASCADE,"+
					")");


			stmt.executeUpdate("CREATE TABLE SetType ("+
							"SetTypeID int PRIMARY KEY,"+ 
							"SetTypeType varchar(20) unique not null"+
							")");
	
					
			stmt.executeUpdate("CREATE TABLE Sets ("+
					"SetNo INT,"+
					"WorkoutExerciseID INT," +
					"MetricA INT NOT NULL,"+
					"MetricB INT,"+
					"SetType INT NOT NULL,"+
					"RestTime INT DEFAULT 0,"+
					"CONSTRAINT PK_Sets PRIMARY KEY (WorkoutExerciseID, SetNo),"+
					"CONSTRAINT FK_WorkoutExercise_Sets FOREIGN KEY (WorkoutExerciseID) REFERENCES WorkoutExercise(Id) ON DELETE CASCADE,"+
					"CONSTRAINT FK_SetType_Sets FOREIGN KEY (SetType) REFERENCES SetType(SetTypeID)"+
					")");



			con.commit();
			System.out.println("Tables created");
		}
		catch (SQLException e){
			e.printStackTrace(System.out);
		}
    }
}
