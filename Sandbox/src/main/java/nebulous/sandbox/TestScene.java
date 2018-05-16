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
import nebulous.loreEngine.core.game.UIFont;
import nebulous.loreEngine.core.game.UILayer;
import nebulous.loreEngine.core.game.UIText;

public class TestScene extends Scene {
	
	public Sprite  		block;
	public Sprite  		block2;
	public Sprite  		player;
	public UILayer 		background;
	public UILayer 		foreground;
	
	public UIElement 	testLeft;
	public UIElement 	testRight;
	
	public UIText		text;
	public UIFont		font;
	
	@Override
	public void onLoad(Game game) {
		
		game.enableDrawBoundingBoxes(true);
		
		block 		= new Block();
		block2 		= new Block();
		player 		= new Player(game.getTexture("anim"), 32);
		background 	= new StaticBackground(game.getTexture("orange"));
		foreground 	= new StaticForeground(game.getTexture("orange"));
		font		= UIFont.create("fonts/arial.fnt", "fonts/arial.png");
		text		= new UIText("Hello World", font , 20, new Vector2f(1.0f, 1.0f), Anchor.BOTTOM_LEFT);
		
		//setForeground(foreground);
		setBackground(background);

		add(block);
		add(block2);
		add(player);
		add(text);
		
		block.setPos(3, 0);
		block.setSize(2, 3);
		block.enableDrawBounds();
		block.enableGravity();
		
		block2.setPos(-3, -3);
		block2.setSize(20, 4);
		
		player.setSize(1, 1);
		player.enableDrawBounds();
		
		player.rotate(45.0f);
		
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
		
		add(testLeft);
		add(testRight);
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
	
	@Override
	public void onUpdate(Game game, double delta) {
		
	}

}
