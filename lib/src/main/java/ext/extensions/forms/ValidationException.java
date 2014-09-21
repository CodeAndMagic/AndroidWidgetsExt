package ext.extensions.forms;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

/**
 * Created by evelina on 17/09/14.
 */
public class ValidationException extends Exception {
	public final ValidationFailure[] failures;

	public ValidationException(List<ValidationFailure> failures) {
		this(failures.toArray(new ValidationFailure[failures.size()]));
	}

	public ValidationException(ValidationFailure... failures) {
		this.failures = failures;
	}

	public ValidationException(String key, int errorMessageId, Object... args) {
		this(new ValidationFailure(key, errorMessageId, args));
	}

	public String toString(Context context) {
		StringBuilder b = new StringBuilder("ValidationException {failures=");
		boolean firstLine = true;
		for (ValidationFailure failure : failures) {
			if (firstLine) {
				firstLine = false;
			} else {
				b.append(",");
			}
			b.append(failure.toString(context));
		}
		b.append("}");
		return b.toString();
	}

	@Override
	public String toString() {
		return "ValidationException{" +
			"failures=" + Arrays.toString(failures) +
			'}';
	}
}
