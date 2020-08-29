package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;

import settings.Settings;

public class ClientConnection implements Runnable{

	//Connection
	Socket connection;
	String name;
	PrintStream out;
	Scanner in;
	Thread listener;
	
	//
	float delay = 0;
	
	//Basic Values
	int hp = 0;
	int x=0,y=0;
	int points = 0;
	
	//Ingame Functions
	int currentTileKey = 0;
	boolean sneaking = false;
	
	
	
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
    void sendMessage(String text) {
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
    
    public void sendFeedbackMessage(String actionName, boolean success) {
    	out.println(createMesssage(new String[]{"MESSAGE","FEEDBACK",actionName,String.valueOf(success)}));
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
    			sendFeedbackMessage("attack["+player+"]", true);
        		if(result.checkDead()) {
        			sendFeedbackMessage("kill", true);
        			//Player isDead
        			points+=Integer.valueOf(result.points/10);
        			result.resetPosition();
        			result.resetKillstreak();
        			result.resetHP();
        			Server.clog(name+" killed "+result.name);
        		}
        		punish(Settings.delay_playerAttack);
    		}else {
    			//Player not in Range!
    			sendFeedbackMessage("attack(range)", false);
        		punish(Settings.delay_playerAttack__OUT_OF_RANGE);
    		}
    	}else {
    		//No player with this Name found!
    		sendFeedbackMessage("attack(name)", false);
    		punish(Settings.delay_playerAttack__NAME_NOT_FOUND);
    	}
    	
    }
    
    
    void for_move_set_x_and_y(int mvx, int mvy) {
    	/*sendFeedbackMessage("move["+x+"|"+y+"]", true);*/
    	x=mvx;
		y=mvy;
		sendFeedbackMessage("move", true);
    }
    
    
    public void move(float mvx, float mvy) {
    	
    	double distance = Server.calculateDistanceBetweenPoints(x, y, mvx, mvy);
    	
    	int tox = (int)(x+mvx);
    	int toy = (int)(y+mvy);
    	
    	if(!(distance>Settings.player_move_radius)) {
    		Tile jumpTo = World.tiles[tox][toy];
    		if(jumpTo==null) {
    			sendFeedbackMessage("move(border)", false);
    			punish(Settings.delay_playerMovement__OUT_OF_MAP);
    			
    		}else
    		switch (jumpTo.getID()) {
			case 0:	//Normal
				for_move_set_x_and_y(tox,toy);
				punish(Settings.delay_playerMovement_normal);
				break;
			case 1:	//Highway
				for_move_set_x_and_y(tox,toy);
				break;
			case 2:	//Wall
				if(currentTileKey == jumpTo.getKey()) {
					for_move_set_x_and_y(tox,toy);
					punish(Settings.delay_playerMovement_wall);
				}else {
					sendFeedbackMessage("move", false);
					punish(Settings.delay_playerMovement_wall__WALL_KEY);
				}
				break;
			case 3:	//Trap
				for_move_set_x_and_y(tox,toy);
				//---------------------------------------------------------------------------------ExecuteTrapAttack------------------------------------------------------------------------------
			break;

			default:
				break;
			}
    		
    		
    	}else {
    		sendFeedbackMessage("move(distance)", false);
    		punish(Settings.delay_playerMovement__RANGE);
    	}
    		
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
    
	    
	    public void punish(float seconds) {
	    	delayAdd(seconds);
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
    
    
    //InfluenceTileOptions
    
    public void createWallAtCurrentPos() {
    	createWallAtSpecificPos(this.x, this.y);
    }
    
    public void createWallAtSpecificPos(int x, int y) {
    	if(!(Server.calculateDistanceBetweenPoints(this.x, this.y, x, y)>Settings.player_interact_tile_radius)) {
    		World.tiles[x][y].setID(Tile.HIGHWAY);
    		World.tiles[x][y].setKey(currentTileKey);
    		World.tiles[x][y].setOwner(name);
    		sendFeedbackMessage("wallCreation["+x+"|"+y+"]", true);
    		punish(Settings.delay_mapInteraction_createWall);
    	}else
    		punish(Settings.delay_mapInteraction_createWall__OUT_OF_RANGE);
    	
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
    	points=0;
    }
    
    public void resetHP() {
    	hp=Settings.player_hp;
    }
    
	@Override
	public void run() {
		try {
		while(true) {
			try {
				
			
				String recv = in.nextLine();
				//System.err.println("[FROM_CLIENT] --> "+recv);
				iclog(recv);
				String[] args = recv.split(Settings.connection_message_seperator);
				
				
				
				if(isLoggedIn()&&!isStunned()) {
					//Character Controlls
					
					
					if(args[0].equalsIgnoreCase("attack")) {
						attack(args[1]);
					}
					
					if(args[0].equalsIgnoreCase("move")) {
						move(Float.valueOf(args[1]), Float.valueOf(args[2]));
					}
					
					if(args[0].equalsIgnoreCase("tileInteraction")) {
						if(args[1].equalsIgnoreCase("wallhere")) {
							createWallAtCurrentPos();
						}
						
					}
					
					
					if(args[0].equalsIgnoreCase("chat")) {
						Server.chatSend(args[1]);
						punish(Settings.delay_chat_send);
					}
					
					if(args[0].equalsIgnoreCase("map")) {
						if(args[1].equalsIgnoreCase("tileID")) {
							int x = Integer.valueOf(args[2]);
							int y = Integer.valueOf(args[3]);
							
							sendMessage("map:tileID:"+x+":"+y+":"+World.tiles[x][y].getID());
							punish(Settings.delay_maprequest_tileID);
						}
						if(args[1].equalsIgnoreCase("isNormal")) {
							int x = Integer.valueOf(args[2]);
							int y = Integer.valueOf(args[3]);
							
							sendMessage("map:isNormal:"+x+":"+y+":"+(World.tiles[x][y].getID()==0));
							punish(Settings.delay_maprequest_isNormal);
						}
					}
					
					
					if(args[0].equalsIgnoreCase("data")) {
						
						if(args[1].equalsIgnoreCase("get")) {
							
							
							if(args[2].equalsIgnoreCase("HP")) {returnDataRequestResult("HP", hp);}
							
							if(args[2].equalsIgnoreCase("posX")) {returnDataRequestResult("posX", x);}
							if(args[2].equalsIgnoreCase("posY")) {returnDataRequestResult("posY", y);}
							
							if(args[2].equalsIgnoreCase("delay")) {returnDataRequestResult("delay", delay);}
							
							if(args[2].equalsIgnoreCase("currentTileKey")) {returnDataRequestResult("currentTileKey", currentTileKey);}
							
							
						}
						
						if(args[1].equalsIgnoreCase("set")) {
							if(args[2].equalsIgnoreCase("currentTileKey")) {
								int rcvValue = Integer.valueOf(args[3]);
								if(rcvValue>=0) {
									currentTileKey=rcvValue;
								}
							}
							
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
				
				
			} catch (ArrayIndexOutOfBoundsException e) {
				punish(Settings.delay_recieve_arrayOutOfBound);
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
		System.out.println("[ModServer<\""+name+"\"|\""+connection.getInetAddress()+"\">]--> "+text);
	}

}
