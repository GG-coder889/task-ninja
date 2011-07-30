package android.taskninja.task.views;

import android.app.Activity;
import android.taskninja.R;
import android.taskninja.task.Task;
import android.taskninja.task.TaskString;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

public class TaskSettingsTitleView extends LinearLayout {
	
	private Activity mActivity;
	private Task mTask;
	private EditText mTitleEdit;
	private EditText mNotesEdit;
	private LinearLayout mHiddenView;
	
	public static TaskSettingsTitleView getInstance(Activity activity, Task task){
		return new TaskSettingsTitleView(activity, task);
	}

	public TaskSettingsTitleView(Activity activity, Task task) {
		super(activity);
		
		mActivity = activity;
		mTask = task;
		
		setOrientation(VERTICAL);
		
		LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 5, 0, 5);
		setLayoutParams(layoutParams);
		
		setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
		
		setupHeader();
		setupHiddenView();
		addEditTexts();
	}

	private void setupHeader() {
		View header = TaskHeaderView.getIntance(mActivity, mTask);
		addView(header);
		header.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (mHiddenView.getVisibility()){
				case INVISIBLE:
				case VISIBLE:
					mHiddenView.setVisibility(GONE);
					break;
				case GONE:
					mHiddenView.setVisibility(VISIBLE);
					break;
				}
			}
		});
		
	}
	
	private void setupHiddenView() {
		mActivity.getLayoutInflater().inflate(R.layout.hidden_view, this);
		mHiddenView = (LinearLayout) findViewById(R.id.hiddenLinearLayout);
	}
	
	private void addEditTexts() {
		mTitleEdit = new EditText(getContext());
		mTitleEdit.setHint("Title");
		mTitleEdit.setText(mTask.getString(TaskString.title));
		mTitleEdit.addTextChangedListener(new TextWatcher() {
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				mTask.put(TaskString.title, s.toString());	
			}
		});
		mHiddenView.addView(mTitleEdit);
		
		mNotesEdit = new EditText(getContext());
		mNotesEdit.setHint("Notes");
		mNotesEdit.setText(mTask.getString(TaskString.notes));
		mNotesEdit.addTextChangedListener(new TextWatcher() {
			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				mTask.put(TaskString.notes, s.toString());	
			}
		});
		mHiddenView.addView(mNotesEdit);
	}








}
