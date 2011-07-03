package com.taskninjapro.android.Task;

import java.util.LinkedHashSet;

import android.content.ContentValues;
import android.content.Context;

import com.taskninjapro.android.app.App;
import com.taskninjapro.android.app.Constants;

public class Task implements Constants {

	private static final String TAG = "Task";

	ContentValues mValues;

	boolean mNeedsUpdate = false;
	boolean mNeedsDelete = false;

	private TaskDatabase mTaskDatabase;

	
	public Task(CharSequence what, Context context) {
		mTaskDatabase = TaskDatabase.getInstance(App.getContext());
		mValues = new ContentValues();
		
		mValues.put(_ID, mTaskDatabase.getNewID());
		mValues.put(KEY_WHAT, what.toString());
		
		onChange();
	}

	public Task(ContentValues values, Context context) {
		mTaskDatabase = TaskDatabase.getInstance(App.getContext());
		mValues = values;
	}

	// ###############################################################
	//		What
	// ###############################################################
	public CharSequence getWhat() {
		return mValues.getAsString(KEY_WHAT);
	}
	// ###############################################################

	
	// ###############################################################
	//		ID
	// ###############################################################
	public int getId() {
		Integer id = mValues.getAsInteger(_ID);
		if (id == null)
			return 0;
		else
			return id;
	}
	// ###############################################################

	
	
	// ###############################################################
	//		Values
	// ###############################################################
	public ContentValues getValues() {
		return mValues;
	}
	// ###############################################################

	
	
	// ###############################################################
	// 		Database Status
	// ###############################################################
	public boolean needsUpdate() {
		return mNeedsUpdate;
	}

	public void setNeedsUpdate(boolean b) {
		mNeedsUpdate = b;
		mTaskDatabase.add(this);
	}

	private void onChange() {
		mNeedsUpdate = true;
		mTaskDatabase.add(this);
	}

	public boolean needsDelete() {
		return mNeedsDelete;
	}
	
	public void delete() {
		mNeedsDelete = true;
		if (getParent() != null) {
			if (getParent().getSubtaskIds().contains(this)){
				getParent().removeSubtask(this);
			}	
			setParent(null);
		}
		
		LinkedHashSet<Integer> subtaskIds = getSubtaskIds();
		for (Integer id : subtaskIds) {
			Task subtask = mTaskDatabase.getTask(id);
			if (subtask != null){
				if (subtask.getParent() != null) {
					subtask.setParent(null);
				}
				
				if (!subtask.needsDelete()) {
					subtask.delete();
				}
			}
		}
		onChange();
	}
	// ###############################################################

	
	
	// ###############################################################
	//		Subtasks
	// ###############################################################
	public void addSubtask(Task subtask) {
		if (subtask != null) {
			if (subtask.getParent() != this) {
				subtask.put(KEY_PARENT, getId());
				onChange();
			}
			
			LinkedHashSet<Integer> subtaskIds = getSubtaskIds();
			if (!subtaskIds.contains(subtask.getId())){
				subtaskIds.add(subtask.getId());
				setSubtaskIds(subtaskIds);
				onChange();
			}
		}
	}

	public void setSubtaskIds(LinkedHashSet<Integer> subtaskIds) {
		StringBuilder sb = new StringBuilder();
		for (Integer id : subtaskIds) {
			Task subtask = mTaskDatabase.getTask(id);
			if (subtask != null){
				if (subtask.getParent() != this) {
					subtask.put(KEY_PARENT, getId());
				}
				sb.append(',').append(subtask.getId());
			}
		}
		sb.replace(0, 1, "");
		mValues.put(KEY_TASKS, sb.toString());
		onChange();
	}

	public void removeSubtask(Task subtask) {
		if (subtask != null) {
			LinkedHashSet<Integer> subtaskIds = getSubtaskIds();
			if (subtaskIds.contains(subtask.getId())){
				subtaskIds.remove(subtask.getId());
				setSubtaskIds(subtaskIds);
				if (subtask.getParent() == this){
					subtask.put(KEY_PARENT, 0);
				}
				onChange();
			}
		}
	}

	public LinkedHashSet<Integer> getSubtaskIds() {
		LinkedHashSet<Integer> subtaskIds = new LinkedHashSet<Integer>();
		String string = this.getAsString(KEY_TASKS);
		
		if (string.length() != 0) {
			
			String[] strings = string.split(",");
			for (String s : strings) {
				
				try {
					int id = Integer.valueOf(s);
					Task subtask = mTaskDatabase.getTask(id);
					
					if (subtask != null){
						subtaskIds.add(subtask.getId());
						if (subtask.getParent() != this){
							subtask.setParent(this);
						}
							
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		
		return subtaskIds;
	}
	// ###############################################################

	
	
	// ###############################################################
	//		Parent
	// ###############################################################
	public void setParent(Task task) {
		if (getParent() != task){
			if (task != null){
				put(KEY_PARENT, task.getId());
				task.addSubtask(this);
			} else {
				put(KEY_PARENT, 0);
			}
		}
		
//		if (getParent() != task  || !(task == null && getParent() == null)){
//			if (getParent() != null){
//				getParent().removeSubtask(this);
//			}
//			if (task == null){
//				mValues.put(KEY_PARENT, 0);
//			} else {
//				mValues.put(KEY_PARENT, task.getId());
//				task.addSubtask(this);
//			}
//			onChange();
//		}
	}

	public Task getParent() {
		Integer id = mValues.getAsInteger(KEY_PARENT);
		if (id == null || id < 1) {
			return null;
		} else {
			return mTaskDatabase.getTask(id);
		}

	}
	// ###############################################################



	public void put(String key, int value) {
		mValues.put(key, value);
		onChange();
	}
	
	public void put(String key, String value) {
		mValues.put(key, value);
		onChange();
	}
	
	public void put(String key, boolean value) {
		if (value)
			mValues.put(key, 1);
		else
			mValues.put(key, 0);
		onChange();
	}
	
	public void put(String key, long value) {
		mValues.put(key, value);
		onChange();
	}
	
	public boolean getAsBoolean(String key){
		Integer i = mValues.getAsInteger(key);
		if (i == null) {
			String s = mValues.getAsString(key);
			if (s == null || s.length() == 0){
				return false;
			} else {
				return true;
			}
		} else if (i < 1){
			return false;
		} else {
			return true;
		}
	}
	
	public long getAsLong(String key){
		Long l = mValues.getAsLong(key);
		if (l == null){
			return 0;
		} else {
			return l;
		}
	}
	
	public String getAsString(String key){
		String value = mValues.getAsString(key);
		if (value == null){
			return "";
		} else {
			return value;
		}
	}
	
	public int getAsInteger(String key){
		Integer value = mValues.getAsInteger(key);
		if (value == null){
			return 0;
		} else {
			return value;
		}
	}

	public Task getTask() {
		// TODO Auto-generated method stub
		return null;
	}


}
