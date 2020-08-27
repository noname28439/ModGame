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
	
	float delay = 0;
	
	int hp = 0;
	int x=0,y=0;
	int killstreak = 0;
	
	
    public ClientConnection(Socket connection){
    	this.connection = connection;
    	
    	try {
			out = new PrintStream(connection.getOutputStream());
			in = new Scanner(connection.getInputStream());
		} catch (IOException e) {}
    	
    	listener = new Thread(this);
    	listener.start();
    	
    	//Set basic Player values
    	hp = Settings.player_hp;
    	
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
    
    public void returnDataRequestResult(String key, Object value) {
    	String valueString = String.valueOf(value);
    	out.println(createMesssage(new String[]{"DATA-RETURN",key,valueString}));
    	out.flush();
    }
    
    
    //PlayerControllFunctions
    
    public void attack(String player) {
    	ClientConnection result = Server.getConnectionByName(player);
    	if(result!=null) {
    		if(Server.canPlayerAttack(this, result)) {
    			result.hp-=generateDamage();
        		if(result.checkDead()) {
        			//Player isDead
        			killstreak+=Integer.valueOf(result.killstreak/10);
        			result.resetPosition();
        			result.resetKillstreak();
        			Server.clog(name+" killed "+result.name);
        		}
    		}else {
    			//Player not in Range!
        		punish(1);
    		}
    	}else {
    		//No player with this Name found!
    		punish(5);
    	}
    	
    }
    
    public void move(int x, int y) {
    	
    }
    
    
    
    
    //delay Handeler
	    
	    
	    //basic
	    
	    public void delayTick() {
	    	if(delay>0) {
	    		delay-=0.1;
	    	}else {
	    		delay = 0;
	    	}
	    }
	    
	    public void delayAdd(float amount) {
	    		delay+=amount;
	    }
	    
	    public void delayRemove(float amount) {
	    	if(delay>0)
	    		delay-=amount;
	    }
	    
	    public float getDelay() {
	    	return delay;
	    }
	    
	    //advanced
    
	    
	    public void punish(float strength) {
	    	delayAdd(strength);
	    }
    
	    public boolean isStunned() {
	    	return (getDelay()>0);
	    }
    
	    
	    
	    
    
    //ground Functions
    public boolean isLoggedIn() {
    	return !(name == null);
    }
    
    public float generateDamage() {
    	return Settings.player_damage;
    }
    
    
    
    //Death handeling
    
    public boolean checkDead() {
    	if(hp<=0) {
    		sendInfoMessage("you died!");
    	}
    	return hp<=0;
    }
    
    public void resetPosition() {
    	x=0;
    	y=0;
    }
    
    public void resetKillstreak() {
    	killstreak=0;
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
					
					
					if(args[0].equalsIgnoreCase("attack")) {
						attack(args[1]);
					}
					
					
					
					
					if(args[0].equalsIgnoreCase("data")) {
						
						if(args[1].equalsIgnoreCase("get")) {
							
							
							if(args[2].equalsIgnoreCase("HP")) {returnDataRequestResult("HP", hp);}
							
							if(args[2].equalsIgnoreCase("posX")) {returnDataRequestResult("posX", x);}
							if(args[2].equalsIgnoreCase("posY")) {returnDataRequestResult("posY", y);}
							
							if(args[2].equalsIgnoreCase("delay")) {returnDataRequestResult("delay", delay);}
							
						}
						
						if(args[1].equalsIgnoreCase("set")) {
							
							
						}
						
						if(args[1].equalsIgnoreCase("add")) {
							if(args[2].equalsIgnoreCase("delay")) delayAdd(Float.valueOf(args[3]));
							
						}
						if(args[1].equalsIgnoreCase("remove")) {
							
							
						}
						
					}
					
					
					
					
					
					
					
				}else {
					//Login
					if(args[0].equalsIgnoreCase("login")) {
						int result = DataBaseHandeler.checkPlayerAccess(args[1], args[2]);
						
						switch (result) {
						case 0:	//Zugriff gewährt
							name=args[1];
							sendInfoMessage("Login successfull");
							Server.infoToAll(name + " joined the Game!");
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
				Server.clog(" "+name+" hat sich ausgeloggt!");
				Server.infoToAll(name + " left the Game!");
			}else {
				Server.clog("Ein nicht verifizierter Client hat sich wieder ausgeloggt!");
			}
			Server.connections.remove(this);
			Thread.currentThread().stop();
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
	
	
	public void iclog(String text) {
		System.out.println("[ModServer<\""+name+"\">]--> "+text);
	}

}
