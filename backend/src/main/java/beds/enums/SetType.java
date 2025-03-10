package beds.enums;

public enum SetType {
    NORMAL(1), FALIURE(2), DROPSET(3);
	private int id;
	private SetType(int id) {this.id = id;}
	public int getID() {return id;}
}
