package ext.extensions.forms;

import android.widget.TextView;

import ext.R;

/**
 * Created by evelina on 21/09/14.
 */
public class TextErrorHandler implements ErrorHandler {

	public final TextView view;

	public TextErrorHandler(TextView textView) {
		this.view = textView;
	}

	@Override
	public void onError(ValidationFailure[] failures) {
		view.setTextColor(view.getContext().getResources().getColor(R.color.error));
	}
}
