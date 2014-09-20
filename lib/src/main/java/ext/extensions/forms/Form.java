package ext.extensions.forms;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ext.extensions.forms.Forms.ViewExtractor;

import static android.text.TextUtils.*;

/**
 * Created by evelina on 17/09/14.
 */
public abstract class Form<T> {

	public final Input<?>[] inputs;
	public final Map<String, ViewPair<?>> views;

	protected Form(Input<?>[] inputs, Map<String, ViewPair<?>> views) {
		this.inputs = inputs;
		this.views = views;
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
				ViewPair<?> viewPair = views.get(key);
				if (viewPair != null) {
					viewPair.onError(entry.getValue());
				}
			}
		}
	}

	public abstract T bind(Map<String, String> data) throws ValidationException;
}

class SingleForm<T> extends Form<T> {

	SingleForm(final Input<T> input, final ViewPair<?> view) {
		super(new Input[]{input}, new HashMap<String, ViewPair<?>>() {{
			put(input.key, view);
		}});
	}

	@Override
	public T bind(Map<String, String> data) throws ValidationException {
		return (T) data.get(inputs[0].key);
	}
}

class MapForm extends Form<Map<String, Object>> {

	MapForm(Input<?>[] inputs, Map<String, ViewPair<?>> views) {
		super(inputs, views);
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

	ObjectForm(Mapping<Map<String, Object>, T> mapping, Input<?>[] inputs, Map<String, ViewPair<?>> views) {
		super(inputs, views);
		this.mapping = mapping;
		mMapForm = new MapForm(inputs, views);
	}

	@Override
	public T bind(Map<String, String> data) throws ValidationException {
		return mapping.bind(mMapForm.bind(data));
	}
}

class ViewPair<V extends View> {
	public final V view;
	public final ViewExtractor<? super V> extractor;
	public final ViewErrorHandler<? super V> errorHandler;

	ViewPair(V view, ViewExtractor<? super V> extractor, ViewErrorHandler<? super V> errorHandler) {
		this.view = view;
		this.extractor = extractor;
		this.errorHandler = errorHandler;
	}

	public String get() {
		return extractor.get(view);
	}

	public void onError(List<ValidationFailure> failures) {
		errorHandler.onError(view, failures.toArray(new ValidationFailure[failures.size()]));
	}
}
