package taskninja.core.views.buttons;

import android.content.Context;
import android.widget.Button;

public class AddButton extends Button {
	
	public static AddButton getInstance(Context context){
		return new AddButton(context);
	}

	private AddButton(Context context) {
		super(context);
		setText("New");
		setTextColor(android.R.color.black);
	}

}
