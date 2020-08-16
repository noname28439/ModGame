package connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import main.Main;

public class Client implements Runnable{
	
	
	public static Socket connection;
	public static PrintWriter out;
	public static Scanner in;
	
	public static Thread listener;
	
	
	public static void connect(String IP, int port) {
		
		try {
			connection = new Socket(IP, port);
			out = new PrintWriter(connection.getOutputStream());
			in = new Scanner(connection.getInputStream());
		} catch (IOException e) {}
		
		listener = new Thread(new Client());
		listener.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendMessage("test");
		
	}
	
	public static void sendMessage(String text) {
		out.println(text);
		out.flush();
	}

	@Override
	public void run() {
		while(true) {
			String recv = in.nextLine();
			System.err.println("[FROM_SERVER] --> "+recv);
			String[] args = recv.split(":");
			
			if(recv.equalsIgnoreCase("test angekommen!")) {
				sendMessage("test");
			}
			
			
		}
	}
	
	
	
}
