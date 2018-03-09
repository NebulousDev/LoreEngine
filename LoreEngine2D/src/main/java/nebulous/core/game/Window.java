package nebulous.core.game;

import nebulous.core.graphics.Graphics;
import nebulous.core.utils.Log;
import nebulous.core.utils.Log.LogLevel;

public class Window {
	
	enum DisplayMode
	{
		WINDOWED,
		WINDOWED_FULLSCREEN,
		FULLSCREEN
	}
	
	private String 		title;
	private int 		width;
	private int 		height;
	private int			posX;
	private int 		posY;
	private boolean		vSync;
	private DisplayMode displayMode;
	
	private long 		windowID;
	
	private boolean 	hidden;
	private boolean		created;
	
	private Graphics	graphics;
	
	public Window(String title, int width, int height, int posX, int posY, boolean vSync, DisplayMode mode)
	{
		this.title = title;
		this.width = width;
		this.height = height;
		this.posX = posX;
		this.posY = posY;
		this.vSync = vSync;
		this.displayMode = mode;
	}
	
	public Window(String title, int width, int height)
	{
		this(title, width, height, 0, 0, false, DisplayMode.WINDOWED);	
	}
	
	public Window create(Game game)
	{
		this.graphics = game.getGraphics();
		this.windowID = game.getGraphics().createWindow(this);
		this.created = true;
		return this;
	}
	
	public void update(Game game)
	{
		if(created)
		{
			graphics.pollEvents();			
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to update window, but window was never created!");
		}
	}
	
	public Window resize(int width, int height)
	{
		if(created)
		{
			if(graphics.resizeWindow(this, width, height))
			{
				this.width = width;
				this.height = height;
			}
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to resize window, but window was never created!");
		}
		
		return this;
	}
	
	public Window reposition(int x, int y)
	{
		if(created)
		{
			if(graphics.repositionWindow(this, x, y))
			{
				this.posX = x;
				this.posY = y;
			}
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to reposition window, but window was never created!");
		}
		
		return this;
	}
	
	public Window center()
	{
		if(created)
		{
			int sWidth = graphics.getScreenWidth(this);
			int sHeight = graphics.getScreenHeight(this);
			reposition((sWidth / 2) - (width / 2), (sHeight / 2) - (height / 2));
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to center window, but window was never created!");
		}
		
		return this;			
	}
	
	public Window show()
	{
		if(created)
		{			
			if(graphics.showWindow(this))
			{
				this.hidden = false;
			}
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to show window, but window was never created!");
		}
		
		return this;
	}
	
	public Window hide()
	{
		if(created)
		{			
			if(graphics.hideWindow(this))
			{
				this.hidden = true;
			}
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to show window, but window was never created!");
		}
		
		return this;
	}
	
	public boolean shouldClose()
	{
		if(created)
		{
			return graphics.shouldWindowClose(this);
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to show window, but window was never created!");
			return true;
		}
	}
	
	public void destroy()
	{
		if(created)
		{
			graphics.destroyWindow(this);			
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to destroy window, but window was never created!");
		}
	}

	public String getTitle() {
		return title;
	}

	public Window setTitle(String title) {
		
		if(created)
		{			
			if(graphics.setWindowTitle(this, title))
			{
				this.title = title;			
			}
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to set window title, but window was never created!");
		}
		
		return this;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public boolean isVSync() {
		return vSync;
	}

	public Window setVSync(boolean vSync) {
		
		if(graphics.setVSync(vSync))
		{
			this.vSync = vSync;
		}
		
		return this;
	}
	
	public long getWindowID() {
		return windowID;
	}

	public boolean isCreated() {
		return created;
	}

	public boolean isHidden() {
		return hidden;
	}

	public DisplayMode getDisplayMode() {
		return displayMode;
	}
	
}
