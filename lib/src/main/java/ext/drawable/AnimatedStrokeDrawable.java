package ext.drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
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

import ext.R;

/**
 * Created by evelina on 14/09/14.
 */
public class AnimatedStrokeDrawable extends AnimatedDrawable {

	protected Rect mBounds;
	protected Paint mPaint;
	protected Paint mFocusedPaint;
	protected ObjectAnimator mAnimator;
	protected boolean mAnimateFromCenter;
	protected int mStrokeWidth;
	protected int mWidth;
	protected boolean mFocused;
	protected boolean mStartAfterBoundsSet;

	public AnimatedStrokeDrawable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);

		TypedArray array = context.obtainStyledAttributes(R.styleable.ThemeExtension);
		int colorPrimary = array.getColor(R.styleable.ThemeExtension_colorPrimary, Color.TRANSPARENT);
		int colorPrimaryDark = array.getColor(R.styleable.ThemeExtension_colorPrimaryDark, Color.TRANSPARENT);
		array.recycle();

		array = context.obtainStyledAttributes(attrs, R.styleable.AnimatedStrokeDrawable, defStyleAttr, defStyleRes);
		mStrokeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
		mStrokeWidth = array.getDimensionPixelSize(R.styleable.AnimatedStrokeDrawable_strokeWidth, mStrokeWidth);
		mAnimateFromCenter = array.getBoolean(R.styleable.AnimatedStrokeDrawable_animateFromCenter, mAnimateFromCenter);
		array.recycle();

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(colorPrimaryDark);

		mFocusedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mFocusedPaint.setStyle(Style.FILL);
		mFocusedPaint.setColor(colorPrimary);

		mBounds = new Rect();
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		mBounds.set(bounds);
		if (mStartAfterBoundsSet) {
			animateFocus();
			mStartAfterBoundsSet = false;
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
		if (mBackground == null) {
			canvas.drawRect(bounds.left, bounds.bottom - mStrokeWidth, bounds.right, bounds.bottom, mPaint);
		} else {
			Bitmap bitmap = Bitmap.createBitmap(mBounds.width(), mStrokeWidth, Config.ARGB_8888);
			Canvas bgCanvas = new Canvas(bitmap);
			mBackground.draw(bgCanvas);
			canvas.drawBitmap(bitmap, bounds.left, bounds.bottom - mStrokeWidth, mPaint);
		}

		if (mAnimateFromCenter) {
			int w = getIntrinsicWidth();
			float left = ((float) (w - mWidth)) / 2.0f;
			float right = left + mWidth;
			canvas.drawRect(left, bounds.bottom - mStrokeWidth, right, bounds.bottom, mFocusedPaint);
		} else {
			canvas.drawRect(bounds.left, bounds.bottom - mStrokeWidth, bounds.left + mWidth, bounds.bottom, mFocusedPaint);
		}
		canvas.restoreToCount(saveCount);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.UNKNOWN;
	}

	public void setWidth(int width) {
		mWidth = width;
		invalidateSelf();
	}

	private void startAnimation() {
		if (mAnimator == null) {
			mAnimator = ObjectAnimator.ofInt(this, "width", 0, getIntrinsicWidth());
			mAnimator.setDuration(mAnimationDuration);
			mAnimator.setInterpolator(mInterpolator);
		}
		mAnimator.start();
	}

	private void reverseAnimation() {
		if (mAnimator != null) {
			mAnimator.reverse();
		}
	}

	private void stopAnimation() {
		if (mAnimator != null) {
			mAnimator.removeAllListeners();
			mAnimator.removeAllUpdateListeners();
			mAnimator.cancel();
		}
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
	public void onViewAttachedToWindow(View view) {
		// nothing
	}

	@Override
	public void onViewDetachedToWindow(View view) {
		stopAnimation();
	}

	@Override
	public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		mFocused = focused;
		if (mBounds.isEmpty()) {
			mStartAfterBoundsSet = true;
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
