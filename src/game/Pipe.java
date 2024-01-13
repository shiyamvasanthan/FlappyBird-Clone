package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Pipe extends GameObject{
	//Pipe height and width
	public static final float pipeWidth = 52;
	public static final float pipeHeight = 320;
	
	//PipeUp (pipe facing up) and PipeDown (pipe facing down) BufferedImages
	private BufferedImage pipeUpImage;
	private BufferedImage pipeDownImage;
			
	//ObjectHandler object
	private ObjectHandler handler;
				
	//Create BufferedImageLoader object
	private BufferedImageLoader imgLoader = new BufferedImageLoader();
		
	//Pipe Constructor, subclass of GameObject
	public Pipe(float x, float y, ObjectId id, ObjectHandler handler) {
		super(x, y, id);
			
		//Set the pipe handler equal to the the argument in the constructor
		this.handler = handler;
		
		//Load pipe images
		pipeUpImage = imgLoader.loadImage("/pipe-up-green.png");
		pipeDownImage = imgLoader.loadImage("/pipe-down-green.png");

		//Create new handler object
		handler = new ObjectHandler();
		
		//Add the pipe objects to the linkedList
		handler.addObject(this);
	}

	//Updates the movement of each pipe in the LinkedList
	public void tick(LinkedList<GameObject> object) {
		//If the pipe moves off the screen, remove it from the LinkedList
		//I didn't make it zero because the next pipe would glitch forward
		//Making it negative makes it so that nothing glitches
		if (x + pipeWidth < -200) {
			handler.removeObject(this);
		} 
		
		//The pipes move from right to left across the screen
		x -= 2;
	}

	//Draws the pipes to the screen based on whether the pipe is facing up or down
	public void render(Graphics g) {
		if (id == ObjectId.PipeUp) {
			g.drawImage(pipeUpImage, (int)x, (int)y, null);
		} else if (id == ObjectId.PipeDown) {
			g.drawImage(pipeDownImage, (int)x, (int)y, null);
		}
	}

	//Gets the bounds of the pipe
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, (int)pipeWidth, (int)pipeHeight);
	}
}
