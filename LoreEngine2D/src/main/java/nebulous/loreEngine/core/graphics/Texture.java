package nebulous.loreEngine.core.graphics;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import nebulous.loreEngine.core.utils.Log;
import nebulous.loreEngine.core.utils.Log.LogLevel;

public class Texture {
	
	public static Texture DEFAULT = Texture.create("textures/default.png");
	
	public enum TextureFilter
	{
		FILTER_LINEAR,
		FILTER_NEAREST
	}
	
	public enum TextureWrap
	{
		WRAP_CLAMP_EDGE,
		WRAP_REPEAT,
		WARP_MIRROR
	}
	
	private int bufferID;
	
	private int width;
	private int height;
	private int channels;
	
	private boolean bound;
	
	private Texture() { }

	/*
	public static Texture create(String path)
	{
		ByteBuffer image;
        int width;
        int height;
        int channels;
        
        ByteBuffer imageBuffer = IOUtil.ioResourceToByteBuffer(path, bufferSize)
		try{
			DataBuffer byteBuffer = ImageIO.read(Texture.class.getResource("/" + path)).getRaster().getDataBuffer();
			imageBuffer = ByteBuffer.wrap(((DataBufferByte) byteBuffer).getData());
			System.out.println(System.identityHashCode(byteBuffer));
		} catch(Exception e){
			Log.println(LogLevel.ERROR, "Failed to locate texture " + path + "! Using DEFAULT texture...");
        	return Texture.DEFAULT;
		}
        
        try (MemoryStack stack = MemoryStack.stackPush()) {

        	IntBuffer w 	= stack.mallocInt(1);
            IntBuffer h 	= stack.mallocInt(1);
            IntBuffer comp 	= stack.mallocInt(1);

            STBImage.stbi_set_flip_vertically_on_load(true);
            image = STBImage.stbi_load_from_memory(imageBuffer, w, h, comp, 4);
            
            if (image == null) {
            	Log.println(LogLevel.ERROR, "Failed to load texture : " + path + "! Using DEFAULT texture...");
            	return Texture.DEFAULT;
            }

            width 		= w.get();
            height 		= h.get();
            channels 	= comp.get();
        }
        
        Texture texture 	= new Texture();
        texture.bufferID 	= GL11.glGenTextures();
        texture.width 		= width;
        texture.height 		= height;
        texture.channels	= channels;
        
        texture.bind();
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, image);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        texture.unbind();
        
        Log.println(LogLevel.INFO, "Successfully loaded texture : " + path);

		return texture;
	}
	*/
	
	public static Texture create(String path){
		
		boolean successful = true;
		Texture	texture = new Texture();
		BufferedImage image;
		
		try{
			image = ImageIO.read(Texture.class.getResource("/" + path));
		} catch(Exception e){
			Log.println(LogLevel.ERROR, "Failed to locate texture " + path + "! Using DEFAULT texture...");
        	return Texture.DEFAULT;
		}

		texture.width = image.getWidth();
		texture.height = image.getHeight();

		int[] pixels = new int[texture.width * texture.height * 4];
		pixels = image.getRGB(0, 0, texture.width, texture.height, null, 0, texture.width);

		ByteBuffer pixelBuffer = BufferUtils.createByteBuffer(texture.width * texture.height * 4);
		
		boolean hasAlpha = image.getColorModel().hasAlpha();

		for (int y = 0; y < texture.height; y++) {
			for (int x = 0; x < texture.width; x++) {
				int pixel = pixels[x + y * texture.width];
				pixelBuffer.put((byte) ((pixel >> 16) & 0xFF)); // RED
				pixelBuffer.put((byte) ((pixel >> 8) & 0xFF)); // GREEN
				pixelBuffer.put((byte) ((pixel >> 0) & 0xFF)); // BLUE
				if(hasAlpha) pixelBuffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
				else pixelBuffer.put((byte)(0xFF));
			}
		}

		pixelBuffer.flip();

		texture.bufferID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.bufferID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST); //GL_LINEAR
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); //GL_LINEAR

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, texture.width, texture.height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, pixelBuffer);

		if(successful)
		Log.println(LogLevel.INFO, "Texture successfully created: " + path);
		
		return texture;
	}
	
	public void bind(int bind)
	{
		if(!bound)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + bind);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, bufferID);
			bound = true;
		}
	}
	
	public void unbind()
	{
		if(bound)
		{
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			 GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
			 bound = false;
		}
	}
	
	public int getBufferID() {
		return bufferID;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public int getChannels() {
		return channels;
	}

}
