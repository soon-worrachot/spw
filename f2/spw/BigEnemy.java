package f2.spw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class BigEnemy extends Sprite{
	public static final int Y_TO_FADE = 500;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	BufferedImage storm;
	private boolean alive = true;
	
	public BigEnemy(int x, int y) {
		super(x, y, 150, 50);
		try{
			storm = ImageIO.read(new File("f2/image/storm.png"));
		}
		catch(IOException d){

		}

	}

	@Override
	public void draw(Graphics2D g) {
		if(y < Y_TO_FADE)
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		else{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 
					(float)(Y_TO_DIE - y)/(Y_TO_DIE - Y_TO_FADE)));
		}
		// g.setColor(Color.RED);
		// g.fillRect(x, y, width, height);
		g.drawImage(storm,x,y,width,height,null);
		
	}

	public void proceed(int y2die){
		y += step;
		if(y > y2die){
			die();
		}
	}

	public void die(){
		alive = false;
	}
	
	public boolean isAlive(){
		return alive;
	}
}