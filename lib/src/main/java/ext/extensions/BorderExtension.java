package ext.extensions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import ext.R;

/**
 * Created by evelina on 11/09/14.
 */
public class BorderExtension<T extends TextView> extends ThemeExtension<T> implements OnGlobalLayoutListener {

	protected static final int BORDER_TOP = 0x01;
	protected static final int BORDER_LEFT = 0x02;
	protected static final int BORDER_RIGHT = 0x04;
	protected static final int BORDER_BOTTOM = 0x08;

	protected int mBorderWidth;
	protected int mBorderPosition;
	protected Paint mBorderPaint;
	protected boolean mBorderAnimated;
	private int mAnimWidth;
	private int mAnimHeight;
	private int mAnimDuration;

	private AnimatorSet mAnimation;

	@Override
	public void init(T view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super.init(view, attrs, defStyleAttr, defStyleRes);

		Context context = view.getContext();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BorderExtension, defStyleAttr, defStyleRes);
		int borderColor = array.getColor(R.styleable.BorderExtension_borderColor, 0);
		mBorderWidth = array.getDimensionPixelSize(R.styleable.BorderExtension_borderWidth, 0);
		mBorderPosition = array.getInt(R.styleable.BorderExtension_borderPosition, 0);
		mBorderAnimated = array.getBoolean(R.styleable.BorderExtension_borderAnimated, false);
		mAnimDuration = context.getResources().getInteger(android.R.integer.config_longAnimTime);
		array.recycle();

		if (borderColor == 0) {
			borderColor = mColorPrimary;
		}

		mBorderPaint = new Paint();
		mBorderPaint.setAntiAlias(true);
		mBorderPaint.setStyle(Style.FILL);
		mBorderPaint.setColor(borderColor);
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mBorderAnimated) {
			mView.getViewTreeObserver().addOnGlobalLayoutListener(this);
		}
	}

	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mBorderAnimated) {
			mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
		}
	}

	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mBorderPosition == 0) {
			return;
		}

		int w = mBorderAnimated ? mAnimWidth : mView.getWidth();
		int h = mView.getHeight();


		if ((mBorderPosition & BORDER_TOP) != 0) {
			canvas.drawRect(0, 0, w, mBorderWidth, mBorderPaint);
		}
		if ((mBorderPosition & BORDER_LEFT) != 0) {
			canvas.drawRect(0, 0, mBorderWidth, h, mBorderPaint);
		}
		if ((mBorderPosition & BORDER_RIGHT) != 0) {
			canvas.drawRect(w - mBorderWidth, 0, w, h, mBorderPaint);
		}
		if ((mBorderPosition & BORDER_BOTTOM) != 0) {
			canvas.drawRect(0, h - mBorderWidth, w, h, mBorderPaint);
		}
	}

	protected void startAnimation() {
		if (mAnimation == null) {
			ObjectAnimator widthAnimator = ObjectAnimator.ofInt(this, "animWidth", 0, mView.getWidth());
			ObjectAnimator heightAnimator = ObjectAnimator.ofInt(this, "animHeight", 0, mView.getHeight());
			mAnimation = new AnimatorSet();
			mAnimation.play(widthAnimator).with(heightAnimator);
			mAnimation.setInterpolator(new DecelerateInterpolator());
			mAnimation.setDuration(mAnimDuration);
			mAnimation.addListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mView.getViewTreeObserver().removeOnGlobalLayoutListener(BorderExtension.this);
				}
			});
			mAnimation.start();
		}
	}

	public void setAnimWidth(int animWidth) {
		mAnimWidth = animWidth;
		mView.invalidate();
	}

	public void setAnimHeight(int animHeight) {
		mAnimHeight = animHeight;
		mView.invalidate();
	}

	@Override
	public void onGlobalLayout() {
		startAnimation();
	}
}
