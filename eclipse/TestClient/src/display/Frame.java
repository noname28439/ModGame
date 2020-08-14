package display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Frame extends JFrame{

	
	private BufferStrategy strat;
	
	
	
	
	public Frame() {
		super("Fangen");
		addKeyListener(new Keyboard());
		addMouseListener(new Keyboard());
		addMouseMotionListener(new Keyboard());
		
	}
	
	public void makestrat() {
		createBufferStrategy(2);
		
		strat = getBufferStrategy();
	}
	
	public void repaint() {
		Graphics g = strat.getDrawGraphics();
		draw(g);
		g.dispose();
		strat.show();
	}
	
	public void draw(Graphics g) {
		
	}
	
	public void update() {
		
	}
	
}
