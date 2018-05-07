package nebulous.loreEngine.core.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryUtil;

import lore.math.Matrix4f;
import lore.math.Vector3f;
import lore.math.Vector4f;
import nebulous.loreEngine.core.utils.Log;
import nebulous.loreEngine.core.utils.Log.LogLevel;

public class Shader {
	
	private int		programID;
	private boolean bound;
	private String	vertex;
	private String	fragment;
	
	private int		vertID;
	private int		fragID;
	private int		geomID;
	
	private Shader()
	{
		vertID = -1;
		fragID = -1;
		geomID = -1;
	}
	
	public static String readFile(String path) throws IOException 
	{
	  InputStream is = Shader.class.getResourceAsStream(path);
	  InputStreamReader isr = new InputStreamReader(is);
	  BufferedReader br = new BufferedReader(isr);
	  StringBuffer sb = new StringBuffer();
	  String line;
	  while ((line = br.readLine()) != null) sb.append(line + "\n");
	  br.close();
	  isr.close();
	  is.close();
	  return sb.toString();
	}
	
	public static Shader create(String name, String vertex, String fragment)
	{
		return create(name, vertex, fragment, null);
	}
	
	public static Shader create(String name, String vertex, String fragment, String geometry)
	{
		String vertString = "";
		String fragmentString = "";
		String geometryString = "";
		
		boolean success = true;
		
		try {
			vertString = readFile("/" + vertex);
		} catch (IOException e) {
			Log.println(LogLevel.ERROR, "Unable to locate shader file : " + vertex);
			success = false;
		}
		
		try {
			fragmentString = readFile("/" + fragment);
		} catch (IOException e) {
			Log.println(LogLevel.ERROR, "Unable to locate shader file : " + fragment);
			success = false;
		} 
		
		if(geometry != null)
		{
			try {
				geometryString = readFile("/" + geometry);
			} catch (IOException e) {
				Log.println(LogLevel.ERROR, "Unable to locate shader file : " + geometry);
				success = false;
			} 
		}
		
		Shader shader = new Shader();
		shader.programID = GL20.glCreateProgram();
		
		shader.vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		shader.fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		
		if(geometry != null) shader.geomID = GL20.glCreateShader(GL32.GL_GEOMETRY_SHADER);
		
		GL20.glShaderSource(shader.vertID, vertString);
		GL20.glAttachShader(shader.programID, shader.vertID);
		GL20.glCompileShader(shader.vertID);
		if(GL20.glGetShaderi(shader.vertID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			String message = GL20.glGetShaderInfoLog(shader.vertID);
			Log.println(LogLevel.ERROR, "Error Compiling Vertex Shader : " + name + ":" + vertex + "\n" + message);
			GL20.glDeleteShader(shader.vertID);
			success = false;
		}

		GL20.glShaderSource(shader.fragID, fragmentString);
		GL20.glAttachShader(shader.programID, shader.fragID);
		GL20.glCompileShader(shader.fragID);
		if(GL20.glGetShaderi(shader.fragID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			String message = GL20.glGetShaderInfoLog(shader.fragID);
			Log.println(LogLevel.ERROR, "Error Compiling Fragment Shader : " + name + ":" + fragment + "\n" + message);
			GL20.glDeleteShader(shader.fragID);
			success = false;
		}
		
		if(shader.geomID != -1)
		{
			GL20.glShaderSource(shader.geomID, geometryString);
			GL20.glAttachShader(shader.programID, shader.geomID);
			GL20.glCompileShader(shader.geomID);
			if(GL20.glGetShaderi(shader.geomID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
			{
				String message = GL20.glGetShaderInfoLog(shader.geomID);
				Log.println(LogLevel.ERROR, "Error Compiling Geometry Shader : " + name + ":" + geometry + "\n" + message);
				GL20.glDeleteShader(shader.geomID);
				success = false;
			}
		}
		
		GL20.glLinkProgram(shader.programID);
		if(GL20.glGetProgrami(shader.programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE)
		{
			String message = GL20.glGetProgramInfoLog(shader.programID);
			Log.println(LogLevel.ERROR, "Error Linking Shader Program : " + name + "\n" + message);
			GL20.glDeleteProgram(shader.programID);
			success = false;
		}
		
		if(success)
			Log.println(LogLevel.INFO, "Successfully loaded Shader Program : " + name);
		else
			Log.println(LogLevel.INFO, "Failed to load Shader Program: " + name);
			
		return shader;
	}
	
	public void bind()
	{
		if(!bound)
		{
			GL20.glUseProgram(programID);
			bound = true;
		}
	}
	
	public void unbind()
	{
		if(bound)
		{
			GL20.glUseProgram(0);
			bound = false;
		}
	}

	public void setUniform(String uniform, int value)
	{
		GL20.glUniform1i(GL20.glGetUniformLocation(programID, uniform), value);
	}

	public void setUniform(String uniform, float value)
	{
		GL20.glUniform1f(GL20.glGetUniformLocation(programID, uniform), value);
	}

	public void setUniform(String uniform, Vector3f value)
	{
		GL20.glUniform3f(GL20.glGetUniformLocation(programID, uniform), value.x, value.y, value.z);
	}
	
	public void setUniform(String uniform, Vector4f value)
	{
		GL20.glUniform4f(GL20.glGetUniformLocation(programID, uniform), value.x, value.y, value.z, value.w);
	}
	
	public void setUniform(String uniform, float x, float y, float z, float w)
	{
		GL20.glUniform4f(GL20.glGetUniformLocation(programID, uniform), x, y, z, w);
	}

	public void setUniform(String uniform, Matrix4f value)
	{
		FloatBuffer elements = MemoryUtil.memAllocFloat(value.getElements().length);
		elements.put(value.getElements()).flip();
		GL20.glUniformMatrix4fv(GL20.glGetUniformLocation(programID, uniform), false, elements);
		MemoryUtil.memFree(elements);
	}

	public boolean isBound() {
		return bound;
	}

	public String getVertex() {
		return vertex;
	}

	public String getFragment() {
		return fragment;
	}

	public int getProgramID() {
		return programID;
	}

	public int getVertID() {
		return vertID;
	}

	public int getFragID() {
		return fragID;
	}
	
}
