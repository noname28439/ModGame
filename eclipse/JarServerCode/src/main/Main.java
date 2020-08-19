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
    	Server.start();

    }
    
	
}
