package nebulous.loreEngine.core.game;

import lore.math.Vector2f;
import nebulous.loreEngine.core.graphics.FrameBuffer;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Shader;
import nebulous.loreEngine.core.graphics.Texture;

public class UIText extends UIElement {
	
	public static final Shader DEFAULT_TEXT_SHADER
		= Shader.create("default_text_shader", 
				"vs_default_text.glsl",
				"gs_default_text.glsl",
				"fs_default_text.glsl");
	
	protected String 		text;
	protected UIFont		font;
	protected float 		fontSize;
	protected boolean		invalidated;
	
	protected FrameBuffer	framebuffer;
	protected Texture		texture;

	public UIText(String text, UIFont font, float fontSize, Vector2f offset, Anchor anchor)
	{
		super(offset, new Vector2f(1, 1), anchor);
		this.font			= font;
		this.fontSize 		= fontSize;
		this.texture		= null;
		this.framebuffer 	= null;
	}
	
	private static Texture renderTextToTexture(Game game, FrameBuffer buffer, String text, UIFont font)
	{
		int width 	= 0;
		int height 	= 0;
		
		for(char c : text.toCharArray())
		{
			Glyph glyph = font.getGlyph(c);
			width += glyph.width + glyph.xOffset;
			int tempHeight = glyph.height + Math.abs(glyph.yOffset);
			if(tempHeight > height) height = tempHeight;
		}
		
		Texture texture = buffer.genTextureBuffer(width, height);
		buffer.bind();
		game.getGraphics().drawTexturedQuadUI(game.getWindow(), 0, 0, width, height, Sprite.DEFAULT_SPRITE_SHADER, texture);
		buffer.unbind();
		return texture;
	}
	
	@Override
	public void create(Game game, Scene scene)
	{
		framebuffer = FrameBuffer.create();
		texture = renderTextToTexture(game, framebuffer, text, font);
	}

	@Override
	public void destroy(Game game, Scene scene)
	{
		
	}

	@Override
	public void update(Game game, Scene scene, double delta)
	{
		if(isInvalid())
		{
			this.texture = renderTextToTexture(game, framebuffer, text, font);
		}
	}
	
	public void invalidate()
	{
		this.texture.release();
		this.invalidated = true;
	}
	
	public void setText(String text)
	{
		this.text = text;
		invalidate();
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setFont(UIFont font)
	{
		this.font = font;
		invalidate();
	}
	
	public UIFont getFont()
	{
		return font;
	}
	
	public boolean isInvalid()
	{
		return invalidated;
	}
	
	@Override
	public void draw(Game game, Graphics gfx)
	{
		
	}

}
