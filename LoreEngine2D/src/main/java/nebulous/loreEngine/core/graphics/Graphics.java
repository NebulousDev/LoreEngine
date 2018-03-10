package nebulous.loreEngine.core.graphics;

import java.awt.Color;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import lore.math.Matrix4f;
import nebulous.loreEngine.core.game.Window;
import nebulous.loreEngine.core.utils.Log;
import nebulous.loreEngine.core.utils.Log.LogLevel;

public class Graphics {
	
	public enum DrawMode
	{
		TRIANGLES,
		TRIANGLE_STRIPS,
		LINES,
		POINTS
	}
	
	private boolean initialized = false;

	public Graphics initialize()
	{
		if(initOpenGL()) 
			return this;
		
		return null;
	}
	
	public boolean initOpenGL()
	{
		GLFWErrorCallback.createPrint(System.err).set();
		
		if(!GLFW.glfwInit())
		{
			Log.println(LogLevel.ERROR, "Failed to initialize GLFW!");
			return false;
		}
		
		initialized = true;
		
		return true;
	}
	
	public long createWindow(Window window)
	{
		if(initialized)
		{			
			GLFW.glfwDefaultWindowHints();
			GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
			GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
			
			//GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
			//GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
			//GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
			//GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
			
			long winID = GLFW.glfwCreateWindow(window.getWidth(), window.getHeight(), window.getTitle(), 0, 0);
			if (winID == 0)
			{
				Log.println(LogLevel.ERROR, "GLFW Window failed to create for unknown reasons.");
				return -1;
			}
			
			GLFW.glfwMakeContextCurrent(winID);	
			
			try
			{
				GL.createCapabilities();			
			} 
			
			catch(Exception e)
			{
				Log.println(LogLevel.ERROR, "Failed to create OpenGL capabilites!");
				return -1;
			}
			
			GLFW.glfwSwapInterval(window.isVSync() ? 1 : 0);
			GLFW.glfwSetWindowPos(winID, window.getPosX(), window.getPosY());
			
			GLFW.glfwSetWindowSizeCallback(winID, new GLFWWindowSizeCallback(){
				
	            @Override
	            public void invoke(long win, int width, int height){
	            	window.resize(width, height);
	            }
	            
	        });
			
			//GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			GL11.glDisable(GL11.GL_CULL_FACE);

			return winID;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to create window, but OpenGL has not been initialized!");
			return -1;
		}
	}
	
	public boolean showWindow(Window window)
	{
		if(initialized)
		{
			GLFW.glfwShowWindow(window.getWindowID());
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to show window, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean hideWindow(Window window)
	{
		if(initialized)
		{
			GLFW.glfwHideWindow(window.getWindowID());
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to hide window, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean resizeWindow(Window window, int width, int height)
	{
		if(initialized)
		{			
			GLFW.glfwSetWindowSize(window.getWindowID(), width, height);
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to resize window, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean repositionWindow(Window window, int x, int y)
	{
		if(initialized)
		{			
			GLFW.glfwSetWindowPos(window.getWindowID(), x, y);
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to reposition window, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean setWindowTitle(Window window, String title)
	{
		if(initialized)
		{
			GLFW.glfwSetWindowTitle(window.getWindowID(), title);
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to resize window, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean setVSync(boolean vSync)
	{
		if(initialized)
		{
			GLFW.glfwSwapInterval(vSync ? 1 : 0);
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to set vSync, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean destroyWindow(Window window)
	{
		if(initialized)
		{			
			GLFW.glfwDestroyWindow(window.getWindowID());
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to destroy window, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean shouldWindowClose(Window window)
	{
		if(initialized)
		{			
			return GLFW.glfwWindowShouldClose(window.getWindowID());
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to get window state, but OpenGL has not been initialized!");
			return true;
		}
	}
	
	public int getScreenWidth(Window window)
	{
		if(initialized)
		{
			long monitor = GLFW.glfwGetPrimaryMonitor();
	        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
			return vidMode.width();
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to get screen width, but OpenGL has not been initialized!");
			return 0;
		}
	}
	
	public int getScreenHeight(Window window)
	{
		if(initialized)
		{
			long monitor = GLFW.glfwGetPrimaryMonitor();
	        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
			return vidMode.height();
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to get screen height, but OpenGL has not been initialized!");
			return 0;
		}
	}
	
	public boolean pollEvents()
	{
		if(initialized)
		{			
			GLFW.glfwPollEvents();
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to poll events, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean clearBuffers()
	{
		if(initialized)
		{			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to clear buffers, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean swapBuffers(Window window)
	{
		if(initialized)
		{			
			GLFW.glfwSwapBuffers(window.getWindowID());
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to swap buffers, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean setClearColor(float red, float green, float blue)
	{
		if(initialized)
		{
			GL11.glClearColor(red, green, blue, 1.0f);
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to set clear colors, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public boolean setClearColor(Color color)
	{
		return setClearColor(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	public boolean terminate()
	{
		if(initialized)
		{			
			GLFW.glfwTerminate();
			initialized = false;
			return true;
		}
		else
		{
			Log.println(LogLevel.ERROR, "Attempted to terminate OpenGL, but OpenGL has not been initialized!");
			return false;
		}
	}
	
	public void drawTexturedQuadPerspective(Window window, Camera camera, Matrix4f transform, Shader shader, Texture texture)
	{
		shader.bind();
		GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
		Primatives.QUAD.bindBuffers();
		shader.setUniform("view", camera.getView());
		shader.setUniform("perspective", camera.getPerspective(window));
		shader.setUniform("model", transform);
		texture.bind(0);
		GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, Primatives.QUAD.getIbo().size(), GL11.GL_UNSIGNED_INT, 0);
		texture.unbind();
		Primatives.QUAD.unbindBuffers();
		shader.unbind();
	}
	
	public void drawTexturedPointQuadPerspective(Window window, Camera camera, Matrix4f transform, Shader shader, Texture texture)
	{
		shader.bind();
		GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
		Primatives.POINT_QUAD.bindBuffers();
		shader.setUniform("view", camera.getView());
		shader.setUniform("perspective", camera.getPerspective(window));
		shader.setUniform("model", transform);
		texture.bind(0);
		GL11.glDrawElements(GL11.GL_POINTS, Primatives.POINT_QUAD.getIbo().size(), GL11.GL_UNSIGNED_INT, 0);
		int err = GL11.glGetError();
		if(err != 0) System.out.println("GL Error : " + err);
		texture.unbind();
		Primatives.POINT_QUAD.unbindBuffers();
		shader.unbind();
	}
	
	public void drawTexturedQuadUI(Window window, Matrix4f transform, Shader shader, Texture texture)
	{
		shader.bind();
		GL11.glViewport(0, 0, window.getWidth(), window.getHeight());
		Primatives.QUAD.bindBuffers();
		shader.setUniform("model", transform);
		texture.bind(0);
		GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, Primatives.QUAD.getIbo().size(), GL11.GL_UNSIGNED_INT, 0);
		texture.unbind();
		Primatives.QUAD.unbindBuffers();
		shader.unbind();
	}
	
	public void drawTexturedQuadUI(Window window, int x, int y, int width, int height, Shader shader, Texture texture)
	{
		shader.bind();
		GL11.glViewport(x, y, width, height);
		Primatives.QUAD.bindBuffers();
		shader.setUniform("model", Matrix4f.Identity());
		texture.bind(0);
		GL11.glDrawElements(GL11.GL_TRIANGLE_STRIP, Primatives.QUAD.getIbo().size(), GL11.GL_UNSIGNED_INT, 0);
		texture.unbind();
		Primatives.QUAD.unbindBuffers();
		shader.unbind();
	}
	
	public boolean isInitialized()
	{
		return initialized;
	}
	
	public String getGLStats(){
		return
        " OPENGL: " + GL11.glGetString(GL11.GL_VERSION) + "\n" +
        " LWJGL: " + Version.getVersion() + "\n" + 
        " GRAPHICS: " + GL11.glGetString(GL11.GL_RENDERER) + "\n" +
        " VENDORS: " + GL11.glGetString(GL11.GL_VENDOR) + "\n" +
        " OPERATING SYSTEM: " + System.getProperty("os.name") + "\n" +
        " JAVA VERSION: " + System.getProperty("java.version") + "\n" +
        " CURRENT DIRECTORY: \n" +
        " " + System.getProperty("user.dir");
    }
	
}
