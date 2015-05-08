package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceShip extends Sprite{

	int step = 8;

	BufferedImage player;

	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		try{
			player = ImageIO.read(new File("f2/image/player.png"));
		}
		catch(IOException d){

		}
		
	}

	@Override
	public void draw(Graphics2D g) {
		// g.setColor(Color.BLUE);
		// g.fillRect(x, y, width, height);
		
		g.drawImage(player,x,y,width,height,null);
	}

	public void move_Y(int direction){
		y += (step * direction);
		if(y < 30)
			y = 498;
		if(y >= 600 - height)
			y = 30;
	}

	public void move_X(int direction){
		x += (step * direction);
		if(x <= 0)
			x = 438;
		if(x >= 480 - width)
			x = 0;
	}

}
