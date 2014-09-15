package ext.extensions.typeface;

import android.util.AttributeSet;
import android.widget.TextView;

import ext.extensions.BaseTextExtension;

/**
 * Created by evelina on 10/09/14.
 */
public class FontExtension<T extends TextView> extends BaseTextExtension<T> {

    @Override
    public void init(T view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.init(view, attrs, defStyleAttr, defStyleRes);
        TypefaceManager.getInstance().applyFont(view, attrs, defStyleAttr, defStyleRes);
    }
}
