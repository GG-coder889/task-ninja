package android.taskninja.taskgroup;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import org.json.JSONObject;

import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.dbmodel.Db_NullEnum;
import android.taskninja.task.Task;
import android.taskninja.taskcollection.TaskCollection;
import android.taskninja.taskcollection.TaskCollection_String;

public class TaskGroup 
	extends Db_Model<TaskGroup, Db_NullEnum, Db_NullEnum, TaskGroup_String, Db_NullEnum>
	implements List<Task> {

	private TaskCollection mCollection;
	
	@Override
	public String toString(){
		return getString(TaskGroup_String.title);
	}
		
	// ----------------------------------------------------------------------------------------------------
	//  Constructor/Builder
	// ----------------------------------------------------------------------------------------------------
	public static TaskGroup getInstance(String title){
		return new TaskGroup(title);
	}
	
	private TaskGroup(String title){
		super();
		put(TaskGroup_String.title, title);
		mCollection = TaskCollection.get(getString(TaskGroup_String.COLLECTION_ID));
		if (mCollection == null){
			mCollection = TaskCollection.getInstance();
			put(TaskGroup_String.COLLECTION_ID, mCollection.getId());
		}
	}
	// ----------------------------------------------------------------------------------------------------
		
		
	
	// ----------------------------------------------------------------------------------------------------
	//  Static Methods
	// ----------------------------------------------------------------------------------------------------
	public static TaskGroup get(String id){
		return controller().get(id);
	}
	
	public static LinkedHashSet<TaskGroup> getAll() {
		LinkedHashSet<TaskGroup> all = controller().getAll();
		if (all.size() == 0) {
			all.add(TaskGroup.getInstance("Default"));
		}
		
		return controller().getAll();
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Db_Controller<TaskGroup, Db_NullEnum, Db_NullEnum, TaskGroup_String, Db_NullEnum> mController;
	
	@Override
	protected Db_Controller <TaskGroup, Db_NullEnum, Db_NullEnum, TaskGroup_String, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<TaskGroup, Db_NullEnum, Db_NullEnum, TaskGroup_String, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(TaskGroup.class, mContext);
			}catch (Exception e2){
				e2.printStackTrace();
				return null;
			}
		}
		return mController;
	}
	
	public TaskGroup(JSONObject json) {
		super(json);
		mCollection = TaskCollection.get(getString(TaskGroup_String.COLLECTION_ID));
		if (mCollection == null){
			mCollection = TaskCollection.getInstance();
			put(TaskGroup_String.COLLECTION_ID, mCollection.getId());
		}
		
	}

	private static class LocalController extends Db_Controller<TaskGroup, Db_NullEnum, Db_NullEnum, TaskGroup_String, Db_NullEnum> {
			
		protected LocalController(Class<TaskGroup> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected TaskGroup getInstance(JSONObject json) {
			return new TaskGroup(json);
			
		}

	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  List Methods
	// ----------------------------------------------------------------------------------------------------
	@Override
	public boolean add(Task task) {
		return mCollection.add(task);
	}

	@Override
	public void add(int location, Task task) {
		mCollection.add(location, task);
	}

	@Override
	public boolean addAll(Collection<? extends Task> arg0) {
		return addAll(arg0);
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends Task> arg1) {
		return mCollection.addAll(arg0, arg1);
	}

	@Override
	public void clear() {
		mCollection.clear();
	}

	@Override
	public boolean contains(Object object) {
		return mCollection.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return mCollection.containsAll(arg0);
	}

	@Override
	public Task get(int location) {
		return mCollection.get(location);
	}

	@Override
	public int indexOf(Object object) {
		return mCollection.indexOf(object);
	}

	@Override
	public boolean isEmpty() {
		return mCollection.isEmpty();
	}

	@Override
	public int lastIndexOf(Object object) {
		return mCollection.lastIndexOf(object);
	}

	@Override
	public ListIterator<Task> listIterator() {
		return mCollection.listIterator();
	}

	@Override
	public ListIterator<Task> listIterator(int location) {
		return mCollection.listIterator(location);
	}

	@Override
	public Task remove(int location) {
		return mCollection.remove(location);
	}

	@Override
	public boolean remove(Object object) {
		return mCollection.remove(object);
	}

	@Override
	public boolean removeAll(Collection<?> arg0) {
		return mCollection.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0) {
		return mCollection.removeAll(arg0);
	}

	@Override
	public Task set(int location, Task object) {
		return mCollection.set(location, object);
	}

	@Override
	public List<Task> subList(int start, int end) {
		return mCollection.subList(start, end);
	}

	@Override
	public Object[] toArray() {
		return mCollection.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return mCollection.toArray(array);
	}
	
	@Override
	public Iterator<Task> iterator() {
		return mCollection.iterator();
	}
	
	@Override
	public int size() {
		return mCollection.size();
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	

	

	

	

	

	
}
