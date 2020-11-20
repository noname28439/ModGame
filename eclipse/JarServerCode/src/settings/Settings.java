package settings;

public class Settings {

	
	//Server
    public static int PORT = 25566;
    public static String userDB_file_Path = "D:\\Programmieren\\1_ModGame\\WebPage\\userDB.db";
    
    public static String bestPlayer_ever_file_Path = "D:\\Programmieren\\1_ModGame\\WebPage\\z_ever.best";
    public static String bestPlayer_now_file_Path = "D:\\Programmieren\\1_ModGame\\WebPage\\z_now.best";
    public static int PlayerRankFileListUpdateDelay = 1000;
    
    public static String connection_message_seperator = ":";
    public static int ClientConnectionLimit = 3;
    public static int mapsize = 2000;
    public static int spawnRadius = 300;
    
    //PlayerSettings
    public static int player_delay_regeneration_delay = 100;
    public static int player_hp = 100;
    public static int player_damage = 5;
    public static int player_attack_default_radius = 10;
    public static int player_attack_tower_radius = player_attack_default_radius*2;
    public static int player_move_radius = 10;
    public static int player_interact_tile_radius = 3;
    public static int player_sneaking_detectRange = 30;
    public static int player_hp_max = 200;
    
    //DelayBalance
    public static float
    delay_recieve_arrayOutOfBound = 0.3f,
    delay_maprequest_isNormal = 0.02f,
    delay_maprequest_tileID = 1,
    delay_chat_send = 0.1f,
    delay_mapInteraction_createWall = 5,
    delay_mapInteraction_createHighway = 3,
    delay_mapInteraction_createTrap = 10,
    delay_mapInteraction_resetTile = 2,
    delay_mapInteraction_resetTileID__OUT_OF_RANGE = 2,
    delay_playerMovement__RANGE = 0.5f,
    delay_playerMovement_wall__WALL_KEY = 0.2f,
    delay_playerMovement_wall = 0.1f,
    delay_playerMovement_normal = 0.1f,
    delay_playerMovement__OUT_OF_MAP = 1,
    delay_playerAttack__NAME_NOT_FOUND = 3,
    delay_playerAttack__OUT_OF_RANGE = 1,
    delay_playerAttack = 0.1f,
    delay_tile_request_outOfMap = 0.1f
    ;
    	
}
