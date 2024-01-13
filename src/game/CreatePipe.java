package game;

import java.util.Random;

public class CreatePipe {
	//Create new random object to generate random integers
	private Random rand = new Random();
			
	//Height of the pipe facing up and the pipe facing down
	private int pipe_height_up;
	private int pipe_height_down;
	
	//Constants used to calculate pipe height and width
	private final int floor_y = 556;
	private final int max_pipe_height = 320;
	public static final int pipeGapVertical = 150;
				
	//ObjectHandler object
	private ObjectHandler handler;
		
	//Timer variables for the pipe generation
	private long timer = 0;
	private long lastTime = System.currentTimeMillis();
		
	//CreatePipe constructor, takes the ObjectHandler object as the argument
	public CreatePipe(ObjectHandler handler) {
		this.handler = handler;
	}
		
	//Called in the tick method of the game class
	public void tick() {
		//Gets the time elapsed from the last time this method was called
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		//Create new pair of pipes every 1.5 seconds
		if (timer > 1500) {
			//Generate a random height for pipeUp using formula I created
			//minimum pipeUp height + random integer from (0, 320 - minimum pipeUp height) inclusive
			pipe_height_up = (floor_y - max_pipe_height - pipeGapVertical) + rand.nextInt(max_pipe_height - (floor_y - max_pipe_height - pipeGapVertical) + 1);
			
			//Height of pipeDown is dependent on the random height of pipeUp
			//Y value of floor minus the height of the pipe facing up, minus the vertical gap between the pipes
			pipe_height_down = floor_y - pipe_height_up - pipeGapVertical;
			
			//Add the pair of pipes to the LinkedList
			handler.addObject(new Pipe(400, floor_y - pipe_height_up, ObjectId.PipeUp, handler));
			handler.addObject(new Pipe(400, pipe_height_down - max_pipe_height, ObjectId.PipeDown, handler));
			timer = 0;
		}
	}
}
