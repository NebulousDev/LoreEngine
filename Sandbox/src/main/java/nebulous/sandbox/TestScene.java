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
import nebulous.loreEngine.core.game.UIElement.Anchor;
import nebulous.loreEngine.core.game.UILayer;
import nebulous.loreEngine.core.graphics.Texture;
import nebulous.loreEngine.core.utils.Input;

public class TestScene extends Scene {

	public static final Texture BLUE 		= Texture.create("textures/default.png");
	public static final Texture ORANGE 		= Texture.create("textures/default2.png");
	public static final Texture VIGNETTE 	= Texture.create("textures/vignette.png");
	public static final Texture ANIM 		= Texture.create("textures/default_animated.png");
	
	public Sprite  block 		= new Block();
	public Sprite  player 		= new Player(ANIM, 32);
	public UILayer background 	= new StaticBackground(ORANGE);
	public UILayer foreground 	= new StaticForeground(VIGNETTE);
	
	public UIElement testLeft;
	public UIElement testRight;
	
	@Override
	public void onLoad(Game game) {
		game.enableDrawBoundingBoxes(true);
		//setForeground(foreground);
		//setBackground(background);
		add(block);
		add(player);
		
		block.setPos(3, 3);
		
		testLeft = new UIButton(BLUE, 0, 0, 100, 500, Anchor.LEFT, new MouseInteactionEvent() {
			
			@Override
			public void interact(Vector2f pos, MouseInteraction interaction) {
				
				switch (interaction) {
				case MOUSE_ENTER:
				{
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
		testRight = new UIButton(BLUE, 0, 0, 100, 100, Anchor.RIGHT, new MouseInteactionEvent() {
			
			@Override
			public void interact(Vector2f pos, MouseInteraction interaction) {
				
				switch (interaction) {
				case MOUSE_ENTER:
				{
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
		
		//add(testLeft);
		//add(testRight);
		getCamera().zoom(-5.0f);
	}

	@Override
	public void onUnload(Game game) {
		remove(player);
		remove(block);
	}

	@Override
	public void onTick(Game game, int tick, int tock) {
		game.getWindow().setTitle("Sandbox | FPS:" + game.getFramerate());
	}
	
	float cameraSpeed = 5.0f;

	@Override
	public void onUpdate(Game game, double delta) {
		
		float speed = cameraSpeed * (float)delta;
		
		if(Input.isKeyHeld(Input.KEY_W))
		{
			player.move(0.0f, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_S))
		{
			player.move(0.0f, -speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_A))
		{
			player.move(-speed, 0.0f);
		}
		
		if(Input.isKeyHeld(Input.KEY_D))
		{
			player.move(speed, 0.0f);
		}
		
		if(Input.isKeyHeld(Input.KEY_UP))
		{
			getCamera().zoom(-0.001f);
		}
		
		if(Input.isKeyHeld(Input.KEY_DOWN))
		{
			getCamera().zoom(0.001f);
		}
		
		if(Input.isKeyHeld(Input.KEY_LEFT))
		{
		}
		
		if(Input.isKeyHeld(Input.KEY_RIGHT))
		{
		}
		
		getCamera().setPos(-player.getPosX(), -player.getPosY());
	}

}
