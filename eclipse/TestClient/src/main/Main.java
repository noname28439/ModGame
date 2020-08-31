package main;

import connection.Client;
import game.Thread_Console;
import game.Thread_MapReader;
import game.Thread_ServerRequest;

public class Main {

	static Thread displayThread;
	
	public static void main(String[] args) {
		displayThread = new Thread(new display.Main());
		displayThread.start();
		
		new Thread(new Thread_ServerRequest()).start();
		new Thread(new Thread_MapReader()).start();
		new Thread(new Thread_Console()).start();
		
		Client.connect("localhost", 25565);
		
		
	}
	
}
