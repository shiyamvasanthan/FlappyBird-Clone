package game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class StartMenu implements MouseListener{
	//Handler object
	//private ObjectHandler handler;
	
	//BufferedImageLoader object
	private BufferedImageLoader imgLoader;
	
	//Start menu BufferedImages
	private BufferedImage title;
	private BufferedImage play;
	
	//Y Coordinate of the title and bird
	private float titleY = 200;
	private float birdY = 212;
	
	//Animation object
	private Animation animation;
	
	//AudioLoader object
	private AudioLoader audioLoader;
	
	//Bird and title bob up and down
	private boolean up = true;
	
	//Start menu constructor
	public StartMenu() {		
		//Instantiate bufferedImageLoader
		imgLoader = new BufferedImageLoader();
		
		//Instantiate audioLoader
		audioLoader = new AudioLoader();
		
		//Instantiate animation for start menu bird
		animation = new Animation(277, birdY);
		
		//Load the title and play button images
		title = imgLoader.loadImage("/title.png");
		play = imgLoader.loadImage("/play.png");
	}

	//Update the start menu
	public void tick() {
		//Title and bird bob up and down
		if (up) {
			titleY -= 0.3;
			birdY -= 0.3;
			if (titleY <= 195 && birdY <= 207) {
				up = false;
			}
		} else {
			titleY += 0.3;
			birdY += 0.3;
			if (titleY >= 205 && birdY >= 217) {
				up = true;
			}
		}
			
		//Tick the bird flapping animation
		animation.tick();
	}
	
	//Render the start menu
	public void render(Graphics g) {
		g.drawImage(title, 40, (int)titleY, (int)(title.getWidth() * 1.25), (int)(title.getHeight() * 1.25), null);
		g.drawImage(play, 115, 290, (int)(play.getWidth() * 1.25), (int)(play.getHeight() * 1.25), null);
		g.drawImage(animation.getFrame(), 277, (int)birdY, (int)(animation.getFrame().getWidth() * 1.25), (int) (animation.getFrame().getHeight() * 1.25), null);
	}
	
	//When the mouse is pressed
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		//When the user presses the mouse button exit the start menu
		if (mx >= 115 && mx <= 115 + play.getWidth() * 1.25 && my >= 290 && my <= 290 + play.getHeight() * 1.25 && Game.intro) {
			audioLoader.loadAudio("/swoosh.wav");
			audioLoader.playAudio();
			Game.intro = false; 
		}
	}

	//Abstract methods
	public void mouseClicked(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {
		
	}
}
