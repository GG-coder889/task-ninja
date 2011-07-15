package com.taskninjapro.android.task;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;

import com.taskninjapro.android.app.App;
import com.taskninjapro.android.dbmodel.DbController;
import com.taskninjapro.android.dbmodel.DbModel;


public class Task extends DbModel<Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> {
	
	private static Context mContext;
	private static DbController<Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> mController;
	
	@Override
	protected DbController <Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> getController() {
		if (mController == null){
			try {
				mController = new LocalController(Task.class, App.getContext(), 1);
			} catch (Exception e1){
				try {
					mController = new LocalController(Task.class, mContext, 1);
				}catch (Exception e2){
					e2.printStackTrace();
					return null;
				}
			}
		}
		return mController;
	}
	
	public static void setContext(Context context){
		LocalController.mContext = context;
	}
	
	private Task(ContentValues values){
		super(values);
	}
	
	public Task(CharSequence what) {
		put(TaskString.KEY_WHAT, what.toString());
	}

	public static Task get(int id){
		return mController.get(id);
	}
	
	private static class LocalController extends DbController<Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> {
		
		public static Context mContext;
			
		protected LocalController(Class<Task> dbModel, Context context, int version) {
			super(dbModel, context, version);
		}

		@Override
		protected TaskIntegerList[] getIntegerListValues() {
			return TaskIntegerList.values();
		}

		@Override
		protected TaskInteger[] getIntegerValues() {
			return TaskInteger.values();
		}

		@Override
		protected TaskLong[] getLongValues() {
			return TaskLong.values();
		}

		@Override
		protected Task getNewInstance(ContentValues arg0) {
			return new Task(arg0);
		}

		@Override
		protected TaskString[] getStringValues() {
			return TaskString.values();
		}

		@Override
		protected TaskBool[] getBoolValues() {
			return TaskBool.values();
		}


	
	}

	public static LinkedHashSet<Task> getAll() {
		LinkedHashSet<Task> tasks = new LinkedHashSet<Task>();
		for (Task task: mController.getAll()) {
			tasks.add(task);
		}
		return tasks;
	}

	public Task getParent() {
		Integer id = getInteger(TaskInteger.KEY_PARENT);
		if (id != null){
			return get(id); 
		} else {
			return null;
		}
		
	}
	
	public void setParent(Task task){
		
		Task parent = getParent();
		if (parent != null){
			parent.removeChild(this);
		}
		
		task.addChild(this);
		put(TaskInteger.KEY_PARENT, task.getId());
		
	}
	
	private void removeChild(Task task) {
		LinkedHashSet<Task> children = getChildren();
		children.remove(task);
	}

	public void addChild(Task task){
		LinkedHashSet<Task> children = getChildren();
		children.add(task);
		if (!this.equals(task.getParent())){
			task.setParent(this);
		}
	}
	
	public void setChildren(LinkedHashSet<Task> tasks) {
		List<Integer> list = new LinkedList<Integer>();
		for (Task task: tasks){
			if (!list.contains(task.getId()))
			list.add(task.getId());
		}
		put(TaskIntegerList.KEY_TASKS, list);
	}
	
	public LinkedHashSet<Task> getChildren() {
		LinkedHashSet<Task> children = getChildren();
		List<Integer> list = getIntegerList(TaskIntegerList.KEY_TASKS);
		for (int id: list){
			children.add(get(id));
		}
		return children;
	}

	public CharSequence getWhat() {
		return getString(TaskString.KEY_WHAT);
	}
	
	public boolean completed(){
		return getBool(TaskBool.KEY_COMPLETED);
	}
	
	public void completed(boolean completed){
		put(TaskBool.KEY_COMPLETED, completed);
	}
}



















