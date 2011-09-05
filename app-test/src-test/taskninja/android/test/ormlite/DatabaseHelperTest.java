package taskninja.android.test.ormlite;

import java.sql.SQLException;

import taskninja.android.models.NinjaList;
import taskninja.android.ormlite.DatabaseHelper;
import android.test.AndroidTestCase;

import com.j256.ormlite.dao.Dao;

public class DatabaseHelperTest extends AndroidTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		DatabaseHelper.setContext(getContext());
	}
	
	public void testGetInstace(){
		DatabaseHelper instance1 = DatabaseHelper.getInstance();
		DatabaseHelper instance2 = DatabaseHelper.getInstance();
		assertSame(instance1, instance2);
	}
	
	public void testGetDao() throws SQLException {
		DatabaseHelper helper = DatabaseHelper.getInstance();
		helper.getDao(NinjaList.class);
	}
	
	public void testGetListDao() throws SQLException{
		Dao dao1 = DatabaseHelper.getListDao();
		Dao dao2 = DatabaseHelper.getListDao();
		assertSame(dao1, dao2);
	}

}
