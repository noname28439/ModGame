package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.sun.org.apache.regexp.internal.recompile;

import settings.Settings;

public class Server {

	

    static ServerSocket server;

    public static ArrayList<ClientConnection> connections = new ArrayList<>();
    
    
    
    //Basic helpingFunctions
    
    public static ArrayList<ClientConnection> getConnections() {
    	ArrayList<ClientConnection> toReturn = new ArrayList<>();
    	
    	for(int i = 0; i<connections.size();i++)
    		if(connections.get(i).isLoggedIn())
    			toReturn.add(connections.get(i));
    	return toReturn;
    }
    
    
    
    public static void chatSend(String text) {
    	sendMessageToAll(buildMesssage(new String[] {"MESSAGE","CHAT",text}));
    }
    
    
    
    public static ClientConnection getConnectionByName(String name) {
		for(int i = 0; i<connections.size();i++) {
			if(connections.get(i).isLoggedIn())
				if(connections.get(i).name.equalsIgnoreCase(name))
					return connections.get(i);
		}
		return null;
	}
    
    public static ClientConnection findPlayerByName(ClientConnection caller, String name) {
    	ClientConnection found = null;
		for(int i = 0; i<connections.size();i++) {
			if(connections.get(i).isLoggedIn())
				if(connections.get(i).name.equalsIgnoreCase(name))
					found = connections.get(i);
		}
		if(found!=null) {
			//Spieler gefunden
			if(found.sneaking) {
				if(calculateDistanceBetweenPoints(caller.x, caller.y, found.x, found.y)<Settings.player_sneaking_detectRange) {
					//Kann spieler sehen
					return found;
				}
			}else {
				return found;
			}
			
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
	
	public static String buildMesssage(String[] args) {
		String result = "";
		for (int i = 0; i < args.length; i++) {
			if(i+1 == args.length)
				result+=args[i];
			else
				result+=args[i]+Settings.connection_message_seperator;
		}
		return result;
	}
    
	
	//Advanced helping functions#
	public static void infoToAll(String text) {
		sendMessageToAll(buildMesssage(new String[] {"MESSAGE","SERVERWIDE-INFO",text}));
	}
	
	public static boolean canPlayerAttack(ClientConnection attacker, ClientConnection target) {
		return (calculateDistanceBetweenPoints(attacker.x, attacker.y, target.x, target.y)<Settings.player_attack_radius);
	}
	
	static double calculateDistanceBetweenPoints(
			  double x1, 
			  double y1, 
			  double x2, 
			  double y2) {       
			    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
			}
    
    public static void start() {
    	

        clog("Server Loading...");
        
        World.load();
        
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
				int regIps = 0;
				for(int i = 0; i<connections.size();i++) {
					if(connections.get(i).connection.getInetAddress().toString().equalsIgnoreCase(currentConnection.getInetAddress().toString()))
						regIps++;
				}
				if(regIps>=Settings.ClientConnectionLimit) {
					currentConnection.close();
					clog("Client Connection to "+currentConnection.getInetAddress().toString()+" refused, because it already had connected "+Settings.ClientConnectionLimit+" connections!");
				}else {
					connections.add(new ClientConnection(currentConnection));
					clog("Client Connected ["+currentConnection.getInetAddress()+"]");
				}
			} catch (IOException e) {}
        }


    }




    public static void clog(String text){
        System.out.println("[ModServer]--> "+text);
    }

	
	
}
