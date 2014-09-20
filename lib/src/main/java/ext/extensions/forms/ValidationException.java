package ext.extensions.forms;

import java.util.Arrays;

/**
 * Created by evelina on 17/09/14.
 */
public class ValidationException extends Exception {
	public final ValidationFailure[] failures;

	public ValidationException(ValidationFailure... failures) {
		this.failures = failures;
	}

	public ValidationException(String key, int errorMessageId, Object... args) {
		this(new ValidationFailure(key, errorMessageId, args));
	}

	@Override
	public String toString() {
		return "ValidationException{" +
			"failures=" + Arrays.toString(failures) +
			'}';
	}
}
