package ext.extensions;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ext.R;
import ext.extensions.typeface.FontExtension;

/**
 * Created by evelina on 10/09/14.
 */
public class ExtensionManager {

	private static final int FONT_EXT = 0x01;
	private static final int THEME_EXT = 0x02;
	private static final int BORDER_EXT = 0x04;
	private static final int CLEARABLE_EXT = 0x08;
	private static final int PUSH_BUTTON_EXT = 0x10;
	private static final int ANIMATED_BG_EXT = 0x20;

	public static <V extends View> List<ViewExtension<V>> getExtensions(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		if (attrs == null) {
			return Collections.emptyList();
		}

		TypedArray array = context.obtainStyledAttributes(attrs, new int[]{R.attr.extensions}, defStyleAttr, defStyleRes);
		int featuresMask = array.getInt(0, 0);
		array.recycle();

		List<ViewExtension<V>> features = new ArrayList<>();

		if ((featuresMask & FONT_EXT) != 0) {
			features.add(new FontExtension<V>());
		}
		if ((featuresMask & THEME_EXT) != 0) {
			features.add(new ThemeExtension<V>());
		}
		if ((featuresMask & BORDER_EXT) != 0) {
			features.add(new BorderExtension<V>());
		}
		if ((featuresMask & CLEARABLE_EXT) != 0) {
			// TODO
		}
		if ((featuresMask & PUSH_BUTTON_EXT) != 0) {
			features.add(new PushButtonExtension<V>());
		}
		if ((featuresMask & ANIMATED_BG_EXT) != 0) {
			features.add(new AnimatedBackgroundExtension<V>());
		}

		return features;
	}
}
