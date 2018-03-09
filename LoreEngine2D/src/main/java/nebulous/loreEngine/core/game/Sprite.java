package nebulous.loreEngine.core.game;

import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Shader;
import nebulous.loreEngine.core.graphics.Texture;

public abstract class Sprite extends Entity2D {

	public static final Shader DEFAULT_SPRITE_SHADER 
		= Shader.create("default_sprite_shader", "shaders/vs_default.glsl", "shaders/fs_default.glsl");
	
	private Texture texture;
	private Shader	shader;
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);
	
	public Sprite(Texture texture, Shader shader)
	{
		this.texture = texture;
		this.shader = shader;
	}
	
	public Sprite(Texture texture)
	{
		this(texture, DEFAULT_SPRITE_SHADER);
	}

	public Sprite()
	{
		this(Texture.DEFAULT);
	}
	
	public Texture getTexture() 
	{
		return texture;
	}
	
	public void setTexture(Texture texture)
	{
		this.texture = texture;
	}
	
	@Override
	public void draw(Game game, Graphics gfx) 
	{
		gfx.drawTexturedQuadPerspective(game.getWindow(), game.getActiveScene().getCamera(), getTransform(), shader, texture);
	}

}
