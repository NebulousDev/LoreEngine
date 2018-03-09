package nebulous.core.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import lore.math.Vector2f;
import lore.math.Vector3f;

public class GraphicsBuffers {
	
	public static class Vertex
	{		
		public static final int STRIDE_POSITION = 3 * Float.BYTES;
		public static final int STRIDE_TEXCOORD = 2 * Float.BYTES;
		public static final int STRIDE_NORMAL   = 3 * Float.BYTES;
		
		public static final int SIZE = 3 + 2 + 3;
		
		public Vector3f position;
		public Vector2f texCoord;
		public Vector3f normal;
		
		public Vertex(Vector3f pos, Vector2f tex, Vector3f norm)
		{
			this.position 	= pos;
			this.texCoord 	= tex;
			this.normal 	= norm;
		}
		
		public void placeInBuffer(FloatBuffer buffer)
		{
			buffer.put(position.x);
			buffer.put(position.y);
			buffer.put(position.z);
			
			buffer.put(texCoord.x);
			buffer.put(texCoord.y);
			
			buffer.put(normal.x);
			buffer.put(normal.y);
			buffer.put(normal.z);
		}
	}
	
	public static class VertexArray
	{
		private int		bufferID;
		private boolean bound;
		
		public VertexArray() { }
		
		public static VertexArray create()
		{
			VertexArray array = new VertexArray();
			array.bufferID = GL30.glGenVertexArrays();
			return array;
		}
		
		public void bind()
		{
			if(!bound)
			{
				GL30.glBindVertexArray(bufferID);
				bound = true;
			}
		}
		
		public void unbind()
		{
			if(bound)
			{
				GL30.glBindVertexArray(0);
				bound = false;
			}
		}
		
		public void attach(VertexBuffer vBuffer) {
			bind();
			vBuffer.bind();
			GL20.glEnableVertexAttribArray(0);
			GL20.glEnableVertexAttribArray(1);
			GL20.glEnableVertexAttribArray(2);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, Vertex.SIZE * Float.BYTES, 0);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, Vertex.SIZE * Float.BYTES, (3 * Float.BYTES));
			GL20.glVertexAttribPointer(2, 3, GL11.GL_FLOAT, false, Vertex.SIZE * Float.BYTES, (3 * Float.BYTES) + (2 * Float.BYTES));
			vBuffer.unbind();
			unbind();	
		}
		
		public long getBufferID() {
			return bufferID;
		}

		public boolean isBound() {
			return bound;
		}
		
		public void delete()
		{
			GL15.glDeleteBuffers(bufferID);
		}
		
	}
	
	public static class VertexBuffer
	{
		private int 	bufferID;
		private boolean bound;
		
		private VertexBuffer() {}
		
		public static VertexBuffer create(Vertex[] vertices)
		{
			VertexBuffer buffer = new VertexBuffer();
			
			FloatBuffer bytes = MemoryUtil.memAllocFloat(vertices.length * Vertex.SIZE);
			for(Vertex v : vertices) v.placeInBuffer(bytes);
			bytes.flip();
			
			buffer.bufferID = GL15.glGenBuffers();
			
			buffer.bind();
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, bytes, GL15.GL_STATIC_DRAW);
			buffer.unbind();
			
			MemoryUtil.memFree(bytes);
			
			return buffer;
		}

		public void bind()
		{
			if(!bound)
			{
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
				bound = true;
			}
		}
		
		public void unbind()
		{
			if(bound)
			{
				GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
				bound = false;
			}
		}
		
		public long getBufferID() {
			return bufferID;
		}

		public boolean isBound() {
			return bound;
		}
		
		public void delete()
		{
			GL15.glDeleteBuffers(bufferID);
		}
		
	}
	
	public static class IndexBuffer
	{
		private int 	bufferID;
		private boolean bound;
		private int		size;
		
		private IndexBuffer() {}
		
		public static IndexBuffer create(int[] indices)
		{
			IndexBuffer buffer = new IndexBuffer();
			
			IntBuffer bytes = MemoryUtil.memAllocInt(indices.length);
			bytes.put(indices);
			bytes.flip();
			
			buffer.bufferID = GL15.glGenBuffers();
			
			buffer.bind();
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, bytes, GL15.GL_STATIC_DRAW);
			buffer.unbind();

			buffer.size = indices.length;
			
			MemoryUtil.memFree(bytes);
			
			return buffer;
		}
		
		public int size()
		{
			return size;
		}
		
		public void bind()
		{
			if(!bound)
			{
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, bufferID);
				bound = true;
			}
		}
		
		public void unbind()
		{
			if(bound)
			{
				GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
				bound = false;
			}
		}
		
		public long getBufferID() {
			return bufferID;
		}

		public boolean isBound() {
			return bound;
		}
		
		public void delete()
		{
			GL15.glDeleteBuffers(bufferID);
		}
		
	}

}
