package nebulous.loreEngine.core.utils;

import java.awt.Color;

public class Log {
	
	public enum LogLevel
	{
		DEBUG		("[DEBUG]", Color.BLUE, Color.WHITE),
		GENERAL		("[GENERAL]", Color.LIGHT_GRAY, Color.WHITE),
		WARNING		("[WARNING]", Color.YELLOW, Color.WHITE),
		ERROR		("[ERROR]", Color.RED, Color.ORANGE),
		INFO		("[INFO]", Color.GRAY, Color.WHITE);
		
		private String prefix;
		
		private Color prefixColor;
		private Color lineColor;
		
		LogLevel(String prefix, Color prefixColor, Color lineColor)
		{
			this.prefix = prefix;
			this.prefixColor = prefixColor;
			this.lineColor = lineColor;
		}

		public String getPrefix() {
			return prefix;
		}
		
		public Color getPrefixColor() {
			return prefixColor;
		}

		public Color getLineColor() {
			return lineColor;
		}
		
	}
	
	public static void println(LogLevel level, Object text)
	{
		System.out.println(level.prefix + text);
	}

}
