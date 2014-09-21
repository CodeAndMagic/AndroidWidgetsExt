package ext.extensions.forms;

import android.view.View;
import android.widget.TextView;

/**
 * Created by evelina on 21/09/14.
 */
public class ViewErrorHandler implements ErrorHandler {

	public final TextView errorView;

	public ViewErrorHandler(TextView errorView) {
		this.errorView = errorView;
	}

	@Override
	public void onError(ValidationFailure[] failures) {
		errorView.setText(failures[0].getMessage(errorView.getContext()));
		errorView.setVisibility(View.VISIBLE);
	}
}
