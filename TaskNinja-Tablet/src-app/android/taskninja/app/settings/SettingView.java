package android.taskninja.app.settings;

import android.content.Context;
import android.taskninja.views.TitleText;
import android.widget.LinearLayout;

public class SettingView extends LinearLayout {
	
	private Settings mSetting;
	
	public static SettingView getInstance(Context context, Settings setting){
		return new SettingView(context, setting);
	}
	
	protected SettingView(Context context, Settings setting){
		super(context);
		
		mSetting = setting;
		
		setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
		
		addView(TitleText.getInstance(getContext(), mSetting.toString()));
	}

}
