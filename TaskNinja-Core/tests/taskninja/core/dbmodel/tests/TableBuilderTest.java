package taskninja.core.dbmodel.tests;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.taskninja.core.dbmodel.TableBuilder;
import android.test.AndroidTestCase;
import android.util.Log;


public class TableBuilderTest extends AndroidTestCase {
	
	private static String TAG = "TableBuilderTest";
	
	private static String mCommand;

	public void testBuild(){
		TableBuilder builder = new TableBuilder("TEST");
		
		builder.addNum("message");
		builder.addNum("name");
		builder.addNum("location");
		builder.addNum("parent");
		builder.addText("details");
		
		mCommand = builder.buildCommand();
		Log.d(TAG, mCommand);
		
		
		
		SQLiteOpenHelper helper = new SQLiteOpenHelper(mContext, "TEST.db", null, 1) {
			
			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL(mCommand);
				
			}
		};
		
		
		
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("ID", 6);
		values.put("parent", 5);
		values.put("location", 4);
		values.put("message", 3);
		
		assertFalse(-1 == db.insert("TEST", null, values));
		
		String query = "SELECT ID FROM TEST;";
		Cursor cursor = db.rawQuery(query, null);
		assertTrue(cursor.moveToFirst());
		
		int index = cursor.getColumnIndex("ID");
		assertFalse(index == -1);
		
		int id = cursor.getInt(index);
		assertEquals(6, id);

	}
}
