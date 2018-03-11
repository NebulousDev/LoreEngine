package nebulous.loreEngine.core.game;

import lore.math.Vector2f;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.IRenderable;

public abstract class Entity2D extends Transform2D implements IRenderable, IUpdatable {
	
	protected BoxCollider2D collider;
	
	protected Entity2D() { }
	
	public void create(Game game, Scene scene)
	{
		onCreate(game, scene);
		scene.getEntityRenderList().add(this);
		this.collider = new BoxCollider2D(position, scale);
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
	public void move(Vector2f delta) {
		super.move(delta);
		collider.move(delta);
	}
	
	@Override
	public void move(float deltaX, float deltaY) {
		super.move(deltaX, deltaY);
		collider.move(new Vector2f(deltaX, deltaY));
	}
	
	@Override
	public void scale(Vector2f delta) {
		super.scale(delta);
		collider.scale(delta);
	}
	
	@Override
	public void scale(float deltaX, float deltaY) {
		super.scale(deltaX, deltaY);
		collider.scale(new Vector2f(deltaX, deltaY));
	}
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);
	
	@Override
	public void draw(Game game, Graphics gfx) {
		if(game.drawBoundingBoxesEnabled())
		{
			collider.draw(game, gfx);
		}
	}
	
}
