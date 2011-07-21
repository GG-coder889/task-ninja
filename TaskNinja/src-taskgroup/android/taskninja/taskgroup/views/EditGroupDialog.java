package android.taskninja.taskgroup.views;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.app.TaskGroupActivity;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskgroup.TaskGroup;
import android.taskninja.taskgroup.TaskGroup_String;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class EditGroupDialog extends DialogFragment implements OnClickListener {
	
	private EditText mTitleEditText;
	private TaskGroup mGroup;
	
	public static EditGroupDialog getInstance(TaskGroup group) {
    	return new EditGroupDialog(group);
    }
	
	private EditGroupDialog(TaskGroup group){
		super();
		mGroup = group;
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	getDialog().setTitle("Edit: " + mGroup.toString());
    	getDialog().setContentView(R.layout.edit_group_dialog);
    	
    	mTitleEditText = (EditText) getDialog().findViewById(R.id.editText);
    	mTitleEditText.setText(mGroup.toString());
    	
    	getDialog().findViewById(R.id.cancelButton).setOnClickListener(this);
    	getDialog().findViewById(R.id.saveButton).setOnClickListener(this);
    	getDialog().findViewById(R.id.deleteButton).setOnClickListener(this);
    	
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
		case R.id.deleteButton:
			onDelete();
			break;
		}
	}

	private void onDelete() {
		mGroup.delete();
		startActivity(new Intent(getActivity(), TaskGroupActivity.class));
		this.dismiss();
	}

	private void onSave() {
		String title = mTitleEditText.getText().toString();
		mGroup.put(TaskGroup_String.title, title);
		Intent i = new Intent(getActivity(), TaskGroupActivity.class);
		i.putExtra(Db_Model.BuiltIn.ID.name(), mGroup.getId());
		startActivity(i);
		this.dismiss();
	}
}
