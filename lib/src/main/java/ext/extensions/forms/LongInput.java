package ext.extensions.forms;

import ext.R;

/**
 * Created by evelina on 18/09/14.
 */
public class LongInput extends Input<Long> {
	public final long minValue;
	public final long maxValue;

	public LongInput(String key) {
		this(key, Long.MIN_VALUE, Long.MAX_VALUE);
	}

	public LongInput(String key, long minValue, long maxValue) {
		super(key);
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public Long bindSingle(String value) throws ValidationException {
		try {
			long number = Long.parseLong(value);
			if(number < minValue)
				throw new ValidationException(key, R.string.form_error_min, minValue);
			if(number > maxValue)
				throw new ValidationException(key, R.string.form_error_max, maxValue);
			return number;
		}catch (NumberFormatException e){
			throw new ValidationException(key, R.string.form_error_int);
		}
	}

	@Override
	public String unbindSingle(Long value) {
		return String.valueOf(value);
	}
}
