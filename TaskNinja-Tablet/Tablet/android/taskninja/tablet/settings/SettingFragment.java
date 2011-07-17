package android.taskninja.tablet.settings;

import android.app.Fragment;
import android.os.Bundle;
import android.taskninja.core.settings.SettingView;
import android.taskninja.core.settings.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SettingFragment extends Fragment {
	
	private Settings mSetting;
	private SettingView mSettingView;
	
	public static SettingFragment getInstance(Settings setting){
		return new SettingFragment(setting);
	}
	
	protected SettingFragment(Settings setting){
		super();
		mSetting = setting;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mSettingView = SettingView.getInstance(getActivity().getApplicationContext(), mSetting);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return mSettingView;
	}
}
