package f2.spw;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

public class Regeneration extends Sprite{
	public static final int Y_TO_FADE = 500;
	public static final int Y_TO_DIE = 600;
	
	private int step = 12;
	BufferedImage capsule;
	private boolean alive = true;
	
	public Regeneration(int x, int y) {
		super(x, y, 20, 20);
		try{
			capsule = ImageIO.read(new File("f2/image/capsule.png"));
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
		// g.setColor(Color.GREEN);
		// g.fillRect(x, y, width, height);
		g.drawImage(capsule,x,y,width,height,null);
		
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