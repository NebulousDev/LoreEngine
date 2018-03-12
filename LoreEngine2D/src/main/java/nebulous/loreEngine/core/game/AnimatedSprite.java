package nebulous.loreEngine.core.game;

import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Shader;
import nebulous.loreEngine.core.graphics.Texture;

public abstract class AnimatedSprite extends Sprite {

	public static final Shader DEFAULT_ANIMATED_SPRITE_SHADER 
		= Shader.create("default_anim_sprite_shader", 
				"shaders/vs_default_anim_sprite.glsl",
				"shaders/fs_default_anim_sprite.glsl",
				"shaders/gs_default_anim_sprite.glsl");
	
	protected int offset;
	protected int frame;
	protected int maxFrames;

	public AnimatedSprite(Texture texture, int offset, Shader shader)
	{
		super(texture, shader);
		this.offset = offset;
		this.frame = 1;
		this.maxFrames = texture.getWidth() / offset;
	}
	
	public AnimatedSprite(Texture texture, int offset)
	{
		this(texture, offset, DEFAULT_ANIMATED_SPRITE_SHADER);
	}
	
	public void setFrame(int frame)
	{
		this.frame = frame;
	}
	
	public void nextFrame()
	{
		if(frame == maxFrames) 
		{
			frame = 1;
		}
		else
		{
			this.frame++;
		}
	}
	
	public void lastFrame()
	{
		if(frame == 1) 
		{
			frame = maxFrames;
		}
		else
		{
			this.frame--;
		}
	}
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);

	public int getOffset()
	{
		return offset;
	}

	public int getFrame()
	{
		return frame;
	}

	@Override
	public void draw(Game game, Graphics gfx) {
		shader.bind();
		shader.setUniform("frame", (float)frame - 1);
		shader.setUniform("maxFrames", (float)maxFrames);
		gfx.drawTexturedPointQuadPerspective(game.getWindow(), game.getActiveScene().getCamera(), getTransform(), shader, texture);
		if(game.drawBoundingBoxesEnabled())
			box.draw(game, gfx);
	}
}
