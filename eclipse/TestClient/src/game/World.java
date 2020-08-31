package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import connection.Client;
import connection.Methods;
import display.Keyboard;

public class World {

	public static String lastSent = "NO_MESSAGE";
	
	public static boolean advancedMenu = true;
	
	
	public static ArrayList<OtherPlayer> players = new ArrayList<>();
	
	public static OtherPlayer getPlayerByName(String name) {
		for(int i = 0; i<players.size();i++) {
			if(players.get(i).name.equalsIgnoreCase(name))
				return players.get(i);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	public static ArrayList<Tile> tiles = new ArrayList<>();
	
	public static Tile getTileAt(int x, int y) {
		for(int i = 0; i<tiles.size();i++) {
			if(tiles.get(i).x==x&&tiles.get(i).y==y)
				return tiles.get(i);
		}
		return null;
	}
	
	
	
	public static int gridSize = 10;
	
	public static int mapx = -500*gridSize+300;
	public static int mapy = -500*gridSize+300;
	
	public static int coordToPosX(int x) {
		return gridify(x)+mapx;
	}
	public static int coordToPosY(int y) {
		return gridify(y)+mapy;
	}
	public static int gridify(int in) {
		return in*gridSize;
	}
	
	public static void draw(Graphics g) {
		
		//g.fillRect(World.coordToPosX(x), World.coordToPosY(y), World.gridSize, World.gridSize);
		
	
		
		
		for(int i = 0; i<tiles.size();i++)
			tiles.get(i).draw(g);
		
		
		for(int i = 0; i<players.size();i++)
			players.get(i).draw(g);
		
		g.setColor(Color.MAGENTA);
		g.fillOval(World.coordToPosX(Client.x), World.coordToPosY(Client.y), gridSize, gridSize);
		
		
		
		
		
		
		if(advancedMenu) {
		
		g.setColor(Color.darkGray);
		for(int x = 0; x<1000;x++)
			g.drawLine(coordToPosX(x), coordToPosY(0), coordToPosX(x), coordToPosY(1000));
		for(int y = 0; y<1000;y++)
			g.drawLine(coordToPosX(0), coordToPosY(y), coordToPosX(1000), coordToPosY(y));
		
		
		g.setColor(Color.WHITE);
		 g.setFont(new Font("Impact",Font.PLAIN,30));
		 g.drawString(lastSent, 500, 100);
		 
		 g.setColor(Color.MAGENTA);
		 g.drawLine(Keyboard.getMousex(), Keyboard.getMousey(), coordToPosX(Client.x)+gridSize/2,coordToPosY(Client.y)+gridSize/2);
		 
		 g.setColor(Color.YELLOW);
		 for(int i = 0; i<players.size();i++) {
			 g.drawLine(Keyboard.getMousex(), Keyboard.getMousey(), coordToPosX(players.get(i).x)+gridSize/2,coordToPosY(players.get(i).y)+gridSize/2);
		 }
		 
		
		 
		}
		
	}
	
	public static void update() {
		//Methods.getTileID(new Random().nextInt(10), new Random().nextInt(10));
		
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_ALT)) {
			Keyboard.keys[KeyEvent.VK_ALT] = false;
			advancedMenu=!advancedMenu;
			System.out.println("lelek");
		}
		
		if(Keyboard.isKeyPressed(KeyEvent.VK_UP)) {
			System.out.println("w");
			Keyboard.keys[KeyEvent.VK_W] = false;
			Methods.move(0, -10);
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_DOWN)) {
			System.out.println("s");
			Keyboard.keys[KeyEvent.VK_S] = false;
			Methods.move(0, 10);
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_LEFT)) {
			System.out.println("a");
			Keyboard.keys[KeyEvent.VK_A] = false;
			Methods.move(-10, 0);
		}
		if(Keyboard.isKeyPressed(KeyEvent.VK_RIGHT)) {
			System.out.println("d");
			Keyboard.keys[KeyEvent.VK_D] = false;
			Methods.move(10, 0);
		}
		
		
		int camspeed = 22;
		if(Keyboard.isKeyPressed(KeyEvent.VK_S))
			mapy-=camspeed;
		if(Keyboard.isKeyPressed(KeyEvent.VK_W))
			mapy+=camspeed;
		if(Keyboard.isKeyPressed(KeyEvent.VK_A))
			mapx+=camspeed;
		if(Keyboard.isKeyPressed(KeyEvent.VK_D))
			mapx-=camspeed;
	}
	
}
