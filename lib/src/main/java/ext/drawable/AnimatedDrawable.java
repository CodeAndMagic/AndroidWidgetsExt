package ext.drawable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
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
	protected Drawable mBackground;

	public AnimatedDrawable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		mContext = context;
		mAnimationDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AnimatedBackgroundExtension, defStyleAttr, defStyleRes);
		String interpolatorClass = array.getString(R.styleable.AnimatedBackgroundExtension_interpolatorClass);
		mAnimationDuration = array.getInt(R.styleable.AnimatedBackgroundExtension_android_animationDuration, mAnimationDuration);
		int backgroundId = array.getResourceId(R.styleable.AnimatedBackgroundExtension_android_background, 0);
		if (backgroundId != 0) {
			mBackground = context.getResources().getDrawable(backgroundId);
		}
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

	public abstract void onViewAttachedToWindow(View view);

	public abstract void onViewDetachedToWindow(View view);

	public abstract void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect);

	@Override
	public void draw(Canvas canvas) {
		if (mBackground != null) {
			mBackground.draw(canvas);
		}
	}

	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		if (mBackground != null) {
			mBackground.setBounds(left, top, right, bottom);
		}
		super.setBounds(left, top, right, bottom);
	}

	@Override
	public void setBounds(Rect bounds) {
		if (mBackground != null) {
			mBackground.setBounds(bounds);
		}
		super.setBounds(bounds);
	}

	@Override
	public void setChangingConfigurations(int configs) {
		if (mBackground != null) {
			mBackground.setChangingConfigurations(configs);
		}
		super.setChangingConfigurations(configs);
	}

	@Override
	public boolean setState(int[] stateSet) {
		if (mBackground != null) {
			mBackground.setState(stateSet);
		}
		return super.setState(stateSet);
	}

	@Override
	public void jumpToCurrentState() {
		if (mBackground != null) {
			mBackground.jumpToCurrentState();
		}
		super.jumpToCurrentState();
	}
}
