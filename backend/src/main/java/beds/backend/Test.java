package beds.backend;

import java.sql.Time;

import beds.enums.MetricType;
import beds.enums.MuscleGroup;

public class Test {
    public static void main(String[] args) {
        Workout workout = new Workout();
        workout.setName("Test");

        workout.addExercise(new Exercise("Pushup", MetricType.REPS, MuscleGroup.CHEST));
        Exercise curExercise = workout.getExercise("Pushup");

        Set set1 = new Set(new Time(120));
        Set set2 = new Set(new Time(120));
        Set set3 = new Set(new Time(120));

        curExercise.addSet(set1);
        curExercise.addSet(set2);
        curExercise.addSet(set3);

        set1.setMetricA(12);
        set2.setMetricA(14);
        set3.setMetricA(11);

        workout.displayInfo();
    }
}
