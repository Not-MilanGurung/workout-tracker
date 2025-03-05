package beds.backend;

import java.sql.Time;

import beds.enums.SetType;

/**
 * Class representing a set of a exercise in a workout 
 */
public class Set {
    protected int metricA;
    protected int metricB;
    protected SetType type;
    protected Time restTime;
    protected boolean isComplete;

    /**
     * Constructor for when creating a new set for a exercise
     * @param restTime Rest time defined in the parent exercise
     */
    public Set(Time restTime) {
        this.type = SetType.NORMAL;
        this.restTime = restTime;
    }

    /**
     * Constructor for when reading from the database
     * @param metricA Value of metric A
     * @param metricB Value of metric B
     * @param type Type of set
     * @param restTime Rest time
     */
    public Set(int metricA, int metricB, SetType type, Time restTime) {
        this.metricA = metricA;
        this.metricB = metricB;
        this.type = type;
        this.restTime = restTime;
        this.isComplete = true;
    }

    public void displayInfo(){
        System.out.println("MetricA: "+metricA);
        System.out.println("MetricB: "+metricB);
        System.out.println("Set Type: "+type);
        System.out.println("Rest Time: "+restTime);
        System.out.println("Completion status: "+isComplete);
    }

    /**
     * Sets the value of {@link #metricA}
     * @param metricA First metric
     */
    public void setMetricA(int metricA) {
        this.metricA = metricA;
    }
    /**
     * Gets the value of the first metric of the set
     * @return {@link #metricA}
     */
    public int getMetricA() {
        return this.metricA;
    }
    
    /**
     * Sets the value of {@link #metricB}
     * @param metricB Second metric
     */
    public void setMetricB(int metricB) {
        this.metricB = metricB;
    }
    /**
     * Gets the value of the second metric of the set
     * @return {@link #metricB}
     */
    public int getMetricB() {
        return this.metricB;
    }

    /**
     * Set the {@link #restTime rest time} after the exercise is completed
     * @param restTime ({@link Time})
     */
    public void setRestTime(Time restTime) {
        if (type == SetType.DROPSET) this.restTime = new Time(0);
        else this.restTime = restTime;
    }
    /**
     * Get the rest time after the exercise is completed
     * @return {@link #restTime}
     */
    public Time getRestTime() {
        return this.restTime;
    }

    /**
     * Acts as a flip switch that sets the {@link #isComplete} to true and false
     */
    public void setComplete() {
        this.isComplete = !(this.isComplete);
    }

    /**
     * Returns the status of the exercise
     * @return {@link #isComplete}
     */
    public boolean getIsComplete(){
        return this.isComplete;
    }
}