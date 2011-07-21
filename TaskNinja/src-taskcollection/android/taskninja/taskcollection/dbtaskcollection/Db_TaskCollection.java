package android.taskninja.taskcollection.dbtaskcollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.dbmodel.Db_NullEnum;
import android.taskninja.task.dbtask.Db_Task;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup_String;

public class Db_TaskCollection 
	extends Db_Model <Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum>
	implements List<Db_Task> 
	{
		
	private final List<Db_Task> mTasks = new ArrayList<Db_Task>();
	
	
	private void updateTasks(){
		StringBuffer buffer = new StringBuffer();
		for (Db_Task task : mTasks){
			if (buffer.length() != 0){
				buffer.append(',');
			}
			buffer.append(task.getId());
		}
		put(Db_TaskCollection_String.ITEM_IDS, buffer.toString());
	}
	
	// ----------------------------------------------------------------------------------------------------
	//  Constructor/Builder
	// ----------------------------------------------------------------------------------------------------
	public static Db_TaskCollection getInstance() {
		return new Db_TaskCollection();
	}
	
	private Db_TaskCollection() {
		String itemIds = getString(Db_TaskCollection_String.ITEM_IDS);
		if (itemIds != null){
			String[] split = itemIds.split(",");
			for (String id: split){
				Db_Task task = Db_Task.get(id);
				if (task != null && !mTasks.contains(task)){
					mTasks.add(task);
				}
			}
		}
	}
	// ----------------------------------------------------------------------------------------------------
			
			
		
	// ----------------------------------------------------------------------------------------------------
	//  Static Methods
	// ----------------------------------------------------------------------------------------------------
	public static Db_TaskCollection get(String id) {
		return controller().get(id);
	}
		
	public static LinkedHashSet<Db_TaskCollection> getAll() {
		return controller().getAll();
	}
	// ----------------------------------------------------------------------------------------------------
		
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> mController;
	
	@Override
	protected Db_Controller <Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Db_TaskCollection.class, App.getContext());
			} catch (Exception e1){
				try {
					mController = new LocalController(Db_TaskCollection.class, mContext);
				}catch (Exception e2){
					e2.printStackTrace();
					return null;
				}
			}
		}
		return mController;
	}

	public Db_TaskCollection(JSONObject json) {
		super(json);
	}

	private static class LocalController extends Db_Controller<Db_TaskCollection, Db_NullEnum, Db_NullEnum, Db_TaskCollection_String, Db_NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<Db_TaskCollection> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected Db_TaskCollection getInstance(JSONObject json) {
			return new Db_TaskCollection(json);
		}

	}
	// ----------------------------------------------------------------------------------------------------

	
	
	// ----------------------------------------------------------------------------------------------------
	//  List Methods
	// ----------------------------------------------------------------------------------------------------
	@Override
	public boolean add(Db_Task object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(int location, Db_Task object) {
		if (! mTasks.contains(object)){
			mTasks.add(object);
		} else {
			
		}
		
	}

	@Override
	public boolean addAll(Collection<? extends Db_Task> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends Db_Task> arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Db_Task get(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Db_Task> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int lastIndexOf(Object object) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Db_Task> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Db_Task> listIterator(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Db_Task remove(int location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Db_Task set(int location, Db_Task object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Db_Task> subList(int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] array) {
		// TODO Auto-generated method stub
		return null;
	}
	// ----------------------------------------------------------------------------------------------------


	

}
