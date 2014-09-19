package ext.extensions.forms;

/**
 * Created by evelina on 17/09/14.
 */
public class ValidationException extends Exception {
	public final ValidationFailure[] failures;

	public ValidationException(ValidationFailure... failures) {
		this.failures = failures;
	}

	public ValidationException(String key, int errorMessageId, Object... args){
		this(new ValidationFailure(key, errorMessageId, args));
	}
}
