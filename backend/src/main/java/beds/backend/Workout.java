package beds.backend;

import java.util.ArrayList;


import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Class that represents a workout. Has the ability to manage exercises
 */
public class Workout {
    protected StringProperty name;
    protected ArrayList<Exercise> exercises;
	protected LongProperty completionTime;

	public Workout(){
		this.name = new SimpleStringProperty();
		this.exercises = new ArrayList<Exercise>();
		this.completionTime = new SimpleLongProperty(0l);
	}

	/**
	 * Removes the exercise from the workout
	 * @param e Exercise to remove
	 */
    public void removeExercise(Exercise e){exercises.remove(e);}

	/**
	 * Adds the exercise to the exercise arraylist
	 * @param e Exercise to add
	 */
    public void addExercise(Exercise e){exercises.add(e);}

	/**
	 * Get the array list of exercises of the workout
	 * @return {@link #exercises}
	 */
	public ArrayList<Exercise> getExercises(){return this.exercises;}

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
	/**
	 * Get the name property for Javafx
	 * @return {@link #name}
	 */
	public StringProperty getNameProperty () {return this.name;}
}
