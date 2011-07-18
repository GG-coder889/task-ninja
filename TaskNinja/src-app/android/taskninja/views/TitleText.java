package android.taskninja.views;

import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.tools.Color;
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
