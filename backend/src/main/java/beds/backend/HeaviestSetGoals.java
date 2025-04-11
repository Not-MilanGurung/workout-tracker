package beds.backend;

import java.time.LocalDateTime;

import beds.enums.GoalType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class HeaviestSetGoals extends Goals {
	private DoubleProperty heaviestWeightGoal;

	public HeaviestSetGoals(LocalDateTime goalDeadLine, double heaviestWeightGoal) {
		super(goalDeadLine, GoalType.HEAVIESTSET);
		this.heaviestWeightGoal = new SimpleDoubleProperty(heaviestWeightGoal);
	}
	
	public boolean isGoalAchieved(double currentWeight) {
		if (currentWeight >= heaviestWeightGoal.get()) {
			this.setGoalAchievedDateTime(LocalDateTime.now());
			return true;
		} else {
			return false;
		}
	}

	public double getHeaviestWeightGoal() {
		return heaviestWeightGoal.get();
	}
	public void setHeaviestWeightGoal(double heaviestWeightGoal) {
		this.heaviestWeightGoal.set(heaviestWeightGoal);
	}
}