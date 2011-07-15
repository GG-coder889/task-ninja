package com.taskninjapro.android.views;

import java.util.LinkedHashSet;
import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.taskninjapro.android.R;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.task.Task;
import com.taskninjapro.android.task.TaskIntegerList;
import com.taskninjapro.android.task.TaskLong;

public class TaskView extends LinearLayout implements OnClickListener, Constants {
	
	private static final String TAG = "TaskView";
	public Task mTask;
	protected TaskListView mTaskList;
	
	public TaskHeader mTaskHeader;
	public TaskListView mSubtaskList;
	

	public TaskView(TaskListView taskList, Task task) {
		super(taskList.getActivity());
		
		mTask = task;
		mTaskList = taskList;
		
		setOrientation(VERTICAL);
		setPadding(10, 5, 10, 5);
		
		mTaskHeader = new TaskHeader(this);
		addView(mTaskHeader);
		
		setSelected(!mTask.completed());
	}
	
	@Override
	public void setSelected(boolean selected) {
		if (mTask.completed()) {
			super.setSelected(false);
		} else {
			super.setSelected(selected);
		}
	}

//	protected void update() {
//		if (mPosition!=0){
//			mTaskToggleButton.setTextOff(mPosition + ". " + mTask.getWhat());
//			mTaskToggleButton.setTextOn(mPosition + ". " + mTask.getWhat());
//		} else {
//			mTaskToggleButton.setTextOff(mTask.getWhat());
//			mTaskToggleButton.setTextOn(mTask.getWhat());
//		}
//		
//		if (mTask.getAsBoolean(KEY_COMPLETED)) {
//			showCompleted();
//			setSubtasksShown(false);
//		} else {
//			showIncomplete();
//		}
//		
//		setMenuShown(mTaskToggleButton.isChecked());
//	}
	
	public void setMenuShown(boolean shown) {
		mTaskHeader.setMenuShown(shown);
	}
	
	public void setPosition(int i) {
		mTaskHeader.setPosition(i);
	}
	
	public void onClick(View v) {

	}

	protected void onDelete() {
		mTaskList.removeView(this);
		mTask.delete();
	}
	
//	protected void showCompleted(){
//		mTaskToggleButton.setPaintFlags(mCompleteFlags);
//		setSelected(false);
//	}
//	
//	protected void showIncomplete(){
//		mTaskToggleButton.setPaintFlags(mIncompleteFlags);
//		setSelected(true);
//	}
	
	protected TaskView getSubtaskView(TaskListView taskList, Task t) {
		TaskView tv = new TaskView(taskList, t);
		return tv;
	}
	
	Drawable mTaskHeaderBaseBackground;
	protected void setSubtasksShown(boolean shown){
		if (shown){
			mTaskHeader.mSubsImageButton.setImageResource(R.drawable.ic_toggle_subs_on);
		} else {
			mTaskHeader.mSubsImageButton.setImageResource(R.drawable.ic_toggle_subs_off);
		}
		
		if (mTaskHeader.mSubsImageButton != null && mTaskHeader.mSubsShown != shown){
			mTaskHeader.mSubsShown = shown;
		}
		
		if (shown){
			if (mSubtaskList == null){
				
				mTaskHeaderBaseBackground = mTaskHeader.getBackground();
				mSubtaskList = new TaskListView(mTaskList.getActivity());
				
//				mSubtaskList.setBackgroundResource(R.drawable.subtask_list_background);
				mSubtaskList.setSelected(isSelected());
				mSubtaskList.setPadding(0, 0, 0, 0);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					     android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(40, 0, 0, 0);
				
				
				addView(mSubtaskList, layoutParams);
				mSubtaskList.setVisibility(VISIBLE);
				
				mTempSubtaskIds = mTask.getIntegerList(TaskIntegerList.KEY_TASKS);
				new AsyncSubtaskView().execute((Void[])null);
				
			} else if (mSubtaskList.getChildCount() != 0){
				mTaskHeader.setBackgroundResource(R.drawable.task_header_with_subs_dark);
				mTaskHeader.setPadding(2, 2, 2, 2);
				mSubtaskList.setBackgroundResource(R.drawable.subtask_list_background);
				mSubtaskList.setVisibility(VISIBLE);
			}
		} else {
			if (mSubtaskList != null){
				mSubtaskList.setVisibility(GONE);
				mTaskHeader.setBackgroundDrawable(mTaskHeaderBaseBackground);
				mTaskHeader.setPadding(2, 2, 2, 2);
			}
		}

		
	}
	
	protected List<Integer> mTempSubtaskIds;
	private class AsyncSubtaskView extends AsyncTask<Void, Void, TaskView> {
		
		@Override
		protected TaskView doInBackground(Void ... voids) {
			
			for (Integer id: mTempSubtaskIds){
				Task task = Task.get(id);
				if (task != null){
					mTempSubtaskIds.remove(id);
					return getSubtaskView(mSubtaskList, task);
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(TaskView view) {
			if (view != null){
				mSubtaskList.setBackgroundResource(R.drawable.subtask_list_background);
				mSubtaskList.setPadding(0, 0, 0, 0);
				mTaskHeader.setBackgroundResource(R.drawable.task_header_with_subs_dark);
				mTaskHeader.setPadding(2, 2, 2, 2);
				view.mTaskHeader.setBackgroundDrawable(null);
				view.mTaskHeader.mInfoLinearLayout.setVisibility(GONE);
				view.setPadding(0, 0, 0, 0);
				mSubtaskList.addView(view);
				view.setPosition(mSubtaskList.indexOfChild(view)+1);
				new AsyncSubtaskView().execute((Void[]) null);
			}
		}
	}
	
	public Activity getActivity() {
		return mTaskList.getActivity();
	}

	public void setActive() {
		mTaskList.setActive(this);
	}

	public void moveUp() {
		mTaskList.moveUp(this);
	}

	public void moveDown() {
		mTaskList.moveDown(this);
	}

	public void onCompleted(boolean completed) {
		setSelected(!completed);
	}

	public Task getTask() {
		return mTask;
	}

}
