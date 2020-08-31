package game;

import java.awt.Color;
import java.awt.Graphics;

public class Tile {

	public int id,x,y;
	
	public Tile(int x, int y, int id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public void draw(Graphics g) {
		Color c = Color.BLUE;
		switch (id) {
		case 0:
			c = Color.GREEN;
			break;
		case 1:
			c = Color.LIGHT_GRAY;
			break;
		case 2:
			c = Color.RED;
			break;
		case 3:
			c = Color.ORANGE;
			break;

		default:
			break; 
		}
		g.setColor(c);
		g.fillRect(World.coordToPosX(x), World.coordToPosY(y), World.gridSize, World.gridSize);
		
	}
	
}
