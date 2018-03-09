package nebulous.loreEngine.core.graphics;

import nebulous.loreEngine.core.graphics.GraphicsBuffers.IndexBuffer;
import nebulous.loreEngine.core.graphics.GraphicsBuffers.Vertex;
import nebulous.loreEngine.core.graphics.GraphicsBuffers.VertexArray;
import nebulous.loreEngine.core.graphics.GraphicsBuffers.VertexBuffer;
import nebulous.loreEngine.core.utils.Log;
import nebulous.loreEngine.core.utils.Log.LogLevel;

public class Mesh {
	
	private VertexArray		vao;
	private VertexBuffer 	vbo;
	private IndexBuffer		ibo;
	
	private Mesh() {}
	
	public static Mesh create(String name, Vertex[] vertices, int[] indices)
	{
		Mesh mesh = new Mesh();
		mesh.vao = VertexArray.create();
		mesh.vbo = VertexBuffer.create(vertices);
		mesh.ibo = IndexBuffer.create(indices);
		
		mesh.vao.attach(mesh.vbo);
		
		Log.println(LogLevel.INFO, "Successfully generated mesh : " + name);
		
		return mesh;
	}
	
	public void bindBuffers()
	{
		vao.bind();
		vbo.bind();
		ibo.bind();
	}
	
	public void unbindBuffers()
	{
		ibo.unbind();
		vbo.unbind();
		vao.unbind();
	}

	public VertexBuffer getVbo() {
		return vbo;
	}

	public IndexBuffer getIbo() {
		return ibo;
	}
	
	public void delete()
	{
		vao.delete();
		vbo.delete();
		ibo.delete();
	}

}
