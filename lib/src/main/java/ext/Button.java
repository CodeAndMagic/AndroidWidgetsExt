package ext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import ext.extensions.ExtensionManager;
import ext.extensions.ViewExtension;

/**
 * Created by evelina on 27/08/14.
 */
public class Button extends android.widget.Button {

	protected List<ViewExtension<Button>> mExtensions = new ArrayList<>();

	public Button(Context context) {
		super(context);
		init(context, null, 0);
	}

	public Button(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public Button(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, 0);
	}

	private void init(Context context, AttributeSet attrs, int defStyleRes) {
		if (isInEditMode()) {
			return;
		}
		mExtensions = ExtensionManager.getExtensions(context, attrs, android.R.attr.buttonStyle, defStyleRes);
		for (ViewExtension<Button> extension : mExtensions) {
			extension.init(this, attrs, android.R.attr.buttonStyle, defStyleRes);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		for (ViewExtension<Button> extension : mExtensions) {
			extension.onFinishInflate();
		}
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		if (mExtensions == null) {
			return;
		}
		for (ViewExtension<Button> extension : mExtensions) {
			extension.onTextChanged(text, start, lengthBefore, lengthAfter);
		}
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		for (ViewExtension<Button> extension : mExtensions) {
			extension.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		for (ViewExtension<Button> extension : mExtensions) {
			extension.onDetachedFromWindow();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (ViewExtension<Button> extension : mExtensions) {
			extension.onDraw(canvas);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		for (ViewExtension<Button> extension : mExtensions) {
			extension.onTouchEvent(event);
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void setActivated(boolean activated) {
		super.setActivated(activated);
		for (ViewExtension<Button> extension : mExtensions) {
			extension.setActivated(activated);
		}
	}

}
