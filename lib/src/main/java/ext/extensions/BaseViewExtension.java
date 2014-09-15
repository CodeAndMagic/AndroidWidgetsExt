package ext.extensions;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by evelina on 26/08/14.
 */
public class BaseViewExtension<V extends View> extends ViewExtension<V> {

	@Override
	public void onFinishInflate() {
		// not implemented
	}

	@Override
    public void setActivated(boolean activated) {
		// not implemented
    }

	@Override
	public void onAttachedToWindow() {
		// not implemented
	}

	@Override
	public void onDetachedFromWindow() {
		// not implemented
	}

	@Override
	public void onDraw(Canvas canvas) {
		// not implemented
	}

	@Override
	public void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		// not implemented
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// not implemented
		return false;
	}

	@Override
	public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		// not implemented
	}
}
