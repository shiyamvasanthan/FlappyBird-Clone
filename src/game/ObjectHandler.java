package game;

import java.awt.Graphics;
import java.util.LinkedList;

//Class that stores all the objects in a LinkedList
public class ObjectHandler {
	//Creates a linked list of all the objects in our game
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	//Create a temporary object
	private GameObject tempObject;
	
	//Update each object in the linked list
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			//Return each object in the linked list
			tempObject = object.get(i);
			
			//Call the tick method in the GameObject child class (pipe and bird)
			tempObject.tick(object);
		}
	}
	
	//Draw each object in the linked list
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			//Return each object in the linked list
			tempObject = object.get(i);
			
			//Call the render method in the GameObject child class (pipe and bird)
			tempObject.render(g);
		}
	}
	
	//Add the object to the linked list
	public void addObject(GameObject object) {
		//'this.object' refers to the linkedlist
		this.object.add(object);
	}
	
	//Remove the object from the linked list
	public void removeObject(GameObject object) {
		//'this.object' refers to the linkedlist
		this.object.remove(object);
	}
}
