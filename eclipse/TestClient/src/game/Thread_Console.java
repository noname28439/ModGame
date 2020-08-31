package game;

import java.util.Scanner;

import connection.Client;

public class Thread_Console implements Runnable{

	@Override
	public void run() {
		Scanner in = new Scanner(System.in);
		
		while(true) {
			String cmd = in.nextLine();
			String[] args = cmd.split(" ");
			Client.sendMessage(cmd);
			World.lastSent=cmd;
		}
	}

}
