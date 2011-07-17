package android.taskninja.core.views.buttons;

import android.content.Context;
import android.taskninja.R;
import android.widget.Button;

public class BaseButton extends Button {

	protected BaseButton(Context context) {
		super(context);
		setBackgroundResource(R.drawable.button_background);
		setTextColor(R.color.button_text);
//		setTextColor(android.R.color.white);
		getBackground().setAlpha(100);
	}

}
