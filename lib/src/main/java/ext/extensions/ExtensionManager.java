package ext.extensions;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

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

    public static <T extends TextView> List<TextExtension<T>> getExtensions(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs == null) {
            return Collections.emptyList();
        }

        List<TextExtension<T>> features = new ArrayList<>();
        TypedArray array = context.obtainStyledAttributes(attrs, new int[]{R.attr.extensions}, defStyleAttr, defStyleRes);

        int featuresMask = array.getInt(0, 0);
        if ((featuresMask & FONT_EXT) != 0) {
            features.add(new FontExtension<T>());
        }
        if ((featuresMask & THEME_EXT) != 0) {
            features.add(new ThemeExtension<T>());
        }
        if ((featuresMask & BORDER_EXT) != 0) {
            features.add(new BorderExtension<T>());
        }
        if ((featuresMask & CLEARABLE_EXT) != 0) {
            // TODO
        }
	    if ((featuresMask & PUSH_BUTTON_EXT) != 0) {
		    features.add(new PushButtonExtension<T>());
	    }

        array.recycle();
        return features;
    }
}
