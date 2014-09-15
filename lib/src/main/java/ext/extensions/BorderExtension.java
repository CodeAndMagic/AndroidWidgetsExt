package ext.extensions;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import ext.R;

/**
 * Created by evelina on 11/09/14.
 */
public class BorderExtension<V extends View> extends ThemeExtension<V> {

	protected static final int BORDER_TOP = 0x01;
	protected static final int BORDER_LEFT = 0x02;
	protected static final int BORDER_RIGHT = 0x04;
	protected static final int BORDER_BOTTOM = 0x08;

	protected int mBorderWidth;
	protected int mBorderPosition;
	protected Paint mBorderPaint;

	@Override
	public void init(V view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super.init(view, attrs, defStyleAttr, defStyleRes);

		Context context = view.getContext();
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BorderExtension, defStyleAttr, defStyleRes);
		int borderColor = array.getColor(R.styleable.BorderExtension_borderColor, Color.TRANSPARENT);
		mBorderWidth = array.getDimensionPixelSize(R.styleable.BorderExtension_borderWidth, mBorderWidth);
		mBorderPosition = array.getInt(R.styleable.BorderExtension_borderPosition, mBorderPosition);
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
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (mBorderPosition == 0) {
			return;
		}

		int w = mView.getWidth();
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
}
