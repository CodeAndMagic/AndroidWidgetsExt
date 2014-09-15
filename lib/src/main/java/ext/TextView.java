package ext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import ext.extensions.ExtensionManager;
import ext.extensions.ViewExtension;

/**
 * Created by evelina on 26/08/14.
 */
public class TextView extends android.widget.TextView {

	protected List<ViewExtension<TextView>> mExtensions = new ArrayList<>();

	public TextView(Context context) {
		super(context);
		init(context, null, 0);
	}

	public TextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public TextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, 0);
	}

	private void init(Context context, AttributeSet attrs, int defStyleRes) {
		if (isInEditMode()) {
			return;
		}
		mExtensions = ExtensionManager.getExtensions(context, attrs, android.R.attr.textViewStyle, defStyleRes);
		for (ViewExtension<TextView> extension : mExtensions) {
			extension.init(this, attrs, android.R.attr.textViewStyle, defStyleRes);
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		for (ViewExtension<TextView> extension : mExtensions) {
			extension.onFinishInflate();
		}
	}

	@Override
	protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		if (mExtensions == null) {
			return;
		}
		for (ViewExtension<TextView> extension : mExtensions) {
			extension.onTextChanged(text, start, lengthBefore, lengthAfter);
		}
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		for (ViewExtension<TextView> extension : mExtensions) {
			extension.onFocusChanged(focused, direction, previouslyFocusedRect);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		for (ViewExtension<TextView> extension : mExtensions) {
			extension.onAttachedToWindow();
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		for (ViewExtension<TextView> extension : mExtensions) {
			extension.onDetachedFromWindow();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (ViewExtension<TextView> extension : mExtensions) {
			extension.onDraw(canvas);
		}
	}

	@Override
	public boolean requestRectangleOnScreen(Rect rectangle, boolean immediate) {
		return false;
	}
}
