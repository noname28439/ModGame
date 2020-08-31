package game;

import java.util.Random;

import connection.Client;
import connection.Methods;

public class Thread_MapReader implements Runnable{

	int scansize = 10;
	
	int tick = 0;
	
	@Override
	public void run() {
		try {Thread.currentThread().sleep(2000);} catch (InterruptedException e) {}
		while(true) {
			try {Thread.currentThread().sleep(150);} catch (InterruptedException e) {}
			
			
			//System.err.println(Client.x+"--"+Client.y);
			
			tick++;
			
			if(tick<10)
				tick++;
			else {
				//Nach 10 abfragen
				//World.mapx=World.coordToPosX(-Client.x)-300;
				//World.mapy=World.coordToPosY(-Client.y)-300;
			}
			
			boolean sent = false;
			int tryCount = 0;
			while(!sent) {
				tryCount++;
				int lookx = Client.x+(new Random().nextInt(scansize)-scansize/2);
				int looky = Client.y+(new Random().nextInt(scansize)-scansize/2);
				
				if(lookx>0&&looky>0&&lookx<1000&&looky<1000)
					if(World.getTileAt(lookx, looky)==null) {
						Methods.isTileNormal(lookx, looky);
						sent=true;
					}
				if(tryCount>200)
					break;
			}
			
			
			Client.sendMessage("data:get:posx");
			Client.sendMessage("data:get:posy");
			
			
		}
		
		
	}

}


