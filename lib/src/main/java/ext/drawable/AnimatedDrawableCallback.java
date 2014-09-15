package ext.drawable;

import android.view.View;

/**
 * Created by evelina on 15/09/14.
 */
public interface AnimatedDrawableCallback {

	void onViewAttachedToWindow(View view);

	void onViewDetachedToWindow(View view);
}
