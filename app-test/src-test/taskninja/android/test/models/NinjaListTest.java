package taskninja.android.test.models;

import java.sql.SQLException;

import taskninja.android.models.NinjaList;
import taskninja.android.ormlite.DatabaseHelper;
import android.test.AndroidTestCase;

public class NinjaListTest extends AndroidTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DatabaseHelper.setContext(getContext());
		DatabaseHelper.getListDao().delete(DatabaseHelper.getListDao().queryForAll());
	}
	
	public void testConstructor() {
		String title = "title";
		NinjaList list = new NinjaList(title);
		assertEquals(title, list.getTitle());
	}
	
	public void testToString(){
		String title = "title";
		NinjaList list = new NinjaList(title);
		assertEquals(title, list.toString());
	}
	
	public void testCreation() throws SQLException{
		String title = "title";
		DatabaseHelper.getListDao().create(new NinjaList(title));
		assertEquals(title, DatabaseHelper.getListDao().queryForAll().get(0).toString());
	}

}
