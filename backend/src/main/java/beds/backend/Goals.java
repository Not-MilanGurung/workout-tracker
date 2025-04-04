package beds.backend;

import java.time.LocalDateTime;

import beds.enums.GoalType;

public class Goals {
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
