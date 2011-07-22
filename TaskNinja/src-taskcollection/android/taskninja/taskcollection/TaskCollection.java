package android.taskninja.taskcollection;

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
import android.taskninja.task.Task;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.TaskGroup_String;

public class TaskCollection 
	extends Db_Model <TaskCollection, Db_NullEnum, Db_NullEnum, TaskCollection_String, Db_NullEnum>
	implements List<Task> 
	{
		
	private final List<Task> mTasks = new ArrayList<Task>();
	
	
	private void updateTasks(){
		StringBuffer buffer = new StringBuffer();
		for (Task task : mTasks){
			if (buffer.length() != 0){
				buffer.append(',');
			}
			buffer.append(task.getId());
		}
		put(TaskCollection_String.ITEM_IDS, buffer.toString());
	}
	
	// ----------------------------------------------------------------------------------------------------
	//  Constructor/Builder
	// ----------------------------------------------------------------------------------------------------
	public static TaskCollection getInstance() {
		return new TaskCollection();
	}
	
	private TaskCollection() {
		String itemIds = getString(TaskCollection_String.ITEM_IDS);
		if (itemIds != null){
			String[] split = itemIds.split(",");
			for (String id: split){
				Task task = Task.get(id);
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
	public static TaskCollection get(String id) {
		return controller().get(id);
	}
		
	public static LinkedHashSet<TaskCollection> getAll() {
		return controller().getAll();
	}
	// ----------------------------------------------------------------------------------------------------
		
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Db_Controller<TaskCollection, Db_NullEnum, Db_NullEnum, TaskCollection_String, Db_NullEnum> mController;
	
	@Override
	protected Db_Controller <TaskCollection, Db_NullEnum, Db_NullEnum, TaskCollection_String, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<TaskCollection, Db_NullEnum, Db_NullEnum, TaskCollection_String, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(TaskCollection.class, App.getContext());
			} catch (Exception e1){
				try {
					mController = new LocalController(TaskCollection.class, mContext);
				}catch (Exception e2){
					e2.printStackTrace();
					return null;
				}
			}
		}
		return mController;
	}

	public TaskCollection(JSONObject json) {
		super(json);
	}

	private static class LocalController extends Db_Controller<TaskCollection, Db_NullEnum, Db_NullEnum, TaskCollection_String, Db_NullEnum> {
		
		public static Context mContext;
			
		protected LocalController(Class<TaskCollection> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected TaskCollection getInstance(JSONObject json) {
			return new TaskCollection(json);
		}

	}
	// ----------------------------------------------------------------------------------------------------

	
	
	// ----------------------------------------------------------------------------------------------------
	//  List Methods
	// ----------------------------------------------------------------------------------------------------
	@Override
	public boolean add(Task object) {
		boolean result = false;
		if (!mTasks.contains(object)){
			result = mTasks.add(object);
			updateTasks();
		}
		return result;
	}

	@Override
	public void add(int location, Task object) {
		if (!mTasks.contains(object)){
			mTasks.add(location, object);
			updateTasks();
		} 
	}

	@Override
	public boolean addAll(Collection<? extends Task> arg0) {
		boolean result = true;
		
		for (Task task: arg0){
			if (mTasks.contains(task)){
				result = false;
				break;
			}
		}
		
		if (result){
			result = mTasks.addAll(arg0);
			updateTasks();
		}
		
		return result;
		
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends Task> arg1) {
		boolean result = true;
		
		for (Task task: arg1){
			if (mTasks.contains(task)){
				result = false;
				break;
			}
		}
		
		if (result){
			result = mTasks.addAll(arg0, arg1);
			updateTasks();
		}
		
		return result;
	}

	@Override
	public void clear() {
		for (Task task: mTasks){
			task.delete();
		}
		mTasks.clear();
		updateTasks();
	}

	@Override
	public boolean contains(Object object) {
		return mTasks.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return mTasks.containsAll(arg0);
	}

	@Override
	public Task get(int location) {
		return mTasks.get(location);
	}

	@Override
	public int indexOf(Object object) {
		return mTasks.indexOf(object);
	}

	@Override
	public boolean isEmpty() {
		return mTasks.isEmpty();
	}

	@Override
	public Iterator<Task> iterator() {
		return mTasks.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		return mTasks.lastIndexOf(object);
	}

	@Override
	public ListIterator<Task> listIterator() {
		return mTasks.listIterator();
	}

	@Override
	public ListIterator<Task> listIterator(int location) {
		return mTasks.listIterator(location);
	}

	@Override
	public Task remove(int location) {
		Task result = mTasks.remove(location);
		if (result != null){
			updateTasks();
		}
		return result;
	}

	@Override
	public boolean remove(Object object) {
		boolean result = mTasks.remove(object);
		if (result){
			updateTasks();
		}
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		boolean result = mTasks.removeAll(arg0);
		updateTasks();
		return result;
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return mTasks.retainAll(arg0);
	}

	@Override
	public Task set(int location, Task object) {
		Task result = null;
		if (!mTasks.contains(object)){
			result = mTasks.set(location, object);
			updateTasks();
		}
		return result;
	}

	@Override
	public int size() {
		return mTasks.size();
	}

	@Override
	public List<Task> subList(int start, int end) {
		return mTasks.subList(start, end);
	}

	@Override
	public Object[] toArray() {
		return mTasks.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return mTasks.toArray(array);
	}
	// ----------------------------------------------------------------------------------------------------


	

}