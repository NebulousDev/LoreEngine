package nebulous.loreEngine.core.game;

import java.util.HashMap;

import org.lwjgl.opengl.GL11;

import nebulous.loreEngine.core.LoreEngineInfo;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Texture;
import nebulous.loreEngine.core.utils.Input;
import nebulous.loreEngine.core.utils.Log;
import nebulous.loreEngine.core.utils.Log.LogLevel;

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
	
	protected HashMap<String, Scene> 	sceneMap;
	protected Scene						activeScene;
	
	protected HashMap<String, Texture> 	textureMap;
	
	protected int			activeFPS;
	protected double		activeMS;
	protected int			maxTPS = 20;
	
	private boolean			drawBoundingBox;
	
	public Game(Window window)
	{
		this.state 		= State.STOPPED;
		this.graphics 	= new Graphics().initialize();
		this.window 	= window.create(this);
		this.sceneMap	= new HashMap<String, Scene>();
		this.textureMap = new HashMap<String, Texture>();
	}
	
	public void preInit()
	{
		onPreInit();
		graphics.initialize();
		Input.init(window);
		window.show();
		window.center();
		
		registerTexture("default", Texture.DEFAULT1);
		registerTexture("default2", Texture.DEFAULT2);
	}
	
	public void start()
	{
		System.out.println("-------------------------------------");
		System.out.println("  LoreEngine2D (V9) - " + LoreEngineInfo.VERSION);
		System.out.println("-------------------------------------");
		System.out.println(graphics.getGLStats());
		System.out.println("-------------------------------------");
		
		Log.println(LogLevel.GENERAL, "Preinitializing game...");
		preInit();

		Log.println(LogLevel.GENERAL, "Initializing game...");
		this.state = State.INITIALIZING;
		onInit();
		
		Log.println(LogLevel.GENERAL, "Starting game...");
		this.state = State.STARTING;
		onStart();
		
		if(activeScene == null)
		{
			Log.println(LogLevel.WARNING, "No scene has been attached! Game will likely crash!");
		}
		
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
			window.update(this);
		
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
			
			window.render();
			
			msEnd = System.nanoTime();
			
			activeMS = msEnd - msStart;
		}
		
		graphics.terminate();
		stop();
		
	}
	
	public void registerScene(String tag, Scene scene)
	{
		sceneMap.put(tag, scene);
	}
	
	public void registerTexture(String tag, Texture texture)
	{
		textureMap.put(tag, texture);
	}
	
	public Texture getTexture(String tag)
	{
		if(textureMap.containsKey(tag))
		{
			return textureMap.get(tag);
		}
		else
		{
			Log.println(LogLevel.ERROR, "Unable to find texture '" + tag + "'. Check spelling " + 
					"or make sure you the texture has been properly registered.");
			return Texture.DEFAULT1;
		}
	}
	
	public void loadScene(String tag)
	{
		unloadScene();
		activeScene = sceneMap.get(tag);
		activeScene.load(this);
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
	
	public int getFramerate()
	{
		return activeFPS;
	}
	
	public void setTickrate(int rate)
	{
		maxTPS = rate;
	}
	
	public int getTickrate()
	{
		return maxTPS;
	}
	
	public void enableDrawBoundingBoxes(boolean draw)
	{
		this.drawBoundingBox = draw;
	}
	
	public boolean drawBoundingBoxesEnabled()
	{
		return drawBoundingBox;
	}

}
