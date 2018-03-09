package nebulous.loreEngine.core.game;

import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.IRenderable;

public abstract class Entity2D extends Transform2D implements IRenderable, IUpdatable {
	
	protected Entity2D() { }
	
	public void create(Game game, Scene scene)
	{
		onCreate(game, scene);
		scene.getEntityRenderList().add(this);
	}
	
	public void destroy(Game game, Scene scene)
	{
		onDestroy(game, scene);
		scene.getEntityRenderList().remove(this);
	}
	
	public void tick(Game game, Scene scene, int tick, int tock)
	{
		onTick(game, scene, tick, tock);
	}
	
	public void update(Game game, Scene scene, double delta)
	{
		onUpdate(game, scene, delta);
	}
	
	@Override
	public void draw(Game game, Graphics gfx) {
		
	}
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);
	
}
