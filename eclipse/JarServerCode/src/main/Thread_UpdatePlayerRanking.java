package main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import settings.Settings;

public class Thread_UpdatePlayerRanking implements Runnable{
	
	
	static boolean debug = false,
			transmitCoords = false;
	
	@Override
	public void run() {
		
		while(true) {
			try { Thread.currentThread().sleep(Settings.PlayerRankFileListUpdateDelay); } catch (InterruptedException e) {}
			
			if(debug)
				System.err.println("----------------upthyck---------------");
			
			
			//-------------------------------------LivePlayerList-------------------------------------
			
			String pn = "";
			
			for(int i = 0; i<Server.connections.size();i++) {
				if(transmitCoords) {
					pn+=(Server.connections.get(i).name+":"+Server.connections.get(i).points+":"+Server.connections.get(i).x+":"+Server.connections.get(i).y+":"+(int)Server.connections.get(i).hp+"\n");
				}else {
					pn+=(Server.connections.get(i).name+":"+Server.connections.get(i).points+":"+0+":"+0+":"+(int)Server.connections.get(i).hp+"\n");
				}
				
			}
			//pn.replaceFirst("\n", "");
			
			PrintWriter writer;
			try {
				writer = new PrintWriter(new File(Settings.bestPlayer_now_file_Path));
				writer.print(pn);
				writer.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			
			if(debug)
				System.err.println("Live Update finished...");
			
			//-------------------------------------AlltimePlayerList-------------------------------------
			
			ArrayList<String> playerList = new ArrayList<>();
			
			try {
			      File everFile = new File(Settings.bestPlayer_ever_file_Path);
			      Scanner fileIn = new Scanner(everFile);
			      while (fileIn.hasNextLine()) {
			        String data = fileIn.nextLine();
			        playerList.add(data);
			      }
			      fileIn.close();
			    } catch (FileNotFoundException e) {e.printStackTrace();}
			if(debug)
				System.err.println("SCS: "+Server.connections.size());
			
			for(int i = 0; i<Server.connections.size();i++) {
				ClientConnection currentConnection = Server.connections.get(i);
				if(!currentConnection.isLoggedIn())
					break;
				
				boolean playerInList = false;
				
				for(int ii = 0; ii<playerList.size();ii++) {
					String cln = playerList.get(ii).split(":")[0];
					int clp = Integer.valueOf(playerList.get(ii).split(":")[1]);
					
					if(cln.equalsIgnoreCase(currentConnection.name)) {
						if(debug)
							System.err.println("Player in list");
						playerInList=true;
						if(debug)
							System.err.println(clp+"<"+currentConnection.points);
						if(clp<currentConnection.points) {
							System.err.println("call");
							playerList.set(ii, cln+":"+String.valueOf(currentConnection.points));
							//playerList.remove(ii);
							//playerList.add(cln+":"+String.valueOf(clp));
						}
					}
				}
				
				if(!playerInList)
					playerList.add(currentConnection.name+":"+currentConnection.points);
			}
			
			String output = "";
			
			for(int i = 0; i<playerList.size();i++) {
				output+=(playerList.get(i)+"\n");
			}
			
			PrintWriter allWriter;
			try {
				allWriter = new PrintWriter(new File(Settings.bestPlayer_ever_file_Path));
				allWriter.print(output);
				allWriter.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			
			if(debug)
				System.err.println("Alltime Update finished...");
		}
		
		
	}
	
	public static void start() {
		Thread t = new Thread(new Thread_UpdatePlayerRanking());
		t.start();
	}
	
	
}
