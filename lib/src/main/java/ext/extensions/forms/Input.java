package ext.extensions.forms;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by evelina on 17/09/14.
 */
public abstract class Input<T> {

	public final String key;

	protected Input(String key) {
		this.key = key;
	}

	public T bind(Map<String, String> data) throws ValidationException {
		return bindSingle(data.get(key));
	}

	protected T bindSingle(String data) throws ValidationException {
		return null;
	}

	public Map<String, String> unbind(T value) {
		Map<String, String> data = new HashMap<>();
		data.put(key, unbindSingle(value));
		return data;
	}

	protected String unbindSingle(T value) {
		return null;
	}

	public <Q> Input<Q> map(final Mapping<T, Q> mapping) {
		return new Input<Q>(key) {
			@Override
			public Q bind(Map<String, String> data) throws ValidationException {
				return mapping.bind(Input.this.bind(data));
			}

			@Override
			public Map<String, String> unbind(Q value) {
				return Input.this.unbind(mapping.unbind(value));
			}

			@Override
			protected Q bindSingle(String data) throws ValidationException {
				return null;
			}

			@Override
			protected String unbindSingle(Q value) {
				return null;
			}

			@Override
			protected void onDataChanged(String key, String oldValue, String newValue, Map<String, String> partialData) throws ValidationException {
				Input.this.onDataChanged(key, oldValue, newValue, partialData);
			}
		};
	}

	public Input<T> verifying(final Condition<T> condition, final int errorMessageId) {
		return new Input<T>(key) {
			@Override
			public T bind(Map<String, String> data) throws ValidationException {
				T v = Input.this.bind(data);
				if (condition.verify(v)) return v;
				else throw new ValidationException(key, errorMessageId);
			}

			@Override
			public Map<String, String> unbind(T value) {
				return Input.this.unbind(value);
			}

			@Override
			protected T bindSingle(String data) throws ValidationException {
				return null;
			}

			@Override
			protected String unbindSingle(T value) {
				return null;
			}

			@Override
			protected void onDataChanged(String key, String oldValue, String newValue, Map<String, String> partialData) throws ValidationException {
				Input.this.onDataChanged(key, oldValue, newValue, partialData);
			}
		};
	}

	protected void onDataChanged(String key, String oldValue, String newValue, Map<String, String> partialData) throws ValidationException {
		// default implementation where the input listens for its own change
		Log.d("form", "Input: " + key + " changed to " + newValue);
		if (this.key.equals(key)) {
			bind(partialData);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Input input = (Input) o;
		if (key != null ? !key.equals(input.key) : input.key != null) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return key != null ? key.hashCode() : 0;
	}
}
