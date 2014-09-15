package ext.extensions.typeface;

import android.graphics.Typeface;

import ext.extensions.typeface.Font.Style;

/**
 * Created by evelina on 10/09/14.
 */
public abstract class FontExtractor {

	public abstract Font[] getFonts();

	public Font getFont(String fontName, int textStyle) {

		Style style = null;
		switch (textStyle) {
			case Typeface.NORMAL:
				style = Style.normal;
				break;

			case Typeface.BOLD:
				style = Style.bold;
				break;

			case Typeface.ITALIC:
				style = Style.italic;
				break;

			case Typeface.BOLD_ITALIC:
				style = Style.boldItalic;
				break;

			default:
				style = Style.normal;
				break;
		}


		for (Font font : getFonts()) {
			if (font.getName().equals(fontName) && font.getStyle() == style) {
				return font;
			}
		}
		return null;
	}
}
