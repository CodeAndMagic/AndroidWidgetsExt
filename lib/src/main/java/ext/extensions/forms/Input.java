package ext.extensions.forms;

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

	public T bind(Map<String, String> data) throws ValidationException{
		return bindSingle(data.get(key));
	}

	protected T bindSingle(String data) throws ValidationException{
		return null;
	}

	public Map<String, String> unbind(T value){
		Map<String,String> data = new HashMap<>();
		data.put(key, unbindSingle(value));
		return data;
	}

	protected String unbindSingle(T value){
		return null;
	}

	public <Q> Input<Q> map(final Mapping<T, Q> mapping) {
		return new Input<Q>(key) {
			@Override
			public Q bind(Map<String, String> data) throws ValidationException {
				return mapping.bind(Input.this.bind(data));
			}

			@Override
			public Map<String,String> unbind(Q value) {
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
		};
	}

	public Input<T> verifying(final Condition<T> condition, final int errorMessageId) {
		return new Input<T>(key) {
			@Override
			public T bind(Map<String,String> data) throws ValidationException {
				T v = Input.this.bind(data);
				if (condition.verify(v)) return v;
				else throw new ValidationException(key, errorMessageId);
			}

			@Override
			public Map<String,String> unbind(T value) {
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
		};
	}
}
