package main;

import settings.Settings;

public class World {

	private static Tile[][] tiles = new Tile[Settings.mapsize][Settings.mapsize];
	
	public static void load() {
		Server.clog("loading World...");
		for(int x = 0; x<tiles[0].length;x++)
			for(int y = 0; y<tiles[1].length;y++) {
				tiles[x][y]=new Tile(Tile.NORMAL);
			}
				
		Server.clog("World successfully loaded!");
	}
	
	public static Tile getTile(int x, int y) {
		Tile toReturn = null;
		
		try {
			toReturn=tiles[x][y];
		} catch (Exception e) {
		}
		
		
		return toReturn;
	}
	
	
	
}
