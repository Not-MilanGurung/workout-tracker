package beds.backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import beds.enums.MuscleGroup;
/**
 * Class that represents a workout. Has the ability to manage exercises
 */
public class Workout {
    protected StringProperty name = new SimpleStringProperty();
    protected ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    protected IntegerProperty noOfSets = new SimpleIntegerProperty();
	protected Map<MuscleGroup, IntegerProperty> muscleSplit = new HashMap<>();
	protected StringProperty datetime;
	protected LongProperty completionTime;

	/**
	 * Removes the exercise from the workout
	 * @param e Exercise to remove
	 */
    public void removeExercise(Exercise e){
        exercises.remove(e);
    }

	/**
	 * Adds the exercise to the exercise arraylist
	 * @param e Exercise to add
	 */
    public void addExercise(Exercise e){
        exercises.add(e);
    }

	/**
	 * Get the number of sets in the workout
	 * @return {@link #noOfSets}
	 */
    public int getNoOfSets(){
        return this.noOfSets.get();
    }
	public IntegerProperty getNoOfSetsProperty() {
		return this.noOfSets;
	}

	/**
	 * Test program to increase the number of sets. Will be 
	 * private in the future
	 * @param numOfSets No of sets in the workout
	 */
    public void incrementNoOfSets(int numOfSets){
        if (numOfSets > 0)
        this.noOfSets.set(this.noOfSets.get() + numOfSets);
    }

	/**
	 * Gets a exercise from the arraylist 
	 * @param name Name of the exercise
	 * @return {@link Exercise exercise} mathing the name from the arryalist
	 */
    public Exercise getExercise(String name){
        for (Exercise e : exercises){
            if (e.getName().equals(name))
                return e;
        }
        return null;
    }

	/**
	 * Sets the name of the workout
	 * @param name of the workout
	 */
    public void setName(String name){ this.name.set(name);}
	/**
	 * Get the name of the workout
	 * @return {@link #name}
	 */
    public String getName() {return this.name.get();}
	public StringProperty getNameProperty () {return this.name;}
}
