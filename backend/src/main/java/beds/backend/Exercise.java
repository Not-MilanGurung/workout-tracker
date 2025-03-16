package beds.backend;

import java.sql.Time;
import java.util.ArrayList;

import beds.enums.MetricType;
import beds.enums.MuscleGroup;

/** Represents a exercise */
public class Exercise {
    protected String name;
    protected MetricType metricAType;
    protected MetricType metricBType;
    protected MuscleGroup primaryMuscle;
    protected Time restTime;
    protected ArrayList<Set> sets;

	/**
	 * Basic constructor with only one metric and empty  sets list
	 * @param name of the exercise
	 * @param metricAType metric type 
	 * @param primaryMuscleGroup targeted by the exercise
	 */
    public Exercise(String name, MetricType metricAType, MuscleGroup primaryMuscleGroup){
        this.name = name;
        this.metricAType = metricAType;
        this.primaryMuscle = primaryMuscleGroup;
        this.sets = new ArrayList<Set>();
    }

	/**
	 * Basic constructor for exercise with two metrics and empty sets list 
	 * @param name of the exercise
	 * @param metricAType metric type of first metric
	 * @param metricBType metric typr of second metric
	 * @param primarMuscleGroup targeted by the exercise
	 */
    public Exercise(String name, MetricType metricAType, MetricType metricBType, MuscleGroup primarMuscleGroup){
        this.name = name;
        this.metricAType = metricAType;
        this.metricBType = metricBType;
        this.primaryMuscle = primarMuscleGroup;
        this.sets = new ArrayList<Set>();
    }

	/**
	 * Constructor for exercises with defined sets (i.e. recored workouts or routine)
	 * @param name Name of the exercise
	 * @param metricAType metric type of metric A
	 * @param metricBType metric type of metric B
	 * @param primarMuscleGroup primary musclegroup
	 * @param sets ArrayList of sets
	 * @param restTime rest time between sets
	 */
    public Exercise(String name, MetricType metricAType, MetricType metricBType, MuscleGroup primarMuscleGroup,
            ArrayList<Set> sets, Time restTime){
        this.name = name;
        this.metricAType = metricAType;
        this.metricBType = metricBType;
        this.primaryMuscle = primarMuscleGroup;
        this.sets = sets;
        this.restTime = restTime;
    }
    
    /** Test output */
    public void displayInfo(){
        System.out.println("Name: "+name);
        System.out.println("MetricA: "+metricAType);
        System.out.println("MetricB: "+metricBType);
        System.out.println("Primary Muscle: "+primaryMuscle);
        System.out.println("Rest time: "+restTime);
        System.out.println("No of sets: "+sets.size());
        for (int i = 1; i <= sets.size(); i++){
            System.out.println("Info of  set "+i);
            sets.get(i-1).displayInfo();
            System.out.println();
        }
    }

    /**
     * Add a set to the exercise
     * @param set
     */
    public void addSet(Set set){
        this.sets.add(set);
    }
    /**
     * Remove the set from the exercise
     * @param set
     */
    public void removeSet(Set set){
        this.sets.remove(set);
    }

    /**
     * Returns the name of the exercise
     * @return {@link #name}
     */
    public String getName() {return this.name;}
    /**
     * Sets the {@link #name} of the exercise
     * @param name String
     */
    public void setName(String name) {this.name = name;}

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
    public Time getRestTime() {return this.restTime;}
    /**
     * Sets the default rest time between sets of the exercise
     * @param restTime ({@link Time})
     */
    public void setRestTime(Time restTime) {this.restTime = restTime;}

    public ArrayList<Set> getSets(){
        return this.sets;
    }
}
