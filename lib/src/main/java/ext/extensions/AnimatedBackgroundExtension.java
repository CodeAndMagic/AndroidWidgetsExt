package ext.extensions;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import ext.R;
import ext.drawable.AnimatedDrawable;

import static android.text.TextUtils.*;
import static ext.Const.*;

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
		String animatedDrawableClass = array.getString(R.styleable.AnimatedBackgroundExtension_animatedBackgroundDrawable);
		array.recycle();

		if (!isEmpty(animatedDrawableClass)) {
			try {
				mAnimatedDrawable = (AnimatedDrawable) Class.forName(animatedDrawableClass)
					.getConstructor(Context.class, AttributeSet.class, int.class, int.class)
					.newInstance(context, attrs, defStyleAttr, defStyleRes);
			} catch (Exception e) {
				Log.e(TAG, "Cannot instantiate the AnimatedDrawable of class '" + animatedDrawableClass + "'.", e);
			}
		}

		if (mAnimatedDrawable == null) {
			throw new IllegalArgumentException("Please provide an AnimatedDrawable subclass.");
		}
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
	public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		if (mAnimatedDrawable != null) {
			mAnimatedDrawable.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		if (mAnimatedDrawable != null) {
			mView.setBackground(mAnimatedDrawable);
		}
	}
}
