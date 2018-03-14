package nebulous.loreEngine.core.physics;

import lore.math.Matrix4f;
import lore.math.Vector2f;
import lore.math.Vector3f;
import lore.math.Vector4f;
import nebulous.loreEngine.core.game.Game;
import nebulous.loreEngine.core.game.Scene;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Shader;

public class PhysicsObject {

	public static final Shader DEFAULT_LINE_SHADER 
		= Shader.create("default_line_shader", 
			"shaders/vs_default_line.glsl", 
			"shaders/fs_default_line.glsl",
			"shaders/gs_default_line.glsl");
	
	public static final Vector4f COLOR_NO_COLLISION 	= new Vector4f(0, 0, 	1, 1);
	public static final Vector4f COLOR_COLLISION 		= new Vector4f(1, 0.5f, 0, 1);
	
	public static final float 	GRAVITY_ACCELERATION 	= -9.81f;
	public static final float 	MAX_VELOCITY			= 1.0f;
	public static final float 	FRICTION				= 0.999999f;

	protected Vector2f 			vel;
	protected Vector2f 			pos;
	protected Vector2f			size;
	protected float				rot;
	
	protected boolean			gravityEnabled;
	
	protected boolean			isColliding;
	protected boolean			drawBounds;
	
	public PhysicsObject(Vector2f pos, float rot, Vector2f size, boolean gravity) {
		this.vel 				= new Vector2f(0);
		this.pos				= new Vector2f(0);
		this.size				= new Vector2f(1);
		this.pos.x 				= pos.x;
		this.pos.y				= pos.y;
		this.size.x				= size.x;
		this.size.y				= size.y;
		this.rot				= rot;
		this.gravityEnabled 	= gravity;
		this.isColliding		= false;
		this.drawBounds			= false;
	}
	
	public PhysicsObject() {
		this(new Vector2f(0), 0, new Vector2f(1,1), false);
	}

	public void update(Game game, Scene scene, double delta)
	{
		if(gravityEnabled) 
			vel.y += GRAVITY_ACCELERATION;
		
		pos.x += vel.x;
		pos.y += vel.y;
		
		vel.x = 0;
		vel.y = 0;
	}

	public static Vector2f checkCollisionAABB(PhysicsObject a, PhysicsObject b)
	{
		float posX 		= a.pos.x - (b.pos.x + b.size.x);
		float posY 		= a.pos.y - (b.pos.y + b.size.y);
		
		float sizeX		= a.size.x + b.size.x;
		float sizeY		= a.size.y + b.size.y;
		
		if(posX <= 0 && posX + sizeX >= 0 && posY <= 0 && posY + sizeY >= 0)
		{
			float minDist 	= Math.abs(-posX);
			float boundX 	= posX;
			float boundY	= 0;
			
			if (Math.abs(posX + sizeX) < minDist)
			{
				minDist = Math.abs(posX + sizeX);
				boundX	= posX + sizeX;
				boundY	= 0;
			}
			
			if (Math.abs(posY + sizeY) < minDist)
			{
				minDist = Math.abs(posY + sizeY);
				boundX	= 0;
				boundY	= posY + sizeY;
			}
			
			if (Math.abs(posY) < minDist)
			{
				minDist = Math.abs(posY + sizeY);
				boundX	= 0;
				boundY	= posY;
			}
			
			a.isColliding = true;
		    return new Vector2f(-boundX, -boundY);
		}
		
		a.isColliding = false;
		return null;
	}
	
	public void move(Vector2f dir, float speed)
	{
		vel.x += dir.x * speed;
		vel.y += dir.y * speed;
	}
	
	public void move(float deltaX, float deltaY)
	{
		vel.x += deltaX;
		vel.y += deltaY;
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

	public Vector2f getVelocity()
	{
		return vel;
	}
	
	public void setVelocity(Vector2f vel)
	{
		this.vel = vel;
	}
	
	public void setVelocity(float x, float y)
	{
		this.vel.x = x;
		this.vel.y = y;
	}
	
	public void setSize(Vector2f size)
	{
		this.size.x = size.x;
	}
	
	public void setSize(float width, float height)
	{
		this.size.x = width;
		this.size.y = height;
	}
	
	public Vector2f getSize()
	{
		return size;
	}

	public float getWidth()
	{
		return size.x;
	}
	
	public float getHeight()
	{
		return size.y;
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
	
	public void enableDrawBounds()
	{
		drawBounds = true;
	}
	
	public void disableDrawBounds()
	{
		drawBounds = false;
	}
	
	public void drawBoundingBox(Game game, Graphics gfx) {
		
		if(drawBounds)
		{
			Matrix4f tansform = Matrix4f.Translation(new Vector3f(pos, 0));
			gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
					tansform, 0, 0, 0, 0 + size.y, DEFAULT_LINE_SHADER, isColliding ? COLOR_COLLISION : COLOR_NO_COLLISION);
			gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
					tansform, 0, 0 + size.y, 0 + size.x, 0 + size.y, DEFAULT_LINE_SHADER, isColliding ? COLOR_COLLISION : COLOR_NO_COLLISION);
			gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
					tansform, 0 + size.x, 0 + size.y, 0 + size.x, 0, DEFAULT_LINE_SHADER, isColliding ? COLOR_COLLISION : COLOR_NO_COLLISION);
			gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
					tansform, 0 + size.x, 0, 0, 0, DEFAULT_LINE_SHADER, isColliding ? COLOR_COLLISION : COLOR_NO_COLLISION);
		}
		
	}
	
}
