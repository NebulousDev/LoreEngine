package nebulous.loreEngine.core.game;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import lore.math.Vector2f;
import nebulous.loreEngine.core.graphics.FrameBuffer;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Shader;
import nebulous.loreEngine.core.graphics.Texture;
import nebulous.loreEngine.core.utils.Log;
import nebulous.loreEngine.core.utils.Log.LogLevel;

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
	
	protected FrameBuffer	framebuffer;
	protected Texture		texture;

	public UIText(String text, UIFont font, float fontSize, Vector2f offset, Anchor anchor)
	{
		super(offset, new Vector2f(1, 1), anchor);
		this.text			= text;
		this.font			= font;
		this.fontSize 		= fontSize;
		this.texture		= null;
		this.framebuffer 	= null;
	}
	
	private static Texture renderTextToTexture(Game game, FrameBuffer buffer, String text, UIFont font)
	{	
		FloatBuffer points 	= MemoryUtil.memAllocFloat(8 * text.length());
		int 		width 	= 0;
		int 		height 	= 0;
		float 		advance = 0;
		
		for(char c : text.toCharArray())
		{
			Glyph glyph = font.getGlyph(c);
			points.put(advance);
			points.put(glyph.x);
			points.put(glyph.y);
			points.put(glyph.width);
			points.put(glyph.height);
			points.put(glyph.xOffset);
			points.put(glyph.yOffset);
			points.put(glyph.xAdvance);
			
			width += glyph.width + glyph.xOffset;
			int tempHeight = glyph.height + Math.abs(glyph.yOffset);
			if(tempHeight > height) height = tempHeight;
			advance += glyph.xAdvance;
		}
		
		points.flip();
		
		int vao = GL30.glGenVertexArrays();
		int vbo = GL15.glGenBuffers();
		
		GL30.glBindVertexArray(vao);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, points, GL15.GL_STATIC_DRAW);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		GL20.glVertexAttribPointer(0, 1, GL11.GL_FLOAT, false, 8 * Float.BYTES, 0);
		GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, 8 * Float.BYTES, 1 * Float.BYTES);
		GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, 8 * Float.BYTES, 5 * Float.BYTES);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		Texture texture = buffer.genTextureBuffer(width, height);
		
		buffer.bind();
		
		game.getGraphics().setClearColor(0.0f, 1.0f, 0.0f);
		game.getGraphics().clearBuffers();

		IntBuffer drawBuffer = MemoryUtil.memAllocInt(1);
		drawBuffer.put(GL30.GL_COLOR_ATTACHMENT0);
		drawBuffer.flip();
		GL20.glDrawBuffers(drawBuffer);
		
		DEFAULT_TEXT_SHADER.bind();
		texture.bind(0);
		GL11.glDrawArrays(GL11.GL_POINTS, 0, 8 * text.length());
		texture.unbind();
		DEFAULT_TEXT_SHADER.unbind();
		buffer.unbind();
		
		GL30.glBindVertexArray(0);
		
		int err = GL11.glGetError();
		if(err != 0) Log.println(LogLevel.WARNING, "A OpenGL error occured generating text texture: " + err);
		
		return texture;
	}
	
	@Override
	public void create(Game game, Scene scene)
	{
		framebuffer = FrameBuffer.create();
		texture = renderTextToTexture(game, framebuffer, text, font);
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
		gfx.drawTexturedQuadUI(game.getWindow(), 20, 20, 200, 200, UIButton.DEFAULT_UI_SHADER, texture);
	}

}
