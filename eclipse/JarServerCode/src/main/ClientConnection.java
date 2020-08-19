package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import settings.Settings;

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
    
    
    //Basic Connection Functions
    public void sendMessage(String text) {
    	out.println(text);
    	out.flush();
    }
    
    //Advanced Connection Functions
    public void sendErrorMessage(String text) {
    	out.println(createMesssage(new String[]{"MESSAGE","ERROR",text}));
    	out.flush();
    }
    
    public void sendInfoMessage(String text) {
    	out.println(createMesssage(new String[]{"MESSAGE","INFO",text}));
    	out.flush();
    }
    
    
    
    
    
    
    
    
    
    
    //helping Functions
    public boolean isLoggedIn() {
    	return !(name == null);
    }
    
    
    
    
	@Override
	public void run() {
		try {
		while(true) {
				String recv = in.nextLine();
				System.err.println("[FROM_CLIENT] --> "+recv);
				String[] args = recv.split(Settings.connection_message_seperator);
				
				
				
				if(isLoggedIn()) {
					//Character Controlls
					
					
					
					
					
					
				}else {
					//Login
					if(args[0].equalsIgnoreCase("login")) {
						int result = DataBaseHandeler.checkPlayerAccess(args[1], args[2]);
						
						switch (result) {
						case 0:	//Zugriff gewährt
							name=args[1];
							sendInfoMessage("Login successfull");
							break;
						case 1:	//Falsches Passwort
							sendErrorMessage("Wrong password");
							break;
						case 2:	//Falscher Nutzername
							sendErrorMessage("Wrong username");
							break;

						default:
							break;
						}
						
					}
				}
				
				
			
			
		}
		} catch (java.util.NoSuchElementException e) {
			if(isLoggedIn()) {
				Main.clog(" "+name+" hat sich ausgeloggt!");
			}else {
				Main.clog("Ein nicht verifizierter Client hat sich wieder ausgeloggt!");
			}
			
		}
	}

    
    
	
	//static helping Functions
	public static String escape(String text) {
		return text.replaceAll(Settings.connection_message_seperator, "{{"+Settings.connection_message_seperator+"}}");
	}
	public static String createMesssage(String[] args) {
		String result = "";
		for (int i = 0; i < args.length; i++) {
			if(i+1 == args.length)
				result+=args[i];
			else
				result+=args[i]+Settings.connection_message_seperator;
		}
		return result;
	}
	

}
