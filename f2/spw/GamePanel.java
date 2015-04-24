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
	Graphics2D damage;
	Graphics2D score;
	Graphics2D highscore;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();

	public GamePanel() {
		bi = new BufferedImage(500, 600, BufferedImage.TYPE_INT_ARGB);
		big = (Graphics2D) bi.getGraphics();
		damage = (Graphics2D) bi.getGraphics();
		score = (Graphics2D) bi.getGraphics();
		highscore = (Graphics2D) bi.getGraphics();
		big.setBackground(Color.BLACK);
	}

	public void updateGameUI(GameReporter reporter){
		damage.clearRect(0, 0, 500, 600);
		if(reporter.getDamage() < 5){
			damage.setColor(Color.WHITE);	}
		else if(reporter.getDamage() >= 5 && reporter.getDamage() < 8){
			damage.setColor(Color.YELLOW);	}	
		else if(reporter.getDamage() >= 8 && reporter.getDamage() < 10){
			damage.setColor(Color.ORANGE);	}
		else if(reporter.getDamage() >= 10){
			damage.setColor(Color.RED);	}

		highscore.setColor(Color.WHITE);
		score.setColor(Color.WHITE);

		damage.drawString(String.format("Damage : %d",reporter.getDamage()), 10, 20);
		score.drawString(String.format("Score : %06d",reporter.getScore()), 390, 20);
		highscore.drawString(String.format("HighScore : %06d",reporter.getHighScore()), 176, 20);
		for(Sprite s : sprites){
			s.draw(damage);
		}
		
		repaint();
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bi, null, 0, 0);
	}

}
