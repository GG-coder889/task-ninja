package android.taskninja.app.settings;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import com.rocksolidmobility.taskninja.android.R;
import android.taskninja.app.AbsractBaseActivity;
import android.taskninja.tools.OnActionListener;
import android.widget.FrameLayout;

public class SettingsActivity extends AbsractBaseActivity implements OnActionListener<Settings> {
	
	private SettingsList mSettingList;
	private FragmentFrame mFragmentFrame;
	private SettingFragment mSettingFragment;
	private Settings mCurrentSetting = Settings.TaskNinja;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        
        mSettingList = SettingsList.getInstance(getApplicationContext());
        mSettingList.addOnActionListener(this);
//        mRoot.addView(mSettingList);
        
        mFragmentFrame = FragmentFrame.getInstance(getApplicationContext());
//        mRoot.addView(mFragmentFrame);
        
        showSetting();
        
    }
    
    private void showSetting(){
    	FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        
        mSettingFragment = SettingFragment.getInstance(mCurrentSetting);
        fragmentTransaction.add(R.id.settings, mSettingFragment);
        fragmentTransaction.commit();
    }

    private static class FragmentFrame extends FrameLayout {

		private FragmentFrame(Context context) {
			super(context);
			setId(R.id.settings);
		}

		public static FragmentFrame getInstance(Context context) {
			return new FragmentFrame(context);
		}
    	
    }

	@Override
	public void onAction(Settings action) {
		if (action != mCurrentSetting){
			mCurrentSetting = action;
			showSetting();
		}
	}


}
