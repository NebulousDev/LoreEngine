package nebulous.loreEngine.core.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import nebulous.loreEngine.core.graphics.Shader;
import nebulous.loreEngine.core.graphics.Texture;

class Glyph
{
	public int 		id;
	public int 		x, y;
	public int 		width, height;
	public int 		xOffset, yOffset;
	public int 		xAdvance;
	public float[] 	texCoords;
}

public class UIFont {
	
	protected Texture 					fontMapTexture;
	protected HashMap<Character, Glyph> glyphMap;
	
	private UIFont() {}
	
	public static UIFont create(String fontPath, String fontMapPath)
	{
		UIFont font 		= new UIFont();
		font.fontMapTexture = Texture.create(fontMapPath);
		font.glyphMap		= loadFontMapData(fontPath);
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
	
	private static HashMap<Character, Glyph> loadFontMapData(String location) {
		
		ArrayList<String> fontData = new ArrayList<String>();
		HashMap<Character, Glyph> glyphMap = new HashMap<Character, Glyph>();
		
		try {
			
			fontData = readFile("/" + location);
			
			for(String s : fontData) {										//FIXME: This is not the best way to do this..
				if(s.startsWith("char") && !s.startsWith("chars")) {
					Glyph glyph = new Glyph();
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
	
	public Glyph getGlyph(char character)
	{
		return glyphMap.get(character);
	}
	
	public Texture getFontMapTexture()
	{
		return fontMapTexture;
	}

}
