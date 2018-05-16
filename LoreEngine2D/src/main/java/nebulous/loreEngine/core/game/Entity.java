package nebulous.loreEngine.core.game;

import lore.math.Matrix4f;
import lore.math.Vector2f;
import lore.math.Vector3f;
import nebulous.loreEngine.core.graphics.IRenderable;
import nebulous.loreEngine.core.physics.PhysicsObject;

public abstract class Entity extends PhysicsObject implements IRenderable {
	
	protected Entity() { } 
	
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
		for(Entity e : scene.getEntityList())
		{
			if(e != this)
			{
				Vector2f collision = checkCollisionAABB(this, e);
				if(collision != null)
				{
					onCollide(game, scene, e, collision);
				}
			}
		}

	}
	
	@Override
	public void update(Game game, Scene scene, double delta)
	{
		super.update(game, scene, delta);
		onUpdate(game, scene, delta);
	}
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);
	public abstract void onCollide(Game game, Scene scene, Entity entity, Vector2f distance);

	public Matrix4f getTransform() {
		return  Matrix4f.Identity().mul(
				Matrix4f.Translation(new Vector3f(pos.x - 0.5f, pos.y - 0.5f, 0.0f)).mul(
				Matrix4f.Rotation(new Vector3f(0.0f, 0.0f, rot)).mul(
				Matrix4f.Scale(new Vector3f(size.x, size.y, 0.0f)))));
	}
	
}
