package beds.backend;

import java.sql.Time;
import java.util.ArrayList;

import beds.enums.MetricType;
import beds.enums.MuscleGroup;

public class Exercise {
    protected String name;
    protected MetricType metricAType;
    protected MetricType metricBType;
    protected MuscleGroup primaryMuscle;
    protected Time restTime;
    protected ArrayList<Set> sets;

    public Exercise(String name, MetricType metricAType, MuscleGroup primaryMuscleGroup){
        this.name = name;
        this.metricAType = metricAType;
        this.primaryMuscle = primaryMuscleGroup;
    }

    public Exercise(String name, MetricType metricAType, MetricType metricBType, MuscleGroup primarMuscleGroup){
        this.name = name;
        this.metricAType = metricAType;
        this.metricBType = metricBType;
        this.primaryMuscle = primarMuscleGroup;
    }

    public Exercise(String name, MetricType metricAType, MetricType metricBType, MuscleGroup primarMuscleGroup,
            ArrayList<Set> sets, Time restTime){
        this.name = name;
        this.metricAType = metricAType;
        this.metricBType = metricBType;
        this.primaryMuscle = primarMuscleGroup;
        this.sets = sets;
        this.restTime = restTime;
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
