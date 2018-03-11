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
	
	private static final Vector4f LINE_COLOR = new Vector4f(0, 0, 1, 1);
	
	private Vector2f pos;
	private Vector2f size;
	
	public BoxCollider2D(Vector2f pos, Vector2f size)
	{
		this.pos = pos;
		this.size = size;
	}
	
	public void scale(Vector2f deltaScale)
	{
		this.size.add(deltaScale);
	}
	
	public void move(Vector2f deltaPos)
	{
		this.pos.add(deltaPos);
	}
	
	public Vector2f checkCollision(BoxCollider2D other)
	{
		float xDist = pos.x - other.getX();
		float yDist = pos.y - other.getY();
		
		if(xDist < size.x || xDist < other.getWidth())
		{
			System.out.println("test");
		}
		
		return null;
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
				tansform, pos.x, pos.y, pos.x, pos.y + size.y, DEFAULT_LINE_SHADER, LINE_COLOR);
		gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
				tansform, pos.x, pos.y + size.y, pos.x + size.x, pos.y + size.y, DEFAULT_LINE_SHADER, LINE_COLOR);
		gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
				tansform, pos.x + size.x, pos.y + size.y, pos.x + size.x, pos.y, DEFAULT_LINE_SHADER, LINE_COLOR);
		gfx.drawLinePerspective(game.getWindow(), game.getActiveScene().getCamera(), 
				tansform, pos.x + size.x, pos.y, pos.x, pos.y, DEFAULT_LINE_SHADER, LINE_COLOR);
	}
	
}
