package taskninja.core.dbmodel.task;

import java.util.LinkedHashSet;

import taskninja.core.app.App;
import taskninja.core.dbmodel.DbController;
import taskninja.core.dbmodel.DbModel;
import android.content.ContentValues;
import android.content.Context;


public class Task extends DbModel<Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> {
	
	public Task(CharSequence title) {
		put(TaskString.title, title.toString());
	}
	
	public static Task get(int id){
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
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static DbController<Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> mController;
	
	@Override
	protected DbController <Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> getController() {
		return controller();
	}
	
	private static DbController<Task, TaskInteger, TaskLong, TaskString, TaskIntegerList, TaskBool> controller(){
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



















