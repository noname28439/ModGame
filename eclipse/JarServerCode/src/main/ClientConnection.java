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
	
    public ClientConnection(Socket connection){
    	this.connection = connection;
    	
    	try {
			out = new PrintStream(connection.getOutputStream());
			in = new Scanner(connection.getInputStream());
		} catch (IOException e) {}
    	
    	
    	
    	
    	

    }
    
    public void sendMessage(String text) {
    	out.println(text);
    	out.flush();
    }

	@Override
	public void run() {
		while(true) {
			String recv = in.nextLine();
			String[] args = recv.split(":");
			
			
		}
		
	}

    
    

}
