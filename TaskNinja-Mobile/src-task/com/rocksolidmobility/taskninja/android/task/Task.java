package com.rocksolidmobility.taskninja.android.task;

import java.util.LinkedHashSet;

import org.json.JSONObject;

import android.content.Context;

import com.rocksolidmobility.android.rsmodel.RSController;
import com.rocksolidmobility.android.rsmodel.RSModel;
import com.rocksolidmobility.taskninja.android.App;


public class Task extends RSModel<Task, TaskInteger, TaskLong, TaskString, TaskBool>  {
	
	private static final String TAG = "Task";
	
	public String toString() {
		return getString(TaskString.title);
	}
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  Constructor/Builder
	// ----------------------------------------------------------------------------------------------------
	public static Task getInstance(CharSequence title) {
		return new Task(title);
	}
	
	private Task(CharSequence title) {
		put(TaskString.title, title.toString());
	}
	// ----------------------------------------------------------------------------------------------------
				
				
			
	// ----------------------------------------------------------------------------------------------------
	//  Static Methods
	// ----------------------------------------------------------------------------------------------------
	public static Task get(String id) {
		return staticGetController().get(id);
	}

	public static LinkedHashSet<Task> getAll() {
		LinkedHashSet<Task> tasks = new LinkedHashSet<Task>();
		for (Task task: staticGetController().getAll()) {
			tasks.add(task);
		}
		return tasks;
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  RSModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static RSController<Task, TaskInteger, TaskLong, TaskString, TaskBool> mController;
	
	@Override
	protected RSController <Task, TaskInteger, TaskLong, TaskString, TaskBool> instanceGetController() {
		return staticGetController();
	}
	
	private static RSController<Task, TaskInteger, TaskLong, TaskString, TaskBool> staticGetController(){
		if (mController == null){
			try {
				mController = new LocalController(Task.class, mContext);
			}catch (Exception e2){
				e2.printStackTrace();
				return null;
			}
		}
		return mController;
	}
	
	private Task(JSONObject JSONObject){
		super(JSONObject);
	}
	
	private static class LocalController extends RSController<Task, TaskInteger, TaskLong, TaskString, TaskBool> {
			
		protected LocalController(Class<Task> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected Task getInstance(JSONObject json) {
			return new Task(json);
		}	
	}
	// ----------------------------------------------------------------------------------------------------


	
	




	
	
//	// ---------------------------------------------------------------------------------
//	// Parent-Child
//	// ---------------------------------------------------------------------------------
//	public Task getParent() {
//		Integer id = getInteger(TaskInteger.KEY_PARENT);
//		if (id != null){
//			return get(id); 
//		} else {
//			return null;
//		}
//		
//	}
//	
//	public void setParent(Task task){
//		
//		if (!hasParent(task)){
//			
//			Task parent = getParent();
//			if (parent != null){
//				parent.removeChild(this);
//			}
//			
//			put(TaskInteger.KEY_PARENT, task.getId());
//		}
//		
//		if (!task.hasChild(this)) {
//			task.addChild(this);
//		}
//	}
//	
//	public boolean hasParent(Task task) {
//		return task.equals(getParent());
//	}
//
//	public void removeChild(Task task) {
//		LinkedHashSet<Task> children = getChildren();
//		if (children.remove(task))
//			setChildren(children);
//	}
//
//	public void addChild(Task child){
//		LinkedHashSet<Task> children = getChildren();
//		if (children.add(child)){
//			setChildren(children);
//		}
//		if (!child.hasParent(this)){
//			child.setParent(this);
//		}
//		
//	}
//	
//	public void setChildren(LinkedHashSet<Task> tasks) {
//		List<Integer> list = new LinkedList<Integer>();
//		for (Task task: tasks){
//			if (!list.contains(task.getId()))
//			list.add(task.getId());
//		}
//		put(TaskIntegerList.KEY_TASKS, list);
//	}
//	
//	public boolean hasChild(Task child){
//		return getChildren().contains(child);
//	}
//	
//	public LinkedHashSet<Task> getChildren() {
//		LinkedHashSet<Task> children = new LinkedHashSet<Task>();
//		List<Integer> list = getIntegerList(TaskIntegerList.KEY_TASKS);
//		for (int id: list){
//			children.add(get(id));
//		}
//		return children;
//	}
//	// ---------------------------------------------------------------------------------
//	
//	
//	
//	public CharSequence getWhat() {
//		return getString(TaskString.KEY_WHAT);
//	}
//	
//	public boolean completed(){
//		return getBool(TaskBool.KEY_COMPLETED);
//	}
//	
//	public void completed(boolean completed){
//		put(TaskBool.KEY_COMPLETED, completed);
//	}
}



















