package nebulous.loreEngine.core.game;

import lore.math.Vector2f;
import nebulous.loreEngine.core.graphics.IRenderable;
import nebulous.loreEngine.core.graphics.Shader;

public abstract class UIElement implements IRenderable, IUpdatable {

	public static final Shader DEFAULT_UI_SHADER 
		= Shader.create("default_layer_shader", "shaders/vs_default_ui.glsl", "shaders/fs_default_ui.glsl");
	
	public static enum Anchor
	{
		CENTER			( 0,  0),
		LEFT			(-1,  0),
		RIGHT			( 1,  0),
		TOP				( 0,  1),
		TOP_LEFT 		(-1,  1),
		TOP_RIGHT		( 1,  1),
		BOTTOM			( 0, -1),
		BOTTOM_LEFT 	(-1, -1),
		BOTTOM_RIGHT	( 1, -1);
		
		public Vector2f ndc;
		
		Anchor(float ndcx, float ndcy)
		{
			this.ndc = new Vector2f(ndcx, ndcy);
		}
	}
	
	protected Vector2f 	offset;
	protected Vector2f 	size;
	protected Anchor	anchor;
	
	public UIElement(Vector2f offset, Vector2f size, Anchor anchor) 
	{
		this.offset = offset;
		this.size = size;
		this.anchor = anchor;
	}
	
	public Vector2f calcOffsets(Vector2f pSpace, Vector2f offset, Vector2f size, Anchor anchor)
	{
		Vector2f result = pSpace;
		
		switch (anchor) {
		
		case CENTER:
			result.x += -0.5f * size.x + offset.x;
			result.y += -0.5f * size.y + offset.y;
			return result;
			
		case LEFT:
			result.x += offset.x;
			result.y += -0.5f * size.y + offset.y;
			return result;
			
		case RIGHT:
			result.x += -size.x - offset.x;
			result.y += -0.5f * size.y + offset.y;	
			return result;
					
		case TOP:
			result.x += -0.5f * size.x + offset.x;
			result.y += -size.y - offset.y;
			return result;
			
		case TOP_LEFT:
			result.x += offset.x;
			result.y += -size.y -offset.y;
			return result;
			
		case TOP_RIGHT:
			result.x += -size.x - offset.x;
			result.y += -size.y - offset.y;
			return result;
			
		case BOTTOM:
			result.x += -0.5f * size.x + offset.x;
			result.y += offset.y;
			return result;
			
		case BOTTOM_LEFT:
			result.x += offset.x;
			result.y += offset.y;
			return result;
			
		case BOTTOM_RIGHT:
			result.x += -size.x - offset.x;
			result.y += offset.y;
			return result;

		default:
			return result;
		}
	}
	
	public Vector2f toScreenPos(Vector2f ndc, Vector2f screen)
	{
		int px = (int) (((ndc.x + 1.0f) * (screen.x / 2.0f) + 0) + 0.5f);
		int py = (int) (((ndc.y + 1.0f) * (screen.y / 2.0f) + 0) + 0.5f);
		return new Vector2f(px, py);
	}
	
}
