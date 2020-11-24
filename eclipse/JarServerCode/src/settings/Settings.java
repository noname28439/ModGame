package settings;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.ArrayList;


import com.sun.org.apache.bcel.internal.generic.CPInstruction;

import main.DateiReader;

public class Settings {

	
	public static void loadFromFile(String path) {
		ArrayList<String> content = DateiReader.read(Paths.get(path));
		
		for(String cl : content) {
			String type = cl.split(":")[0];
			String tag = cl.split(":")[1];
			String value = cl.split(":")[2];
			
			try {
				Field foundField = Settings.class.getField(tag);
				
				
				try {
					switch (type) {
					case "String":
							foundField.set(null, value);
							System.out.println("Successfully set "+tag+" to "+value);
						break;
					case "int":
						foundField.set(null, Integer.valueOf(value));
						System.out.println("Successfully set "+tag+" to "+value);
						break;
					case "float":
						foundField.set(null, Float.valueOf(value));
						System.out.println("Successfully set "+tag+" to "+value);
						break;
					case "boolean":
						foundField.set(null, Boolean.valueOf(value));
						System.out.println("Successfully set "+tag+" to "+value);
						break;
	
					default:
						System.out.println("ERROR: No valid datatype [\""+type+"\"]");
						break;
					}
					
				} catch (IllegalArgumentException | IllegalAccessException e) {e.printStackTrace();}
				
				
			} catch (NoSuchFieldException | SecurityException e) {
				System.out.println("ERROR: No Setting named \""+tag+"\" found!");
			}
			
		}
		
	}
	
	//Server
    public static int PORT = 25566;
    public static String userDB_file_Path = "D:\\Programmieren\\1_ModGame\\WebPage\\userDB.db";
    
    public static String bestPlayer_ever_file_Path = "D:\\Programmieren\\1_ModGame\\WebPage\\z_ever.best";
    public static String bestPlayer_now_file_Path = "D:\\Programmieren\\1_ModGame\\WebPage\\z_now.best";
    public static int PlayerRankFileListUpdateDelay = 1000;
    
    
    public static boolean friendlyFire = false;			//false: Spieler der selben IP können sich nicht angereifen | true: Spieler der selben IP können sich angreifen
    
    public static String connection_message_seperator = ":";
    public static int ClientConnectionLimit = 3;
    public static int mapsize = 2000;
    public static int spawnRadius = 300;
    
    //PlayerSettings
    public static int player_delay_regeneration_delay = 100;
    public static int player_hp_spawn = 100;
    public static int player_damage = 5;
    public static int player_attack_default_radius = 10;
    public static int player_attack_tower_radius = player_attack_default_radius*2;
    public static int player_move_default_radius = 10;
    public static int player_move_sneaking_radius = player_attack_default_radius/2;
    public static int player_interact_tile_radius = 3;
    public static int player_sneaking_detectRange = 30;
    public static int player_hp_max = 200;
    public static int player_hp_kill_regen = player_hp_max/2;
    
    public static int map_noTile_possibility = 2500;
    
    public static int trap_damage = 100;
    
    //DelayBalance
    public static float
    delay_recieve_arrayOutOfBound = 0.3f,
    delay_maprequest_isNormal = 0.02f,
    delay_maprequest_tileID = 1,
    delay_chat_send = 0.1f,
    delay_mapInteraction_createWall = 1,
    delay_mapInteraction_createHighway = 2,
    delay_mapInteraction_createTrap = 5,
    delay_mapInteraction_resetTile = 2,
    delay_mapInteraction_resetTileID__OUT_OF_RANGE = 2,
    delay_playerMovement__RANGE = 0.5f,
    delay_playerMovement_wall__WALL_KEY = 0.4f,
    delay_playerMovement_wall = 0.2f,
    delay_playerMovement_normal = 0.2f,
    delay_playerMovement__OUT_OF_MAP = 1,
    delay_playerAttack__NAME_NOT_FOUND = 3,
    delay_playerAttack__OUT_OF_RANGE = 1,
    delay_playerAttack = 0.1f,
    delay_tile_request_outOfMap = 0.1f,
    delay_item_mine_item1 = 0.1f,
    delay_item_mine_item2 = 0.2f,
    delay_item_mine_item3 = 10
    ;
    	
}
