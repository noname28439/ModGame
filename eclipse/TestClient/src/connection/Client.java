package connection;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import game.OtherPlayer;
import game.Tile;
import game.World;
import main.Main;

public class Client implements Runnable{
	
	
	public static final String NAME = "Fred", PW = "pw321";
	
	
	
	
	public static Socket connection;
	public static PrintWriter out;
	public static Scanner in;
	
	public static Thread listener;
	
	public static int x=0,y=0;
	
	
	public static void connect(String IP, int port) {
		
		try {
			connection = new Socket(IP, port);
			out = new PrintWriter(connection.getOutputStream());
			in = new Scanner(connection.getInputStream());
		} catch (IOException e) {}
		
		listener = new Thread(new Client());
		listener.start();
		
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sendMessage("login:"+NAME+":"+PW);
		
		
		try {Thread.currentThread().sleep(100);} catch (InterruptedException e) {}
		sendMessage("playerlist");
		
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
			
			if(args[0].equalsIgnoreCase("map")) {
				if(args[1].equalsIgnoreCase("tileID")) {
					World.tiles.add(new Tile(_int(args[2]),_int(args[3]),_int(args[4])));
				}
				if(args[1].equalsIgnoreCase("isNormal")) {
					if(args[4].equalsIgnoreCase("true")) {
						World.tiles.add(new Tile(_int(args[2]),_int(args[3]),0));
					}else {
						World.tiles.add(new Tile(_int(args[2]),_int(args[3]),-187));
						Methods.getTileID(_int(args[2]),_int(args[3]));
					}
				}
			}
			
			//DATA-RETURN:player_Noname_x:490
			if(args[0].equalsIgnoreCase("DATA-RETURN")) {
				
				if(args[1].equalsIgnoreCase("posX"))
					x=Integer.valueOf(args[2]);
				if(args[1].equalsIgnoreCase("posY"))
					y=Integer.valueOf(args[2]);
				
				
				if(args[1].startsWith("playerlist")) {
					String[] list = args[2].split("_");
					
					for(int i = 0; i<list.length;i++)
						if(!list[i].equalsIgnoreCase(NAME))
							World.players.add(new OtherPlayer(0, 0, list[i]));
					
				}
				
				
				
				
				
				
				if(args[1].split("_")[0].equalsIgnoreCase("player")) {
					String name = args[1].split("_")[1];
					String argType = args[1].split("_")[2];
					
					if(argType.equalsIgnoreCase("x")) {
						if(World.getPlayerByName(name)!=null) {
							World.getPlayerByName(name).x=Integer.valueOf(args[2]);
						}
					}
					if(argType.equalsIgnoreCase("y")) {
						if(World.getPlayerByName(name)!=null) {
							World.getPlayerByName(name).y=Integer.valueOf(args[2]);
						}
					}
					
				}
			}
			
			if(args[0].equalsIgnoreCase("join")) {
				if(!args[1].equalsIgnoreCase(NAME))
					World.players.add(new OtherPlayer(0, 0, args[1]));
			}
			if(args[0].equalsIgnoreCase("leave")) {
				if(!args[1].equalsIgnoreCase(NAME))
					if(World.getPlayerByName(args[1])!=null)
						World.getPlayerByName(args[1]).stop();
			}
		}
	}
	
	
	public static int _int(String text) {
		return Integer.valueOf(text);
	}
	
	
}
