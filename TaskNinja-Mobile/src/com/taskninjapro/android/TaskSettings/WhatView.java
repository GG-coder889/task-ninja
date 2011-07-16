package com.taskninjapro.android.TaskSettings;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.taskninjapro.android.R;
import com.taskninjapro.android.app.Constants;
import com.taskninjapro.android.app.LifeCycleListener;
import com.taskninjapro.android.task.Task;
import com.taskninjapro.android.task.TaskString;

public class WhatView extends LinearLayout implements OnClickListener, LifeCycleListener, Constants{
	
	Task mTask;
	Activity mActivity;
	
	EditText mWhatEditText;
	EditText mNotesEditText;
	
	public WhatView(Activity activity, Task task) {
		super(activity);
		
		mTask = task;
		mActivity = activity;
		
		LayoutInflater inflator = mActivity.getLayoutInflater();
		inflator.inflate(R.layout.task_settings_what, this);
		
		mWhatEditText = (EditText) findViewById(R.id.whatEditText);
		mWhatEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		mWhatEditText.setText(mTask.getWhat());
		
		mNotesEditText = (EditText) findViewById(R.id.notesEditText);
		mNotesEditText.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		mNotesEditText.setText(mTask.getString(TaskString.KEY_NOTES));
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	public void onPause() {
		mTask.put(TaskString.KEY_WHAT, mWhatEditText.getText().toString());
		mTask.put(TaskString.KEY_NOTES, mNotesEditText.getText().toString());
		
	}
	

}
