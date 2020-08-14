package display;

public class Collision {

	
	public static boolean circleToRect(float cx, float cy, int radius, float rectx, float recty, int width , int height) {
		
		if(cx >= rectx && cx <=rectx + width) {
			if((cy - recty) * (cy - recty) <= radius * radius) return true;
			if((cy - (recty + height)) * (cy - (recty + height)) <= radius * radius) return true;
		}
		
		if(cy >= recty && cy <=recty + height) {
			if((cx - rectx) * (cx - rectx) <= radius * radius) return true;
			if((cx - (rectx + width)) * (cx - (rectx + width)) <= radius * radius) return true;
		}
		
		
		for(int x = 0 ; x < 2 ; x++) {
			for(int y = 0 ; y < 2 ; y++) {
				
				
				float absx = cx - (rectx + x * width);
				float absy = cy - (recty + y * height);
				float abs = absx * absx + absy * absy;
				
				if(abs <= radius * radius) return true;
			}
			
		}
		
		
		if(cx >= rectx && cx <=rectx + width && cy >= recty && cy <=recty + height) return true;
		
		
		return false;
	}
	
	
	public static boolean CircleToCircle(float c1x, float c1y, int c1radius , float c2x, float c2y, int c2radius) {
		float absx = c1x - c2x;
		float absy = c1y - c2y;
		float abs =	absx * absx + absy * absy;
		
		if(abs <= (c1radius + c2radius) *  (c1radius + c2radius)) return true;
		
		
		
		return false;
	}
	
	public static boolean rectToRect(float rect1x, float rect1y , int width1, int height1, float rect2x, float rect2y , int width2, int height2) {
		
		if(rect1x <= rect2x + width2 && rect1x + width1 >= rect2x && 
				rect1y <= rect2y + height2 && rect1y + height1 >= rect2y) return true;
		
		
		
		
		return false;
	}
	
	
	
	
	
}
