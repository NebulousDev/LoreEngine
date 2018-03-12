package nebulous.loreEngine.core.game;

import lore.math.Matrix4f;
import lore.math.Vector2f;
import lore.math.Vector3f;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.IRenderable;
import nebulous.loreEngine.core.physics.PhysicsObject;

public abstract class Entity extends PhysicsObject implements IRenderable, IUpdatable {
	
	protected Game 		game;
	protected Vector2f	scale;
	protected float		rotation;
	
	protected AABBCollisionBox box;
	
	protected Entity()
	{
		this.scale 		= new Vector2f(1);
		this.rotation 	= 0.0f;
		this.box 		= new AABBCollisionBox(pos, scale);
	}
	
	public void create(Game game, Scene scene)
	{
		onCreate(game, scene);
		scene.getEntityRenderList().add(this);
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
		for(Entity e : scene.getEntityList())
		{
			if(e != this)
			{
				if(box.checkCollision(e.box))
				{
					System.out.println("COLLIDE");
				}
			}
		}
		
		onUpdate(game, scene, delta);
	}
	
	public void move(Vector2f delta) {
		this.pos.add(delta);
		this.box.move(delta);
	}
	
	public void move(float deltaX, float deltaY) {
		this.pos.x += deltaX;
		this.pos.y += deltaY;
		this.box.move(deltaX, deltaY);
	}
	
	public void rotate(float deltaDeg)
	{
		rotation += deltaDeg;
	}
	
	public void scale(Vector2f delta) {
		scale.x += delta.x;
		scale.y += delta.y;
		this.box.scale(delta);
	}
	
	public void scale(float deltaX, float deltaY) {
		scale.x += deltaX;
		scale.y += deltaY;
		this.box.scale(deltaX, deltaY);
	}
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);
	public abstract void onCollide(Game game, Scene scene, Entity entity);
	
	@Override
	public void draw(Game game, Graphics gfx) {
		if(game.drawBoundingBoxesEnabled())
		{
			box.draw(game, gfx);
		}
	}
	
	public void setRot(float degree)
	{
		rotation = degree;
	}
	
	public float getRot()
	{
		return rotation;
	}
	
	public void setScale(float x, float y)
	{
		scale.x = x;
		scale.y = y;
	}
	
	public void setScale(Vector2f scale)
	{
		scale.x = scale.x;
		scale.y = scale.y;
	}
	
	public Vector2f getScale()
	{
		return scale;
	}
	
	public float getScaleX()
	{
		return scale.x;
	}
	
	public float getScaleY()
	{
		return scale.y;
	}
	
	public Matrix4f getTransform() {
		return  Matrix4f.Identity().mul(
				Matrix4f.Rotation(new Vector3f(0.0f, rotation, 0.0f)).mul(
				Matrix4f.Translation(new Vector3f(pos.x, pos.y, 0.0f)).mul(
				Matrix4f.Scale(new Vector3f(scale.x, scale.y, 0.0f)))));
	}
	
}
