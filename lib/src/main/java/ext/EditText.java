package ext;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

import ext.extensions.ExtensionManager;
import ext.extensions.TextExtension;

/**
 * Created by evelina on 27/08/14.
 */
public class EditText extends android.widget.EditText {

	protected List<TextExtension<EditText>> mExtensions = new ArrayList<>();

	public EditText(Context context) {
		super(context);
		init(context, null, 0);
	}

	public EditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public EditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs, defStyleAttr);
	}

	private void init(Context context, AttributeSet attrs, int defStyleRes) {
		if (isInEditMode()) {
			return;
		}
		mExtensions = ExtensionManager.getExtensions(context, attrs, android.R.attr.editTextStyle, defStyleRes);
		for (TextExtension<EditText> extension : mExtensions) {
			extension.init(this, attrs, android.R.attr.editTextStyle, defStyleRes);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		for (TextExtension<EditText> extension : mExtensions) {
			extension.onAttachedToWindow();
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		for (TextExtension<EditText> extension : mExtensions) {
			extension.onDetachedFromWindow();
		}
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		for (TextExtension<EditText> extension : mExtensions) {
			extension.onFinishInflate();
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (TextExtension<EditText> extension : mExtensions) {
			extension.onDraw(canvas);
		}
	}
}
