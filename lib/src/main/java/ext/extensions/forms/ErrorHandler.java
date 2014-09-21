package ext.extensions.forms;

/**
 * Created by evelina on 20/09/14.
 */
public interface ErrorHandler {

	void onError(ValidationFailure[] failures);

	void reset();
}
