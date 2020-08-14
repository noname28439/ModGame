package display;

import java.awt.Toolkit;

public class Main implements Runnable{
	static Frame frame;
	
	double interpolation = 0;
	final int TICKS_PER_SECOND = 100;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 5;
	
	@Override
	public void run() {
		frame = new Frame();
		
		int frameSize = 4;
		
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width/frameSize, Toolkit.getDefaultToolkit().getScreenSize().height/frameSize);
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		
		frame.makestrat();
		
		
		long lastFrame = System.currentTimeMillis();
		
		
		
		double next_game_tick = System.currentTimeMillis();
	    int loops;
		
		while(true) {
			
			
			loops = 0;
	        while (System.currentTimeMillis() > next_game_tick
	                && loops < MAX_FRAMESKIP) {

	        	frame.update();

	            next_game_tick += SKIP_TICKS;
	            loops++;
	        }

	        interpolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick
	                / (double) SKIP_TICKS);
	        
	        frame.repaint();
			
			
			
			
			
			
			
//			frame.update();
//			frame.repaint();
//			if((System.currentTimeMillis() - LastRefresh) > SleepTime) {
//				LastRefresh = System.currentTimeMillis();
//
//
//				
//				
//				break;
//			}else {
//				try {
//					Thread.sleep((long) (SleepTime - (System.currentTimeMillis() - LastRefresh)));
//				} catch (InterruptedException e) {e.printStackTrace();}
//			}
//			
		
				
				
				
			
			
			//try {	Thread.sleep(10);	} catch (InterruptedException e) {e.printStackTrace();}	
			
	}
	
	
		
		
		
	}

	
	
}
