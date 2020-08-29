package settings;

public class Settings {

	
	//Server
    public static int PORT = 25565;
    public static String userDB_file_Path = "D:\\Programmieren\\1_ModGame\\WebPage\\userDB.db";
    public static String connection_message_seperator = ":";
    
    //PlayerSettings
    public static int player_delay_regeneration_delay = 100;
    public static int player_hp = 100;
    public static int player_damage = 5;
    public static int player_attack_radius = 10;
    public static int player_move_radius = 10;
    public static int player_interact_tile_radius = 3;
    
    //DelayBalance
    public static float 
    delay_recieve_arrayOutOfBound = 0.3f,
    delay_maprequest_isNormal = 0.1f,
    delay_maprequest_tileID = 1,
    delay_chat_send = 0.1f,
    delay_mapInteraction_createWall = 10,
    delay_mapInteraction_createWall__OUT_OF_RANGE = 2,
    delay_playerMovement__RANGE = 0.5f,
    delay_playerMovement_wall__WALL_KEY = 0.2f,
    delay_playerMovement_wall = 0.1f,
    delay_playerMovement_normal = 0.1f,
    delay_playerMovement__OUT_OF_MAP = 3,
    delay_playerAttack__NAME_NOT_FOUND = 5,
    delay_playerAttack__OUT_OF_RANGE = 1,
    delay_playerAttack = 0.1f
    ;
    	
}
