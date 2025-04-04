package beds.enums;

/**
 * Enum representing the different types of goals that can be set by the user.
 * Each goal type has a unique ID associated with it.
 */
public enum GoalType {
	HEAVIESTSET(1), HIGHESTVOLUMESET(2), WEEKLYCONSISTENCY(3), WEEKLYVOLUME(4), 
	WEEKLYINTENSITY(5), WEEKLYFREQUENCY(6), WEIGHTLOSS(7), WEIGHTGAIN(8);
	private int id;

	GoalType(int id) {
		this.id = id;
	}
	/**
	 * Returns the ID of the goal type.
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the GoalType enum constant associated with the given ID.
	 * @param id The ID of the goal type.
	 * @return The GoalType enum constant, or null if no matching ID is found.
	 */
	public GoalType getByID(int id) {
		for (GoalType goalType : GoalType.values()) {
			if (goalType.getId() == id) {
				return goalType;
			}
		}
		return null;
	}
}
