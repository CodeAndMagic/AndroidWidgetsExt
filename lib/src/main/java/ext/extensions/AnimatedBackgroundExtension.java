package ext.extensions;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import ext.L;
import ext.R;
import ext.drawable.AnimatedDrawable;

import static android.text.TextUtils.*;

/**
 * Created by evelina on 15/09/14.
 */
public class AnimatedBackgroundExtension<V extends View> extends ThemeExtension<V> {

	protected AnimatedDrawable mAnimatedDrawable;

	@Override
	public void init(V view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super.init(view, attrs, defStyleAttr, defStyleRes);

		Context context = view.getContext();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AnimatedBackgroundExtension, defStyleAttr, defStyleRes);
		String drawableClass = array.getString(R.styleable.AnimatedBackgroundExtension_drawableClass);
		array.recycle();

		mAnimatedDrawable = instantiateDrawable(drawableClass, context, attrs, defStyleAttr, defStyleRes);
		if (mAnimatedDrawable == null) {
			throw new IllegalArgumentException("AnimatedDrawable class '" + drawableClass + "' not found.");
		}
	}

	private AnimatedDrawable instantiateDrawable(String drawableClass, Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		if (!isEmpty(drawableClass)) {
			try {
				return (AnimatedDrawable) Class.forName(drawableClass)
					.getConstructor(Context.class, AttributeSet.class, int.class, int.class)
					.newInstance(context, attrs, defStyleAttr, defStyleRes);
			} catch (Exception e) {
				L.log("Can't instantiate AnimatedDrawable of class '{0}'.", drawableClass, e);
			}
		}
		return null;
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mAnimatedDrawable != null) {
			mAnimatedDrawable.onViewAttachedToWindow(mView);
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mAnimatedDrawable != null) {
			mAnimatedDrawable.onViewDetachedToWindow(mView);
		}
	}

	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		if (mAnimatedDrawable != null) {
			mView.setBackground(mAnimatedDrawable);
		}
	}

	@Override
	public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (mAnimatedDrawable != null) {
			mAnimatedDrawable.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}
}
