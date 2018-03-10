package nebulous.loreEngine.core.game;

import lore.math.Vector2f;
import nebulous.loreEngine.core.game.MouseInteactionEvent.MouseInteraction;
import nebulous.loreEngine.core.graphics.Graphics;
import nebulous.loreEngine.core.graphics.Texture;
import nebulous.loreEngine.core.utils.Input;

public class UIButton extends UIElement {
	
	protected Texture 				texture;
	protected MouseInteactionEvent 	event;
	protected boolean				mouseOver;

	public UIButton(Texture texture, int offX, int offY, int width, int height, Anchor anchor, MouseInteactionEvent interaction) {
		super(new Vector2f(offX, offY), new Vector2f(width, height), anchor);
		this.texture = texture;
		this.event = interaction;
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
		Vector2f mouse = Input.getMousePosition();
		Vector2f pos = calcOffsets(toScreenPos(anchor.ndc, game.getWindow().getSize()), offset, size, anchor);
		
		if
		(
			mouse.x >= pos.x && 
			mouse.x <= pos.x + size.x && 
			mouse.y >= (game.getWindow().getHeight() - pos.y - size.y) && 
			mouse.y <= (game.getWindow().getHeight() - pos.y))
		{
			if(!mouseOver)
			{
				event.interact(mouse, MouseInteraction.MOUSE_ENTER);
				mouseOver = true;
			}
			
			if(Input.isMouseButtonClicked(Input.MOUSE_BUTTON_1))
			{
				event.interact(mouse, MouseInteraction.MOUSE_LEFT_CLICK);
			}
			
			if(Input.isMouseButtonClicked(Input.MOUSE_BUTTON_2))
			{
				event.interact(mouse, MouseInteraction.MOUSE_RIGHT_CLICK);
			}
			
			event.interact(mouse, MouseInteraction.MOUSE_HOVER);
		}
		else
		{
			if(mouseOver)
			{
				event.interact(mouse, MouseInteraction.MOUSE_EXIT);
				mouseOver = false;
			}
		}
		
	}

	@Override
	public void draw(Game game, Graphics gfx)
	{
		Vector2f pos = calcOffsets(toScreenPos(anchor.ndc, game.getWindow().getSize()), offset, size, anchor);
		gfx.drawTexturedQuadUI(game.getWindow(), (int)pos.x, (int)pos.y, (int)size.x, (int)size.y, DEFAULT_UI_SHADER, texture);
	}

	@Override
	public void tick(Game game, Scene scene, int tick, int tock) { }

	@Override
	public void onCreate(Game game, Scene scene) { }

	@Override
	public void onDestroy(Game game, Scene scene) { }

	@Override
	public void onTick(Game game, Scene scene, int tick, int tock) { }

	@Override
	public void onUpdate(Game game, Scene scene, double delta) { }

}
