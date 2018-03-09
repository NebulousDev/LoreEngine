package nebulous.core.game;

import java.util.ArrayList;

import nebulous.core.graphics.Graphics;
import nebulous.core.graphics.IRenderable;

public class RenderList {
	
	private ArrayList<IRenderable> renderables;
	
	public RenderList() {
		renderables = new ArrayList<IRenderable>();
	}
	
	public void add(IRenderable renderable)
	{
		renderables.add(renderable);
	}
	
	public void remove(IRenderable renderable)
	{
		renderables.remove(renderable);
	}

	public void render(Game game, Graphics gfx)
	{
		for(IRenderable r : renderables)
		{
			r.draw(game, gfx);
		}
	}
	
	public ArrayList<IRenderable> getRenderables() {
		return renderables;
	}

	public void clear()
	{
		renderables.clear();
	}

}
