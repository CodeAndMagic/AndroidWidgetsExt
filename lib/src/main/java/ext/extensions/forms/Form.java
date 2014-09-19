package ext.extensions.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by evelina on 17/09/14.
 */
public abstract class Form<T> {
	public final Input<?>[] inputs;

	protected Form(Input<?>... inputs) {
		this.inputs = inputs;
	}

	public abstract T bind(Map<String,String> data) throws ValidationException;

	public static <Q> Form<Q> single(final Input<Q> input) {
		return new SingleForm<>(input);
	}

	public static Form<Map<String,Object>> multiple(final Input<?>... inputs) {
		return new MapForm(inputs);
	}

	public static <Q> Form<Q> mapping(Mapping<Map<String,Object>, Q> mapping, Input<?>... inputs) {
		return new ObjectForm(mapping, inputs);
	}
}

class SingleForm<Q> extends Form<Q>{
	SingleForm(Input<Q> input) {
		super(input);
	}

	@Override
	public Q bind(Map<String, String> data) throws ValidationException {
		return (Q) data.get(inputs[0].key);
	}
}

class MapForm extends Form<Map<String,Object>>{
	MapForm(Input<?>... inputs) {
		super(inputs);
	}

	@Override
	public Map<String, Object> bind(Map<String, String> data) throws ValidationException {
		List<ValidationFailure> failures = new ArrayList<>();
		Map<String,Object> values = new HashMap<>();
		for (Input input : inputs) {
			try {
				values.put(input.key,input.bind(data));
			} catch (ValidationException e) {
				failures.addAll(Arrays.asList(e.failures));
			}
		}
		if (failures.isEmpty()) return values;
		else throw new ValidationException(failures.toArray(new ValidationFailure[0]));
	}
}

class ObjectForm<Q> extends Form<Q> {
	public final Mapping<Map<String,Object>, Q> mapping;
	private final MapForm mMapForm;

	ObjectForm(Mapping<Map<String,Object>, Q> mapping, Input<?>... inputs) {
		super(inputs);
		this.mapping = mapping;
		mMapForm = new MapForm(inputs);
	}

	@Override
	public Q bind(Map<String,String> data) throws ValidationException {
		return mapping.bind(mMapForm.bind(data));
	}
}
