package display;

import java.awt.Toolkit;


public class Main implements Runnable{
	public static Frame frame;
	
	double interpolation = 0;
	final int TICKS_PER_SECOND = 100;
	final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
	final int MAX_FRAMESKIP = 5;
	
	@Override
	public void run() {
		frame = new Frame();
		
		
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		
		frame.makestrat();
		
		
		long lastFrame = System.currentTimeMillis();
		while(true) {
			

			long thisFrame = System.currentTimeMillis();
			
			float timesincelastframe = (float) ((thisFrame - lastFrame) / 1000.0);
			
			if(timesincelastframe>0.01) {
				lastFrame = thisFrame;
				
				frame.update(timesincelastframe);
				
				frame.repaint();
				
				//System.err.print("Frame: "+timesincelastframe);
			}
			//try {Thread.sleep(10);} catch (InterruptedException e) {e.printStackTrace();}
	}
	
	
		
		
		
	}

	
	
}
