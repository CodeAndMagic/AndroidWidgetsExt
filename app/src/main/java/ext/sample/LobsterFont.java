package ext.sample;

import ext.extensions.typeface.Font;

/**
 * Created by evelina on 15/09/14.
 */
public enum LobsterFont implements Font {
	LOBSTER_NORMAL("fonts/Lobster.ttf");

	private String mName;

	LobsterFont(String name) {
		mName = name;
	}

	@Override
	public String getName() {
		return mName;
	}
}
