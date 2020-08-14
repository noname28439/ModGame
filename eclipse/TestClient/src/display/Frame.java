package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Frame extends JFrame{

	
	private BufferStrategy strat;
	
	public boolean isLodaing = true;
	public int ltick=0;
	
	
	public Frame() {
		super("Noname Game");
		addKeyListener(new Keyboard());
		addMouseListener(new Keyboard());
		addMouseMotionListener(new Keyboard());
		
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        System.err.println("WINDOW_CLOSE");
		    }
		});
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
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 10000, 10000);
		
		
		//else
		//Client_World.draw(g);
		
		
		
	}
	
	public void update(float tslf) {
		//Client_World.update(tslf);
	}
	
}
