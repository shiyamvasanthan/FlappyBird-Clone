package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

//Class that handles the bird animation
public class Animation {
	//Timer variables for the pipe generation
	private long timer = 0;
	private long lastTime = System.currentTimeMillis();
	
	//Switches the direction of the bird animation
	private boolean switchAnimation = false;
		
	//The bird bobs up and down
	private boolean up = true;
	
	//Index of the birdAnimation array
	private int birdNum = 0;
	
	//Array of BufferedImages storing each image in the animation
	private BufferedImage[] birdAnimation;
		
	//X and Y coordinates of the bird animation
	private float x;
	private float y;
	
	public Animation(float x, float y) {
		this.x = x;
		this.y = y;
		
		//Create new BufferedImageLoader object
		BufferedImageLoader imgLoader = new BufferedImageLoader();
				
		birdAnimation = new BufferedImage[3];
		
		//Load the bird animation images named bird0.png, bird1.png, bird2.png
		for (int i = 0; i < birdAnimation.length; i++) {
			birdAnimation[i] = imgLoader.loadImage("/bird" + i + ".png");
		}
				
	}
	
	public void tick() {
		//Gets the time elapsed from the last time this method was called
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		//Every 10 frames the bird flaps its wings
		if (timer > 100) {
			//birdNum is 0, 1, 2, once it's 2 it switches the direction of the animation
			if (switchAnimation == false) {
				birdNum++;
				if (birdNum == 2) {
					switchAnimation = true;
				}
			} 
			//birdNum is 1, 0, once it's 0 it switches the direction of the animation
			else {
				birdNum--;
				if (birdNum == 0) {
					switchAnimation = false;
				}
			}
			//Set ticks back to zero so this repeats over and over again
			timer = 0;
		}
		
		//The bird bobs up and down
		if (up) {
			y -= 1;
			if (y <= Game.birdY - 11) {
				up = false;
			}
		} else {
			y += 1;
			if (y >= Game.birdY + 11) {
				up = true;
			}
		}
	}
		
	public void render(Graphics g) {
		g.drawImage(birdAnimation[birdNum], (int)Game.birdX, (int)y, null);
	}
	
	//Returns the current frame of the animation
	public BufferedImage getFrame() {
		return birdAnimation[birdNum];
	}
}
