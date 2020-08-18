package main;

import settings.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    static ServerSocket server;

    public static ArrayList<ClientConnection> connections = new ArrayList<>();
    
    public static void main(String[] args) {
    	

        clog("Server Loading...");

        DataBaseHandeler.load();
        clog("SQL DataBaseHandeler successfully loaded...");
        
        try {
            server = new ServerSocket(Settings.PORT);
            clog("ServerSocket successfully started");
        }catch (IOException e){
            e.printStackTrace();
        }
        
        DataBaseHandeler.checkPlayerAccess("Noname", "pw");
        
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
