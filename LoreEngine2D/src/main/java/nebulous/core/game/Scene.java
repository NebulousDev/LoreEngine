package nebulous.core.game;

import java.util.ArrayList;

import nebulous.core.graphics.Camera;
import nebulous.core.graphics.Graphics;

public abstract class Scene {

	private ArrayList<Entity2D> entityList;
	private ArrayList<UIElement> uiList;
	
	private UILayer background;
	private UILayer foreground;
	
	private RenderList entityRenderList;
	private RenderList uiRenderList;
	
	private Camera camera;
	
	private boolean loaded;
	
	public Scene()
	{
		entityList 			= new ArrayList<Entity2D>();	
		uiList				= new ArrayList<UIElement>();
		entityRenderList 	= new RenderList();
		uiRenderList 		= new RenderList();
		camera				= new Camera();
		background			= null;
		foreground			= null;
	}
	
	void load(Game game)
	{
		onLoad(game);
		
		for(Entity2D e : entityList)
		{
			e.create(game, this);
		}
		
		for(UIElement u : uiList)
		{
			u.create(game, this);
		}
		
		loaded = true;
	}
	
	void unload(Game game)
	{
		onUnload(game);
		
		for(Entity2D e : entityList)
		{
			e.destroy(game, this);
		}
		
		for(UIElement u : uiList)
		{
			u.destroy(game, this);
		}
		
		entityList.clear();
		uiList.clear();
		
		entityRenderList.clear();
		uiRenderList.clear();
		loaded = false;
	}
	
	void tick(Game game, int tick, int tock)
	{
		onTick(game, tick, tock);
		for(Entity2D e : entityList)
		{
			e.tick(game, this, tick, tock);
		}
	}
	
	void update(Game game, double delta)
	{
		onUpdate(game, delta);
		
		for(Entity2D e : entityList)
		{
			e.update(game, this, delta);
		}
		
		for(UIElement u : uiList)
		{
			u.update(game, this, delta);
		}
	}
	
	public void setBackground(UILayer background)
	{
		this.background = background;
	}
	
	public void setForeground(UILayer foreground)
	{
		this.foreground = foreground;
	}
	
	public void add(Entity2D entity)
	{
		if(loaded)
		{
			entityList.add(entity);
			entityRenderList.add(entity);
		}
		else
		{			
			entityList.add(entity);
		}
		
	}
	
	public void remove(Entity2D entity)
	{
		if(loaded)
		{
			entityList.remove(entity);
			entityRenderList.remove(entity);
		}
		else
		{			
			entityList.remove(entity);
		}
	}
	
	public void add(UIElement element)
	{
		if(loaded)
		{
			uiList.add(element);
			uiRenderList.add(element);
		}
		else
		{			
			uiList.add(element);
		}
	}
	
	public void remove(UIElement element)
	{
		if(loaded)
		{
			uiList.remove(element);
			uiRenderList.remove(element);
		}
		else
		{			
			uiList.remove(element);
		}
	}
	
	public void render(Game game, Graphics gfx)
	{
		if(background != null) background.draw(game, gfx);
		entityRenderList.render(game, gfx);
		if(foreground != null) foreground.draw(game, gfx);
		uiRenderList.render(game, gfx);
	}
	
	public abstract void onLoad(Game game);
	public abstract void onUnload(Game game);
	public abstract void onTick(Game game, int tick, int tock);
	public abstract void onUpdate(Game game, double delta);

	public ArrayList<Entity2D> getEntityList() {
		return entityList;
	}

	public ArrayList<UIElement> getUIList() {
		return uiList;
	}
	
	public boolean isLoaded() {
		return loaded;
	}

	public RenderList getEntityRenderList() {
		return entityRenderList;
	}
	
	public RenderList getUIRenderList() {
		return entityRenderList;
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
}
