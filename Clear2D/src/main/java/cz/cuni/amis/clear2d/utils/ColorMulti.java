package cz.cuni.amis.clear2d.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorMulti {
	
	public static BufferedImage multi(BufferedImage image, Color color) {
		int width = image.getWidth(null);
		int height = image.getHeight(null);
		
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		
		float fColorR = ((float)color.getRed())   / 255.0f;
		float fColorG = ((float)color.getGreen()) / 255.0f;
		float fColorB = ((float)color.getBlue())  / 255.0f;
		
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				
				int rgb = image.getRGB(x, y);
				
				int alpha = (rgb >> 24) & 0xFF;
				int red =   (rgb >> 16) & 0xFF;
				int green = (rgb >>  8) & 0xFF;
				int blue =  (rgb      ) & 0xFF;
				
				float fRed   = ((float)red)   / (float)255;
				float fGreen = ((float)green) / (float)255;
				float fBlue  = ((float)blue)  / (float)255;
				
				float fResultR = fRed * fColorR;
				float fResultG = fGreen * fColorG;
				float fResultB = fBlue * fColorB;
				
				red = (int)Math.round(fResultR * 255);
				green = (int)Math.round(fResultG * 255);
				blue = (int)Math.round(fResultB * 255);
				
				rgb = alpha << 24 | red << 16 | green << 8 | blue;
				
				result.setRGB(x, y, rgb);
			}
		}
		
		return result;
	}

}
