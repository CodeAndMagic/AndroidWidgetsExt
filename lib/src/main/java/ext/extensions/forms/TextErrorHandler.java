package ext.extensions.forms;

import android.widget.TextView;

import ext.R;

/**
 * Created by evelina on 21/09/14.
 */
public class TextErrorHandler implements ErrorHandler {

	public final TextView view;
	private int mColor;

	public TextErrorHandler(TextView textView) {
		this.view = textView;
	}

	@Override
	public void onError(ValidationFailure[] failures) {
		mColor = view.getCurrentTextColor();
		view.setTextColor(view.getContext().getResources().getColor(R.color.error));
	}

	@Override
	public void reset() {
		view.setTextColor(mColor);
	}
}
