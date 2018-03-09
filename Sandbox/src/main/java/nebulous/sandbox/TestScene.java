package nebulous.sandbox;

import lore.math.Vector2f;
import nebulous.loreEngine.core.game.Game;
import nebulous.loreEngine.core.game.MouseInteactionEvent;
import nebulous.loreEngine.core.game.Scene;
import nebulous.loreEngine.core.game.Sprite;
import nebulous.loreEngine.core.game.StaticBackground;
import nebulous.loreEngine.core.game.StaticForeground;
import nebulous.loreEngine.core.game.UIButton;
import nebulous.loreEngine.core.game.UIElement;
import nebulous.loreEngine.core.game.UILayer;
import nebulous.loreEngine.core.graphics.Texture;
import nebulous.loreEngine.core.utils.Input;

public class TestScene extends Scene {

	public static final Texture BLUE 		= Texture.create("textures/default.png");
	public static final Texture ORANGE 		= Texture.create("textures/default2.png");
	public static final Texture VIGNETTE 	= Texture.create("textures/vignette.png");
	
	public Sprite  player 		= new Player();
	public UILayer background 	= new StaticBackground(ORANGE);
	public UILayer foreground 	= new StaticForeground(VIGNETTE);
	
	public UIElement testUI;
	
	@Override
	public void onLoad(Game game) {
		setForeground(foreground);
		setBackground(background);
		add(player);
		
		testUI = new UIButton(BLUE, 0.2f, 0.2f, 0.1f, 0.1f, new MouseInteactionEvent() {
			
			@Override
			public void interact(Vector2f pos, MouseInteraction interaction) {
				
				switch (interaction) {
				case MOUSE_ENTER:
				{
					System.out.println(pos);
					System.out.println("ENTER");
					break;				
				}

				case MOUSE_EXIT:
				{
					System.out.println("EXIT");
					break;				
				}
				
				case MOUSE_LEFT_CLICK:
				{
					System.out.println("CLICK");
					game.stop();
					break;				
				}
				
				default:
					break;
				}
			}

		});
		
		add(testUI);
		player.move(0.5f, 0.5f);
		getCamera().zoom(-5.0f);
	}

	@Override
	public void onUnload(Game game) {
		remove(player);
	}

	@Override
	public void onTick(Game game, int tick, int tock) {
		game.getWindow().setTitle("Sandbox | FPS:" + game.getActiveFPS());
	}
	
	float cameraSpeed = 10.0f;

	@Override
	public void onUpdate(Game game, double delta) {
		
		float speed = cameraSpeed * (float)delta;
		
		if(Input.isKeyHeld(Input.KEY_W))
		{
			getCamera().move(0.0f, -speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_S))
		{
			getCamera().move(0.0f, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_A))
		{
			getCamera().move(speed, 0.0f);
		}
		
		if(Input.isKeyHeld(Input.KEY_D))
		{
			getCamera().move(-speed, 0.0f);
		}
		
		if(Input.isKeyHeld(Input.KEY_UP))
		{
			//getCamera().zoom(-0.001f);
			testUI.move(0.0f, speed / 10.0f);
		}
		
		if(Input.isKeyHeld(Input.KEY_DOWN))
		{
			//getCamera().zoom(0.001f);
			testUI.move(0.0f, -speed / 10.0f);
		}
		
		if(Input.isKeyHeld(Input.KEY_LEFT))
		{
			//getCamera().zoom(-0.001f);
			testUI.move(-speed / 10.0f, 0.0f);
		}
		
		if(Input.isKeyHeld(Input.KEY_RIGHT))
		{
			//getCamera().zoom(0.001f);
			testUI.move(speed / 10.0f, 0.0f);
		}
		
	}

}
