package ext.sample;

import java.util.ArrayList;
import java.util.Arrays;

import ext.extensions.typeface.Font;
import ext.extensions.typeface.FontExtractor;

/**
 * Created by evelina on 15/09/14.
 */
public class CustomFonts extends FontExtractor {

	private static final CustomFonts INSTANCE = new CustomFonts();

	public static CustomFonts getInstance() {
		return INSTANCE;
	}

	@Override
	public Font[] getFonts() {
		ArrayList<Font> fonts = new ArrayList<Font>() {{
			addAll(Arrays.asList(RalewayFont.values()));
			addAll(Arrays.asList(LobsterFont.values()));
		}};
		return fonts.toArray(new Font[fonts.size()]);
	}
}
