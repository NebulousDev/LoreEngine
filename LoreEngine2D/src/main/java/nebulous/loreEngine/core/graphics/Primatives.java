package nebulous.loreEngine.core.graphics;

import lore.math.Vector2f;
import lore.math.Vector3f;
import nebulous.loreEngine.core.graphics.GraphicsBuffers.Vertex;

public class Primatives {
	
	private static final Vertex[] QUAD_VERTS = new Vertex[]
	{
		new Vertex(new Vector3f(-1.0f, -1.0f, 0.0f), new Vector2f(0.0f, 0.0f), new Vector3f(0.0f, 0.0f ,0.0f)),
		new Vertex(new Vector3f( 1.0f, -1.0f, 0.0f), new Vector2f(1.0f, 0.0f), new Vector3f(0.0f, 0.0f ,0.0f)),
		new Vertex(new Vector3f(-1.0f,  1.0f, 0.0f), new Vector2f(0.0f, 1.0f), new Vector3f(0.0f, 0.0f ,0.0f)),
		new Vertex(new Vector3f( 1.0f,  1.0f, 0.0f), new Vector2f(1.0f, 1.0f), new Vector3f(0.0f, 0.0f ,0.0f))
	};
	
	private static final int[] QUAD_INDICES = new int[] { 0, 1, 2, 3 };
	
	private static final Vertex[] POINT_QUAD_VERTS = new Vertex[]
	{
		new Vertex(new Vector3f(0.0f, 0.0f, 0.0f), new Vector2f(0.0f, 0.0f), new Vector3f(0.0f, 0.0f ,0.0f))
	};
	
	private static final int[] POINT_QUAD_INDICES = new int[] { 0, 1, 2, 3 };
	
	public static final Mesh QUAD = Mesh.create("default_quad", QUAD_VERTS, QUAD_INDICES);
	public static final Mesh POINT_QUAD = Mesh.create("default_point_quad", POINT_QUAD_VERTS, POINT_QUAD_INDICES);
	
}
