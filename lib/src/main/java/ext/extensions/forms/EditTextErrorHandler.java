package ext.extensions.forms;

import android.widget.EditText;

/**
 * Created by evelina on 21/09/14.
 */
public class EditTextErrorHandler implements ErrorHandler {

	public final EditText view;

	public EditTextErrorHandler(EditText view) {
		this.view = view;
	}

	@Override
	public void onError(ValidationFailure[] failures) {
		view.setError(failures[0].getMessage(view.getContext()));
	}
}
