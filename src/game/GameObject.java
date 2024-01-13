package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

//Abstract superclass that keeps track of all the objects in the game
//Abstract because it has no functionality, serves solely as a blueprint for game objects
public abstract class GameObject {
	//Only the subclasses Bird, Pipe can access these variables
	protected float x;
	protected float y;
	protected float velX = 0;
	protected float velY = 0;
	protected ObjectId id;
	
	//GameObject constructor, subclasses will inherit these attributes
	public GameObject(float x, float y, ObjectId id) {
		//'this.x' means the protected variable x
		//Set the protected variable x to the argument
		//Do the same for y and id
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	//Whenever we create class that extends GameObject, these methods need to be implemented
	public abstract void tick(LinkedList<GameObject> object);
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	//Getters and setters
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getVelX() {
		return velX;
	}
	
	public float getVelY() {
		return velY;
	}
	
	public void setVelX(float velX) {
		this.velX = velX;
	}
	
	public void setVelY(float velY) {
		this.velY = velY;
	}

	public ObjectId getId() {
		return id;
	}
}
