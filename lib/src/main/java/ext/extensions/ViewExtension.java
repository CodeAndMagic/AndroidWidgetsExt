package ext.extensions;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by evelina on 26/08/14.
 */
public abstract class ViewExtension<V extends View> {

	protected V mView;
	protected AttributeSet mAttrs;
	protected int mDefStyleAttr;
	protected int mDefStyleRes;

	public void init(V view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		mView = view;
		mAttrs = attrs;
		mDefStyleAttr = defStyleAttr;
		mDefStyleRes = defStyleRes;
	}

	public abstract void onFinishInflate();

	public abstract void setActivated(boolean activated);

	public abstract void onAttachedToWindow();

	public abstract void onDetachedFromWindow();

	public abstract void onDraw(Canvas canvas);

	public abstract void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect);

	public abstract boolean onTouchEvent(MotionEvent event);

	public abstract void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);

}
