package ext.sample;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by evelina on 20/09/14.
 */
public class FormActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
			.replace(android.R.id.content, new SimpleFormFragment())
			.commit();
	}
}
