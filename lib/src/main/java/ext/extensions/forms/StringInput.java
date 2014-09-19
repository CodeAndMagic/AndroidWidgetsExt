package ext.extensions.forms;

import ext.R;

/**
 * Created by evelina on 17/09/14.
 */
public class StringInput extends Input<String> {
	public final int minLength;
	public final int maxLength;

	public StringInput(String key){
		this(key, 0, Integer.MAX_VALUE);
	}

	public StringInput(String key, int minLength, int maxLength) {
		super(key);
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	@Override
	public String bindSingle(String value) throws ValidationException {
		if(value.length() < minLength)
			throw new ValidationException(key, R.string.form_error_min_length, minLength);
		if(value.length() > maxLength)
			throw new ValidationException(key, R.string.form_error_max_length, maxLength);
		return value;
	}

	@Override
	public String unbindSingle(String value) {
		return value;
	}
}
