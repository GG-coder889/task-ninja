package com.rocksolidmobility.taskninja.android.taskgroup;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONObject;

import com.rocksolidmobility.android.rsmodel.RSController;
import com.rocksolidmobility.android.rsmodel.RSModel;
import com.rocksolidmobility.android.rsmodel.RSNullEnum;
import com.rocksolidmobility.taskninja.android.task.Task;
import com.rocksolidmobility.taskninja.android.taskcollection.TaskCollection;

import android.content.Context;

public class TaskGroup 
	extends RSModel<TaskGroup, RSNullEnum, RSNullEnum, TaskGroupString, RSNullEnum>
	implements List<Task> {

	private TaskCollection mCollection;
	
	@Override
	public String toString(){
		return getString(TaskGroupString.title);
	}
		
	// ----------------------------------------------------------------------------------------------------
	//  Constructor & Builder
	// ----------------------------------------------------------------------------------------------------
	public static TaskGroup getInstance(String title){
		return new TaskGroup(title);
	}
	
	private TaskGroup(String title){
		super();
		put(TaskGroupString.title, title);
		mCollection = TaskCollection.get(getString(TaskGroupString.COLLECTION_ID));
		if (mCollection == null){
			mCollection = TaskCollection.getInstance();
			put(TaskGroupString.COLLECTION_ID, mCollection.getId());
		}
	}
	// ----------------------------------------------------------------------------------------------------
		
		
	
	// ----------------------------------------------------------------------------------------------------
	//  Static Methods
	// ----------------------------------------------------------------------------------------------------
	public static TaskGroup get(String id){
		return staticGetController().get(id);
	}
	
	public static LinkedHashSet<TaskGroup> getAll() {
		LinkedHashSet<TaskGroup> all = staticGetController().getAll();
		if (all.size() == 0) {
			all.add(TaskGroup.getInstance("Default"));
		}
		
		return staticGetController().getAll();
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  RSModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static RSController<TaskGroup, RSNullEnum, RSNullEnum, TaskGroupString, RSNullEnum> mController;
	
	@Override
	protected RSController<TaskGroup, RSNullEnum, RSNullEnum, TaskGroupString, RSNullEnum> 
	instanceGetController() {
		return staticGetController();
	}
	
	private static RSController<TaskGroup, RSNullEnum, RSNullEnum, TaskGroupString, RSNullEnum> 
	staticGetController(){
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
		mCollection = TaskCollection.get(getString(TaskGroupString.COLLECTION_ID));
		if (mCollection == null){
			mCollection = TaskCollection.getInstance();
			put(TaskGroupString.COLLECTION_ID, mCollection.getId());
		}
		
	}

	private static class LocalController extends RSController<TaskGroup, RSNullEnum, RSNullEnum, TaskGroupString, RSNullEnum> {
			
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
	//  List Interface Methods
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
