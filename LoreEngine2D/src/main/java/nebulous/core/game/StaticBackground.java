package nebulous.core.game;

import lore.math.Matrix4f;
import lore.math.Vector3f;
import nebulous.core.graphics.Graphics;
import nebulous.core.graphics.Texture;

public class StaticBackground extends UILayer {
	
	protected Texture 	texture;
	protected Matrix4f 	transform;
	
	public StaticBackground(Texture texture)
	{
		this.texture = texture;
		this.transform = Matrix4f.Translation(new Vector3f(0, 0, -1.0f));
	}

	@Override
	public void draw(Game game, Graphics gfx) {
		gfx.drawTexturedQuadUI(game.getWindow(), transform, shader, texture);			
	}
	
}
