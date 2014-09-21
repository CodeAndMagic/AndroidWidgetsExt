package ext.extensions.forms;

import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by evelina on 21/09/14.
 */
public abstract class ViewExtractor<V extends View> {

	public abstract String get(V view);

	public abstract void set(V view, String value);

	public void addChangeListener(final V view, final OnDataChangeListener dataChangeListener) {
		view.setOnFocusChangeListener(new OnFocusChangeListener() {
			String beforeText = null;

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					beforeText = get(view);
				} else {
					//Log.d("form", "View "+v+" has changed value: "+get(view));
					dataChangeListener.onDataChanged(beforeText, get(view));
				}
			}
		});
	}

	public static final ViewExtractor<TextView> TEXT_VIEW_EXTRACTOR = new ViewExtractor<TextView>() {
		@Override
		public String get(TextView view) {
			return view.getText().toString();
		}

		@Override
		public void set(TextView view, String value) {
			view.setText(value);
		}
	};

	public static final ViewExtractor<CompoundButton> COMPOUND_BUTTON_EXTRACTOR = new ViewExtractor<CompoundButton>() {
		@Override
		public String get(CompoundButton view) {
			return view.isChecked() ? "true" : "false";
		}

		@Override
		public void set(CompoundButton view, String value) {
			view.setChecked("true".equals(value));
		}
	};

	public static final ViewExtractor<RadioGroup> RADIO_GROUP_EXTRACTOR = new ViewExtractor<RadioGroup>() {

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
