package android.taskninja.task.views;

import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.task.Task;
import android.taskninja.task.Task_String;
import android.taskninja.tools.Background;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TaskSettingsTitleView extends LinearLayout {
	
	Task mTask;
	EditText mTitleEdit;
	EditText mNotesEdit;
	LinearLayout mHiddenLayout;
	
	public static TaskSettingsTitleView getInstance(Context context, Task task){
		return new TaskSettingsTitleView(context, task);
	}

	public TaskSettingsTitleView(Context context, Task task) {
		super(context);
		mTask = task;
		
		setOrientation(VERTICAL);
		setWeightSum(1);
		
		setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
		
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		layoutParams.setMargins(0, 5, 0, 5);
		this.setLayoutParams(layoutParams);
		
		TextView titleView = TaskTitleTextView.getIntance(getContext(), mTask);
		addView(titleView);

		mHiddenLayout = new LinearLayout(getContext());
		mHiddenLayout.setOrientation(VERTICAL);
		mHiddenLayout.setVisibility(GONE);
		mHiddenLayout.setPadding(10, 10, 10, 10);
		addView(mHiddenLayout);
		
		titleView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (mHiddenLayout.getVisibility()){
				case INVISIBLE:
				case VISIBLE:
					mHiddenLayout.setVisibility(GONE);
					break;
				case GONE:
					mHiddenLayout.setVisibility(VISIBLE);
					break;
				}
			}
		});
		
		
		
		mTitleEdit = new EditText(getContext());
		mTitleEdit.setHint("Title");
		mTitleEdit.setText(mTask.getString(Task_String.title));
		mTitleEdit.addTextChangedListener(new TextWatcher() {
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				mTask.put(Task_String.title, s.toString());	
			}
		});
		mHiddenLayout.addView(mTitleEdit);
		
		mNotesEdit = new EditText(getContext());
		mNotesEdit.setHint("Notes");
		mNotesEdit.setText(mTask.getString(Task_String.notes));
		mNotesEdit.addTextChangedListener(new TextWatcher() {
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				mTask.put(Task_String.notes, s.toString());	
			}
		});
		mHiddenLayout.addView(mNotesEdit);
	}




}
