package nebulous.loreEngine.core.graphics;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import nebulous.loreEngine.core.utils.Log;
import nebulous.loreEngine.core.utils.Log.LogLevel;

public class Texture {

	public static Texture DEFAULT1 			= Texture.create("textures/default.png");
	public static Texture DEFAULT2			= Texture.create("textures/default2.png");
	public static Texture DEFAULT_ANIMATED	= Texture.create("textures/default_animated.png");

	public enum TextureFilter {
		FILTER_LINEAR, FILTER_NEAREST
	}

	public enum TextureWrap {
		WRAP_CLAMP_EDGE, WRAP_REPEAT, WARP_MIRROR
	}

	private int bufferID;

	private int width;
	private int height;
	private int channels;

	private boolean bound;

	private Texture() {
	}

	public static Texture create(int id, int width, int height, int channels) {
		Texture texture = new Texture();
		texture.bufferID = id;
		texture.channels = channels;
		texture.width = width;
		texture.height = height;
		return texture;
	}

	public static BufferedImage flip(BufferedImage image) {
		BufferedImage flipped = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		AffineTransform tran = AffineTransform.getTranslateInstance(0, image.getHeight());
		AffineTransform flip = AffineTransform.getScaleInstance(1d, -1d);
		tran.concatenate(flip);

		Graphics2D g = flipped.createGraphics();
		g.setTransform(tran);
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return flipped;
	}

	public static Texture create(String path) {

		boolean successful = true;
		Texture texture = new Texture();
		BufferedImage image;

		try {
			image = ImageIO.read(Texture.class.getResource("/" + path));
			//image = flip(image);
		} catch (Exception e) {
			Log.println(LogLevel.ERROR, "Failed to locate texture " + path + "! Using DEFAULT texture...");
			return Texture.DEFAULT1;
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
				if (hasAlpha)
					pixelBuffer.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
				else
					pixelBuffer.put((byte) (0xFF));
			}
		}

		pixelBuffer.flip();

		texture.bufferID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.bufferID);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_CLAMP);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_CLAMP);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST); // GL_LINEAR
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST); // GL_LINEAR

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, texture.width, texture.height, 0, GL11.GL_RGBA,
				GL11.GL_UNSIGNED_BYTE, pixelBuffer);

		if (successful)
			Log.println(LogLevel.INFO, "Texture successfully created: " + path);

		return texture;
	}

	public void bind(int bind) {
		if (!bound) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0 + bind);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, bufferID);
			bound = true;
		}
	}

	public void unbind() {
		if (bound) {
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

	public void release() {
		GL11.glDeleteTextures(bufferID);
	}

}
