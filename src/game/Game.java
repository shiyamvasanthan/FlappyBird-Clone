package game;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{
	//Import the serial version UID
	private static final long serialVersionUID = 1L;

	//Final x and y coordinates of the floor (0, 556)
	private final float floorX = 0;
	public static final float floorY = 556;
	
	//x and y starting coordinates of the bird (105, 275)
	public static float birdX = 105;
	public static float birdY = 275;
	
	//When the game is running, running is true
	private boolean running = false;
	
	//Create thread object
	private Thread thread;
		
	//Create ObjectHandler object
	private ObjectHandler handler;
			
	//Create pipe object
	private CreatePipe createPipe;
	
	//Create floor object
	private Floor floor;
	
	//Create background image
	private BufferedImage background;
			
	//Create getReady image
	private BufferedImage getReady;
	
	//Create getReady subimage
	private BufferedImage subGetReady;
	
	//Create score object
	private Score score;
	
	//Gamescore and highscore variables
	public static int gameScore = 0;
	public static int highScore = 0;
	
	//Variables keeping track of game state
	public static boolean intro = true;
	public static boolean started = false;
	public static boolean gameover = false;
	
	//Create Animation object
	private Animation animation;
	
	//Create start and end menu objects
	private StartMenu startMenu;
	private EndMenu endMenu;
		
	//Create screen flash object when the bird dies
	private Flash flash;
	
	//This method initalizes the game
	private void init() {
		//Instantiate ObjectHandler object
		handler = new ObjectHandler();
		
		//Instantiate CreateHandler object with the ObjectHandler object as the argument
		createPipe = new CreatePipe(handler);
		
		//Instantiate Floor object with coordinates (0, 556)
		floor = new Floor(floorX, floorY);
				
		//Instantiate score object
		score = new Score();
				
		//instantiate BirdAnimation Object at coordinates (105, 275)
		animation = new Animation(birdX, birdY);
		
		//Instantiate StartMenu object
		startMenu = new StartMenu();
		
		//Instantiate EndMenu object
		endMenu = new EndMenu(handler);
		
		//Instantiate flash object
		flash = new Flash();
		
		//instantiate BufferedImageLoader object
		BufferedImageLoader imgLoader = new BufferedImageLoader();
		
		//Load the background image
		background = imgLoader.loadImage("/background-night.png");
		
		//Load the get ready image
		getReady = imgLoader.loadImage("/message.png");
		
		//Crop out the flappybird from the get ready image
		subGetReady = getReady.getSubimage(0, 100, getReady.getWidth(), getReady.getHeight() - 100);
		
		//instantiate bird object with coordinates (105, 275) and ID bird, add to objectHandler
		handler.addObject(new Bird(birdX, birdY, ObjectId.Bird, handler));
							
		//KeyListener is the KeyInput object with the ObjectHandler as the argument
		this.addKeyListener(new KeyInput(handler));
		
		//Add mouse listener to the start menu
		this.addMouseListener(new StartMenu());
		
		//Add mouselistener to the end menu, handler as the argument allows the LinkedList to be cleared on replay
		this.addMouseListener(new EndMenu(handler));
	}
	
	//When this method is called, start the thread
	public synchronized void start() {
		//If the start method is already called, and the user calls it again this prevents any problems
		if(running) {
			return;
		}
	
		//Set game running to true
		running = true;
		
		//Thread takes runnable as the parameter
		thread = new Thread(this);
		
		//Start the thread, this automatically calls the run method below
		thread.start();
	}
	
	//This method contains the game loop
	public void run() {
		//Call init method before the game loop
		init();
		
		//Make sure the canvas gets Input from the respective listener
		this.requestFocus();
		
		//Time elapsed in nanoseconds
		long lastTime = System.nanoTime();
		
		//Run the game at 60 FPS
		double amountOfTicks = 60.0;
		
		//Nanoseconds per frame
		double ns = 1000000000 / amountOfTicks;
		
		double delta = 0;
		
		//Time elapsed in miliseconds
		long timer = System.currentTimeMillis();
		
		//Number of ticks
		int updates = 0;
		
		//Number of frames per second
		int frames = 0;
		
		//Game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1){
				//Update the game
				tick();
				updates++;
				
				//Draw the graphics
				render();
				frames++;
				
				delta--;
			}
			
			//Set frames and updates back to zero every second to maintain 60FPS
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
				updates = 0;
		 	}
		}
	}
	
	//Game updates
	private void tick() {
		//If the game is in the intro and hasn't started
		if (intro && !started) {
			startMenu.tick();
			floor.tick();
		}
		//If the game is done the intro and the game hasn't started and the game is not over
		else if (!intro && !started && !gameover) {
			floor.tick();
			animation.tick();
		}
		//If the game has started and game not over
		else if (!gameover && started) {
			//Call the tick method in the objectHandler class, this ticks the bird and the pipes
			handler.tick();
				
			//Call the tick method in the CreatePipe class, this creates the pipe objects
			createPipe.tick();
			
			//Call the tick method in the Floor class, this moves the pipe from right to left
			floor.tick();
			
			//Call the tick method in the Score class, this updates the score
			score.tick();
		} 
		//If the game is over tick the end menu
		else if (gameover) {
			flash.tick();
			endMenu.tick();
		}
		
	}
	
	//Draw the graphics
	private void render() {
		//Create a buffer strategy for the canvas
		//When the computer draws an image to the screen and has time, it preloads the next image
		BufferStrategy bs = this.getBufferStrategy();
		
		//Buffer strategy starts off as null so create a buffer strategy of 3 images
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		//Create graphics object for buffer strategy
		Graphics g = bs.getDrawGraphics();
						
		//Draw background scaled 125%
		g.drawImage(background, 0, 0, (int)(background.getWidth() * 1.25), (int)(background.getHeight() * 1.25), null);
		
		//After the intro but before the game starts
		if (!intro && !started) {
			//Draw the get ready image
			g.drawImage(subGetReady, 88, 200, null);
		}
		
		//Go to object handler class, call the render method, this renders the bird and the pipes
		handler.render(g);
		
		//Render the floor on top of the pipes
		floor.render(g);
		
		//After the intro and before the game starts draw the bird animation
		if (!intro && !started) {
			animation.render(g);
		}
		
		//If the game is over render the menu
		if (gameover) {
			flash.render(g);
			endMenu.render(g);
		}
		
		//Render the score while the game is not over and it has started
		if (!gameover && started) {
			score.render(g);
		}
		
		//If the game has just been opened render the start menu
		if (intro) {
			startMenu.render(g);
		}
		
		//Dispose the previous frames to free up system resources
		g.dispose();
				
		//Show the buffer strategy to the screen
		bs.show();
	}
	
	//Static main method, no instance required to call it
	public static void main(String[] args) {
		//Create game window of size 360 by 640, with the title FlappyBird
		//Game object as argument means that Window class can call the game class methods
		new Window(360, 640, "Flappybird", new Game());
	}
}
