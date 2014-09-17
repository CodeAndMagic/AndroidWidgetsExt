package ext.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import ext.L;
import ext.R;

import static android.text.TextUtils.*;

/**
 * Created by evelina on 15/09/14.
 */
public abstract class AnimatedDrawable extends Drawable {

	protected Context mContext;
	protected Interpolator mInterpolator;
	protected int mAnimationDuration;

	public AnimatedDrawable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		mContext = context;
		mAnimationDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AnimatedBackgroundExtension, defStyleAttr, defStyleRes);
		String interpolatorClass = array.getString(R.styleable.AnimatedBackgroundExtension_interpolatorClass);
		mAnimationDuration = array.getInt(R.styleable.AnimatedBackgroundExtension_android_animationDuration, mAnimationDuration);
		array.recycle();

		mInterpolator = instantiateInterpolator(interpolatorClass);
	}

	private Interpolator instantiateInterpolator(String interpolatorClass) {
		if (!isEmpty(interpolatorClass)) {
			try {
				return (Interpolator) Class.forName(interpolatorClass).newInstance();

			} catch (Exception e) {
				L.log("Cannot instantiate Interpolator of class '{0}'.", interpolatorClass, e);
			}
		}
		return new AccelerateDecelerateInterpolator();
	}

	public void setAnimationDuration(int animationDuration) {
		mAnimationDuration = animationDuration;
	}

	public void setInterpolator(Interpolator interpolator) {
		mInterpolator = interpolator;
	}

	// Add below all methods that a drawable might be interested in

	public abstract void onViewAttachedToWindow(View view);

	public abstract void onViewDetachedToWindow(View view);

	public abstract void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect);
}
