package ext.extensions;

import android.widget.TextView;

/**
 * Created by evelina on 26/08/14.
 */
public abstract class TextExtension<T extends TextView> extends ViewExtension<T> {

	public abstract void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter);

}
