package ext.sample;

import ext.extensions.typeface.Font;

/**
 * Created by evelina on 15/09/14.
 */
public enum RalewayFont implements Font {
	RALEWAY_BOLD("fonts/Raleway-Bold.ttf"),
	RALEWAY_NORMAL("fonts/Raleway-Regular.ttf"),
	RALEWAY_LIGHT("fonts/Raleway-Light.ttf");

	private String mName;

	RalewayFont(String name) {
		mName = name;
	}

	@Override
	public String getName() {
		return mName;
	}
}
