package beds.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;

import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;
import beds.enums.SetType;

public class InitiliseDefaultTableData {
    private static Connection con;
    private static PreparedStatement stmt;

    public static void initialiseMetricTypeTable() throws SQLException{
        stmt = con.prepareStatement("INSERT INTO MetricType Values (?, ?)");
        for (MetricType type : MetricType.values()){
            stmt.setInt(1, type.getID());
            stmt.setString(2, type.toString());
            stmt.addBatch();
        }
        stmt.executeBatch();
        stmt.close();
    }

    public static void initialiseMuscleGroupTable() throws SQLException{
        stmt = con.prepareStatement("INSERT INTO MuscleGroup Values (?, ?)");
        for (MuscleGroup group : MuscleGroup.values()){
            stmt.setInt(1, group.getID());
            stmt.setString(2, group.toString());
            stmt.addBatch();
        }
        stmt.executeBatch();
        stmt.close();
    }

    public static void initialiseEquipmentTypeTable() throws SQLException{
        stmt = con.prepareStatement("INSERT INTO EquipmentType Values (?, ?)");
        for (EquipmentType type: EquipmentType.values()){
            stmt.setInt(1, type.getID());
            stmt.setString(2, type.toString());
            stmt.addBatch();
        }
        stmt.executeBatch();
        stmt.close();
    }

	public static void initialiseSetTypeTable() throws SQLException {
		stmt = con.prepareStatement("INSERT INTO SetType Values (?, ?)");
        for (SetType type: SetType.values()){
            stmt.setInt(1, type.getID());
            stmt.setString(2, type.toString());
            stmt.addBatch();
        }
        stmt.executeBatch();
        stmt.close();
	}
   

    public static void main(String[] args) {
        DatabaseConnection.main(args);
        con = DatabaseConnection.con;
		try{
            initialiseMetricTypeTable();
			System.out.println("MetricTypeTable initialised");
			initialiseMuscleGroupTable();
			System.out.println("MuscleGroupTable initialised");
			initialiseEquipmentTypeTable();
			System.out.println("EquipmentTypeTable initialised");
			defaultExercises();
			System.out.println("Default exercise table initialised");
			initialiseSetTypeTable();
			System.out.println("SetType table initialised");

			
            con.commit();
			con.close();
		}
		catch (SQLException e){
			e.printStackTrace(System.out);
		}
    }

	public static void defaultExercises() throws SQLException{
        stmt = con.prepareStatement("INSERT INTO Exercises (Name, MetricAType, MetricBType, PrimaryMuscle, SecondaryMuscle, EquipmentType, RestTime) Values (?, ?, ?, ?, ?, ?, ?)");
        
        addBatchToExerciseInsert(stmt, "Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Ab Wheel", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Arnold Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Ball Slams", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.FULL_BODY.getID(), -1,
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Battle Ropes", 
						MetricType.TIME.getID(), -1, 
						MuscleGroup.CARDIO.getID(), MuscleGroup.FULL_BODY.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Bench Dip", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.TRICEPS.getID(), MuscleGroup.CHEST.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Biceps Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.BICEPS.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Biceps Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.BICEPS.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Biceps Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.BICEPS.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Bent Over Row", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Bent Over Row", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Preacher Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.BICEPS.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Preacher Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.BICEPS.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Preacher Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.BICEPS.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Bicycle Crunch", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), -1,
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Box Jump", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.FULL_BODY.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Box Squat", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.QUADRICEPS.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Box Squat", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Burpee", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.FULL_BODY.getID(), MuscleGroup.CARDIO.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Cable Crunch", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.ABDOMINAL.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chest Dip", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.CHEST.getID(), MuscleGroup.TRICEPS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chest Dip", 
						MetricType.ASSISTED.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.TRICEPS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chest Dip", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.TRICEPS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chest Fly", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chest Fly", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chin Up", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.LATS.getID(), MuscleGroup.BICEPS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chin Up", 
						MetricType.ASSISTED.getID(), MetricType.REPS.getID(), 
						MuscleGroup.LATS.getID(), MuscleGroup.BICEPS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Chin Up", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.LATS.getID(), MuscleGroup.BICEPS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Crunch", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), -1,
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Crunch", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Deadlift", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.FULL_BODY.getID(), MuscleGroup.QUADRICEPS.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Decline Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Decline Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Decline Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Decline Crunch", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), -1,
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Dumbbell Row", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.UPPER_BACK.getID(), MuscleGroup.LOWER_BACK.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Elliptical Trainer", 
						MetricType.DISTANCE.getID(), MetricType.TIME.getID(), 
						MuscleGroup.CARDIO.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Face Pull", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.UPPER_BACK.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Front Raise", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), MuscleGroup.TRAPS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Front Raise", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), MuscleGroup.TRAPS.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Front Squat", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.ABDOMINAL.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Hanging Knee Raise", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Hanging Leg Raise", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), MuscleGroup.FOREARMS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Heel Taps", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.OBLIQUES.getID(), MuscleGroup.ABDOMINAL.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Hip Thrust", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.GLUTES.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Hip Thrust", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.GLUTES.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Incline Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Incline Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Incline Bench Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Iso-Lateral Chest Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Incline Chest Fly", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Incline Chest Fly", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Incline Push Ups", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Iso-Lateral Row", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.UPPER_BACK.getID(), MuscleGroup.LATS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Jump Rope", 
						MetricType.TIME.getID(), -1, 
						MuscleGroup.CARDIO.getID(), MuscleGroup.CALVES.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Jump Squat", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.CARDIO.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Jumping Jack", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.FULL_BODY.getID(), MuscleGroup.CARDIO.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Jumping Lunge", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.CARDIO.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Kettlebell Swing", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.FULL_BODY.getID(), MuscleGroup.QUADRICEPS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lat Pulldown - Close Grip", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.LATS.getID(), MuscleGroup.UPPER_BACK.getID(),
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lat Pulldown - Medium Grip", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.LATS.getID(), MuscleGroup.UPPER_BACK.getID(),
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lat Pulldown - Wide Grip", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.LATS.getID(), MuscleGroup.UPPER_BACK.getID(),
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lateral Raise", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), MuscleGroup.TRAPS.getID(),
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lateral Raise", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), MuscleGroup.TRAPS.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Leg Extension", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.QUADRICEPS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Leg Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.QUADRICEPS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lunge", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lunge", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lunge", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Lying Leg Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.HAMSTRINGS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Leg Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.HAMSTRINGS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Mountain Climber", 
						MetricType.TIME.getID(), -1, 
						MuscleGroup.FULL_BODY.getID(), MuscleGroup.ABDOMINAL.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Overhead Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Overhead Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Overhead Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(), 
						MuscleGroup.SHOULDERS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Plank", 
						MetricType.TIME.getID(), -1, 
						MuscleGroup.ABDOMINAL.getID(), -1,
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Pull Up", 
						MetricType.REPS.getID(), -1, 
						MuscleGroup.LATS.getID(), MuscleGroup.BICEPS.getID(),
						EquipmentType.OTHER.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Pull Up", 
						MetricType.ASSISTED.getID(), MetricType.REPS.getID(),
						MuscleGroup.LATS.getID(), MuscleGroup.BICEPS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Pull Up", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.LATS.getID(), MuscleGroup.BICEPS.getID(),
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Push Up", 
						MetricType.REPS.getID(), -1,
						MuscleGroup.CHEST.getID(), MuscleGroup.SHOULDERS.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Reverse Fly", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Reverse Fly", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Romanian Deadlift", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.HAMSTRINGS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Romanian Deadlift", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.HAMSTRINGS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Seated Cable Row - Bar Grip", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Seated Cable Row - Bar Wide Grip", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Seated Cable Row - V Grip", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Seated Calf Raise", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.CALVES.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Seated Dip Machine", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.CHEST.getID(), MuscleGroup.TRICEPS.getID(),
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Seated Incline Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.BICEPS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Seated Shoulder Press", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.SHOULDERS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Shrug", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRAPS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Shrug", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRAPS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Shrug", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRAPS.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Skullcrusher", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Skullcrusher", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Squat", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.BARBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Squat", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.DUMBBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Squat (Band)", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.OTHER.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Squat", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.MACHINE.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Squat", 
						MetricType.REPS.getID(), -1,
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Standing Calf Raise", 
						MetricType.REPS.getID(), -1,
						MuscleGroup.CALVES.getID(), -1,
						EquipmentType.NONE.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Standing Calf Raise", 
						MetricType.REPS.getID(), -1,
						MuscleGroup.CALVES.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Standing Calf Raise", 
						MetricType.REPS.getID(), -1,
						MuscleGroup.CALVES.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Standing Calf Raise", 
						MetricType.REPS.getID(), -1,
						MuscleGroup.CALVES.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "T-Bar Row", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.UPPER_BACK.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Treadmill", 
						MetricType.DISTANCE.getID(), MetricType.TIME.getID(),
						MuscleGroup.CARDIO.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Triceps Extension", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Triceps Extension", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Triceps Extension", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Triceps Kickback", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Triceps Kickback", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Triceps Pushdown", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.TRICEPS.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Wall Sit", 
						MetricType.TIME.getID(), -1,
						MuscleGroup.QUADRICEPS.getID(), MuscleGroup.GLUTES.getID(),
						EquipmentType.NONE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Wrist Roller", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.OTHER.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Wrist Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Wrist Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Wrist Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Wrist Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);

        addBatchToExerciseInsert(stmt, "Wrist Reverse Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.DUMBBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Wrist Reverse Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.BARBELL.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Wrist Reverse Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.CABLE.getID(), 
						null);
        addBatchToExerciseInsert(stmt, "Wrist Reverse Curl", 
						MetricType.WEIGHTS.getID(), MetricType.REPS.getID(),
						MuscleGroup.FOREARMS.getID(), -1,
						EquipmentType.MACHINE.getID(), 
						null);


		stmt.executeBatch();
		stmt.close();
    }
    
    private static void addBatchToExerciseInsert(PreparedStatement stmt, String name, 
					int metricAType, int metricBType, 
					int primaryMuscle, int secondaryMuscle,
					int equipmentType, 
					Time restTime) throws SQLException{
		
        stmt.setString(1, name);
        stmt.setInt(2, metricAType);
		if (metricBType != -1)
			stmt.setInt(3, metricBType);
		else
			stmt.setNull(3, Types.INTEGER);
        stmt.setInt(4, primaryMuscle);
		if (secondaryMuscle != -1)
			stmt.setInt(5, secondaryMuscle);
		else 
			stmt.setNull(5, Types.INTEGER);
		stmt.setInt(6, equipmentType);
        stmt.setTime(7, restTime);
        stmt.addBatch();
    }
}

