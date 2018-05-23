package nebulous.loreEngine.core.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import lore.math.Vector2f;
import lore.math.Vector3f;
import nebulous.loreEngine.core.graphics.GraphicsBuffers.Vertex;
import nebulous.loreEngine.core.graphics.GraphicsBuffers.VertexBuffer;
import nebulous.loreEngine.core.graphics.Shader;
import nebulous.loreEngine.core.graphics.Texture;

public class UIFont {
	
	protected Texture 							fontMapTexture;
	protected HashMap<Character, UIGlyph> 		glyphMap;
	//protected HashMap<Character, VertexBuffer>	vboMap;
	
	private UIFont() {}
	
	public static UIFont create(String fontPath, String fontMapPath)
	{
		UIFont font 		= new UIFont();
		font.fontMapTexture = Texture.create(fontMapPath, true);
		font.glyphMap		= loadFontMapGlyphData(fontPath);
		//font.vboMap			= loadFontMapVBOData(font.glyphMap);
		return font;
	}
	
	private static ArrayList<String> readFile(String path) throws IOException 
	{
	  InputStream is = Shader.class.getResourceAsStream(path);
	  InputStreamReader isr = new InputStreamReader(is);
	  BufferedReader br = new BufferedReader(isr);
	  ArrayList<String> lines = new ArrayList<String>();
	  String line;
	  while ((line = br.readLine()) != null) lines.add(line);
	  br.close();
	  isr.close();
	  is.close();
	  return lines;
	}
	
	private static HashMap<Character, UIGlyph> loadFontMapGlyphData(String location) {
		
		ArrayList<String> fontData = new ArrayList<String>();
		HashMap<Character, UIGlyph> glyphMap = new HashMap<Character, UIGlyph>();
		
		try {
			
			fontData = readFile("/" + location);
			
			for(String s : fontData) {	//FIXME: This is not the best way to do this..
				if(s.startsWith("char") && !s.startsWith("chars")) {
					UIGlyph glyph = new UIGlyph();
					String[] data = s.split("\\s+");
					glyph.id 		= Integer.parseInt(data[1].split("=")[1]);
					glyph.x 		= Integer.parseInt(data[2].split("=")[1]);
					glyph.y 		= Integer.parseInt(data[3].split("=")[1]);
					glyph.width 	= Integer.parseInt(data[4].split("=")[1]);
					glyph.height 	= Integer.parseInt(data[5].split("=")[1]);
					glyph.xOffset 	= Integer.parseInt(data[6].split("=")[1]);
					glyph.yOffset 	= Integer.parseInt(data[7].split("=")[1]);
					glyph.xAdvance 	= Integer.parseInt(data[8].split("=")[1]);
					//meshHash.put((char)glyph.id, generateMesh(glyph.width, glyph.height, glyph.x, glyph.y));
					glyphMap.put((char)glyph.id, glyph);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return glyphMap;
	}
	
	@Deprecated
	@SuppressWarnings("unused")
	private static HashMap<Character, VertexBuffer> loadFontMapVBOData(HashMap<Character, UIGlyph> glyphs)
	{
		HashMap<Character, VertexBuffer> vboMap = new HashMap<Character, VertexBuffer>();
		
		for(char c : vboMap.keySet())
		{
			UIGlyph glyph = glyphs.get(c);
			
			Vertex[] verts = 
			{
				new Vertex(new Vector3f(0.0f, 			0.0f, 0.0f), 			new Vector2f(glyph.x, 				glyph.y), 					new Vector3f(0.0f, 0.0f, 0.0f)),
				new Vertex(new Vector3f(glyph.width, 	0.0f, 0.0f), 			new Vector2f(glyph.x + glyph.width, glyph.y), 					new Vector3f(0.0f, 0.0f, 0.0f)),
				new Vertex(new Vector3f(glyph.width, 	glyph.height, 0.0f), 	new Vector2f(glyph.x + glyph.width, glyph.y + glyph.height), 	new Vector3f(0.0f, 0.0f, 0.0f)),
				new Vertex(new Vector3f(0.0f, 			glyph.height, 0.0f), 	new Vector2f(glyph.x, 				glyph.y + glyph.height), 	new Vector3f(0.0f, 0.0f, 0.0f))
			};
			
			VertexBuffer charVBO = VertexBuffer.create(verts);
			
			vboMap.put(c, charVBO);
		}
		
		return vboMap;
	}

	/*
	public VertexBuffer getGlyphVBO(char character)
	{
		return vboMap.get(character);
	}
	*/
	
	public UIGlyph getGlyph(char character)
	{
		return glyphMap.get(character);
	}
	
	public Texture getFontMapTexture()
	{
		return fontMapTexture;
	}

}
