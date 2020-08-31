package game;

import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;

import connection.Client;
import connection.Methods;
import display.Collision;
import display.Keyboard;

public class OtherPlayer implements Runnable{
	
	public int x, y;
	public String name;
	
	Thread reqester;
	
	public OtherPlayer(int x, int y, String name){
		this.x = x;
		this.y = y;
		this.name = name;
		
		
		System.out.println("JOIN_DETECT: "+name);
		
		reqester = new Thread(this);
		reqester.start();
		
	}
	
	
	
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(World.coordToPosX(x), World.coordToPosY(y), World.gridSize, World.gridSize);
		
		
		if(Collision.rectToRect(World.coordToPosX(x), World.coordToPosY(y), World.gridSize, World.gridSize, Keyboard.getMousex(), Keyboard.getMousey(), 1, 1)) {
			g.setColor(Color.RED);
			g.drawOval(World.coordToPosX(x), World.coordToPosY(y), World.gridSize, World.gridSize);
			if(Keyboard.getButton()==1) {
				Methods.attack(name);
			}	
		}
		
	}

	public void stop() {
		System.out.println("LEAVE_DETECT: "+name);
		reqester.stop();
		World.players.remove(this);
	}

	@Override
	public void run() {
		while(true) {
			try {Thread.currentThread().sleep(500);} catch (InterruptedException e) {}
			Client.sendMessage("player:"+name+":x");
			Client.sendMessage("player:"+name+":y");
		}
		
	}
	
	
	
}
