package beds.enums;

public enum SetType {
    NORMAL(1), FALIURE(2), DROPSET(3);
	private int id;
	private SetType(int id) {this.id = id;}
	public int getID() {return id;}
	public static SetType getSetType(int id) {
		for (SetType type : SetType.values()) {
			if (type.getID() == id) {
				return type;
			}
		}
		return null;
	}
}
