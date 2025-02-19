package beds.backend;

public interface Goals {
    /** Method for when the goal is met */
    void metGoal();

    /** Method for setting the goal */
    void setGoal();

    /** Method for getting the goal */
    void getGoal();

    /** Set the dead line of the goal */
    void setGoalDeadline();

    /** Get the dead line of the goal */
    void getGoalDeadline();

    /** Set the datetime of when the goal was achieved */
    void setGoalAchievedDateTime();

    /** Get the datetime of when the goal was achieved */
    void getGoalAchievedDateTime();
}
