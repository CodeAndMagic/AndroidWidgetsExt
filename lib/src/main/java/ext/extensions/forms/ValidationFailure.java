package ext.extensions.forms;

import android.content.Context;

import java.util.Arrays;

/**
 * Created by evelina on 17/09/14.
 */
public class ValidationFailure {
	public final String key;
	public final int errorMessageId;
	public final Object[] args;

	public ValidationFailure(String key, int errorMessageId, Object... args) {
		this.key = key;
		this.errorMessageId = errorMessageId;
		this.args = args;
	}

	public String getMessage(Context context) {
		return context.getString(errorMessageId, args);
	}

	public String toString(Context context) {
		return "ValidationFailure{" +
			"key='" + key + '\'' +
			", errorMessage=" + getMessage(context) +
			'}';
	}

	@Override
	public String toString() {
		return "ValidationFailure{" +
			"key='" + key + '\'' +
			", errorMessageId=" + errorMessageId +
			", args=" + Arrays.toString(args) +
			'}';
	}
}
