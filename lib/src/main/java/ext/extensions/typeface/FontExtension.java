package ext.extensions.typeface;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import ext.extensions.BaseViewExtension;

/**
 * Created by evelina on 10/09/14.
 */
public class FontExtension<T extends TextView> extends BaseViewExtension<T> {

	@Override
	public void init(T view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super.init(view, attrs, defStyleAttr, defStyleRes);
		FontManager.getInstance().applyFont(view, attrs, defStyleAttr, defStyleRes);
	}

	public static String obtainTextAppearanceFontFamily(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		TypedArray array = FontExtension.obtainTextAppearanceArray(context, attrs, defStyleAttr, defStyleRes);
		int attrFontFamily = FontExtension.obtainTextAppearanceFontFamily();
		String fontFamily = null;

		if (attrFontFamily != -1) {
			int n = array.getIndexCount();
			for (int i = 0; i < n; i++) {
				int attr = array.getIndex(i);
				if (attr == attrFontFamily) {
					fontFamily = array.getString(attr);
					break;
				}
			}
		}
		array.recycle();

		return fontFamily;
	}

	public static TypedArray obtainTextAppearanceArray(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

	private static int[] obtainTextViewAppearance() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return (int[]) clazz.getField("TextViewAppearance").get(clazz);
		} catch (Exception e) {
			return null;
		}
	}

	private static int obtainTextViewAppearanceTextAppearance() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return clazz.getField("TextViewAppearance_textAppearance").getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}

	private static int[] obtainTextAppearance() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return (int[]) clazz.getField("TextAppearance").get(clazz);
		} catch (Exception e) {
			return null;
		}
	}

	public static int obtainTextAppearanceFontFamily() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return clazz.getField("TextAppearance_fontFamily").getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}

	public static int obtainTextViewFontFamily() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return clazz.getField("TextView_fontFamily").getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}

	private static int obtainTextAppearanceTextStyle() {
		try {
			Class clazz = Class.forName("com.android.internal.R$styleable");
			return clazz.getField("TextAppearance_textStyle").getInt(clazz);
		} catch (Exception e) {
			return -1;
		}
	}
}
