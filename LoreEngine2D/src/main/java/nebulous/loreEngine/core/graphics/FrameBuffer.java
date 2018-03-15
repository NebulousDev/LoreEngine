package nebulous.loreEngine.core.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class FrameBuffer {
	
	private int		bufferID;
	private boolean bound;
	
	public FrameBuffer() { }
	
	public static FrameBuffer create()
	{
		FrameBuffer buffer = new FrameBuffer();
		buffer.bufferID = GL30.glGenFramebuffers();
		return buffer;
	}
	
	public void bind()
	{
		if(!bound)
		{
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, bufferID);
			bound = true;
		}
	}
	
	public void unbind()
	{
		if(bound)
		{
			GL30.glBindFramebuffer(GL30.GL_FRAMEBUFFER, 0);
			bound = false;
		}
	}
	
	public Texture genTextureBuffer(int width, int height)
	{
		bind();
		int texture = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, 0);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, texture, 0);
		unbind();
		
		return Texture.create(texture, width, height, 4);
	}
	
	public long getBufferID()
	{
		return bufferID;
	}

	public boolean isBound()
	{
		return bound;
	}
	
	public void delete()
	{
		GL15.glDeleteBuffers(bufferID);
	}

}
