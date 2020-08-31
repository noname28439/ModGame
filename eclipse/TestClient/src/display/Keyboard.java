package display;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class Keyboard implements KeyListener,MouseMotionListener, MouseListener{

	public static boolean[] keys = new boolean[1024];
	
	private static int mousex;
	private static int mousey;
	public static int button;
	public static int getMousex() {
		return mousex;
	}
	
	public static int getMousey() {
		return mousey;
	}
	
	public static boolean isKeyPressed(int KeyCode) {
		
		return keys[KeyCode];
	}
	
	public static int getButton() {
		return button;
	}
	
	

	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mousex = e.getX();
		mousey = e.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
	
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		button = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		button = -1;
	}

	
	
	
}
