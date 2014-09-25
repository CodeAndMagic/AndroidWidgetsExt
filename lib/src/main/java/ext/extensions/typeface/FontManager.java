package ext.extensions.typeface;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static android.text.TextUtils.*;

/**
 * Created by evelina on 10/09/14.
 */
public class FontManager {

	private static final FontManager INSTANCE = new FontManager();

	public static FontManager getInstance() {
		return INSTANCE;
	}

	private final Map<Font, Typeface> mFontCache;
	private final Set<FontExtractor> mFontExtractors;

	public FontManager() {
		mFontCache = new HashMap<>();
		mFontExtractors = new HashSet<>();
	}

	public void addFontExtractor(FontExtractor fontExtractor) {
		if (fontExtractor == null) {
			throw new IllegalArgumentException("Please provide a valid FontExtractor.");
		}
		mFontExtractors.add(fontExtractor);
	}

	public void applyFont(TextView view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

		String fontFamily = FontExtension.obtainTextAppearanceFontFamily(view.getContext(), attrs, defStyleAttr, defStyleRes);
		if (isEmpty(fontFamily)) {
			return;
		}

		for (FontExtractor extractor : mFontExtractors) {
			final Font font = extractor.getFont(fontFamily);
			if (font != null) {
				applyTypeface(view, font);
				break;
			}
		}
	}

	public void applyTypeface(TextView textView, Font font) {
		if (font == null) {
			throw new IllegalArgumentException("Please provide a valid Font.");
		}

		final Typeface typeface = getTypeface(textView.getContext(), font);
		if (typeface != null) {
			textView.setTypeface(typeface);
		}
	}

	private Typeface getTypeface(Context context, Font font) {
		if (mFontCache.containsKey(font)) {
			return mFontCache.get(font);
		}

		final Typeface typeface = Typeface.createFromAsset(context.getAssets(), font.getName());
		if (typeface == null) {
			throw new RuntimeException("Can't create Typeface from '" + font.getName() + "'");
		}

		mFontCache.put(font, typeface);
		return typeface;
	}


}
