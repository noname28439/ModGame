package main;

public class Tile {

	Tile 
	NORMAL = new Tile(0),
	HIGHWAY = new Tile(1),
	WALL = new Tile(2),
	TRAP = new Tile(3)
	;
	
	
	
	
	private int id;
	private int key;
	
	Tile(int id) {
		this.id = id;
		this.key = -1;
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
	
	
}
