package ext.extensions.forms;

/**
 * Created by evelina on 17/09/14.
 */
public interface Mapping<T,Q> {
	Q bind(T value) throws ValidationException;
	T unbind(Q value);
}
