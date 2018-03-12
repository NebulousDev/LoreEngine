package nebulous.sandbox;

import nebulous.loreEngine.core.game.AnimatedSprite;
import nebulous.loreEngine.core.game.Entity;
import nebulous.loreEngine.core.game.Game;
import nebulous.loreEngine.core.game.Scene;
import nebulous.loreEngine.core.graphics.Texture;

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

	@Override
	public void onTick(Game game, Scene scene, int tick, int tock) {
		if(tick == tock) nextFrame();
	}

	@Override
	public void onUpdate(Game game, Scene scene, double delta) {
		
	}

	@Override
	public void onCollide(Game game, Scene scene, Entity entity) {
		
	}

}
