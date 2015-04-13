package f2.spw;

import java.awt.Color;
import java.awt.Graphics2D;

public class SpaceShip extends Sprite{

	int step = 8;
	
	public SpaceShip(int x, int y, int width, int height) {
		super(x, y, width, height);
		
	}

	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, width, height);
		
	}

	public void move_Y(int direction){
		y += (step * direction);
		if(y < 0)
			y = 0;
		if(y > 590 - height)
			y = 590 - height;
	}

	public void move_X(int direction){
		x += (step * direction);
		if(x < 0)
			x = 0;
		if(x > 480 - width)
			x = 480 - width;
	}

}
