package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection implements Runnable{

	Socket connection;
	String name;
	PrintStream out;
	Scanner in;
	Thread listener;
	
    public ClientConnection(Socket connection){
    	this.connection = connection;
    	
    	try {
			out = new PrintStream(connection.getOutputStream());
			in = new Scanner(connection.getInputStream());
		} catch (IOException e) {}
    	
    	
    	listener = new Thread(this);
    	listener.start();

    }
    
    public void sendMessage(String text) {
    	out.println(text);
    	out.flush();
    }

	@Override
	public void run() {
		try {
		while(true) {
			
				String recv = in.nextLine();
				System.err.println("[FROM_CLIENT] --> "+recv);
				String[] args = recv.split(":");
				
				if(recv.equalsIgnoreCase("test")) {
					sendMessage("test angekommen!");
				}
			
			
		}
		} catch (java.util.NoSuchElementException e) {
			if(name == null) {
				Main.clog("Ein nicht verifizierter Client hat sich wieder ausgeloggt!");
			}else {
				Main.clog(" "+name+" hat sich ausgeloggt!");
			}
			
		}
	}

    
    

}
