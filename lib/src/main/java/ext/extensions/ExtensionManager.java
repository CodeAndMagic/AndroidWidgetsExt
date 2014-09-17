package ext.extensions;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ext.R;
import ext.extensions.typeface.FontExtension;

/**
 * Created by evelina on 10/09/14.
 */
public class ExtensionManager {

	private static Map<int[], ViewExtension> sExtensionMap = new HashMap<>();

	static {
		sExtensionMap.put(R.styleable.ThemeExtension, new ThemeExtension());
		sExtensionMap.put(R.styleable.FontExtension, new FontExtension());
		sExtensionMap.put(R.styleable.BorderExtension, new BorderExtension());
		sExtensionMap.put(R.styleable.PushButtonExtension, new PushButtonExtension());
		sExtensionMap.put(R.styleable.AnimatedBackgroundExtension, new AnimatedBackgroundExtension());
	}

	public static <V extends View> List<ViewExtension<V>> getExtensions(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		if (attrs == null) {
			return Collections.emptyList();
		}

		List<ViewExtension<V>> extensions = new ArrayList<>();

		for (Entry<int[], ViewExtension> entry : sExtensionMap.entrySet()) {
			TypedArray array = context.obtainStyledAttributes(attrs, entry.getKey(), defStyleAttr, defStyleRes);
			if (array.getIndexCount() > 0) {
				extensions.add(entry.getValue());
			}
			array.recycle();
		}

		return extensions;
	}
}
