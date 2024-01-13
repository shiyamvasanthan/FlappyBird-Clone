package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Bird extends GameObject{
	//Constants bird width and height
	private final int birdWidth = 34;
	private final int birdHeight = 24;
	
	//Maximum velocity constant
	private final float maxVelocity = 10;
	
	//Gravity the bird experiences
	private float gravity = 0.3f;
			
	//Array of BufferedImages storing each image in the animation
	private BufferedImage[] birdAnimation;
		
	//Create new ObjectHandler object
	private ObjectHandler handler;
	
	//Create new AudioLoader object
	private AudioLoader audioLoader = new AudioLoader();
		
	//Create new Animation object
	private Animation animation;
	
	//Create new flash object
	private Flash flash;
	
	//Bird constructor, subclass of GameObject
	public Bird(float x, float y, ObjectId id, ObjectHandler handler) {
		//Inherit GameObject
		super(x, y, id);
		
		//Set the bird handler equal to the argument in constructor
		this.handler = handler;
		
		//Array of three BufferedImages 
		birdAnimation = new BufferedImage[3];
		
		//Create new BufferedImageLoader object
		BufferedImageLoader imgLoader = new BufferedImageLoader();
		
		//Create object handler object
		handler = new ObjectHandler();
		
		//Load the bird animation images named bird0.png, bird1.png, bird2.png
		for (int i = 0; i < birdAnimation.length; i++) {
			birdAnimation[i] = imgLoader.loadImage("/bird" + i + ".png");
		}
		
		//Create new Animation object
		animation = new Animation(x, y);
				
		//Instantiate flash object
		flash = new Flash();
		
		//Add the bird object to the LinkedList
		handler.addObject(this);
	}

	//Update the bird
	public void tick(LinkedList<GameObject> object) {
		//Downward motion of the bird is affected by gravity
		velY += gravity;
		y += velY;
		
		//Prevent the bird going past maximum downwards velocity
		if (velY > maxVelocity) {
			velY = maxVelocity;
		}
		
		//Prevent the bird going past maximum upwards velocity
		if (velY < -maxVelocity) {
			velY = -maxVelocity;
		}
		
		//If the bird hits the top of the screen, stop it from going past the screen
		if (y <= 0) {
			y = 0;
			velY = 0;
		}
		
		//Call the animation method
		animation.tick();
		
		//Call the point method
		point();
		
		//Call the collision method
		collision();
	}

	public void collision() {
		//Iterate through all the game objects
		for (int i = 0; i < handler.object.size(); i++) {
			//Create temporary object in the LinkedList
			GameObject tempObject = handler.object.get(i);
	
			//If the bounds of the bird intersects the bounds of the floor
			if (getBounds().intersects(new Rectangle(0, 556, 360, 84))) {
				//If the bird hits the floor the game is over
				y = Game.floorY - birdHeight;
				Game.gameover = true;
				
				//Play the hit and die sound when the bird hits the floor
				audioLoader.loadAudio("/hit.wav");
				audioLoader.playAudio();	
				audioLoader.loadAudio("/die.wav");
				audioLoader.playAudio();
				
				//Draw the flash onto the screen
				Flash.switchAlpha = true;
				
				//Set the y coordinate of the scoreboard back to the bottom of the screen
				EndMenu.scoreBoardY = 640;
			}
			
			//If the bounds of the bird intersects the bounds of the pipeUp or pipeDown
			if (tempObject.getId() == ObjectId.PipeUp || tempObject.getId() == ObjectId.PipeDown) {
				if (getBounds().intersects(tempObject.getBounds())) {
					//The game is over, play the hit and die sound when the bird hits the pipe
					Game.gameover = true;
					audioLoader.loadAudio("/hit.wav");
					audioLoader.playAudio();
					audioLoader.loadAudio("/die.wav");
					audioLoader.playAudio();
					
					//Set the y coordinate of the scoreboard back to the bottom of the screen
					EndMenu.scoreBoardY = 640;
				}
			}
		}
	}
	
	public void point() {
		//Iterate through all the game objects
		for (int i = 0; i < handler.object.size(); i++) {
			//Create temporary object in the LinkedList
			GameObject tempObject = handler.object.get(i);
			
			//If the bird successfully reaches the middle of the pipe the player scores a point
			//Do this calculation based on the pipe facing upwards
			if (tempObject.getId() == ObjectId.PipeUp) {
				if (y > tempObject.y - CreatePipe.pipeGapVertical && y < tempObject.y - birdHeight) {
					if (x >= tempObject.x + Pipe.pipeWidth/2 - birdWidth && x <= tempObject.x + Pipe.pipeWidth/2 - birdWidth + 1) {
						//Add one to the score
						Game.gameScore++;
						//Play the point sound when the player scores
						audioLoader.loadAudio("/point.wav");
						audioLoader.playAudio();
					}
				}
			}
		}
	}
	
	//Draw the bird on the screen at the specified coordinates
	public void render(Graphics g) {
		if (Game.started) {
			g.drawImage(animation.getFrame(), (int)Game.birdX, (int)y, null);
		}
	}

	//Gets the bounds of the bird to detect collision
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, birdWidth, birdHeight);
	}
	
	//Get the x value of the bird
	public float getX() {
		return x;
	}

	//Get the y value of the bird
	public float getY() {
		return y;
	}

	//Set the x value of the bird to the argument
	public void setX(float x) {
		this.x = x;
	}

	//Set the y value of the bird to the argument
	public void setY(float y) {
		this.y = y;
	}

	//Get the velX of the bird
	public float getVelX() {
		return velX;
	}

	//Get the velY of the bird
	public float getVelY() {
		return velY;
	}

	//Set the velX of the bird to the argument
	public void setVelX(float velX) {
		this.velX = velX;
	}

	//Set the velY of the bird to the argument
	public void setVelY(float velY) {
		this.velY = velY;
	}

	//Get the id of the bird object
	public ObjectId getId() {
		return id;
	}
}
