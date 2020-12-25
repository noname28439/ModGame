package main;

public class Tile {

	public static int 
	NORMAL = 0,
	HIGHWAY = 1,
	WALL = 2,
	TRAP = 3,
	TOWER = 4,
	CRAFTING_STATION = 5,
	ORE_1 = 6,
	ORE_2 = 7,
	ORE_3 = 8,
	AIRSTRIKE_LAUNCHER = 9
	;
	
	
	public static int[] getIngredientsForTileCraft(int ItemID) {
		switch (ItemID) {
		case 1:
			return new int[] {1,1,0};
		case 2:
			return new int[] {5,2,0};
		case 3:
			return new int[] {2,5,0};
		case 4:
			return new int[] {10,10,0};
		case 5:
			return new int[] {25,50,0};

		default:
			return null;
		}
	}
	
	public static boolean isCraftable(Tile standingOn, int ToCraftTileID) {
		//This Method is disabled, because There was a logical Mistake in the Crafging Station System
		return true;
		/*
		if(standingOn.getID()==Tile.CRAFTING_STATION) {
			return true;
		}else {
			if(ToCraftTileID==TOWER||ToCraftTileID==TRAP) {
				return false;
			}else {
				return true;
			}
		}
		*/
	}
	
	
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
