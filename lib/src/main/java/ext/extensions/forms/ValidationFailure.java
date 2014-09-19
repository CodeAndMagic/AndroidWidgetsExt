package ext.extensions.forms;

import android.content.Context;

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

	public String getMessage(Context context){
		return context.getString(errorMessageId, args);
	}
}