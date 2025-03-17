package beds.backend;

import java.util.ArrayList;

import beds.enums.EquipmentType;
import beds.enums.MetricType;
import beds.enums.MuscleGroup;

public class StoredExercise extends Exercise {
	private ArrayList<Set> sets;

	public StoredExercise(int id, String name, MetricType metricA, MetricType metricB, MuscleGroup primaryMuscle,
			MuscleGroup secondaryMuscle, EquipmentType equipmentType, long restTime) {
				
		super(id, name, metricA, metricB, primaryMuscle, secondaryMuscle, equipmentType, restTime);
	}

	public void loadSets(){
		
	}
	
}
