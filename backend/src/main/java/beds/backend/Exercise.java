package beds.backend;

import java.sql.Time;

import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/** Represents a exercise */
public class Exercise {
	protected IntegerProperty id;
    protected StringProperty name;
    protected MetricType metricAType;
    protected MetricType metricBType;
    protected MuscleGroup primaryMuscle;
    protected MuscleGroup secondaryMuscle;
	protected EquipmentType equipmentType;
    protected LongProperty restTime;

	public Exercise(int id, String name, MetricType metricA, MetricType metricB,
			MuscleGroup primaryMuscle, MuscleGroup secondaryMuscle, EquipmentType equipmentType,
			long restTime){
		
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.metricAType = metricA;
		this.metricBType = metricB;
		this.primaryMuscle = primaryMuscle;
		this.secondaryMuscle = secondaryMuscle;
		this.equipmentType = equipmentType;
		this.restTime = new SimpleLongProperty(restTime);
	}
    


    /**
     * Returns the name of the exercise
     * @return {@link #name}
     */
    public String getName() {return this.name.get();}
	public StringProperty getNamProperty() {return this.name;}
    /**
     * Sets the {@link #name} of the exercise
     * @param name String
     */
    public void setName(String name) {this.name.set(name);;}

    /**
     * Returns the {@link MetricType} of metric A of the sets of the exercise
     * @return {@link #metricAType}
     */
    public MetricType getMetricAType() {return this.metricAType;}
    /**
     * Sets the {@link MetricType} of {@link #metricAType metric A} of the sets of the exercise
     * @param metricType of {@link MetricType} class type
     */
    public void setMetricAType(MetricType metricType) {this.metricAType = metricType;}
    
    /**
     * Returns the {@link MetricType} of metric B of the sets of the exercise
     * @return {@link #metricBType}
     */
    public MetricType getMetricBType() {return this.metricBType;}
    /**
     * Sets the {@link MetricType} of {@link #metricBType metric b} of the sets of the exercise
     * @param metricType of {@link MetricType} class type
     */
    public void setMetricBType(MetricType metricType) {this.metricBType = metricType;}
    
    /**
     * Returns the primary {@link MuscleGroup muscle group} targeted by the exercise
     * @return {@link #primaryMuscle}
     */
    public MuscleGroup getPrimaryMuscle() {return this.primaryMuscle;}
    /**
     * Set the {@link #primaryMuscle primary muscle group} targeted by the exercise
     * @param muscleGroup
     */
    public void setPrimaryMuscle(MuscleGroup muscleGroup) {this.primaryMuscle = muscleGroup;}

    /**
     * Gets the default rest time between sets of the exercise
     * @return {@link #restTime}
     */
    public Time getRestTime() {return new Time(this.restTime.get());}
	public LongProperty getRestTimProperty() {return this.restTime;}
    /**
     * Sets the default rest time between sets of the exercise
     * @param restTime ({@link Time})
     */
    public void setRestTime(Long restTime) {this.restTime.set(restTime);}

}
