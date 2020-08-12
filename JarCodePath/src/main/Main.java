package main;

import settings.Settings;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    static ServerSocket server;

    public static void main(String[] args) {



        try {
            server = new ServerSocket(Settings.PORT);
        }catch (IOException e){
            e.printStackTrace();
        }


        System.out.println("Test...?");

    }




    public static void clog(String text){
        System.out.println("[ModServer]--> "+text);
    }

}
