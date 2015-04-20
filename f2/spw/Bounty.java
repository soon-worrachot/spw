package f2.spw;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Bounty extends Sprite{
	public static final int Y_TO_FADE = 500;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	private boolean alive = true;
	
	public Bounty(int x, int y) {
		super(x, y, 10, 20);
		
	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);
		
	}

	public void proceed(int y2die){
		y += step;
		if(y > y2die){
			alive = false;
		}
	}
	
	public boolean isAlive(){
		return alive;
	}
}