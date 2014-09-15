package ext.extensions;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import ext.R;

/**
 * Created by evelina on 11/09/14.
 */
public class ThemeExtension<T extends TextView> extends BaseTextExtension<T> {

	protected int mColorPrimary = Color.TRANSPARENT;
	protected int mColorPrimaryDark = Color.TRANSPARENT;

	@Override
	public void init(T view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super.init(view, attrs, defStyleAttr, defStyleRes);

		TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.ThemeExtension, defStyleAttr, defStyleRes);
		mColorPrimary = array.getColor(R.styleable.ThemeExtension_colorPrimary, 0);
		mColorPrimaryDark = array.getColor(R.styleable.ThemeExtension_colorPrimaryDark, 0);
		array.recycle();
	}
}
