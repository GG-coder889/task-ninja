/**
 * 
 */
package taskninja.android.ormlite;

import java.sql.SQLException;

import taskninja.android.models.NinjaList;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * @author alden
 *
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
	
	// name of the database file for your application -- change to something appropriate for your app
	private static final String DATABASE_NAME = "taskninja.db";
	// any time you make changes to your database objects, you may have to increase the database version
	private static final int DATABASE_VERSION = 2;
	
	private static DatabaseHelper mDatabaseHelper = null;
	private static Context mContext;
		
	public static DatabaseHelper getInstance() {
		if (mDatabaseHelper == null){
			mDatabaseHelper = new DatabaseHelper(mContext);
		}
		return mDatabaseHelper;
	}
	
	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * This is called when the database is first created. Usually you should call createTable statements here to create
	 * the tables that will store your data.
	 */
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, NinjaList.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
	 * the various data to match the new version number.
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, NinjaList.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Close the database connections and clear any cached DAOs.
	 */
	@Override
	public void close() {
		super.close();
	}

	public static void setContext(Context context) {
		mContext = context;
	}

	// --------------------------------------------------
	// DAOs
	// --------------------------------------------------
	private Dao<NinjaList, Integer> listDao = null;
	public static Dao<NinjaList, Integer> getListDao() throws SQLException{
		if (getInstance().listDao == null){
			getInstance().listDao = getInstance().getDao(NinjaList.class);
		}
		return getInstance().listDao;
	}
	// --------------------------------------------------
	
}
