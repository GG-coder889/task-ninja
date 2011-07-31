package android.taskninja.task.views;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.app.TaskGroupActivity;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.task.Task;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.TaskGroup_String;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

public class TaskDialogNew extends DialogFragment implements OnClickListener {
	
	private EditText mTittleEditText;
	private TaskGroup mGroup;
	
	public static TaskDialogNew getInstance(TaskGroup group) {
		return new TaskDialogNew(group);
	}
	
	private TaskDialogNew(TaskGroup group){
		super();
		mGroup = group;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		getDialog().setContentView(R.layout.task_dialog_new);
    	getDialog().setTitle("Create a New Task");
    	
    	getDialog().findViewById(R.id.saveButton).setOnClickListener(this);
    	getDialog().findViewById(R.id.cancelButton).setOnClickListener(this);
    	
        return getView();
    }

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()) {
		case R.id.cancelButton:
			this.dismiss();
			break;
		case R.id.saveButton:
			onSave();
			break;
		}
		
	}

	private void onSave() {
		String title = ((EditText) getDialog().findViewById(R.id.editText)).getText().toString();
		Task task = Task.getInstance(title);
		mGroup.add(task);
		
		Intent i = new Intent(getActivity(), TaskGroupActivity.class);
		i.putExtra(TaskGroupActivity.ExtraChoices.TaskGroupId.name(), mGroup.getId());
		i.putExtra(TaskGroupActivity.ExtraChoices.TaskId.name(), task.getId());
		startActivity(i);
		this.dismiss();
	}

}
