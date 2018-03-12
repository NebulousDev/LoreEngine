package nebulous.loreEngine.core.game;

import java.util.ArrayList;

import lore.math.Vector2f;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.IRenderable;

public abstract class Entity2D extends Transform2D implements IRenderable, IUpdatable {
	
	protected BoxCollider2D collider;
	protected boolean		canCollide;
	protected Game			game;
	protected boolean		colliding;
	
	protected Entity2D() { }
	
	public void create(Game game, Scene scene)
	{
		onCreate(game, scene);
		scene.getEntityRenderList().add(this);
		this.collider = new BoxCollider2D(position, scale);
		this.canCollide = true;
		this.game = game;
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
		this.colliding = false;
		onUpdate(game, scene, delta);
	}
	
	@Override
	public void move(Vector2f delta) {
		move(delta.x, delta.y);
	}
	
	@Override
	public void move(float deltaX, float deltaY) {
		collider.move(new Vector2f(deltaX, deltaY));
		if(canCollide)
		{
			ArrayList<Entity2D> entities = game.getActiveScene().getEntityList();
			for(Entity2D e : entities)
			{
				if(e == this) continue;
				if(collider.checkCollision(e.collider))
				{
					collide(e);
					e.collide(this);
					collider.resolve(e.collider);
					setPos(collider.getPos());
				}
			}
		}
		else
		{
			super.move(deltaX, deltaY);
		}
	}
	
	private void collide(Entity2D other)
	{
		this.colliding = true;
		//other.collide(this);
		onCollide(game, game.getActiveScene(), other);
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
	
	public void enableCollision()
	{
		this.canCollide = true;
	}
	
	public void disableCollision()
	{
		this.canCollide = false;
	}
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);
	public abstract void onCollide(Game game, Scene scene, Entity2D entity);
	
	public boolean isColliding()
	{
		return colliding;
	}
	
	@Override
	public void draw(Game game, Graphics gfx) {
		if(game.drawBoundingBoxesEnabled())
		{
			collider.draw(game, gfx);
		}
	}
	
}
