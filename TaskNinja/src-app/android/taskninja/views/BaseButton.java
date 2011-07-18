package android.taskninja.views;

import android.content.Context;
import android.taskninja.R;
import android.widget.Button;

public class BaseButton extends Button {

	protected BaseButton(Context context) {
		super(context);
		setBackgroundResource(R.drawable.button_background);
		setTextColor(R.color.button_text);
		getBackground().setAlpha(100);
	}

}
