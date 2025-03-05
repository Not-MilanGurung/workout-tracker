package beds.backend;

import java.util.ArrayList;

public class Workout {
    protected String name;
    protected ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    protected int noOfSets;
    
    public void removeExercie(Exercise e){
        exercises.remove(e);
    }

    public void addExercise(Exercise e){
        exercises.add(e);
    }

    public int getNoOfSets(){
        return this.noOfSets;
    }

    public void incrementNoOfSets(int numOfSets){
        if (numOfSets > 0)
        this.noOfSets += numOfSets;
    }

    public Exercise getExercise(String name){
        for (Exercise e : exercises){
            if (e.getName().equals(name))
                return e;
        }
        return null;
    }

    public void setName(String name){ this.name = name;}
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
