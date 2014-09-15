package ext.drawable;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by evelina on 15/09/14.
 */
public abstract class AnimatedDrawable extends Drawable implements AnimatedDrawableCallback {

	protected Context mContext;
	protected int mAnimationDuration;

	public AnimatedDrawable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		mContext = context;
		mAnimationDuration = context.getResources().getInteger(android.R.integer.config_mediumAnimTime);
	}

	public int getAnimationDuration() {
		return mAnimationDuration;
	}

	public void setAnimationDuration(int animationDuration) {
		mAnimationDuration = animationDuration;
	}

	public abstract void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect);

	public abstract void startAnimation();

	public abstract void reverseAnimation();
}
