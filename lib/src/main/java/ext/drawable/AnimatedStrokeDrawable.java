package ext.drawable;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;

import ext.R;

/**
 * Created by evelina on 14/09/14.
 */
public class AnimatedStrokeDrawable extends Drawable {

	private Rect mBounds;
	private Paint mPaint;
	private int mStrokeWidth;
	private int mAnimWidth;
	private int mAnimDuration;

	public AnimatedStrokeDrawable(Context context) {
		TypedArray array = context.obtainStyledAttributes(R.styleable.ThemeExtension);
		int colorPrimary = array.getColor(R.styleable.ThemeExtension_colorPrimary, 0);
		array.recycle();

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(mStrokeWidth);
		mPaint.setColor(colorPrimary);

		mBounds = new Rect();
		mAnimDuration = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
	}


	@Override
	protected void onBoundsChange(Rect bounds) {
		mBounds.set(bounds);
		startAnimation();
	}

	@Override
	public int getIntrinsicHeight() {
		if (mBounds.isEmpty()) {
			return -1;
		} else {
			return (mBounds.bottom - mBounds.top);
		}
	}

	@Override
	public int getIntrinsicWidth() {
		if (mBounds.isEmpty()) {
			return -1;
		} else {
			return (mBounds.right - mBounds.left);
		}
	}

	@Override
	public void draw(Canvas canvas) {
		final Rect bounds = getBounds();
		final int saveCount = canvas.save();
		canvas.translate(bounds.left, bounds.top);
		canvas.drawRect(getBounds().left, getBounds().bottom - mStrokeWidth, getBounds().left + mAnimWidth, getBounds().bottom, mPaint);
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

	public void setStrokeWidth(int strokeWidth) {
		mStrokeWidth = strokeWidth;
	}

	public void startAnimation() {
		ObjectAnimator animator = ObjectAnimator.ofInt(this, "width", 0, getIntrinsicWidth());
		animator.setDuration(mAnimDuration);
		animator.setInterpolator(new DecelerateInterpolator());
		animator.start();
	}

	public void setAnimWidth(int animWidth) {
		mAnimWidth = animWidth;
		invalidateSelf();
	}
}
