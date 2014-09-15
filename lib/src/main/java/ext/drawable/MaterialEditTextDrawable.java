package ext.drawable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import ext.R;

/**
 * Created by evelina on 14/09/14.
 */
public class MaterialEditTextDrawable extends AnimatedDrawable {

	protected Rect mBounds;
	protected Paint mPaint;
	protected Paint mFocusedPaint;
	protected ObjectAnimator mAnimator;
	protected boolean mFocused;
	protected boolean mStartAnimation;
	protected boolean mAnimateFromCenter;

	protected int mWidth;
	protected int mStrokeWidth;

	public MaterialEditTextDrawable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

		TypedArray array = context.obtainStyledAttributes(R.styleable.ThemeExtension);
		int colorPrimary = array.getColor(R.styleable.ThemeExtension_colorPrimary, Color.TRANSPARENT);
		int colorPrimaryDark = array.getColor(R.styleable.ThemeExtension_colorPrimaryDark, Color.TRANSPARENT);
		array.recycle();

		array = context.obtainStyledAttributes(attrs, R.styleable.EditText, defStyleAttr, defStyleRes);
		mStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
		mStrokeWidth = array.getDimensionPixelSize(R.styleable.EditText_backgroundStrokeWidth, mStrokeWidth);
		mAnimateFromCenter = array.getBoolean(R.styleable.EditText_animateFromCenter, mAnimateFromCenter);
		array.recycle();

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(colorPrimary);

		mFocusedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFocusedPaint.setStyle(Style.FILL);
		mFocusedPaint.setColor(colorPrimaryDark);

		mBounds = new Rect();
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		mBounds.set(bounds);
		if (mStartAnimation) {
			startAnimation();
		}
	}

	@Override
	public int getIntrinsicHeight() {
		return mBounds.isEmpty() ? -1 : (mBounds.bottom - mBounds.top);
	}

	@Override
	public int getIntrinsicWidth() {
		return mBounds.isEmpty() ? -1 : (mBounds.right - mBounds.left);
	}

	@Override
	public void draw(Canvas canvas) {
		final Rect bounds = getBounds();
		final int saveCount = canvas.save();
		canvas.translate(bounds.left, bounds.top);
		canvas.drawRect(getBounds().left, getBounds().bottom - mStrokeWidth, getBounds().right, getBounds().bottom, mPaint);

		if (mAnimateFromCenter) {
			int w = getIntrinsicWidth();
			float left = ((float) (w - mWidth)) / 2.0f;
			float right = left + mWidth;
			canvas.drawRect(left, getBounds().bottom - mStrokeWidth, right, getBounds().bottom, mFocusedPaint);
		} else {
			canvas.drawRect(getBounds().left, getBounds().bottom - mStrokeWidth, getBounds().left + mWidth, getBounds().bottom, mFocusedPaint);
		}
		canvas.restoreToCount(saveCount);
	}

	@Override
	public void setAlpha(int alpha) {
		// nothing
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// nothing
	}

	@Override
	public int getOpacity() {
		return PixelFormat.UNKNOWN;
	}

	public void setWidth(int width) {
		mWidth = width;
		invalidateSelf();
	}

	@Override
	public void startAnimation() {
		if (mAnimator == null) {
			mAnimator = ObjectAnimator.ofInt(this, "width", 0, getIntrinsicWidth());
			mAnimator.setDuration(mAnimationDuration);
			mAnimator.setInterpolator(new DecelerateInterpolator());
			mAnimator.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mStartAnimation = false;
				}
			});
		}
		mAnimator.start();
	}

	@Override
	public void reverseAnimation() {
		mAnimator.reverse();
	}

	@Override
	public void onViewAttachedToWindow(View view) {
		// nothing
	}

	@Override
	public void onViewDetachedToWindow(View view) {
		if (mAnimator != null) {
			mAnimator.cancel();
		}
	}

	@Override
	public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		mFocused = focused;
		if (mBounds.isEmpty()) {
			mStartAnimation = true;
		} else {
			animateFocus();
		}
	}

	private void animateFocus() {
		if (mFocused) {
			startAnimation();
		} else {
			reverseAnimation();
		}
	}
}
