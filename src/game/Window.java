package game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window{
			
	//Window constructor contains width, height, title, and game object
	public Window(int w, int h, String title, Game game) {
		//Set the canvas size of the window to the width and height parameters
		game.setPreferredSize(new Dimension(w, h));
		
		//Initalize JFrame with the title parameter
		JFrame frame = new JFrame(title);
		
		//Add the canvas to the JFrame
		frame.add(game);
		
		//Allow the JFrame to fit all of the components
		frame.pack();
		
		//When the user exits the window, terminate the program
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Set resizable to false, set visible to true, set the window to the middle of the screen
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
				
		//Start the game by calling the method
		game.start();
		
	}
}
