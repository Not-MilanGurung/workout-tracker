package beds.backend;


import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
    protected IntegerProperty restTime;

	public Exercise(int id, String name, MetricType metricA, MetricType metricB,
			MuscleGroup primaryMuscle, MuscleGroup secondaryMuscle, EquipmentType equipmentType,
			int restTime){
		
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.metricAType = metricA;
		this.metricBType = metricB;
		this.primaryMuscle = primaryMuscle;
		this.secondaryMuscle = secondaryMuscle;
		this.equipmentType = equipmentType;
		this.restTime = new SimpleIntegerProperty(restTime);
	}

	public Exercise( String name, MetricType metricA, MetricType metricB,
			MuscleGroup primaryMuscle, MuscleGroup secondaryMuscle, EquipmentType equipmentType,
			int restTime){
		
		this.id = new SimpleIntegerProperty(0);
		this.name = new SimpleStringProperty(name);
		this.metricAType = metricA;
		this.metricBType = metricB;
		this.primaryMuscle = primaryMuscle;
		this.secondaryMuscle = secondaryMuscle;
		this.equipmentType = equipmentType;
		this.restTime = new SimpleIntegerProperty(restTime);
	}
	public int getID() {return this.id.get();}

    /**
     * Returns the name of the exercise
     * @return {@link #name}
     */
    public String getName() {return this.name.get();}
	public void setName(String name) {this.name.set(name);}
	/**
	 * Get name property for javafx
	 * @return {@link #name}
	 */
	public StringProperty getNameProperty() {return this.name;}

    /**
     * Returns the {@link MetricType} of metric A of the sets of the exercise
     * @return {@link #metricAType}
     */
    public MetricType getMetricAType() {return this.metricAType;}
	public void setMetricAType(MetricType metricA) {this.metricAType = metricA;}
    /**
     * Returns the {@link MetricType} of metric B of the sets of the exercise
     * @return {@link #metricBType}
     */
    public MetricType getMetricBType() {return this.metricBType;}
	public void setMetricBType(MetricType metricB) {this.metricBType = metricB;}

    
    /**
     * Returns the primary {@link MuscleGroup muscle group} targeted by the exercise
     * @return {@link #primaryMuscle}
     */
    public MuscleGroup getPrimaryMuscle() {return this.primaryMuscle;}
	public void setPrimaryMuscle(MuscleGroup primaryMuscle) {this.primaryMuscle = primaryMuscle;}
    /**
     * Returns the secondary {@link MuscleGroup muscle group} targeted by the exercise
     * @return {@link #secondaryMuscle}
     */
    public MuscleGroup getSecondaryMuscle() {return this.secondaryMuscle;}
	public void setSecondaryMuscle(MuscleGroup secondaryMuscle) {this.secondaryMuscle = secondaryMuscle;}

	/**
	 * Returns the equipment type used for the exercise
	 * @return {@link #equipmentType}
	 */
	public EquipmentType getEquipmentType() {return this.equipmentType;}
	public void setEquipmentType(EquipmentType equipmentType) {this.equipmentType = equipmentType;}

    /**
     * Gets the default rest time between sets of the exercise
     * @return {@link #restTime}
     */
    public int getRestTime() {return this.restTime.get();}
	/**
	 * Get the rest time property for javafx
	 * @return
	 */
	public IntegerProperty getRestTimProperty() {return this.restTime;}
    /**
     * Sets the default rest time between sets of the exercise
     * @param restTime 
     */
    public void setRestTime(int restTime) {this.restTime.set(restTime);}

}
