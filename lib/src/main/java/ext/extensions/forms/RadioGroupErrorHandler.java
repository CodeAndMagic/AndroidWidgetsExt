package ext.extensions.forms;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import ext.R;

/**
 * Created by evelina on 21/09/14.
 */
public class RadioGroupErrorHandler implements ErrorHandler {

	public final RadioGroup view;

	public RadioGroupErrorHandler(RadioGroup view) {
		this.view = view;
	}

	@Override
	public void onError(ValidationFailure[] failures) {
		RadioButton radio = (RadioButton) view.findViewById(view.getCheckedRadioButtonId());
		if (radio != null) {
			radio.setTextColor(view.getContext().getResources().getColor(R.color.error));
		}
	}
}
