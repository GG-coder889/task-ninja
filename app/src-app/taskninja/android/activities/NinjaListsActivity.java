package taskninja.android.activities;

import java.sql.SQLException;

import taskninja.android.ActionListener;
import taskninja.android.App;
import taskninja.android.dialogs.NewListDialog;
import taskninja.android.models.NinjaList;
import taskninja.android.ormlite.DatabaseHelper;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.support.CompiledStatement;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.support.DatabaseResults;

public class NinjaListsActivity extends ListActivity implements ActionListener<NinjaList> {
	
	private ArrayAdapter<NinjaList> mAdapter;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
    }
    
    @Override
    public void onResume(){
    	super.onResume();
    	try {
        	Dao<NinjaList, Integer> dao = DatabaseHelper.getListDao();
        	
        	if (dao.queryForAll().isEmpty()){
        		dao.createOrUpdate(new NinjaList("Default"));
        	}
        	
			mAdapter = new ArrayAdapter<NinjaList>(this,  android.R.layout.simple_list_item_1, dao.queryForAll());
			setListAdapter(mAdapter);
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	App.launch(mAdapter.getItem(position));
    }
    
    
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		menu.add("New List");
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		default:
			showNewListDialog();
			return true;
		}
	}
	
	

	private void showNewListDialog() {
		NewListDialog dialog = new NewListDialog(this, this);
		dialog.show();
	}

	@Override
	public void onAction(NinjaList action) {
		mAdapter.add(action);
	}
}