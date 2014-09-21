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

	protected Form(Input<?>[] inputs, Map<String, ViewPair<?>> views, Map<String, ErrorHandler[]> errorHandlers) {
		this.inputs = inputs;
		this.views = views;
		this.errorHandlers = errorHandlers;
	}

	public T bind() throws ValidationException {
		Map<String, String> data = new HashMap<>();
		for (Entry<String, ViewPair<?>> view : views.entrySet()) {
			data.put(view.getKey(), view.getValue().get());
		}
		try {
			return bind(data);
		} catch (ValidationException e) {
			onValidationError(e);
			throw e;
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

	public abstract T bind(Map<String, String> data) throws ValidationException;
}

class MapForm extends Form<Map<String, Object>> {

	MapForm(Input<?>[] inputs, Map<String, ViewPair<?>> views, Map<String, ErrorHandler[]> errorHandlers) {
		super(inputs, views, errorHandlers);
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
		else throw new ValidationException(failures.toArray(new ValidationFailure[0]));
	}
}

class ObjectForm<T> extends Form<T> {
	public final Mapping<Map<String, Object>, T> mapping;
	private final MapForm mMapForm;

	ObjectForm(Mapping<Map<String, Object>, T> mapping, Input<?>[] inputs, Map<String, ViewPair<?>> views, Map<String, ErrorHandler[]> errorHandlers) {
		super(inputs, views, errorHandlers);
		this.mapping = mapping;
		mMapForm = new MapForm(inputs, views, errorHandlers);
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
}
