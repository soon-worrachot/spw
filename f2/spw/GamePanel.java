package f2.spw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private BufferedImage bi;	
	Graphics2D big;
	Graphics2D big2;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(500, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		big2 = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
	}

	public void updateGameUI(GameReporter reporter){
		big.clearRect(0, 0, 500, 600);
		if(reporter.getDamage() < 5){
			big.setColor(Color.WHITE);	}
		else if(reporter.getDamage() >= 5 && reporter.getDamage() < 8){
			big.setColor(Color.YELLOW);	}	
		else if(reporter.getDamage() >= 8 && reporter.getDamage() < 10){
			big.setColor(Color.ORANGE);	}
		else if(reporter.getDamage() >= 10){
			big.setColor(Color.RED);	}

		big2.setColor(Color.WHITE);
		big.drawString(String.format("Damage : %d",reporter.getDamage()), 10, 20);
		big2.drawString(String.format("Score : %05d",reporter.getScore()), 400, 20);
		for(Sprite s : sprites){
			s.draw(big);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
