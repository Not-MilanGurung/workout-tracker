package beds.enums;

public enum MetricType {
    WEIGHTS(1), REPS(2), TIME(3), DISTANCE(4), ASSISTED(5);
    private int id;
    private MetricType(int id){
        this.id = id;
    }

    public int getID(){return id;}
}
