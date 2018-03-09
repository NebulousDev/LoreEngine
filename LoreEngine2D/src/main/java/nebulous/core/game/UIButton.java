package nebulous.core.game;

import lore.math.Vector2f;
import nebulous.core.game.MouseInteactionEvent.MouseInteraction;
import nebulous.core.graphics.Graphics;
import nebulous.core.graphics.Texture;
import nebulous.core.utils.Input;

public class UIButton extends UIElement {

	private Texture					texture;
	private MouseInteactionEvent 	event;
	private boolean 				mouseOver;
	
	int mx;
	int my;
	int mwidth;
	int mheight;
	
	public UIButton(Texture texture, float x, float y, float width, float height, MouseInteactionEvent interaction) {
		super(x, y, width, height);
		event = interaction;
		this.texture = texture;
	}
	
	Vector2f mousePos = null;
	
	@Override
	public void update(Game game, Scene scene, double delta) {
		super.update(game, scene, delta);
		
		mousePos = Input.getMousePosition();
		
		mx = 		(int) (position.x * game.getWindow().getWidth());
		my = 		(int) (position.y * game.getWindow().getHeight());
		mwidth = 	(int) (width * game.getWindow().getWidth());
		mheight = 	(int) (height * game.getWindow().getHeight());
		
		//System.out.println("\n" + mx);
		//System.out.println(my);
		//System.out.println(mwidth);
		//System.out.println(mheight);
		
		if(mousePos.x >= mx && mousePos.x <= mx + mwidth && 
				mousePos.y >= my && mousePos.y <= my + mheight)
		{
			if(!mouseOver)
			{
				event.interact(mousePos, MouseInteraction.MOUSE_ENTER);
				mouseOver = true;
			}
			
			if(Input.isMouseButtonClicked(Input.MOUSE_BUTTON_1))
			{
				event.interact(mousePos, MouseInteraction.MOUSE_LEFT_CLICK);
			}
			
			if(Input.isMouseButtonClicked(Input.MOUSE_BUTTON_2))
			{
				event.interact(mousePos, MouseInteraction.MOUSE_RIGHT_CLICK);
			}
			
			event.interact(mousePos, MouseInteraction.MOUSE_HOVER);
		}
		else
		{
			if(mouseOver)
			{
				event.interact(mousePos, MouseInteraction.MOUSE_EXIT);
				mouseOver = false;
			}
		}
		
	}
	
	@Override
	public void onCreate(Game game, Scene scene) { }

	@Override
	public void onDestroy(Game game, Scene scene) { }

	@Override
	public void onTick(Game game, Scene scene, int tick, int tock) { }

	@Override
	public void onUpdate(Game game, Scene scene, double delta) { }

	@Override
	public void draw(Game game, Graphics gfx) {
		gfx.drawTexturedQuadUI(game.getWindow(), mx, game.getWindow().getHeight() - my - mheight, mwidth, mheight, DEFAULT_UI_SHADER, texture);
	}

}
