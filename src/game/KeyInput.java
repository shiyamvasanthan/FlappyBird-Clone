package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//Class that gets keyboard input
public class KeyInput extends KeyAdapter{
	//Create ObjectHandler object
	private ObjectHandler handler;
		
	//Create AudioLoader object, this will handle the sound when the bird jumps
	private AudioLoader audioLoader = new AudioLoader();
	
	//KeyInput constructor takes the ObjectHandler object as the argument
	public KeyInput(ObjectHandler handler) {
		this.handler = handler;
	}
	
	//Spacebar released method for bird jumping
	public void keyReleased(KeyEvent e) {
		//Iterate through all the game objects
		for (int i = 0; i < handler.object.size(); i++) {
			//Create temporary object in the LinkedList
			GameObject tempObject = handler.object.get(i);
	
			//If the object is the bird
			if (tempObject.getId() == ObjectId.Bird) {
				//If the user spaces the space bar when the game is not over and not in the intro
				if (e.getKeyCode() == KeyEvent.VK_SPACE && !Game.gameover && !Game.intro) {		
					//The game has started
					Game.started = true;
					
					//If the bird is falling the bird stops falling
					if (tempObject.velY > 0) {
						tempObject.velY = 0;
					}
					
					//Bird jumps upward
					tempObject.velY -= 6;
					
					//Play bird jumping sound effect
					audioLoader.loadAudio("/wing.wav");
					audioLoader.playAudio();
				}
			}
		}
	}
}
