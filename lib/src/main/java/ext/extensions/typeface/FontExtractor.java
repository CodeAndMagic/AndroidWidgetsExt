package ext.extensions.typeface;

/**
 * Created by evelina on 10/09/14.
 */
public abstract class FontExtractor {

	public abstract Font[] getFonts();

	public Font getFont(String fontName) {
		for (Font font : getFonts()) {
			if (font.getName().equals(fontName)) {
				return font;
			}
		}
		return null;
	}
}
