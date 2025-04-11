package beds.backend;

import java.time.LocalDateTime;

import beds.enums.GoalType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Goals {
	private IntegerProperty goalID;
	private IntegerProperty userID;
	private LocalDateTime goalDeadLine;
	private LocalDateTime goalAchievedDateTime;
	private LocalDateTime goalStartDateTime;
	private GoalType goalType;


	public Goals(LocalDateTime goalDeadLine, GoalType goalType) {
		this.goalDeadLine = goalDeadLine;
		this.goalAchievedDateTime = null;
		this.goalStartDateTime = LocalDateTime.now();
		this.goalType = goalType;
	}
	
	public LocalDateTime getGoalDeadLine() {
		return goalDeadLine;
	}
	public void setGoalDeadLine(LocalDateTime goalDeadLine) {
		this.goalDeadLine = goalDeadLine;
	}
	public int getGoalID() {
		return goalID.get();
	}
	public void setGoalID(int goalID) {
		this.goalID = new SimpleIntegerProperty(goalID);
	}
	public int getUserID() {
		return userID.get();
	}
	public void setUserID(int userID) {
		this.userID = new SimpleIntegerProperty(userID);
	}
	public LocalDateTime getGoalAchievedDateTime() {
		return goalAchievedDateTime;
	}
	public void setGoalAchievedDateTime(LocalDateTime goalAchievedDateTime) {
		this.goalAchievedDateTime = goalAchievedDateTime;
	}
	public LocalDateTime getGoalStartDateTime() {
		return goalStartDateTime;
	}
	public void setGoalStartDateTime(LocalDateTime goalStartDateTime) {
		this.goalStartDateTime = goalStartDateTime;
	}
	
	public boolean isGoalAchieved() {
		return goalAchievedDateTime != null;
	}
	public boolean isGoalExpired() {
		return LocalDateTime.now().isAfter(goalDeadLine);
	}
	public boolean isGoalActive() {
		return !isGoalExpired() && !isGoalAchieved();
	}
	public GoalType getGoalType() {
		return goalType;
	}
	public void setGoalType(GoalType goalType) {
		this.goalType = goalType;
	}
} 
