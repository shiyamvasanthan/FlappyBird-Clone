package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Floor {
	//Create floor BufferedImage
	private BufferedImage floor;
	
	//Create floor variables and type
	private float x;
	private float y;
	
	//Floor constructor, takes x and y coordinates as arguments
	public Floor(float x, float y) {
		this.x = x;
		this.y = y;
		
		//Create new BufferedImageLoader object
		BufferedImageLoader imgLoader = new BufferedImageLoader();
						
		//Load the floor image
		floor = imgLoader.loadImage("/base.png");
	}

	//Update the floor
	public void tick() {
		//Floor moves from right to left
		x -= 2;
		
		//If the x coordinate of the floor equals the negative width of the floor, set it back to zero
		if (x <= -360) {
			x = 0;
		}
	}
	
	//Draw the floor, x value is initialized as zero in the constructor
	public void render(Graphics g) {
		//Draw two images of the floor, one on the screen, one off the screen
		g.drawImage(floor, (int)x, (int)y, floor.getWidth()*360/336, floor.getHeight()*360/336, null);
		g.drawImage(floor, (int)x + 360, (int)y, floor.getWidth()*360/336, floor.getHeight()*360/336, null);
	}
}
