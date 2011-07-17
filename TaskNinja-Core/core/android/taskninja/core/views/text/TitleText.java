package android.taskninja.core.views.text;

import android.content.Context;
import android.taskninja.core.app.App;
import android.taskninja.core.settings.Color;
import android.widget.TextView;

public class TitleText extends TextView {
	
	public static TitleText getInstance(Context context, CharSequence title){
		return new TitleText(context, title);
	}

	public TitleText(Context context, CharSequence title) {
		super(context);
		setText(title);
		setTextColor(App.getResource(Color.ButtonText));
	}

}
