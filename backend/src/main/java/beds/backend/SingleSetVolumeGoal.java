package beds.backend;

import java.time.LocalDateTime;

import beds.enums.GoalType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class SingleSetVolumeGoal extends Goals {
	private DoubleProperty singleSetVolumeGoal;

	public SingleSetVolumeGoal(LocalDateTime goalDeadLine, double singleSetVolumeGoal) {
		super(goalDeadLine, GoalType.HIGHESTVOLUMESET);
		this.singleSetVolumeGoal = new SimpleDoubleProperty(singleSetVolumeGoal);
	}

	public boolean isGoalAchieved(double currentVolume) {
		if (currentVolume >= singleSetVolumeGoal.get()) {
			this.setGoalAchievedDateTime(LocalDateTime.now());
			return true;
		} else {
			return false;
		}
	}
}
