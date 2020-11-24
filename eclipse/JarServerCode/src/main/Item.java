package main;

import settings.Settings;

public class Item {
	
	public static int 
	ITEM_1 = 0, 
	ITEM_2 = 1,
	ITEM_3 = 2;
	//If you want to implement a new Item you must change the SIZE to SIZE++;
	public static int SIZE = 3;		//Amount of ItemTypes. Has to be changed when new Items are implemented!!!
	
	
	public static int getItemIDfromTileID(int TileID) {
		switch (TileID) {
		case 6:
			return 0;
		case 7:
			return 1;
		case 8:
			return 2;

		default:
			
			break;
		}
	return -1;
	}
	
	public static float getMineDelayForItem(int ItemID) {
		switch (ItemID) {
		case 0:
			return Settings.delay_item_mine_item1;
		case 1:
			return Settings.delay_item_mine_item2;
		case 2:
			return Settings.delay_item_mine_item3;

		default:
			
			break;
		}
	return -1;
	}
	
	
	
	
	
}
