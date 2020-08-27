package main;

public class World {

	public static Tile[][] tiles = new Tile[100][100];
	
	public static void load() {
		Server.clog("loading World...");
		for(int x = 0; x<tiles[0].length;x++)
			for(int y = 0; y<tiles[0].length;y++) {
				tiles[x][y]=new Tile(Tile.NORMAL);
			}
				
		Server.clog("World successfully loaded!");
	}
	
	
	
	
	
}
