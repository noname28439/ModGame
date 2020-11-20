package main;

import settings.Settings;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


import javafx.scene.shape.Path;

public class Main {


    static ServerSocket server;

    public static ArrayList<ClientConnection> connections = new ArrayList<>();
    
    public static void main(String[] args) {
    	
    	String SettingsLoadFile = ".\\settings.txt";
    	
    	if(Files.exists(Paths.get(SettingsLoadFile))) {
    		Settings.loadFromFile(SettingsLoadFile);
    	}
    	DelayThread.start();
    	Thread_UpdatePlayerRanking.start();
    	Server.start();
    	
    	
    }
    
	
}
