package main;

public class Tile {

	public static int 
	NORMAL = 0,
	HIGHWAY = 1,
	WALL = 2,
	TRAP = 3
	;
	
	
	
	private String owner;
	
	private int id;
	private int key;
	
	Tile(int id) {
		this.id = id;
		this.key = -1;
		owner = null;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	public int getID() {
		return this.id;
	}
	public int getKey() {
		return this.key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	
}
