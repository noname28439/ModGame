package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import settings.Settings;

public class Server {

	

    static ServerSocket server;

    public static ArrayList<ClientConnection> connections = new ArrayList<>();
    
    public static ClientConnection getConnectionByName(String name) {
		for(int i = 0; i<connections.size();i++) {
			if(connections.get(i).name.equalsIgnoreCase(name))
				return connections.get(i);
		}
		return null;
	}
    
	public static void sendMessageToAll(String text) {
		for(int i = 0; i<connections.size();i++)
			connections.get(i).sendMessage(text);
	}
	
	public static void sendMessageToAllOutOne(String text, ClientConnection notSendTo) {
		for(int i = 0; i<connections.size();i++)
			if(connections.get(i)!=notSendTo) connections.get(i).sendMessage(text);
	}
    
    
    public static void start() {
    	

        clog("Server Loading...");

        DataBaseHandeler.load();
        clog("SQL DataBaseHandeler successfully loaded...");
        
        try {
            server = new ServerSocket(Settings.PORT);
            clog("ServerSocket successfully started");
        }catch (IOException e){
            e.printStackTrace();
        }
        
        
        
        
        while(true) {
        	try {
				Socket currentConnection = server.accept();
				connections.add(new ClientConnection(currentConnection));
				clog("Client Connected");
			} catch (IOException e) {}
        }


    }




    public static void clog(String text){
        System.out.println("[ModServer]--> "+text);
    }

	
	
}
