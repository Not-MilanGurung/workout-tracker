package beds.backend;

import java.sql.Time;

import beds.enums.SetType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Class representing a set of a exercise in a workout 
 */
public class Set {
	protected IntegerProperty setNo;
    protected IntegerProperty metricA;
    protected IntegerProperty metricB;
    protected SetType type;
    protected IntegerProperty restTime;
    protected BooleanProperty isComplete;

    /**
     * Constructor for when creating a new set for a exercise
     * @param restTime Rest time defined in the parent exercise
     */
    public Set(int setNo, int restTime) {
		this.setNo = new SimpleIntegerProperty(setNo);
		this.metricA = new SimpleIntegerProperty(0);
		this.metricB = new SimpleIntegerProperty(0);
        this.type = SetType.NORMAL;
        this.restTime = new SimpleIntegerProperty(restTime);
		this.isComplete = new SimpleBooleanProperty(false);
    }

    /**
     * Constructor for when reading from the database
     * @param metricA Value of metric A
     * @param metricB Value of metric B
     * @param type Type of set
     * @param restTime Rest time
     */
    public Set(int metricA, int metricB, SetType type, int restTime) {
        this.metricA = new SimpleIntegerProperty(metricA);
        this.metricB = new SimpleIntegerProperty(metricB);
        this.type = type;
        this.restTime = new SimpleIntegerProperty(restTime);
        this.isComplete = new SimpleBooleanProperty(true);
    }

	public Set(int setNo,int metricA, int metricB, SetType type, int restTime, boolean isComplete) {
		this.setNo = new SimpleIntegerProperty(setNo);
		this.metricA = new SimpleIntegerProperty(metricA);
		this.metricB = new SimpleIntegerProperty(metricB);
		this.type = type;
		this.restTime = new SimpleIntegerProperty(restTime);
		this.isComplete = new SimpleBooleanProperty(isComplete);
	}

	public IntegerProperty getSetNoProperty() {return this.setNo;}
	public int getSetNo() {return this.setNo.get();}
	public void setSetNo(int setNo) {this.setNo.set(setNo);}

    /**
     * Sets the value of {@link #metricA}
     * @param metricA First metric
     */
    public void setMetricA(int metricA) {this.metricA.set(metricA);}
    /**
     * Gets the value of the first metric of the set
     * @return {@link #metricA}
     */
    public int getMetricA() { return this.metricA.get();}
	/**
	 * Get the metricA integer property 
	 * @return {@link #metricA}
	 */
	public IntegerProperty getMetricAProperty() {return this.metricA;}
    
    /**
     * Sets the value of {@link #metricB}
     * @param metricB Second metric
     */
    public void setMetricB(int metricB) {this.metricB.set(metricB);}
    /**
     * Gets the value of the second metric of the set
     * @return {@link #metricB}
     */
    public int getMetricB() {return this.metricB.get();}
	public IntegerProperty getMetricBProperty() {return this.metricB;}

    /**
     * Set the {@link #restTime rest time} after the exercise is completed
     * @param restTime ({@link Time})
     */
    public void setRestTime(int restTime) {
        if (type == SetType.DROPSET) this.restTime.set(0); 
        else this.restTime.set(restTime);
    }
    /**
     * Get the rest time after the exercise is completed
     * @return {@link #restTime}
     */
    public int getRestTime() {return this.restTime.get();}
	public IntegerProperty getRestTimProperty() {return this.restTime;}

    /**
     * Acts as a flip switch that sets the {@link #isComplete} to true and false
     */
    public void setComplete() {
        this.isComplete.set(!this.isComplete.get()); 
    }
	public BooleanProperty getIsCompleteProperty() {return this.isComplete;}
    /**
     * Returns the status of the exercise
     * @return {@link #isComplete}
     */
    public boolean getIsComplete(){
        return this.isComplete.get();
    }

	/**
	 * Get the type of the set
	 * @return {@link #type}
	 */
	public SetType getType() {return this.type;}
	/**
	 * Set the type of the set
	 * @param type Type of the set
	 */
	public void setType(SetType type) {this.type = type;}
}