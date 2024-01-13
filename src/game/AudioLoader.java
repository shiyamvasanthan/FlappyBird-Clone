package game;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Class that loads the audio
public class AudioLoader {
	//Create Clip object named clip
	private Clip clip;
	
	//Method that loads the audio from input stream
	public void loadAudio(String path) {
		//Catch the required audio exceptions
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(path));
			clip = AudioSystem.getClip();
			clip.open(audio);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	//Method that plays the audio
	public void playAudio() {
		clip.start();
	}
}
