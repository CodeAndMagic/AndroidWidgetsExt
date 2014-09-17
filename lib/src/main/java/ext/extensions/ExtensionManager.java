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

import ext.L;
import ext.R;
import ext.extensions.typeface.FontExtension;

/**
 * Created by evelina on 10/09/14.
 */
public class ExtensionManager {

	private static Map<int[], Class<? extends ViewExtension>> sExtensionMap = new HashMap<>();

	static {
		sExtensionMap.put(R.styleable.ThemeExtension, ThemeExtension.class);
		sExtensionMap.put(R.styleable.FontExtension, FontExtension.class);
		sExtensionMap.put(R.styleable.BorderExtension, BorderExtension.class);
		sExtensionMap.put(R.styleable.PushButtonExtension, PushButtonExtension.class);
		sExtensionMap.put(R.styleable.AnimatedBackgroundExtension, AnimatedBackgroundExtension.class);
	}

	public static <V extends View> List<ViewExtension<V>> getExtensions(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		if (attrs == null) {
			return Collections.emptyList();
		}

		List<ViewExtension<V>> extensions = new ArrayList<>();

		for (Entry<int[], Class<? extends ViewExtension>> entry : sExtensionMap.entrySet()) {
			TypedArray array = context.obtainStyledAttributes(attrs, entry.getKey(), defStyleAttr, defStyleRes);
			if (array.getIndexCount() > 0) {
				extensions.add(newExtension(entry.getValue()));
			}
			array.recycle();
		}

		return extensions;
	}

	private static ViewExtension newExtension(Class<? extends ViewExtension> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			L.log("Can't instantiate ViewExtension of class '{0}'.", clazz, e);
			throw new RuntimeException("Can't instantiate ViewExtension of class " + clazz.getName());
		}
	}
}
