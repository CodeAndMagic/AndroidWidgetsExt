package ext.extensions.forms;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import static android.text.TextUtils.*;

/**
 * Created by evelina on 17/09/14.
 */
public abstract class Form<T> {

	public final Input<?>[] inputs;
	public final Map<String, ViewPair<?>> views;
	public final Map<String, ErrorHandler[]> errorHandlers;
	public final boolean onTheFlyValidation;

	protected Form(Input<?>[] inputs,
	               Map<String, ViewPair<?>> views,
	               Map<String, ErrorHandler[]> errorHandlers,
	               boolean onTheFlyValidation) {

		this.inputs = inputs;
		this.views = views;
		this.errorHandlers = errorHandlers;
		this.onTheFlyValidation = onTheFlyValidation;
		if(onTheFlyValidation){
			addOnTheFlyValidationListeners();
		}
	}


	private void onValidationError(ValidationException e) {
		Map<String, List<ValidationFailure>> failureMap = new HashMap<>();
		for (ValidationFailure failure : e.failures) {
			if (!failureMap.containsKey(failure.key)) {
				failureMap.put(failure.key, new ArrayList<ValidationFailure>());
			}
			failureMap.get(failure.key).add(failure);
		}

		for (Entry<String, List<ValidationFailure>> entry : failureMap.entrySet()) {
			String key = entry.getKey();
			if (!isEmpty(key)) {
				ErrorHandler[] handlers = errorHandlers.get(key);
				if (handlers != null) {
					for (ErrorHandler h : handlers) {
						h.onError(entry.getValue().toArray(new ValidationFailure[entry.getValue().size()]));
					}
				}
			}
		}
	}

	private void resetValidationErrors() {
		if (!errorHandlers.isEmpty()) {
			for (ErrorHandler[] handlers : errorHandlers.values()) {
				for (ErrorHandler handler : handlers) {
					handler.reset();
				}
			}
		}
	}

	private void addOnTheFlyValidationListeners() {
		for (final Entry<String, ViewPair<?>> entry : views.entrySet()) {
			entry.getValue().setOnDataChangeListener(new OnDataChangeListener() {
				@Override
				public void onDataChanged(String oldValue, String newValue) {
					Form.this.onDataChanged(entry.getKey(), oldValue, newValue);
				}
			});
		}
	}

	protected void onDataChanged(final String key, final String oldValue, final String newValue) {
		Map<String, String> partialData = extractData();
		List<ValidationFailure> failures = new ArrayList<>();
		for (Input input : inputs) {
			try {
				input.onDataChanged(key, oldValue, newValue, partialData);
			} catch (ValidationException e) {
				failures.addAll(Arrays.asList(e.failures));
			}
		}
		if(!failures.isEmpty()) {
			onValidationError(new ValidationException(failures));
		}
	}

	private Map<String, String> extractData() {
		Map<String, String> data = new HashMap<>();
		for (Entry<String, ViewPair<?>> view : views.entrySet()) {
			data.put(view.getKey(), view.getValue().get());
		}
		return data;
	}

	public T bind() throws ValidationException {
		Map<String, String> data = extractData();
		resetValidationErrors();

		try {
			return bind(data);
		} catch (ValidationException e) {
			onValidationError(e);
			throw e;
		}
	}

	public abstract T bind(Map<String, String> data) throws ValidationException;
}

class MapForm extends Form<Map<String, Object>> {

	MapForm(Input<?>[] inputs,
	        Map<String, ViewPair<?>> views,
	        Map<String, ErrorHandler[]> errorHandlers,
	        boolean onTheFlyValidation) {

		super(inputs, views, errorHandlers, onTheFlyValidation);
	}

	@Override
	public Map<String, Object> bind(Map<String, String> data) throws ValidationException {
		List<ValidationFailure> failures = new ArrayList<>();
		Map<String, Object> values = new HashMap<>();
		for (Input input : inputs) {
			try {
				values.put(input.key, input.bind(data));
			} catch (ValidationException e) {
				failures.addAll(Arrays.asList(e.failures));
			}
		}
		if (failures.isEmpty()) return values;
		else throw new ValidationException(failures);
	}
}

class ObjectForm<T> extends Form<T> {
	public final Mapping<Map<String, Object>, T> mapping;
	private final MapForm mMapForm;

	ObjectForm(Mapping<Map<String, Object>, T> mapping,
	           Input<?>[] inputs,
	           Map<String, ViewPair<?>> views,
	           Map<String, ErrorHandler[]> errorHandlers,
	           boolean onTheFlyValidation) {

		super(inputs, views, errorHandlers, onTheFlyValidation);
		this.mapping = mapping;
		mMapForm = new MapForm(inputs, views, errorHandlers, onTheFlyValidation);
	}

	@Override
	public T bind(Map<String, String> data) throws ValidationException {
		return mapping.bind(mMapForm.bind(data));
	}
}

class ViewPair<V extends View> {
	public final V view;
	public final ViewExtractor<? super V> extractor;

	ViewPair(V view, ViewExtractor<? super V> extractor) {
		this.view = view;
		this.extractor = extractor;
	}

	public String get() {
		return extractor.get(view);
	}

	public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
		extractor.addChangeListener(view, onDataChangeListener);
	}
}
