package android.taskninja.tools;

import android.content.Context;
import android.taskninja.R;


public class ColorManager {


//	public static android.graphics.Color get(Color color, Context context){
//		switch(color){
//		case ButtonText: return android.taskninja.R.color.button_text;
//		default: return android.R.color.black;
//		}
//	}
	
	public static int getResource(Color color){
		switch(color){
		case ButtonText: 
			return android.taskninja.R.color.button_text;
		default: 
			return android.R.color.black;
		}
	}
		
}
