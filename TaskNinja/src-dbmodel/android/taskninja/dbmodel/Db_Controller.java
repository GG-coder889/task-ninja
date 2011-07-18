package android.taskninja.dbmodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.taskninja.dbmodel.Db_TableBuilder.BuiltIn;
import android.taskninja.dbmodel.Db_TableBuilder.Prefix;
import android.util.Log;


public abstract class Db_Controller <MODEL extends Db_Model,
	INTEGER extends Enum<INTEGER>,
	LONG extends Enum<LONG>,
	STRING extends Enum<STRING>,
	INTEGER_LIST extends Enum<INTEGER_LIST>,
	BOOL extends Enum<BOOL>
	> {
	
	private static final String TAG = "DbController";
	
	protected final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
	protected final HashMap<Integer, MODEL> mModelPool = new HashMap<Integer, MODEL>();
	protected final Set<Integer> mIdPool = new HashSet<Integer>();
	protected final Queue<Db_Model> mWriteQueue = new LinkedList<Db_Model>();
	protected final Queue<Integer> mDeleteQueue = new LinkedList<Integer>();
	
	protected final String ID = BuiltIn.ID.name();
	
	protected  LocalSQLiteHelper mSQLiteHelper;
	
	protected String mDatabaseName;
	protected String mTableName;
	
	protected Db_Controller(Class<MODEL> dbModel, Context context, int version) {
		String name = dbModel.getName().replace('.', '_');
		name = name.replace('$', '_');
		Log.d(TAG, "Name = "+name);
		
		mDatabaseName = name+".db";
		mTableName = Prefix.asTable(name);
		
		mSQLiteHelper = new LocalSQLiteHelper(context, mDatabaseName, null, version);
	}
	
	private class LocalSQLiteHelper extends SQLiteOpenHelper {

		public LocalSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL(createTableCommand());
			} catch (SQLException e){
				Log.d(TAG, "Failed to create database");
				e.printStackTrace();
			}
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
		}
		
	}
	
	private boolean mWritting = false;
	private class WriteTask extends AsyncTask<ContentValues, Void, List<ContentValues>> {
		@Override
		protected List<ContentValues> doInBackground(ContentValues... valueSets) {
			Log.d(TAG, "new Write AsyncTask with valueSets="+valueSets);
			return write(valueSets);
		}

		@Override
		protected void onPostExecute(List<ContentValues> valueSets) {
			for ( ContentValues valueSet: valueSets) {
				register(Db_Controller.this.get(valueSet.getAsInteger(ID)));
			}
			checkForWrite();
		}
	}
	
	private class DeleteTask extends AsyncTask<Integer, Void, List<Integer>> {
		@Override
		protected List<Integer> doInBackground(Integer... ids) {
			Log.d(TAG, "new DeleteTask with ids="+ids.toString());
			return delete(ids);
		}

		@Override
		protected void onPostExecute(List<Integer> ids) {
			Log.d(TAG, "Delete task complete with failed ids="+ids.toString());
			for ( int id : ids) {
				mIdPool.remove(id);
			}
			checkForWrite();
		}
	}
	
	private String createTableCommand() {
		Db_TableBuilder builder = new Db_TableBuilder(mTableName);
		
		for (INTEGER i: getIntegerValues()){
			builder.addNum(Prefix.asInteger(i.name()));
		}
		
		for (STRING s: getStringValues()){
			builder.addText(Prefix.asString(s.name()));
		}
		
		for (LONG l: getLongValues()){
			builder.addNum(Prefix.asLong(l.name()));
		}
		
		for (INTEGER_LIST iList: getIntegerListValues()){
			builder.addText(Prefix.asIntegerList(iList.name()));
		}
		
		for (BOOL iList: getBoolValues()){
			builder.addText(Prefix.asBool(iList.name()));
		}
		
		return builder.buildCommand();
		
	}



	public void checkForWrite() {
		if (!mWritting){
			if (mWriteQueue.size()!=0) {
				new WriteTask().execute(mWriteQueue.poll().getValues());
			} else if (mDeleteQueue.size() != 0){
				new DeleteTask().execute(mDeleteQueue.peek());
			}
		}
	}

	private List<ContentValues> write(ContentValues[] valueSets) {
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		List<ContentValues> failedSets = new LinkedList<ContentValues>();
		for (ContentValues valueSet : valueSets) {
			Log.d(TAG, "write(): "+valueSet);
			
			try {
				db.insert(mTableName, null, valueSet);
			} catch (Exception e) {
				e.printStackTrace();
				Log.d(TAG, "write(): failed to write valueSet: "+valueSet);
				failedSets.add(valueSet);
			}
			
		}
		db.close();
		return failedSets;
	}
	
	public List<Integer> delete(Integer[] ids) {
		Log.d(TAG, "Deleting ids="+ids.toString());
		
		SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
		List<Integer> failedIds = new LinkedList<Integer>();
		for (Integer id: ids){
			Log.d(TAG, "Deleting id="+id);
			try {
//				String command = "DELETE FROM "+mTableName+" WHERE "+ID+"="+id+";";
//				Log.d(TAG, command);
//				db.execSQL(command);
				int affect = db.delete(mTableName, ID+"="+id, null);
				if (affect == 1){
					Log.d(TAG, "delete succes id="+id);
				} else {
					Log.d(TAG, "no lines were affect in delete id="+id);
					failedIds.add(id);
				}
				
			} catch (Exception e) {
				Log.d(TAG, "Deleting failed id="+id);
				e.printStackTrace();
				failedIds.add(id);
			}
		}
		return failedIds;
	}

	private void fillIdPool() {
		Log.d(TAG, "fillIdPool()");
		
		SQLiteDatabase db = mSQLiteHelper.getReadableDatabase();
		String query = String.format("SELECT %s FROM %s;", ID,
				mTableName, ID);
		
		Log.d(TAG, query);
		
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.moveToFirst()) {
			int index = cursor.getColumnIndex(ID);
			do {
				mIdPool.add(cursor.getInt(index));
			} while (cursor.moveToNext());
		}
		
		Log.d(TAG, "mIdPool = "+mIdPool.toString());
		
		cursor.close();
		db.close();		
	}

	protected abstract INTEGER[] getIntegerValues();
	protected abstract STRING[] getStringValues();
	protected abstract LONG[] getLongValues();
	protected abstract INTEGER_LIST[] getIntegerListValues();
	protected abstract BOOL[] getBoolValues();
	protected abstract MODEL getNewInstance(ContentValues values);
	
	
	public MODEL get(int id) {
		Log.d(TAG, "get(): Getting model with id="+id);
		MODEL model = mModelPool.get(id);
		if (model == null) {
			Log.d(TAG, "get(): Did not find the model in the pool");
			try {
				SQLiteDatabase db = mSQLiteHelper.getWritableDatabase();
				String idKey = ID;
				Cursor cursor = db.query(mTableName, null, idKey + " = " + id,
						null, null, null, null);
				if (cursor.moveToFirst()){
					ContentValues values = readValues(cursor);
					model = getNewInstance(values);
				} else {
					Log.d(TAG, "get(): cursor was null");
				}
				cursor.close();
				db.close();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		if (model != null && !model.isDeleted()){
			return model;
		} else {
			return null;
		}
		
	}
	
	private ContentValues readValues(Cursor cursor) {
		Log.d(TAG, "readValues()");
		
		ContentValues values = new ContentValues();
		int index;
		String key;
		
		index = cursor.getColumnIndex(ID);
		if (index != -1){
			Integer value = cursor.getInt(index);
			values.put(ID, value);
		} 
		
		for (INTEGER i: getIntegerValues()){
			key = Prefix.asInteger(i.name());
			index = cursor.getColumnIndex(key);
			if (index != -1){
				Integer value = cursor.getInt(index);
				values.put(key, value);
			}
		}
		
		for (STRING i: getStringValues()){
			key = Prefix.asString(i.name());
			index = cursor.getColumnIndex(key);
			if (index != -1){
				String value = cursor.getString(index);
				values.put(key, value);
			}
		}
		
		for (LONG i: getLongValues()){
			key = Prefix.asLong(i.name());
			index = cursor.getColumnIndex(key);
			if (index != -1){
				Long value = cursor.getLong(index);
				values.put(key, value);
			}
		}
		
		for (INTEGER_LIST i: getIntegerListValues()){
			key = Prefix.asIntegerList(i);
			index = cursor.getColumnIndex(key);
			if (index != -1){
				String value = cursor.getString(index);
				values.put(key, value);
			}
		}
		
		Log.d(TAG, "readValues() values="+values);
		
		return values;
	}

	public int getNewId() {
		if (mIdPool.size() == 0){
			fillIdPool();
		}
		int i = 1;
		while (!mIdPool.add(i)){
			i++;
		}
		return i;
	}
	
	public void watch(MODEL model){
		if (!model.isDeleted()){
			mModelPool.put(model.getId(), model);
		} else {
			register(model);
		}
	}
	
	public void register(Db_Model model){
		if (model.isDeleted()){
			mDeleteQueue.add(model.getId());
			mModelPool.remove(model.getId());
		} else if (!mWriteQueue.contains(model)){
			mWriteQueue.add(model);
			checkForWrite();
		}
	}
	
	public LinkedHashSet<MODEL> getAll(){
		LinkedHashSet<MODEL> list = new LinkedHashSet<MODEL>();
		for (Integer id: mIdPool){
			MODEL model = get(id);
			if (model != null){
				list.add(model);
			}
		}
		return list;
	}

}













