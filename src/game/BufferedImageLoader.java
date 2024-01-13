package game;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

//Class that loads the buffered images
public class BufferedImageLoader {
	//BufferedImage that will be returned
	private BufferedImage image;
	
	//Method that loads the image from input stream
	public BufferedImage loadImage(String path) {
		//Catch any IOExceptions
		try {
			image = ImageIO.read(getClass().getResource(path));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		//Return the image
		return image;
	}
}
