package nebulous.loreEngine.core.game;

import lore.math.Vector2f;

public abstract class MouseInteactionEvent {

	public static enum MouseInteraction
	{
		MOUSE_ENTER,
		MOUSE_EXIT,
		MOUSE_HOVER,
		MOUSE_LEFT_CLICK,
		MOUSE_RIGHT_CLICK
	}
	
	public abstract void interact(Vector2f pos, MouseInteraction interaction);
	
}
