package android.taskninja.task.dbtask;

import java.util.LinkedHashSet;

import org.json.JSONObject;

import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskcollection.TaskCollection;
import android.taskninja.taskcollection.TaskCollection_String;


public class Task extends Db_Model<Task, Task_Integer, Task_Long, Task_String, Task_Bool> {
	
	
	
	public String toString() {
		return getString(Task_String.title);
	}
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  Constructor/Builder
	// ----------------------------------------------------------------------------------------------------
	public static Task getInstance(CharSequence title) {
		return new Task(title);
	}
	
	private Task(CharSequence title) {
		put(Task_String.title, title.toString());
	}
	// ----------------------------------------------------------------------------------------------------
				
				
			
	// ----------------------------------------------------------------------------------------------------
	//  Static Methods
	// ----------------------------------------------------------------------------------------------------
	public static Task get(String id){
		return controller().get(id);
	}

	public static LinkedHashSet<Task> getAll() {
		LinkedHashSet<Task> tasks = new LinkedHashSet<Task>();
		for (Task task: controller().getAll()) {
			tasks.add(task);
		}
		return tasks;
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Db_Controller<Task, Task_Integer, Task_Long, Task_String, Task_Bool> mController;
	
	@Override
	protected Db_Controller <Task, Task_Integer, Task_Long, Task_String, Task_Bool> getController() {
		return controller();
	}
	
	private static Db_Controller<Task, Task_Integer, Task_Long, Task_String, Task_Bool> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Task.class, App.getContext());
			} catch (Exception e1){
				try {
					mController = new LocalController(Task.class, mContext);
				}catch (Exception e2){
					e2.printStackTrace();
					return null;
				}
			}
		}
		return mController;
	}
	
	private Task(JSONObject JSONObject){
		super(JSONObject);
	}
	
	private static class LocalController extends Db_Controller<Task, Task_Integer, Task_Long, Task_String, Task_Bool> {
			
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



















