package beds.enums;

public enum EquipmentType {
	NONE(1), BARBELL(2), DUMBBELL(3), KETTLEBELL(4),
	MACHINE(5), RESISTANCE_BAND(6), OTHER(7), CABLE(8);
	private int id;

	private EquipmentType(int id){
		this.id = id;
	}

	public int getID(){return id;}
}
