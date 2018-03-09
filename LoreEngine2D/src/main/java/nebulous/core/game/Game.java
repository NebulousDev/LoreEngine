package nebulous.core.game;

import org.lwjgl.opengl.GL11;

import nebulous.core.graphics.Graphics;
import nebulous.core.utils.Input;
import nebulous.core.utils.Log;
import nebulous.core.utils.Log.LogLevel;

public abstract class Game {
	
	public enum State
	{
		INITIALIZING,
		STARTING,
		RUNNING,
		STOPPING,
		STOPPED
	}
	
	protected Window 		window;
	protected State 		state;
	protected Graphics		graphics;
	protected Input			input;
	
	protected Scene			activeScene;
	
	protected int			activeFPS;
	protected double		activeMS;
	protected int			maxTPS = 20;
	
	public Game(Window window)
	{
		this.state 		= State.STOPPED;
		this.graphics 	= new Graphics().initialize();
		this.window 	= window.create(this);
	}
	
	public void preInit()
	{
		System.out.println("-------------------------------------");
		System.out.println(graphics.getGLStats());
		System.out.println("-------------------------------------");
		onPreInit();
		graphics.initialize();
		Input.init(window);
	}
	
	public void start()
	{
		Log.println(LogLevel.GENERAL, "Preinitializing game...");
		preInit();

		Log.println(LogLevel.GENERAL, "Initializing game...");
		this.state = State.INITIALIZING;
		onInit();
		
		Log.println(LogLevel.GENERAL, "Starting game...");
		this.state = State.STARTING;
		onStart();
		
		Log.println(LogLevel.GENERAL, "Game is running...");
		this.state = State.RUNNING;
		loop();
	}
	
	public void stop()
	{
		Log.println(LogLevel.GENERAL, "Stopping game...");
		this.state = State.STOPPING;
		onStop();
		
		Log.println(LogLevel.GENERAL, "Game stopped...");
		this.state = State.STOPPED;
	}
	
	public void loop()
	{
		
		double oldTime = 0.0;
		double newTime = 0.0;
		double delta   = 0.0;
		
		double startTime = System.nanoTime();
		
		double frameTime  = 0.0;
		double tickTime = 0.0;
		
		double msStart = 0.0;
		double msEnd   = 0.0;
		
		int frames = 0;
		int tick = 0;
		
		double elapsedTick = 0.0;
		double elapsedFrame = 0.0;
		
		while(!window.shouldClose() && state == State.RUNNING)
		{
			graphics.clearBuffers();
			Input.update();
			graphics.pollEvents();
		
			oldTime = newTime;
			newTime = System.nanoTime();
			delta	= (newTime - oldTime) / 1000000000.0;
			
			elapsedFrame = ((newTime - startTime) / 1000000000.0) - frameTime;
			
			if(elapsedFrame > 1.0) {
				frameTime += 1.0;
				activeFPS = (int)frames;
				frames = 0;
			}
			
			elapsedTick = ((newTime - startTime) / 1000000000.0) - tickTime;
			
			if(elapsedTick > 1.0 / (double)maxTPS) {
				tickTime += 1.0 / (double)maxTPS;
				tick++;
				activeScene.tick(this, tick, maxTPS);
				if(tick >= maxTPS) tick = 0;
			}
			
			activeScene.update(this, delta);

			msStart = System.nanoTime();
			
			activeScene.render(this, graphics);
			frames++;
			
			int err = GL11.glGetError();
			if(err != 0) System.out.println("GL Error : " + err);
			
			graphics.swapBuffers(window);
			
			msEnd = System.nanoTime();
			
			activeMS = msEnd - msStart;
		}
		
		graphics.terminate();
		stop();
		
	}
	
	public void loadScene(Scene scene)
	{
		unloadScene();
		activeScene = scene;
		activeScene.load(this);
	}
	
	public void unloadScene()
	{
		if(activeScene != null)
		{
			activeScene.unload(this);
		}
	}
	
	public abstract void onPreInit();
	public abstract void onInit();
	public abstract void onStart();
	public abstract void onStop();

	public Window getWindow() {
		return window;
	}

	public State getState() {
		return state;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public Scene getActiveScene() {
		return activeScene;
	}
	
	public int getActiveFPS()
	{
		return activeFPS;
	}

}