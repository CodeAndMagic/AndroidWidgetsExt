package ext;

import android.util.Log;

import java.text.MessageFormat;

/**
 * Created by evelina on 17/09/14.
 */
public abstract class L {

	private static final String TAG = "EXT";

	public static void log(String message) {
		Log.d(TAG, message);
	}

	public static void log(String message, Object... args) {
		if (args == null || args.length == 0) {
			log(message);
			return;
		}

		String formattedMessage = MessageFormat.format(message, args);
		Object lastArg = args[args.length - 1];
		if (lastArg instanceof Throwable) {
			log(formattedMessage, (Throwable) lastArg);
			return;
		}

		log(formattedMessage);
	}

	public static void log(String message, Throwable e) {
		Log.e(TAG, message, e);
	}
}
