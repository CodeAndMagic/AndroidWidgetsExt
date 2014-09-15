package ext.extensions.typeface;

/**
 * Created by evelina on 10/09/14.
 */
public interface Font {

	public static enum Style {
		normal, bold, italic, boldItalic
	}

	String getAsset();

	String getName();

	Style getStyle();
}
