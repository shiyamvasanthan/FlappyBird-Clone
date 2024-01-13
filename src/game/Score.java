package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

//This class handles and draws the score
public class Score {
	//Array of 10 images representing the digits from 0 to 9
	private BufferedImage[] scoreImage = new BufferedImage[10];
		
	//Stores game score as a string
	private String scoreString;
	
	//Spacing from the current digit image to the next digit image
	private int spacing = 0;
		
	//Score constructor loads the all the images into the array
	public Score() {
		BufferedImageLoader imgLoader = new BufferedImageLoader();
		
		for (int i = 0; i < scoreImage.length; i++) {
			scoreImage[i] = imgLoader.loadImage("/" + i + ".png");
		}
	}
	
	//Converts the string to the current game score
	public void tick() {
		scoreString = Integer.toString(Game.gameScore);
	}
	
	//Draws the score images
	public void render(Graphics g) {
		if (scoreString != null) {
			//Iterate through each character in the score string
			for (int i = 0; i < scoreString.length(); i++) {
				//Set each digit of the score string equal to a char called digit
				char digit = scoreString.charAt(i);
				//Draw the image of the digit by subtracting '0' to convert the char to an integer
				//180 is center of screen, length of string * 12 will center the string every time a new digit is added  
				//Spacing is the distance between the current digit and the next one
				g.drawImage(scoreImage[digit - '0'], 180 - (scoreString.length() * 12) + spacing, 100, null);
				//Because the image of digit 1 is smaller than the rest of the images, the spacing is less
				if (digit == '1') {
					spacing += 14;
				} else {
					spacing += 22;
				}
			}
		}
		//Set the spacing back to zero every single time a new digit is drawn
		spacing = 0;
	}
}

