package beds.backend;

import java.util.ArrayList;

/**
 * Class that represents a workout. Has the ability to manage exercises
 */
public class Workout {
    protected String name;
    protected ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    protected int noOfSets;

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
        return this.noOfSets;
    }

	/**
	 * Test program to increase the number of sets. Will be 
	 * private in the future
	 * @param numOfSets No of sets in the workout
	 */
    public void incrementNoOfSets(int numOfSets){
        if (numOfSets > 0)
        this.noOfSets += numOfSets;
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
    public void setName(String name){ this.name = name;}
	/**
	 * Get the name of the workout
	 * @return {@link #name}
	 */
    public String getName() {return this.name;}

    /** Test output */
    public void displayInfo(){
        System.out.println("Name: "+name);
        System.out.println("No of sets: " + noOfSets);
        System.out.println("No of exercise: "+exercises.size());
        for (int i =1; i <= exercises.size(); i++){
            System.out.println("Info of exercise "+i);
            exercises.get(i-1).displayInfo();
            System.out.println();
        }
    }
}
