package nebulous.loreEngine.core.game;

import lore.math.Vector2f;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.IRenderable;
import nebulous.loreEngine.core.graphics.Shader;

public abstract class UILayer implements IRenderable {
	
	public enum RepeatMode
	{
		REPEAT_X,
		REPEAT_Y,
		STRETCH,
		CENTER
	}
	
	protected Vector2f		relPos;
	protected Vector2f 		relScale;
	protected RepeatMode 	repeatMode;
	protected Shader		shader;

	public UILayer()
	{
		this.relPos = new Vector2f(0, 0);
		this.relScale = new Vector2f(1, 1);
		this.repeatMode = RepeatMode.STRETCH;
		this.shader = UIElement.DEFAULT_UI_SHADER;
	}
	
	public abstract void draw(Game game, Graphics gfx);

	public Vector2f getRelPos() {
		return relPos;
	}

	public Vector2f getRelScale() {
		return relScale;
	}

	public RepeatMode getRepeatMode() {
		return repeatMode;
	}

	public Shader getShader() {
		return shader;
	}	
	
}
