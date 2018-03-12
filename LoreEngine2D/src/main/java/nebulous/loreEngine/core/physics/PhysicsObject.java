package nebulous.loreEngine.core.physics;

import lore.math.Vector2f;

public class PhysicsObject {
	
	public static final float GRAVITY_ACCELERATION 	= 0;//-9.81f;
	public static final float MAX_VELOCITY			= 1.0f;
	public static final float FRICTION				= 0.999999f;

	protected Vector2f 	pos;
	protected Vector2f 	vel;
	protected Vector2f 	acc;
	
	protected boolean	gravityEnabled;
	
	public PhysicsObject() {
		this.pos = new Vector2f(0);
		this.vel = new Vector2f(0);
		this.acc = new Vector2f(0);
		this.gravityEnabled = false;
	}

	public void update(float delta)
	{
		/*
		pos.x += vel.x * delta;
		pos.y += vel.y * delta;
		vel.x += acc.x;
		vel.y += acc.y;

		vel.x *= FRICTION;
		vel.y *= FRICTION;
		
		if(gravityEnabled)
			vel.y += GRAVITY_ACCELERATION;
		
		if(vel.x >= MAX_VELOCITY)
			vel.x = MAX_VELOCITY;
		
		if(vel.x <= -MAX_VELOCITY)
			vel.x = -MAX_VELOCITY;
		
		if(vel.y >= MAX_VELOCITY)
			vel.y = MAX_VELOCITY;
		
		if(vel.y <= -MAX_VELOCITY)
			vel.y = -MAX_VELOCITY;
		
		acc.x = 0;
		acc.y = 0;
		*/
	}
	
	public void move(Vector2f dir, float speed)
	{
		pos.x = dir.x;
		pos.y = dir.y;
	}
	
	public static float getGravityAcceleration()
	{
		return GRAVITY_ACCELERATION;
	}

	public static float getMaxVelocity()
	{
		return MAX_VELOCITY;
	}

	public void setPos(float x, float y)
	{
		pos.x = x;
		pos.y = y;
	}
	
	public void setPos(Vector2f pos)
	{
		pos.x = pos.x;
		pos.y = pos.y;
	}
	
	public Vector2f getPos()
	{
		return pos;
	}
	
	public float getPosX()
	{
		return pos.x;
	}
	
	public float getPosY()
	{
		return pos.y;
	}

	public Vector2f getVel()
	{
		return vel;
	}
	
	public void setVel(Vector2f vel)
	{
		this.vel = vel;
	}
	
	public void setVel(float x, float y)
	{
		this.vel.x = x;
		this.vel.y = y;
	}

	public Vector2f getAcc()
	{
		return acc;
	}
	
	public void setAcc(Vector2f vel)
	{
		this.acc = vel;
	}
	
	public void setAcc(float x, float y)
	{
		this.acc.x = x;
		this.acc.y = y;
	}

	public boolean isGravityEnabled()
	{
		return gravityEnabled;
	}
	
	public void enableGravity()
	{
		gravityEnabled = true;
	}
	
	public void disableGravity()
	{
		gravityEnabled = false;
	}
	
}
