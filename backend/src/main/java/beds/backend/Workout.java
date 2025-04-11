package beds.backend;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Class that represents a workout. Has the ability to manage exercises
 */
public class Workout {
	private IntegerProperty id;
    protected StringProperty name;
    protected ArrayList<CurrentExercise> exercises;
	protected IntegerProperty completionTime;
	protected LocalDateTime dateTime;
	private BooleanProperty isRoutine;

	public Workout(){
		this.id = new SimpleIntegerProperty();
		this.name = new SimpleStringProperty();
		this.exercises = new ArrayList<CurrentExercise>();
		this.completionTime = new SimpleIntegerProperty(0);
		this.dateTime = LocalDateTime.now();
		this.isRoutine = new SimpleBooleanProperty(false);
	}

	public Workout(int id, String name, int completionTime, LocalDateTime dateTime, boolean isRoutine){
		this.id = new SimpleIntegerProperty(id);
		this.name = new SimpleStringProperty(name);
		this.exercises = new ArrayList<CurrentExercise>();
		this.completionTime = new SimpleIntegerProperty(completionTime);
		this.dateTime = dateTime;
		this.isRoutine = new SimpleBooleanProperty(isRoutine);
	}

	public int getId() {return id.get();}
	public void setId(int id) {this.id.set(id);}
	public IntegerProperty getIdProperty() {return this.id;}

	public int getTotalSets() {
		return exercises.stream()
			.mapToInt(e -> e.getSets().size())
			.sum();
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
    public void addExercise(CurrentExercise e){exercises.add(e);}

	/**
	 * Get the array list of exercises of the workout
	 * @return {@link #exercises}
	 */
	public ArrayList<CurrentExercise> getExercises(){return this.exercises;}

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

	public int getCompletionTime() {return completionTime.get();}
	public void setCompletionTime(int newCompletionTime) {this.completionTime.set(newCompletionTime);}
	public IntegerProperty getCompletionTimeProperty() {return this.completionTime;}

	public LocalDateTime getDateTime() {return this.dateTime;}
	public void setDateTime(LocalDateTime newDateTime) {this.dateTime = newDateTime;}

	public void setIsRoutine(boolean isRoutine) {this.isRoutine.set(isRoutine);}
	public boolean getIsRoutine() {return this.isRoutine.get();}
	public BooleanProperty getIsRoutineProperty() {return this.isRoutine;}
}
