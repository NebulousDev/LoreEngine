package nebulous.sandbox;

import lore.math.Vector2f;
import nebulous.loreEngine.core.game.AnimatedSprite;
import nebulous.loreEngine.core.game.Entity;
import nebulous.loreEngine.core.game.Game;
import nebulous.loreEngine.core.game.Scene;
import nebulous.loreEngine.core.graphics.Texture;
import nebulous.loreEngine.core.utils.Input;

public class Player extends AnimatedSprite {
	
	public Player(Texture texture, int offset)
	{
		super(texture, offset);
	}

	@Override
	public void onCreate(Game game, Scene scene) {
		
	}

	@Override
	public void onDestroy(Game game, Scene scene) {
		
	}
	
	final Vector2f UP 		= new Vector2f( 0,  1);
	final Vector2f DOWN 	= new Vector2f( 0, -1);
	final Vector2f LEFT 	= new Vector2f(-1,  0);
	final Vector2f RIGHT 	= new Vector2f( 1,  0);

	@Override
	public void onTick(Game game, Scene scene, int tick, int tock) {
		if(tick == tock) nextFrame();
		
		float speed = 0.1f;//cameraSpeed * 0.02f;// * (float)delta;
		
		if(Input.isKeyHeld(Input.KEY_W))
		{
			move(UP, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_S))
		{
			move(DOWN, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_A))
		{
			move(LEFT, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_D))
		{
			move(RIGHT, speed);
		}
		
		if(Input.isKeyHeld(Input.KEY_UP))
		{
			scene.getCamera().zoom(-0.001f);
		}
		
		if(Input.isKeyHeld(Input.KEY_DOWN))
		{
			scene.getCamera().zoom(0.001f);
		}
		
		if(Input.isKeyHeld(Input.KEY_LEFT))
		{
		}
		
		if(Input.isKeyHeld(Input.KEY_RIGHT))
		{
		}
		
		scene.getCamera().setPos(-getPosX(), -getPosY());
	}

	@Override
	public void onUpdate(Game game, Scene scene, double delta) {
		
	}

	@Override
	public void onCollide(Game game, Scene scene, Entity entity, Vector2f distance) {
		move(distance, 1.0f);
	}

}
