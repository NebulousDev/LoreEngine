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
import nebulous.loreEngine.core.utils.Input;

public class TestScene extends Scene {
	
	public Sprite  block;
	public Sprite  player;
	public UILayer background;
	public UILayer foreground;
	
	public UIElement testLeft;
	public UIElement testRight;
	
	@Override
	public void onLoad(Game game) {
		game.enableDrawBoundingBoxes(true);
		
		block 		= new Block();
		player 		= new Player(game.getTexture("anim"), 32);
		background 	= new StaticBackground(game.getTexture("orange"));
		foreground 	= new StaticForeground(game.getTexture("orange"));
		
		//setForeground(foreground);
		setBackground(background);

		add(block);
		add(player);
		
		block.setPos(3, 0);
		block.setSize(2, 3);
		block.enableDrawBounds();
		
		player.setSize(4, 1);
		player.enableDrawBounds();
		
		testLeft = new UIButton(game.getTexture("blue"), 0, 0, 100, 500, Anchor.LEFT, new MouseInteactionEvent() {
			
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
		testRight = new UIButton(game.getTexture("blue"), 0, 0, 100, 100, Anchor.RIGHT, new MouseInteactionEvent() {
			
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
	
	final Vector2f UP 		= new Vector2f( 0,  1);
	final Vector2f DOWN 	= new Vector2f( 0, -1);
	final Vector2f LEFT 	= new Vector2f(-1,  0);
	final Vector2f RIGHT 	= new Vector2f( 1,  0);
	
	float cameraSpeed = 5.0f;

	@Override
	public void onUpdate(Game game, double delta) {
		
		float speed = cameraSpeed * (float)delta;
		
		if(Input.isKeyHeld(Input.KEY_W))
		{
			player.move(UP, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_S))
		{
			player.move(DOWN, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_A))
		{
			player.move(LEFT, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_D))
		{
			player.move(RIGHT, speed);
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
