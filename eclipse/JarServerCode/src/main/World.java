package main;

import java.util.Random;

import settings.Settings;

public class World {

	private static Tile[][] tiles = new Tile[Settings.mapsize][Settings.mapsize];
	
	public static void load() {
		Server.clog("loading World...");
		for(int x = 0; x<tiles[0].length;x++)
			for(int y = 0; y<tiles[1].length;y++) {
				if(new Random().nextInt(Settings.map_noTile_possibility)==0) {
					if(new Random().nextInt(2)==0) {
						tiles[x][y]=new Tile(Tile.ORE_1);
					}else {
						tiles[x][y]=new Tile(Tile.ORE_2);
					}
				}else {
					tiles[x][y]=new Tile(Tile.NORMAL);
				}
				
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
