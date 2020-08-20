package main;

import settings.Settings;

public class DelayThread implements Runnable{

	@Override
	public void run() {
		while(true) {
			
			try {Thread.currentThread().sleep(Settings.player_delay_regeneration_delay);} catch (InterruptedException e) {}
			for(int i = 0; i<Server.connections.size();i++) {
				Server.connections.get(i).delayTick();
			}
		}
	}

	public static void start() {
		Server.clog("DelayThread starting...");
		Thread t = new Thread(new DelayThread());
		t.start();
	}
	
	
	
}
