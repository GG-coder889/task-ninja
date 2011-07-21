package android.taskninja.taskgroup.dbtaskgroup;

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
import android.taskninja.task.dbtask.Db_Task;
import android.taskninja.taskcollection.dbtaskcollection.Db_TaskCollection;
import android.taskninja.taskcollection.dbtaskcollection.Db_TaskCollection_String;

public class Db_TaskGroup 
	extends Db_Model<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum>
	implements List<Db_Task> {

	private Db_TaskCollection mCollection;
	
	@Override
	public String toString(){
		return getString(Db_TaskGroup_String.title);
	}
		
	// ----------------------------------------------------------------------------------------------------
	//  Constructor/Builder
	// ----------------------------------------------------------------------------------------------------
	public static Db_TaskGroup getInstance(String title){
		return new Db_TaskGroup(title);
	}
	
	private Db_TaskGroup(String title){
		super();
		put(Db_TaskGroup_String.title, title);
		mCollection = Db_TaskCollection.get(getString(Db_TaskGroup_String.COLLECTION_ID));
		if (mCollection == null){
			mCollection = Db_TaskCollection.getInstance();
			put(Db_TaskGroup_String.COLLECTION_ID, mCollection.getId());
		}
	}
	// ----------------------------------------------------------------------------------------------------
		
		
	
	// ----------------------------------------------------------------------------------------------------
	//  Static Methods
	// ----------------------------------------------------------------------------------------------------
	public static Db_TaskGroup get(String id){
		return controller().get(id);
	}
	
	public static LinkedHashSet<Db_TaskGroup> getAll() {
		LinkedHashSet<Db_TaskGroup> all = controller().getAll();
		if (all.size() == 0) {
			all.add(Db_TaskGroup.getInstance("Default"));
		}
		
		return controller().getAll();
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  DbModel Interface Configuration
	// ----------------------------------------------------------------------------------------------------
	private static Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> mController;
	
	@Override
	protected Db_Controller <Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> getController() {
		return controller();
	}
	
	private static Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> controller(){
		if (mController == null){
			try {
				mController = new LocalController(Db_TaskGroup.class, mContext);
			}catch (Exception e2){
				e2.printStackTrace();
				return null;
			}
		}
		return mController;
	}
	
	public Db_TaskGroup(JSONObject json) {
		super(json);
	}

	private static class LocalController extends Db_Controller<Db_TaskGroup, Db_NullEnum, Db_NullEnum, Db_TaskGroup_String, Db_NullEnum> {
			
		protected LocalController(Class<Db_TaskGroup> dbModel, Context context) {
			super(dbModel, context);
		}

		@Override
		protected Db_TaskGroup getInstance(JSONObject json) {
			return new Db_TaskGroup(json);
		}

	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	
	// ----------------------------------------------------------------------------------------------------
	//  List Methods
	// ----------------------------------------------------------------------------------------------------
	@Override
	public boolean add(Db_Task task) {
		return mCollection.add(task);
	}

	@Override
	public void add(int location, Db_Task task) {
		mCollection.add(location, task);
	}

	@Override
	public boolean addAll(Collection<? extends Db_Task> arg0) {
		return addAll(arg0);
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends Db_Task> arg1) {
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
	public Db_Task get(int location) {
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
	public ListIterator<Db_Task> listIterator() {
		return mCollection.listIterator();
	}

	@Override
	public ListIterator<Db_Task> listIterator(int location) {
		return mCollection.listIterator(location);
	}

	@Override
	public Db_Task remove(int location) {
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
	public Db_Task set(int location, Db_Task object) {
		return mCollection.set(location, object);
	}

	@Override
	public List<Db_Task> subList(int start, int end) {
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
	public Iterator<Db_Task> iterator() {
		return mCollection.iterator();
	}
	
	@Override
	public int size() {
		return mCollection.size();
	}
	// ----------------------------------------------------------------------------------------------------
	
	
	
	

	

	

	

	

	
}
