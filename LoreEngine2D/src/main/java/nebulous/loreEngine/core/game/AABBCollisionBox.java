package nebulous.loreEngine.core.game;

import lore.math.Matrix4f;
import lore.math.Vector2f;
import lore.math.Vector3f;
import lore.math.Vector4f;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.IRenderable;
import nebulous.loreEngine.core.graphics.Shader;

public class AABBCollisionBox implements IRenderable {

	public static final Shader DEFAULT_LINE_SHADER 
	= Shader.create("default_line_shader", 
		"shaders/vs_default_line.glsl", 
		"shaders/fs_default_line.glsl",
		"shaders/gs_default_line.glsl");
	
	protected Vector2f pos;
	protected Vector2f size;
	
	protected Vector4f color;
	
	public AABBCollisionBox(Vector2f pos, Vector2f size)
	{
		this.pos = pos;
		this.size = size;
		this.color = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	public boolean checkCollision(AABBCollisionBox other)
	{
		Vector2f center = new Vector2f(0);
		pos.add(new Vector2f(size.x / 2.0f, size.y / 2.0f), center);
		
		Vector2f otherCenter = new Vector2f(0);
		other.pos.add(new Vector2f(other.size.x / 2.0f, other.size.y / 2.0f), otherCenter);
		
		boolean collide = 
				Math.abs(center.x - otherCenter.x) > (size.x / 2.0f + other.size.x / 2.0) ||
				Math.abs(center.y - otherCenter.y) > (size.y / 2.0f + other.size.y / 2.0);
		
		if(!collide)
		{
			this.color = new Vector4f(1.0f, 0.5f, 0.0f, 1.0f);
			return true;
		}
		else
		{
			this.color = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);
			return false;
		}
		
	}
	
	public void resolve(AABBCollisionBox other)
	{
		Vector2f center = new Vector2f(0);
		pos.add(new Vector2f(size.x / 2.0f, size.y / 2.0f), center);
		
		Vector2f otherCenter = new Vector2f(0);
		other.pos.add(new Vector2f(other.size.x / 2.0f, other.size.y / 2.0f), otherCenter);
		
		float xdif = Math.abs((center.x - size.x / 2.0f) - (otherCenter.x - size.x / 2.0f));
		float ydif = Math.abs((center.y - size.y / 2.0f) - (otherCenter.y - size.y / 2.0f));
		
		if(center.x > otherCenter.x)
		{
			pos.x += xdif;
		}
		else
		{
			pos.x -= xdif;
		}
		
		if(center.y > otherCenter.y)
		{
			pos.y += xdif;
		}
		else
		{
			pos.y -= xdif;
		}
	}
	
	public void setPos(Vector2f pos)
	{
		this.pos = pos;
	}
	
	public void setSize(Vector2f size)
	{
		this.size = size;
	}
	
	public void move(Vector2f delta)
	{
		this.pos.add(delta);
	}
	
	public void move(float deltaX, float deltaY)
	{
		this.pos.x += deltaX;
		this.pos.y += deltaY;
	}
	
	public void scale(Vector2f delta)
	{
		size.x += delta.x;
		size.y += delta.y;
	}
	
	public void scale(float deltaX, float deltaY)
	{
		size.x += deltaX;
		size.y += deltaY;
	}
	
	@Override
	public void draw(Game game, Graphics gfx) {
		Matrix4f tansform = Matrix4f.Translation(new Vector3f(pos, 0));
		gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
				tansform, 0, 0, 0, 0 + size.y, DEFAULT_LINE_SHADER, color);
		gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
				tansform, 0, 0 + size.y, 0 + size.x, 0 + size.y, DEFAULT_LINE_SHADER, color);
		gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
				tansform, 0 + size.x, 0 + size.y, 0 + size.x, 0, DEFAULT_LINE_SHADER, color);
		gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
				tansform, 0 + size.x, 0, 0, 0, DEFAULT_LINE_SHADER, color);
	}
	
	

}
