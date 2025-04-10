package beds.backend;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import beds.database.DatabaseConnection;
import beds.enums.GoalType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class WeeklyFrequencyGoal extends Goals {
	private DoubleProperty consistencyGoal;


	public WeeklyFrequencyGoal(LocalDateTime goalDeadLine, double consistencyGoal) {
		super(goalDeadLine, GoalType.WEEKLYFREQUENCY);
		this.consistencyGoal = new SimpleDoubleProperty(consistencyGoal);
	}

	public void setConsistencyGoal(double consistencyGoal) {
		this.consistencyGoal.set(consistencyGoal);
	}
	public double getConsistencyGoal() {
		return consistencyGoal.get();
	}

	public boolean isGoalAchievedThisWeek(LocalDateTime dateTimeOfWeek) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement stmt = con.prepareStatement("SELECT COUNT(*) AS WorkoutCount FROM Workouts WHERE YEARWEEK(DateTime, 1) = YEARWEEK(?, 1) AND UserID = ?");
		stmt.setTimestamp(0, Timestamp.valueOf(dateTimeOfWeek));
		stmt.setInt(1, DatabaseConnection.getUserID());
		ResultSet res = stmt.executeQuery();

		while (res.next()) {
			int workoutCount = res.getInt("WorkoutCount");
			if (workoutCount >= consistencyGoal.get()) {
				this.setGoalAchievedDateTime(LocalDateTime.now());
				return true;
			}
		}
		return false;
	}
	
}
