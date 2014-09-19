package ext.extensions.forms;

/**
 * Created by evelina on 17/09/14.
 */
public interface Condition<T> {
	public boolean verify(T value);
}
