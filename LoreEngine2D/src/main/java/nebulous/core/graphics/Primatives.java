package nebulous.core.graphics;

import lore.math.Vector2f;
import lore.math.Vector3f;
import nebulous.core.graphics.GraphicsBuffers.Vertex;

public class Primatives {
	
	private static final Vertex[] QUAD_VERTS = new Vertex[]
	{
		new Vertex(new Vector3f(-1.0f, -1.0f, 0.0f), new Vector2f(0.0f, 0.0f), new Vector3f(0.0f, 0.0f ,0.0f)),
		new Vertex(new Vector3f( 1.0f, -1.0f, 0.0f), new Vector2f(1.0f, 0.0f), new Vector3f(0.0f, 0.0f ,0.0f)),
		new Vertex(new Vector3f(-1.0f,  1.0f, 0.0f), new Vector2f(0.0f, 1.0f), new Vector3f(0.0f, 0.0f ,0.0f)),
		new Vertex(new Vector3f( 1.0f,  1.0f, 0.0f), new Vector2f(1.0f, 1.0f), new Vector3f(0.0f, 0.0f ,0.0f))
	};
	
	private static final int[] QUAD_INDICES = new int[] { 0, 1, 2, 3 };
	
	public static final Mesh QUAD = Mesh.create("default_quad", QUAD_VERTS, QUAD_INDICES);
	
}
