package main;

public class Main {

	static Thread displayThread;
	
	public static void main(String[] args) {
		displayThread = new Thread(new display.Main());
		displayThread.start();
		
		
	}
	
}
