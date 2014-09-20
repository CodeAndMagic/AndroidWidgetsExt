package ext.extensions.forms;

import android.view.View;

/**
 * Created by evelina on 20/09/14.
 */
public interface ViewErrorHandler<V extends View> {

	void onError(V view, ValidationFailure[] failures);
}
