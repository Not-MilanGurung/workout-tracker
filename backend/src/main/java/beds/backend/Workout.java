package beds.backend;

import java.util.ArrayList;

public class Workout {
    protected String name;
    protected ArrayList<Exercise> exercises;
    protected int noOfSets;
    
    protected void removeExercie(Exercise e){
        exercises.remove(e);
    }

    protected void addExercise(Exercise e){
        exercises.add(e);
    }

    protected int getNoOfSets(){
        return this.noOfSets;
    }

    protected void incrementNoOfSets(int numOfSets){
        if (numOfSets > 0)
        this.noOfSets += numOfSets;
    }
}
