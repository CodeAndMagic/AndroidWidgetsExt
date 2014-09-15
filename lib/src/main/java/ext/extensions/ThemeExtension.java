package ext.extensions;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import ext.R;

/**
 * Created by evelina on 11/09/14.
 */
public class ThemeExtension<V extends View> extends BaseViewExtension<V> {

	protected int mColorPrimary = Color.TRANSPARENT;
	protected int mColorPrimaryDark = Color.TRANSPARENT;

	@Override
	public void init(V view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super.init(view, attrs, defStyleAttr, defStyleRes);

		TypedArray array = view.getContext().obtainStyledAttributes(attrs, R.styleable.ThemeExtension, defStyleAttr, defStyleRes);
		mColorPrimary = array.getColor(R.styleable.ThemeExtension_colorPrimary, mColorPrimary);
		mColorPrimaryDark = array.getColor(R.styleable.ThemeExtension_colorPrimaryDark, mColorPrimaryDark);
		array.recycle();
	}
}
