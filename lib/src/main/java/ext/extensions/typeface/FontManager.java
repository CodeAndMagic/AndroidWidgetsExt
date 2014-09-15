package ext.extensions.typeface;

import android.content.Context;
import android.content.res.TypedArray;
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
		Context context = view.getContext();

		TypedArray taArray = obtainTextAppearanceArray(context, attrs, defStyleAttr, defStyleRes);
		int attrFontFamily = obtainTextAppearanceFontFamily();
		String fontFamily = null;

		if (attrFontFamily != -1) {
			int n = taArray.getIndexCount();
			for (int i = 0; i < n; i++) {
				int attr = taArray.getIndex(i);
				if (attr == attrFontFamily) {
					fontFamily = taArray.getString(attr);
					break;
				}
			}
		}
		taArray.recycle();

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

	private TypedArray obtainTextAppearanceArray(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		int textViewAppearanceAttrs[] = obtainTextViewAppearance();

		if (textViewAppearanceAttrs != null) {

			TypedArray textViewAppearance = context.getTheme().obtainStyledAttributes(attrs, textViewAppearanceAttrs, defStyleAttr, defStyleRes);
			int textViewAppearanceTextAppearanceAttr = obtainTextViewAppearanceTextAppearance();

			if (textViewAppearanceTextAppearanceAttr != -1) {

				int textAppearanceResId = textViewAppearance.getResourceId(textViewAppearanceTextAppearanceAttr, -1);
				int textAppearanceAttrs[] = obtainTextAppearance();

				if (textAppearanceResId != -1 && textAppearanceAttrs != null) {
					return context.getTheme().obtainStyledAttributes(textAppearanceResId, textAppearanceAttrs);
				}
			}
			textViewAppearance.recycle();
		}
		return null;
	}

	private int[] obtainTextViewAppearance() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return (int[]) clazz.getField("TextViewAppearance").get(clazz);
		} catch (Exception e) {
			return null;
		}
	}

	private int obtainTextViewAppearanceTextAppearance() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return clazz.getField("TextViewAppearance_textAppearance").getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}

	private int[] obtainTextAppearance() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return (int[]) clazz.getField("TextAppearance").get(clazz);
		} catch (Exception e) {
			return null;
		}
	}

	private int obtainTextAppearanceFontFamily() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return clazz.getField("TextAppearance_fontFamily").getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}

	private int obtainTextAppearanceTextStyle() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return clazz.getField("TextAppearance_textStyle").getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}
}
