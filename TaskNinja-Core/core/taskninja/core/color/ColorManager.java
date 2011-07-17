package taskninja.core.color;

import android.taskninja.R;


public class ColorManager {
	public enum Color {
		ButtonText
	}

	public static int get(Color color){
		switch(color){
		case ButtonText: return android.taskninja.R.color.button_text;
		default: return android.R.color.black;
		}
	}
		
}
