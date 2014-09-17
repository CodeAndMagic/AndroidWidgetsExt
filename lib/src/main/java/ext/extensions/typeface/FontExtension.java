package ext.extensions.typeface;

import android.util.AttributeSet;
import android.view.View;
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
}
