package android.taskninja.core.settings;

import android.content.Context;
import android.taskninja.core.app.App;
import android.taskninja.core.views.text.TitleText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingView extends LinearLayout {
	
	private Settings mSetting;
	
	public static SettingView getInstance(Context context, Settings setting){
		return new SettingView(context, setting);
	}
	
	protected SettingView(Context context, Settings setting){
		super(context);
		
		mSetting = setting;
		
		setBackgroundDrawable(App.get(Background.Primary, getContext()));
		
		addView(TitleText.getInstance(getContext(), mSetting.toString()));
	}

}
