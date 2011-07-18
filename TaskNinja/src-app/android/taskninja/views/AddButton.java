package android.taskninja.views;

import android.content.Context;
import android.widget.Button;

public class AddButton extends BaseButton {
	
	public static AddButton getInstance(Context context){
		return new AddButton(context);
	}

	private AddButton(Context context) {
		super(context);
		setText("New");
	}

}
