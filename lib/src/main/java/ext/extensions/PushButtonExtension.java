package ext.extensions;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import ext.R;

/**
 * Created by evelina on 11/09/14.
 */
public class PushButtonExtension<T extends View> extends ThemeExtension<T> {

	protected int mPushDepth;
	protected int mPushCornerRadius;

	protected int mPaddingTop;
	protected int mPaddingLeft;
	protected int mPaddingBottom;
	protected int mPaddingRight;

	protected LayerDrawable mPressedDrawable;
	protected LayerDrawable mUnpressedDrawable;

	@Override
	public void init(T view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super.init(view, attrs, defStyleAttr, defStyleRes);

		Context context = view.getContext();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PushButtonExtension, defStyleAttr, defStyleRes);
		mPushDepth = array.getDimensionPixelSize(R.styleable.PushButtonExtension_pushDepth, mPushDepth);
		mPushCornerRadius = array.getDimensionPixelSize(R.styleable.PushButtonExtension_pushCornerRadius, mPushCornerRadius);
		array.recycle();

		mPaddingTop = mView.getPaddingTop();
		mPaddingLeft = mView.getPaddingLeft();
		mPaddingBottom = mView.getPaddingBottom();
		mPaddingRight = mView.getPaddingRight();

		mPressedDrawable = createDrawable(Color.TRANSPARENT, mColorPrimary);
		mUnpressedDrawable = createDrawable(mColorPrimary, mColorPrimaryDark);

		updateBackground(mUnpressedDrawable);
		mView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				updateBackground(mPressedDrawable);
				mView.setPadding(mPaddingLeft, mPaddingTop + mPushDepth, mPaddingRight, mPaddingBottom);
				break;

			case MotionEvent.ACTION_MOVE:
				Rect r = new Rect();
				mView.getLocalVisibleRect(r);
				if (!r.contains((int) event.getX(), (int) event.getY() + 3 * mPushDepth) &&
					!r.contains((int) event.getX(), (int) event.getY() - 3 * mPushDepth)) {
					updateBackground(mUnpressedDrawable);
					mView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
				}
				break;

			case MotionEvent.ACTION_OUTSIDE:
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				updateBackground(mUnpressedDrawable);
				mView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
				break;
		}
		return false;
	}

	private void updateBackground(Drawable background) {
		if (background == null) {
			return;
		}
		mView.setBackground(background);
	}

	private LayerDrawable createDrawable(int topColor, int bottomColor) {
		int r = mPushCornerRadius;
		float[] outerRadius = new float[]{r, r, r, r, r, r, r, r};

		RoundRectShape topRoundRect = new RoundRectShape(outerRadius, null, null);
		ShapeDrawable topShapeDrawable = new ShapeDrawable(topRoundRect);
		topShapeDrawable.getPaint().setColor(topColor);

		RoundRectShape roundRectShape = new RoundRectShape(outerRadius, null, null);
		ShapeDrawable bottomShapeDrawable = new ShapeDrawable(roundRectShape);
		bottomShapeDrawable.getPaint().setColor(bottomColor);

		Drawable[] drawArray = {bottomShapeDrawable, topShapeDrawable};
		LayerDrawable layerDrawable = new LayerDrawable(drawArray);

		if (topColor != Color.TRANSPARENT) {
			//unpressed drawable
			layerDrawable.setLayerInset(0, 0, 0, 0, 0);  /*index, left, top, right, bottom*/
		} else {
			//pressed drawable
			layerDrawable.setLayerInset(0, 0, mPushDepth, 0, 0);  /*index, left, top, right, bottom*/
		}
		layerDrawable.setLayerInset(1, 0, 0, 0, mPushDepth);  /*index, left, top, right, bottom*/

		return layerDrawable;
	}

}
