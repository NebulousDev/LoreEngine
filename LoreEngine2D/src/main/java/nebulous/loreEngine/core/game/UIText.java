package nebulous.loreEngine.core.game;

import java.util.ArrayList;

import lore.math.Vector2f;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Shader;
import nebulous.loreEngine.core.graphics.Texture;

public class UIText extends UIElement {
	
	public static final Shader DEFAULT_TEXT_SHADER
		= Shader.create("default_text_shader", 
				"shaders/vs_default_text.glsl",
				"shaders/fs_default_text.glsl",
				"shaders/gs_default_text.glsl");
	
	protected String 		text;
	protected UIFont		font;
	protected float 		fontSize;
	protected boolean		invalidated;
	
	protected ArrayList<UIGlyph> glyphList;

	public UIText(String text, UIFont font, float fontSize, Vector2f offset, Anchor anchor)
	{
		super(offset, new Vector2f(1, 1), anchor);
		this.text			= text;
		this.font			= font;
		this.fontSize 		= fontSize;
		this.glyphList 		= buildGlyphList(font, text);
	}
	
	@Override
	public void create(Game game, Scene scene)
	{
		scene.getUIRenderList().add(this);
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
			glyphList = buildGlyphList(font, text);
		}
	}
	
	private static ArrayList<UIGlyph> buildGlyphList(UIFont font, String text)
	{
		ArrayList<UIGlyph> list = new ArrayList<UIGlyph>();
		
		for(char c : text.toCharArray())
		{
			list.add(font.getGlyph(c));
		}
		
		return list;
	}
	
	public void invalidate()
	{
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
	
	static int size = 30;
	
	@Override
	public void draw(Game game, Graphics gfx)
	{
		for(UIGlyph g : glyphList)
		{
			Vector2f pos = getAbsolutePosition(game.getWindow().getSize());
			Texture tex = font.getFontMapTexture();
			gfx.drawTexturedQuadUI(
					game.getWindow(), (int)pos.x, (int)pos.y, g.width * size, g.height * size, 
					g.x / (float)tex.getWidth(), g.y / (float)tex.getHeight(), 
					g.width / (float)tex.getWidth(), g.height / (float)tex.getHeight(), Sprite.DEFAULT_SPRITE_SHADER, tex);
		}
	}

}
