package game;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class EndMenu implements MouseListener{
	//End Menu images
	private BufferedImage gameOver;
	private BufferedImage scoreBoard;
	private BufferedImage restart;
	private BufferedImage play;
	private BufferedImage bronze;
	private BufferedImage silver;
	private BufferedImage gold;
	private BufferedImage platinum;
	
	//Y coordinate of the scoreboard
	public static int scoreBoardY = 640;
	
	//Audio loader object
	private AudioLoader audioLoader;
	
	//Image loader object
	private BufferedImageLoader imgLoader;
	
	//Makes sure that the scoreboard audio plays only once
	private boolean playOnce = true;
	
	//Create handler object to access the linkedlist
	private ObjectHandler handler;
	
	private float alpha = 0.01f;
	
	//Constructor loads all the images
	public EndMenu(ObjectHandler handler) {
		//Set the handler equal to the handler passed as an argument in the game class
		this.handler = handler;
		
		//Instantiate audio and image loaders
		imgLoader = new BufferedImageLoader();
		audioLoader = new AudioLoader();
				
		//Load menu images
		gameOver = imgLoader.loadImage("/gameover.png");
		scoreBoard = imgLoader.loadImage("/scoreboard.png");	
		restart = imgLoader.loadImage("/restart.png");
		play = imgLoader.loadImage("/play.png");
		bronze = imgLoader.loadImage("/medal_bronze.png");
		silver = imgLoader.loadImage("/medal_silver.png");
		gold = imgLoader.loadImage("/medal_gold.png");
		platinum = imgLoader.loadImage("/medal_platinum.png");
	}
	
	//Updates the menu
	public void tick() {		
		//The scoreboard scrolls upwards quickly
		if (scoreBoardY > 210) {
			scoreBoardY -= 43;
		} 
		//Once that's done, play the audio of the scoreboard once
		else {
			if (playOnce) {
				audioLoader.loadAudio("/swoosh.wav");
				audioLoader.playAudio();
				playOnce = false;
			}
		}
	}
	
	//Renders the menu images
	public void render(Graphics g) {
		//Draw the game over image
		g.drawImage(gameOver, 60, 130, (int)(gameOver.getWidth() * 1.25), (int)(gameOver.getHeight() * 1.25), null);

		//Draw the scoreboard image
		g.drawImage(scoreBoard, 39, scoreBoardY, (int)(scoreBoard.getWidth() * 1.25), (int)(scoreBoard.getHeight() * 1.25), null);
		
		//Draw the game medal image based on the score the user gets
		if (Game.gameScore >= 40) {
			g.drawImage(platinum, 71, 263, (int)(platinum.getWidth() * 1.25), (int)(platinum.getHeight() * 1.25), null);
		} else if (Game.gameScore >= 30) {
			g.drawImage(gold, 71, 263, (int)(gold.getWidth() * 1.25), (int)(gold.getHeight() * 1.25), null);
		} else if (Game.gameScore >= 20) {
			g.drawImage(silver, 71, 263, (int)(silver.getWidth() * 1.25), (int)(silver.getHeight() * 1.25), null);
		} else if (Game.gameScore >= 10) {
			g.drawImage(bronze, 71, 263, (int)(bronze.getWidth() * 1.25), (int)(bronze.getHeight() * 1.25), null);
		}
		
		//Draw the play button image
		g.drawImage(play, 115, 380, (int)(play.getWidth() * 1.25), (int)(play.getHeight() * 1.25), null);	
	}

	public void mousePressed(MouseEvent e) {
		//Keep track of mouse coordinate when the mouse is clicked
		int mx = e.getX();
		int my = e.getY();
		
		//If the game is over and the user clicks the play button, play again
		if (mx >= 115 && mx <= 115 + play.getWidth() * 1.25 && my >= 380 && my <= 380 + play.getHeight() * 1.25 && Game.gameover) {
			//Play the same audio as the scoreboard
			audioLoader.loadAudio("/swoosh.wav");
			audioLoader.playAudio();
			
			//Clear all the objects in the linkedlist, this removes all pipes and the bird
			handler.object.clear();
			
			//Add a new bird
			handler.addObject(new Bird(Game.birdX, Game.birdY, ObjectId.Bird, handler));
			
			//Set the gamescore to 0
			Game.gameScore = 0;
						
			//Set game started to false which goes renders the bird and get ready animation
			Game.started = false;
			
			//Set gameover to false
			Game.gameover = false;
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
