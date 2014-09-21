package ext.extensions.forms;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by evelina on 21/09/14.
 */
public interface ViewExtractor<V extends View> {
	String get(V view);

	void set(V view, String value);

	ViewExtractor<TextView> TEXT_VIEW_EXTRACTOR = new ViewExtractor<TextView>() {
		@Override
		public String get(TextView view) {
			return view.getText().toString();
		}

		@Override
		public void set(TextView view, String value) {
			view.setText(value);
		}
	};

	ViewExtractor<CompoundButton> COMPOUND_BUTTON_EXTRACTOR = new ViewExtractor<CompoundButton>() {
		@Override
		public String get(CompoundButton view) {
			return view.isChecked() ? "true" : "false";
		}

		@Override
		public void set(CompoundButton view, String value) {
			view.setChecked("true".equals(value));
		}
	};

	ViewExtractor<RadioGroup> RADIO_GROUP_EXTRACTOR = new ViewExtractor<RadioGroup>() {
		@Override
		public String get(RadioGroup view) {
			return String.valueOf(view.getCheckedRadioButtonId());
		}

		@Override
		public void set(RadioGroup view, String value) {
			view.check(Integer.parseInt(value));
		}
	};
}
