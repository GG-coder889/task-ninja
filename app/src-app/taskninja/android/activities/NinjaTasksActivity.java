package taskninja.android.activities;

import java.sql.SQLException;

import taskninja.android.App;
import taskninja.android.models.NinjaList;
import taskninja.android.ormlite.DatabaseHelper;
import android.app.ListActivity;
import android.os.Bundle;

public class NinjaTasksActivity extends ListActivity {
	
	private NinjaList mNinjaList = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		int id = getIntent().getIntExtra(NinjaList.class.getSimpleName(), -1);
		try {
			mNinjaList = DatabaseHelper.getListDao().queryForId(id);
			if (mNinjaList == null){
				App.fail(this);
			} else {
				this.setTitle(mNinjaList.getTitle());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
}
