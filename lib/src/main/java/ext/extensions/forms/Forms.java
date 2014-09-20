package ext.extensions.forms;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ext.R;

/**
 * Created by evelina on 20/09/14.
 */
public class Forms {

	public static <T, V extends View> Form<T> single(final Input<T> input, ViewPair<V> view) {
		return new SingleForm<>(input, view);
	}

	public static <T> Form<T> single(final Input<T> input, TextView view) {
		return new SingleForm<>(input, new ViewPair<>(view, TEXT_VIEW_EXTRACTOR, TEXT_ERROR_HANDLER));
	}

	public static <T> Form<T> single(final Input<T> input, CompoundButton view) {
		return new SingleForm<>(input, new ViewPair<>(view, COMPOUND_BUTTON_EXTRACTOR, TEXT_ERROR_HANDLER));
	}

	public static <T> Form<T> single(final Input<T> input, RadioGroup view) {
		return new SingleForm<>(input, new ViewPair<>(view, RADIO_GROUP_EXTRACTOR, RADIO_GROUP_ERROR_HANDLER));
	}

	public static Form<Map<String, Object>> multiple(final Input<?>[] inputs, Map<String, ViewPair<?>> views) {
		return new MapForm(inputs, views);
	}

	public static <T> Form<T> mapping(Mapping<Map<String, Object>, T> mapping, Input<?>[] inputs, Map<String, ViewPair<?>> views) {
		return new ObjectForm(mapping, inputs, views);
	}

	public static class FormBuilder {

		private Set<Input<?>> mInputs = new HashSet<>();
		private Map<String, ViewPair<?>> mViewMap = new HashMap<>();

		private Input<?>[] toArray() {
			return mInputs.toArray(new Input<?>[mInputs.size()]);
		}

		public synchronized FormBuilder addInput(Input<?> input) {
			mInputs.add(input);
			return this;
		}

		public synchronized <V extends View> FormBuilder addView(String key, V view, ViewExtractor<? super V> extractor, ViewErrorHandler<? super V> errorHandler) {
			mViewMap.put(key, new ViewPair<V>(view, extractor, errorHandler));
			return this;
		}

		public synchronized FormBuilder addView(String key, TextView view) {
			return addView(key, view, TEXT_VIEW_EXTRACTOR, TEXT_ERROR_HANDLER);
		}

		public synchronized FormBuilder addView(String key, EditText view) {
			return addView(key, view, TEXT_VIEW_EXTRACTOR, EDIT_TEXT_ERROR_HANDLER);
		}

		public synchronized FormBuilder addView(String key, RadioGroup view) {
			return addView(key, view, RADIO_GROUP_EXTRACTOR, RADIO_GROUP_ERROR_HANDLER);
		}

		public synchronized FormBuilder addView(String key, CompoundButton view) {
			return addView(key, view, COMPOUND_BUTTON_EXTRACTOR, TEXT_ERROR_HANDLER);
		}

		public synchronized <V extends View> FormBuilder addViewAndInput(V view, ViewExtractor<? super V> extractor, ViewErrorHandler<? super V> errorHandler, Input<?> input) {
			addView(input.key, view, extractor, errorHandler);
			return addInput(input);
		}

		public synchronized FormBuilder addViewAndInput(TextView view, Input<?> input) {
			addInput(input);
			return addView(input.key, view);
		}

		public synchronized FormBuilder addViewAndInput(EditText view, Input<?> input) {
			addInput(input);
			return addView(input.key, view);
		}

		public synchronized FormBuilder addViewAndInput(RadioGroup view, Input<?> input) {
			addInput(input);
			return addView(input.key, view);
		}

		public synchronized FormBuilder addViewAndInput(CompoundButton view, Input<?> input) {
			addInput(input);
			return addView(input.key, view);
		}

		public synchronized Form<Map<String, Object>> build() {
			return multiple(toArray(), mViewMap);
		}

		public synchronized <T> Form<T> build(Mapping<Map<String, Object>, T> mapping) {
			return mapping(mapping, toArray(), mViewMap);
		}
	}

	public static interface ViewExtractor<V extends View> {
		String get(V view);

		void set(V view, String value);
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

	public static ViewExtractor<RadioGroup> RADIO_GROUP_EXTRACTOR = new ViewExtractor<RadioGroup>() {
		@Override
		public String get(RadioGroup view) {
			return String.valueOf(view.getCheckedRadioButtonId());
		}

		@Override
		public void set(RadioGroup view, String value) {
			view.check(Integer.parseInt(value));
		}
	};

	public static ViewErrorHandler<TextView> TEXT_ERROR_HANDLER = new ViewErrorHandler<TextView>() {
		@Override
		public void onError(TextView view, ValidationFailure[] failures) {
			view.setTextColor(view.getContext().getResources().getColor(R.color.error));
		}
	};

	public static ViewErrorHandler<EditText> EDIT_TEXT_ERROR_HANDLER = new ViewErrorHandler<EditText>() {
		@Override
		public void onError(EditText view, ValidationFailure[] failures) {
			view.setError(failures[0].getMessage(view.getContext()));
		}
	};

	public static ViewErrorHandler<RadioGroup> RADIO_GROUP_ERROR_HANDLER = new ViewErrorHandler<RadioGroup>() {
		@Override
		public void onError(RadioGroup view, ValidationFailure[] failures) {
			RadioButton radio = (RadioButton) view.findViewById(view.getCheckedRadioButtonId());
			if (radio != null) {
				radio.setTextColor(view.getContext().getResources().getColor(R.color.error));
			}
		}
	};
}
