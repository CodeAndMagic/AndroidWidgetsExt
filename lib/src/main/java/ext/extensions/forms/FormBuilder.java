package ext.extensions.forms;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static ext.extensions.forms.ViewExtractor.*;

/**
 * Created by evelina on 21/09/14.
 */
public class FormBuilder {

	private Set<Input<?>> mInputs = new HashSet<>();
	private Map<String, ViewPair<?>> mViewMap = new HashMap<>();
	private Map<String, List<ErrorHandler>> mErrorHandlers = new HashMap<>();
	private boolean mOnTheFlyValidation = false;

	private Input<?>[] toArray() {
		return mInputs.toArray(new Input<?>[mInputs.size()]);
	}

	private Map<String, ErrorHandler[]> toErrorHandlerArray() {
		Map<String, ErrorHandler[]> map = new HashMap<>();
		for (Entry<String, List<ErrorHandler>> entry : mErrorHandlers.entrySet()) {
			List<ErrorHandler> v = entry.getValue();
			map.put(entry.getKey(), v.toArray(new ErrorHandler[v.size()]));
		}
		return map;
	}

	public synchronized FormBuilder addInput(Input<?> input) {
		mInputs.add(input);
		return this;
	}

	public synchronized <V extends View> FormBuilder addView(String key, V view, ViewExtractor<? super V> extractor, ErrorHandler errorHandler) {
		mViewMap.put(key, new ViewPair<V>(view, extractor));
		addErrorHandler(errorHandler, key);
		return this;
	}

	public synchronized FormBuilder addView(String key, TextView view) {
		return addView(key, view, TEXT_VIEW_EXTRACTOR, new TextErrorHandler(view));
	}

	public synchronized FormBuilder addView(String key, EditText view) {
		return addView(key, view, TEXT_VIEW_EXTRACTOR, new EditTextErrorHandler(view));
	}

	public synchronized FormBuilder addView(String key, RadioGroup view) {
		return addView(key, view, RADIO_GROUP_EXTRACTOR, new RadioGroupErrorHandler(view));
	}

	public synchronized FormBuilder addView(String key, CompoundButton view) {
		return addView(key, view, COMPOUND_BUTTON_EXTRACTOR, new TextErrorHandler(view));
	}

	public synchronized <V extends View> FormBuilder addViewAndInput(V view, ViewExtractor<? super V> extractor, ErrorHandler errorHandler, Input<?> input) {
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

	public synchronized FormBuilder addErrorHandler(ErrorHandler errorHandler, String key, String... keys) {
		if (!mErrorHandlers.containsKey(key)) {
			mErrorHandlers.put(key, new ArrayList<ErrorHandler>());
		}
		mErrorHandlers.get(key).add(errorHandler);
		if (keys != null) {
			for (String k : keys) {
				if (!mErrorHandlers.containsKey(k)) {
					mErrorHandlers.put(k, new ArrayList<ErrorHandler>());
				}
				mErrorHandlers.get(k).add(errorHandler);
			}
		}
		return this;
	}

	public synchronized FormBuilder setOnTheFlyValidation(boolean onTheFlyValidation) {
		mOnTheFlyValidation = onTheFlyValidation;
		return this;
	}

	public synchronized Form<Map<String, Object>> build() {
		return new MapForm(toArray(), mViewMap, toErrorHandlerArray(), mOnTheFlyValidation);
	}

	public synchronized <T> Form<T> build(Mapping<Map<String, Object>, T> mapping) {
		return new ObjectForm(mapping, toArray(), mViewMap, toErrorHandlerArray(), mOnTheFlyValidation);
	}
}
