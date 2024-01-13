package game;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

//This class updates and draws the flash when the bird dies
public class Flash {
	//Loads the flash images
	private BufferedImageLoader imgLoader;
	private BufferedImage flash;
	
	//Alpha value starts at 0
	public float alpha = 0f;
	
	//Global variable switchAlpha, allows the flash to be run only once 
	public static boolean switchAlpha;
		
	//Constructor loads the flash image once
	public Flash() {
		imgLoader = new BufferedImageLoader();
		flash = imgLoader.loadImage("/flash.jpg");
	}
	
	//Update the flash animation
	public void tick() {
		if (switchAlpha) {
			alpha += 0.09f;
			if (alpha > 0.9) {
				switchAlpha = false;
			}
		} else {
			if (alpha > 0.09) {
				alpha -= 0.09;
			}
		} 
	}
	
	//Render the flash
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2d.drawImage(flash, 0, 0, flash.getWidth() * 360/287, flash.getHeight() * 640/448, null);
	}
}
