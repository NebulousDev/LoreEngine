package nebulous.loreEngine.core.game;

import lore.math.Matrix4f;
import lore.math.Vector2f;
import lore.math.Vector3f;
import lore.math.Vector4f;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.IRenderable;
import nebulous.loreEngine.core.graphics.Shader;

public class BoxCollider2D implements IRenderable {

	public static final Shader DEFAULT_LINE_SHADER 
		= Shader.create("default_line_shader", 
			"shaders/vs_default_line.glsl", 
			"shaders/fs_default_line.glsl",
			"shaders/gs_default_line.glsl");
	
	private Vector4f color; 
	
	private Vector2f pos;
	private Vector2f center;
	private Vector2f size;
	
	public BoxCollider2D(Vector2f pos, Vector2f size)
	{
		this.pos = pos;
		this.center = calcCenter(pos, size);
		this.size = size;
		this.color = new Vector4f(0.0f, 0.0f, 1.0f, 1.0f);
	}
	
	private Vector2f calcCenter(Vector2f pos, Vector2f size)
	{
		return new Vector2f(pos.x + (size.x / 2.0f), pos.y + (size.y / 2.0f));
	}
	
	public void scale(Vector2f deltaScale)
	{
		this.size.add(deltaScale);
		this.center = calcCenter(pos, size);
	}
	
	public void move(Vector2f deltaPos)
	{
		this.pos.add(deltaPos);
		this.center = calcCenter(pos, size);
	}
	
	public boolean checkCollision(BoxCollider2D other)
	{
		boolean collide = 
				Math.abs(center.x - other.center.x) > (size.x / 2.0f + other.size.x / 2.0) ||
				Math.abs(center.y - other.center.y) > (size.y / 2.0f + other.size.y / 2.0);
		
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
	
	public void resolve(BoxCollider2D other)
	{
		float xdif = Math.abs((center.x - size.x / 2.0f) - (other.center.x - size.x / 2.0f));
		float ydif = Math.abs((center.y - size.y / 2.0f) - (other.center.y - size.y / 2.0f));
		
		if(center.x > other.center.x)
		{
			pos.x += xdif;
		}
		else
		{
			pos.x -= xdif;
		}
		
		if(center.y > other.center.y)
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
		this.center = calcCenter(pos, size);
	}
	
	public void setScale(Vector2f scale)
	{
		this.size = scale;
		this.center = calcCenter(pos, size);
	}
	
	public Vector2f getPos()
	{
		return pos;
	}
	
	public Vector2f getSize()
	{
		return size;
	}
	
	public float getX()
	{
		return pos.x;
	}
	
	public float getY()
	{
		return pos.y;
	}
	
	public float getWidth()
	{
		return size.x;
	}
	
	public float getHeight()
	{
		return size.y;
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
