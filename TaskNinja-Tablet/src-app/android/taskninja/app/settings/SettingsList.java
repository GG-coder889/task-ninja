package android.taskninja.app.settings;

import java.util.LinkedHashSet;

import android.content.Context;
import com.rocksolidmobility.taskninja.android.R;
import android.taskninja.tools.OnActionListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingsList extends ListView implements android.widget.AdapterView.OnItemClickListener {
	
	private LinkedHashSet<OnActionListener<Settings>> mActionListeners = new LinkedHashSet<OnActionListener<Settings>>();
	private Settings[] mActions = Settings.values();
	
	public static SettingsList getInstance(Context context){
		return new SettingsList(context);
	}

	private SettingsList(Context context) {
		super(context);
		
//		setAdapter(new ArrayAdapter<Settings>(getContext(), R.layout.text_list_item, mActions));
//		setOnItemClickListener(this);
//		
//		setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
		
	}
	
	public void addOnActionListener(OnActionListener<Settings> listener){
		mActionListeners.add(listener);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (position < mActions.length){
			Settings action = mActions[position];
			for (OnActionListener<Settings> listener: mActionListeners){
				listener.onAction(action);
			}
		}
	}



}
