package connection;

public class Methods {

	
	public static float
    delay_recieve_arrayOutOfBound = 0.3f,
    delay_maprequest_isNormal = 0.1f,
    delay_maprequest_tileID = 1,
    delay_chat_send = 0.1f,
    delay_mapInteraction_createWall = 10,
    delay_mapInteraction_createHighway = 5,
    delay_mapInteraction_createTrap = 20,
    delay_mapInteraction_resetTileID__OUT_OF_RANGE = 2,
    delay_playerMovement__RANGE = 0.5f,
    delay_playerMovement_wall__WALL_KEY = 0.2f,
    delay_playerMovement_wall = 0.1f,
    delay_playerMovement_normal = 0.1f,
    delay_playerMovement__OUT_OF_MAP = 3,
    delay_playerAttack__NAME_NOT_FOUND = 5,
    delay_playerAttack__OUT_OF_RANGE = 1,
    delay_playerAttack = 0.1f,
    delay_tile_request_outOfMap = 0.1f
    ;
	
	
	
	public static void move(int x, int y) {
		Client.sendMessage("move:"+x+":"+y);
	}
	
	public static void attack(String name) {
		Client.sendMessage("attack:"+name);
	}
	
	
	
	public static void getTileID(int x, int y) {
		Client.sendMessage("map:tileid:"+x+":"+y);
	}
	public static void isTileNormal(int x, int y) {
		Client.sendMessage("map:isNormal:"+x+":"+y);
	}
	
}
