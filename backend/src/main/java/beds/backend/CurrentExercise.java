package beds.backend;

import java.util.ArrayList;

import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;

public class CurrentExercise extends Exercise {
	private ArrayList<Set> sets;

	public CurrentExercise(int id, String name, MetricType metricA, MetricType metricB, MuscleGroup primaryMuscle,
			MuscleGroup secondaryMuscle, EquipmentType equipmentType, int restTime) {
				
		super(id, name, metricA, metricB, primaryMuscle, secondaryMuscle, equipmentType, restTime);
		this.sets = new ArrayList<Set>();
	}

	public CurrentExercise(Exercise e){
		super(e.getID(), e.getName(), e.getMetricAType(), e.getMetricBType(), e.getPrimaryMuscle(), 
			e.getSecondaryMuscle(), e.getEquipmentType(), e.getRestTime());
		this.sets = new ArrayList<Set>();
	}

	/**
	 * Adds the set to the arraylist
	 * @param s
	 */
	public void addSet(Set s) {sets.add(s);}
	/**
	 * Removes the set from the arraylist
	 * @param s
	 */
	public void removeSet(Set s) {sets.remove(s);}

	/**
	 * Returns the array list of sets
	 * @return {@link #sets}
	 */
	public ArrayList<Set> getSets() {return this.sets;}
	
}
