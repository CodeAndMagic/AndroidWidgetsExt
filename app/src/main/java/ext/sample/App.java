package ext.sample;

import android.app.Application;

import ext.extensions.typeface.FontManager;

/**
 * Created by evelina on 15/09/14.
 */
public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		FontManager.getInstance().addFontExtractor(CustomFonts.getInstance());
	}
}
