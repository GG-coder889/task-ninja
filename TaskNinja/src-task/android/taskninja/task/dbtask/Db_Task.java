package android.taskninja.task.dbtask;

import java.util.LinkedHashSet;

import android.content.ContentValues;
import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Controller;
import android.taskninja.dbmodel.Db_Model;


public class Db_Task extends Db_Model<Db_Task, Db_Task_Integer, Db_Task_Long, Db_Task_String, Db_Task_IntegerList, Db_Task_Bool> {
	
	public static Db_Task getInstance(CharSequence title) {
		return new Db_Task(title);
	}
	
	private Db_Task(CharSequence title) {
		put(Db_Task_String.title, title.toString());
	}
	
	public static Db_Task get(int id){
		return controller().get(id);
	}

	public static LinkedHashSet<Db_Task> getAll() {
		LinkedHashSet<Db_Task> tasks = new LinkedHashSet<Db_Task>();
		for (Db_Task task: controller().getAll()) {
			tasks.add(task);
		}
		return tasks;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Context mContext;
	private static Db_Controller<Db_Task, Db_Task_Integer, Db_Task_Long, Db_Task_String, Db_Task_IntegerList, Db_Task_Bool> mController;
	
	@Override
	protected Db_Controller <Db_Task, Db_Task_Integer, Db_Task_Long, Db_Task_String, Db_Task_IntegerList, Db_Task_Bool> getController() {
		return controller();
	}
	
	private static Db_Controller<Db_Task, Db_Task_Integer, Db_Task_Long, Db_Task_String, Db_Task_IntegerList, Db_Task_Bool> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Db_Task.class, App.getContext(), 1);
			} catch (Exception e1){
				try {
					mController = new LocalController(Db_Task.class, mContext, 1);
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
	
	private Db_Task(ContentValues values){
		super(values);
	}
	
	private static class LocalController extends Db_Controller<Db_Task, Db_Task_Integer, Db_Task_Long, Db_Task_String, Db_Task_IntegerList, Db_Task_Bool> {
		
		public static Context mContext;
			
		protected LocalController(Class<Db_Task> dbModel, Context context, int version) {
			super(dbModel, context, version);
		}

		@Override
		protected Db_Task_IntegerList[] getIntegerListValues() {
			return Db_Task_IntegerList.values();
		}

		@Override
		protected Db_Task_Integer[] getIntegerValues() {
			return Db_Task_Integer.values();
		}

		@Override
		protected Db_Task_Long[] getLongValues() {
			return Db_Task_Long.values();
		}

		@Override
		protected Db_Task getNewInstance(ContentValues arg0) {
			return new Db_Task(arg0);
		}

		@Override
		protected Db_Task_String[] getStringValues() {
			return Db_Task_String.values();
		}

		@Override
		protected Db_Task_Bool[] getBoolValues() {
			return Db_Task_Bool.values();
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



















