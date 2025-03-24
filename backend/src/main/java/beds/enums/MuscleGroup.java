package beds.enums;

public enum MuscleGroup {
    FOREARMS(1), BICEPS(2), TRICEPS(3),
    SHOULDERS(4), TRAPS(5),
    CHEST(6),
    UPPER_BACK(7), LOWER_BACK(8), LATS(9),
    ABDOMINAL(10), OBLIQUES(11),
    QUADRICEPS(12), HAMSTRINGS(13), CALVES(14), GLUTES(15),
    CARDIO(16), OTHERS(17), FULL_BODY(18), ANY(19);

    private int id;
    private MuscleGroup(int id){
        this.id = id;
    }

    public int getID(){ return id;}

	public static MuscleGroup getByID(int id){
		for (MuscleGroup g : MuscleGroup.values())
			if (g.getID() == id)
				return g;
		return null;
	}
}
