package taskninja.core.views.text;

import android.content.Context;
import android.widget.TextView;

public class TitleText extends TextView {
	
	public static TitleText getInstance(Context context, CharSequence title){
		return new TitleText(context, title);
	}

	public TitleText(Context context, CharSequence title) {
		super(context);
		setText(title);
	}

}
