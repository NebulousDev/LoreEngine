package nebulous.loreEngine.core.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

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
	
	private static HashMap<Character, Glyph> loadFontMapData(String location) {
		
		ArrayList<String> fontData = new ArrayList<String>();
		HashMap<Character, Glyph> glyphMap = new HashMap<Character, Glyph>();
		
		try {
			Scanner scanner = null;
			
			try {
				scanner = new Scanner(new File(UIFont.class.getResource(location).toURI().getPath()));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			
			while(scanner.hasNextLine())
				fontData.add(scanner.nextLine());
			
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
			
			scanner.close();
			
		} catch (FileNotFoundException e) {
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
