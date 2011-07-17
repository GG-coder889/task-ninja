package android.taskninja.core.settings;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.taskninja.R;

public class BackgroundManager {
	
	public static Drawable get(Background background, Context context){
		Resources resources = context.getResources();
		Drawable drawable;
		switch (background){
		case Primary:
			drawable = resources.getDrawable(R.drawable.background_primary);
			drawable.setAlpha(150);
			return drawable;
		case Secondary:
			drawable = resources.getDrawable(R.drawable.background_primary);
			drawable.setAlpha(100);
			return drawable;
		default:
			return get(Background.Primary, context);
		}
	}

}
