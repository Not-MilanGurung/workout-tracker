package beds.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import beds.backend.Goals;
import beds.backend.HeaviestSetGoals;
import beds.backend.WeeklyFrequencyGoal;
import beds.enums.GoalType;

public class GoalDAO {

    public static void saveGoal(Goals goal) throws SQLException {
        String sql = "INSERT INTO Goals (UserID, GoalTypeID, DeadLine, AchievedDateTime, StartDateTime, GoalValue) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, goal.getUserID());
            stmt.setInt(2, goal.getGoalType().getId());
            stmt.setTimestamp(3, Timestamp.valueOf(goal.getGoalDeadLine()));
            stmt.setTimestamp(4, goal.getGoalAchievedDateTime() == null ? null : Timestamp.valueOf(goal.getGoalAchievedDateTime()));
            stmt.setTimestamp(5, Timestamp.valueOf(goal.getGoalStartDateTime()));

            if (goal instanceof HeaviestSetGoals) {
                stmt.setDouble(6, ((HeaviestSetGoals) goal).getHeaviestWeightGoal());
            } else if (goal instanceof WeeklyFrequencyGoal) {
                stmt.setDouble(6, ((WeeklyFrequencyGoal) goal).getConsistencyGoal());
            }

            stmt.executeUpdate();
        }
    }

	public static List<Goals> getGoalsByUserID(int userID) throws SQLException {
		String sql = "SELECT * FROM Goals WHERE UserID = ?";
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			List<Goals> goalsList = new ArrayList<>();
			while (rs.next()) {
				int goalID = rs.getInt("GoalID");
				int goalTypeID = rs.getInt("GoalTypeID");
				Timestamp deadLine = rs.getTimestamp("DeadLine");
				Timestamp achievedDateTime = rs.getTimestamp("AchievedDateTime");
				Timestamp startDateTime = rs.getTimestamp("StartDateTime");
				double goalValue = rs.getDouble("GoalValue");

				Goals goal;
				if (goalTypeID == GoalType.HEAVIESTSET.getId()) {
					goal = new HeaviestSetGoals(deadLine.toLocalDateTime(), goalValue);
				} else if (goalTypeID == GoalType.WEEKLYFREQUENCY.getId()) {
					goal = new WeeklyFrequencyGoal(deadLine.toLocalDateTime(), goalValue);
				} else {
					continue; // Unknown goal type
				}

				goal.setUserID(userID);
				goal.setGoalID(goalID);
				goal.setGoalAchievedDateTime(achievedDateTime != null ? achievedDateTime.toLocalDateTime() : null);
				goal.setGoalStartDateTime(startDateTime.toLocalDateTime());

				goalsList.add(goal);
			}
			return goalsList;
		}
	}

	public static void updateGoal(Goals goal) throws SQLException {
		String sql = "UPDATE Goals SET GoalTypeID = ?, DeadLine = ?, AchievedDateTime = ?, StartDateTime = ?, GoalValue = ? " +
					 "WHERE GoalID = ? AND UserID = ?";
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setInt(1, goal.getGoalType().getId());
			stmt.setTimestamp(2, Timestamp.valueOf(goal.getGoalDeadLine()));
			stmt.setTimestamp(3, goal.getGoalAchievedDateTime() == null ? null : Timestamp.valueOf(goal.getGoalAchievedDateTime()));
			stmt.setTimestamp(4, Timestamp.valueOf(goal.getGoalStartDateTime()));
			if (goal instanceof HeaviestSetGoals) {
				stmt.setDouble(5, ((HeaviestSetGoals) goal).getHeaviestWeightGoal());
			} else if (goal instanceof WeeklyFrequencyGoal) {
				stmt.setDouble(5, ((WeeklyFrequencyGoal) goal).getConsistencyGoal());
			}
			stmt.setInt(6, goal.getGoalID());
			stmt.setInt(7, goal.getUserID());
			stmt.executeUpdate();
		}
	}

    // Add load/edit/delete methods if needed
}

