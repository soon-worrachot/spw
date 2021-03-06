package f2.spw;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.Timer;


public class GameEngine implements KeyListener, GameReporter{
	GamePanel gp;
	
	public static final int DAMAGE_TO_DIE = 10;	
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();	
	private ArrayList<Bounty> bountys = new ArrayList<Bounty>();
	private ArrayList<Regeneration> regens = new ArrayList<Regeneration>();
	private ArrayList<BigEnemy> benemies = new ArrayList<BigEnemy>();
	private SpaceShip v;	
	
	private Timer timer;
	
	private int dmg = 0;
	private long score = 0;
	private long highscore = 0;
	private double difficulty = 0.1;
	
	public GameEngine(GamePanel gp, SpaceShip v) {
		this.gp = gp;
		this.v = v;		
		
		gp.sprites.add(v);
		
		timer = new Timer(35 , new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				process();
			}
		});
		timer.setRepeats(true);
		
	}
	
	public void start(){
		timer.start();
	}
	
	private void generateBigEnemy(){
		BigEnemy be = new BigEnemy((int)(Math.random()*500),-20);
		gp.sprites.add(be);
		benemies.add(be);

	}
	private void generateEnemy(){
		Enemy e = new Enemy((int)(Math.random()*500), -20);
		gp.sprites.add(e);
		enemies.add(e);
	}
	
	private void generateBounty(){
		Bounty b = new Bounty((int)(Math.random()*500), -20);
		gp.sprites.add(b);
		bountys.add(b);
	}

	private void generateRegen(){
		Regeneration r = new Regeneration((int)(Math.random()*500), -20);
		gp.sprites.add(r);
		regens.add(r);
	}

	private void process(){
		if(Math.random() < difficulty){
			generateEnemy();
		}
		if(Math.random() > 0.99){
			generateBounty();
		}
		if(Math.random() < 0.01){
			generateRegen();
		}
		if(score > 200){
			if(Math.random() > 0.995)
			generateBigEnemy();
		}
		if(((score % 50) == 0) && (score != 0) ){
			if(difficulty < 1){
			difficulty += 0.01;}
		}

		Iterator<Bounty> b_iter = bountys.iterator();
		while(b_iter.hasNext()){
			Bounty b = b_iter.next();
			b.proceed(600);
			
			if(!b.isAlive()){
				b_iter.remove();
				gp.sprites.remove(b);
			}
		}

		Iterator<Regeneration> r_iter = regens.iterator();
		while(r_iter.hasNext()){
			Regeneration r = r_iter.next();
			r.proceed(600);
			
			if(!r.isAlive()){
				r_iter.remove();
				gp.sprites.remove(r);
			}
		}


		Iterator<Enemy> e_iter = enemies.iterator();
		while(e_iter.hasNext()){
			Enemy e = e_iter.next();
			e.proceed(600);
			
			if(!e.isAlive()){
				e_iter.remove();
				gp.sprites.remove(e);
				score += 1;
			}
		}

		Iterator<BigEnemy> be_iter = benemies.iterator();
		while(be_iter.hasNext()){
			BigEnemy be = be_iter.next();
			be.proceed(600);
			
			if(!be.isAlive()){
				be_iter.remove();
				gp.sprites.remove(be);
				score += 10;
			}
		}
		
		if(score > highscore){
			highscore = score;
		}

		gp.updateGameUI(this);
		
		Rectangle2D.Double vr = v.getRectangle();
		Rectangle2D.Double er;
		for(Enemy e : enemies){
			er = e.getRectangle();

			if(er.intersects(vr)){
				die();
				e.proceed(600 - v.y);
				gp.sprites.remove(e);
				return;
			}
		}

		Rectangle2D.Double ber;
		for(BigEnemy be : benemies){
			ber = be.getRectangle();

			if(ber.intersects(vr)){
				die();
				be.proceed(600 - v.y);
				gp.sprites.remove(be);
				return;
			}
		}


		Rectangle2D.Double bor;
		for(Bounty b : bountys){
			bor = b.getRectangle();

			if(bor.intersects(vr)){
				b.proceed(600 - v.y);
				gp.sprites.remove(b);
				score += 10;
				return;
			}
		}

		Rectangle2D.Double rr;
		for(Regeneration r : regens){
			rr = r.getRectangle();

			if(rr.intersects(vr)){
				r.proceed(600 - v.y);
				gp.sprites.remove(r);
				if(dmg != 0){
					dmg -= 1;
				}
				return;
			}
		}
	}
	
	public void die(){
		if(score <= 5){
			score = 0;
		}else score -= 5;
		if(dmg == DAMAGE_TO_DIE){
			timer.stop();
		}else dmg++;
	}
	
	void controlVehicle(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			v.move_X(-1);
			break;
		case KeyEvent.VK_RIGHT:
			v.move_X(1);
			break;
		case KeyEvent.VK_UP:
			v.move_Y(-1);
			break;
		case KeyEvent.VK_DOWN:
			v.move_Y(1);
			break;
		case KeyEvent.VK_W:
			v.move_X(-1);
			v.move_Y(-1);
			break;
		case KeyEvent.VK_A:
			v.move_X(-1);
			v.move_Y(1);
			break;
		case KeyEvent.VK_S:
			v.move_X(1);
			v.move_Y(1);
			break;
		case KeyEvent.VK_D:
			v.move_X(1);
			v.move_Y(-1);
			break;
		case KeyEvent.VK_ADD:
			if(difficulty < 1){
			difficulty += 0.1;}
			break;
		case KeyEvent.VK_SPACE :
			score += 10;
			break;
		case KeyEvent.VK_ENTER:{
			reSpawn();
			break;
			}
		}
	}

	public long getHighScore(){
		return highscore;
	}

	public long getScore(){
		return score;
	}

	public int getDamage(){
		return dmg;
	}

	private void reSpawn(){
		timer.start(); 
		score = 0;
		dmg = 0;
		difficulty = 0.1;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		controlVehicle(e);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//do nothing		
	}

}
