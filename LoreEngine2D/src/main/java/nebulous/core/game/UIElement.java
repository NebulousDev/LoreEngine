package nebulous.core.game;

import nebulous.core.graphics.Graphics;
import nebulous.core.graphics.IRenderable;
import nebulous.core.graphics.Shader;

public abstract class UIElement extends Transform2D implements IRenderable, IUpdatable {

	public static final Shader DEFAULT_UI_SHADER 
		= Shader.create("default_layer_shader", "shaders/vs_default_ui.glsl", "shaders/fs_default_ui.glsl");
	
	protected float width;
	protected float height;
	
	public UIElement(float x, float y, float width, float height) {
		this.position.x = x;
		this.position.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void create(Game game, Scene scene)
	{
		onCreate(game, scene);
		scene.getUIRenderList().add(this);
	}
	
	public void destroy(Game game, Scene scene)
	{
		onDestroy(game, scene);
		scene.getUIRenderList().remove(this);
	}
	
	public void tick(Game game, Scene scene, int tick, int tock)
	{
		onTick(game, scene, tick, tock);
	}
	
	public void update(Game game, Scene scene, double delta)
	{
		onUpdate(game, scene, delta);
	}
	
	public abstract void onCreate(Game game, Scene scene);
	public abstract void onDestroy(Game game, Scene scene);
	public abstract void onTick(Game game, Scene scene, int tick, int tock);
	public abstract void onUpdate(Game game, Scene scene, double delta);

	public abstract void draw(Game game, Graphics gfx);

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}
	
}
