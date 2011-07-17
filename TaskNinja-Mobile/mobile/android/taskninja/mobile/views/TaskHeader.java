package android.taskninja.mobile.views;

import android.content.Intent;
import android.graphics.Paint;
import android.taskninja.mobile.TaskSettings.TaskSettings;
import android.taskninja.mobile.app.Constants;
import android.taskninja.mobile.task.Task;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.taskninjapro.android.R;
import com.taskninjapro.android.task.TaskInteger;
import com.taskninjapro.android.task.TaskIntegerList;
import com.taskninjapro.android.task.TaskLong;
import com.taskninjapro.android.task.TaskString;

public class TaskHeader extends CustomLayout 
		implements OnClickListener, OnCheckedChangeListener, Constants {
	
	public LinearLayout mInfoLinearLayout;
	public TextView mParentTextView;
	protected TextView mDueDateTextView;
	protected TextView mPriorityTextView;
	public ToggleButton mTaskToggleButton;
	public ImageButton mCompleteButton;
	
	public LinearLayout mMenu;
	private ImageButton mDeleteButton;
	public ImageButton mUpButton;
	public ImageButton mDownButton;
	public ImageButton mMoreButton;
	
	public boolean mSubsShown = false;
	public ImageButton mSubsImageButton;

	public Task mTask;
	private TaskView mTaskView;
	
	int mCompletedFlags;
	int mIncompleteFlags;
	

	public TaskHeader(TaskView taskView) {
		super(taskView.getContext());
		
		mTaskView = taskView;
		mTask = mTaskView.getTask();
		
		setBackgroundResource(R.drawable.film_dark);
		setOrientation(VERTICAL);
		setPadding(2, 2, 2, 2);
		
		LayoutInflater inflator = taskView.getActivity().getLayoutInflater();
		inflator.inflate(R.layout.task_header_info, this);
		inflator.inflate(R.layout.task_header_buttons, this);
		inflator.inflate(R.layout.task_header_menu, this);
		
		mInfoLinearLayout = (LinearLayout) findViewById(R.id.infoLinearLayout);
		mMenu = (LinearLayout) findViewById(R.id.menuLinearLayout);
		
		
		mTaskToggleButton = (ToggleButton) findViewById(R.id.taskToggleButton);
		mTaskToggleButton.setOnClickListener(this);
		mTaskToggleButton.setOnCheckedChangeListener(this);
		mCompletedFlags = mTaskToggleButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG;
		mIncompleteFlags = mTaskToggleButton.getPaintFlags();
		mTaskToggleButton.setTextOn(mTask.getString(TaskString.KEY_WHAT));
		mTaskToggleButton.setTextOff(mTask.getString(TaskString.KEY_WHAT));
		mTaskToggleButton.setText(mTask.getString(TaskString.KEY_WHAT));
		if (mTask.completed()){
			mTaskToggleButton.setPaintFlags(mCompletedFlags);
		}
		
		mCompleteButton = (ImageButton) findViewById(R.id.completeButton);
		mCompleteButton.setOnClickListener(this);
		
		mDeleteButton = (ImageButton) findViewById(R.id.deleteButton);
		mDeleteButton.setOnClickListener(this);
		
		mUpButton = (ImageButton) findViewById(R.id.upButton);
		mUpButton.setOnClickListener(this);
		
		mDownButton = (ImageButton) findViewById(R.id.downButton);
		mDownButton.setOnClickListener(this);
		
		mMoreButton = (ImageButton) findViewById(R.id.moreButton);
		mMoreButton.setOnClickListener(this);
		
		mSubsImageButton = (ImageButton) findViewById(R.id.subsImageButton);
		mSubsImageButton.setOnClickListener(this);
		if (!(mTask.getIntegerList(TaskIntegerList.KEY_TASKS).size() > 0)){
			mSubsImageButton.setVisibility(GONE);
		}
		
		mParentTextView = (TextView) findViewById(R.id.parentTextView);
		if (mTask.getParent() != null){
			mParentTextView.setText(mTask.getParent().getWhat());
		}
		
		mDueDateTextView = (TextView) findViewById(R.id.dueDateTextView);
		Long dueDate = mTask.getLong(TaskLong.KEY_DUE_DATE);
		if (dueDate != null && dueDate > 0){
			mDueDateTextView.setText(DateUtils.formatDateTime(getContext(), dueDate, 
					DateUtils.FORMAT_SHOW_YEAR ));
			mInfoLinearLayout.setVisibility(VISIBLE);
		} 

	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch(buttonView.getId()){
		case R.id.taskToggleButton:
			setMenuShown(isChecked);
			break;
		}
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.taskToggleButton:
			mTaskView.setActive();
			break;
	
		case R.id.deleteButton:
			mTaskView.onDelete();
			break;
	
		case R.id.upButton:
			mTaskView.moveUp();
			break;
	
		case R.id.downButton:
			mTaskView.moveDown();
			break;
	
		case R.id.completeButton:
			boolean completed = mTask.completed();
			if (completed){
				mTask.put(TaskLong.KEY_COMPLETED, 0l);
			} else {
				mTask.put(TaskLong.KEY_COMPLETED, System.currentTimeMillis());
			}
			onCompleted(!completed);
			
			break;
			
		case R.id.moreButton:
			onMore();
			break;
			
		case R.id.subsImageButton:
			mSubsShown = (!mSubsShown);
			mTaskView.setSubtasksShown(mSubsShown);
			if (mSubsShown){
				mTask.put(TaskLong.KEY_COMPLETED, 0l);
				onCompleted(false);
			}
			break;
			
		default:
			mTaskView.onClick(v);
		}
		
	}
	
	public void onCompleted(boolean completed) {
		if (completed){
			setMenuShown(false);
			mTaskToggleButton.setPaintFlags(mCompletedFlags);
			setSelected(false);
		} else {
			mTaskToggleButton.setPaintFlags(mIncompleteFlags);
			setSelected(true);
		}
		mTaskView.onCompleted(completed);
	}

	public void setMenuShown(boolean shown) {
		if (mTaskToggleButton.isChecked() != shown){
			mTaskToggleButton.setChecked(shown);
		}
		
		if (mMenu != null){
			mMenu.setSelected(isSelected());
			if (shown){
				mMenu.setVisibility(VISIBLE);
			} else {
				mTaskView.setSubtasksShown(false);
				mMenu.setVisibility(GONE);
			}
		}
	}
	
	public void setPosition(int i) {
		mTaskToggleButton.setTextOff(i + ". " + mTask.getWhat());
		mTaskToggleButton.setTextOn(i + ". " + mTask.getWhat());
		mTaskToggleButton.setText(i + ". " + mTask.getWhat());
	}
	
	private void onMore() {
		Intent intent = new Intent(mTaskView.getActivity(), TaskSettings.class);
		intent.putExtra("_ID", mTask.getId());
		mTaskView.getActivity().startActivity(intent);
	}
}
