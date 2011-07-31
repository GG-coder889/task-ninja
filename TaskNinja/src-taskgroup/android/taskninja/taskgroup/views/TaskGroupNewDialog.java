package android.taskninja.taskgroup.views;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.taskninja.R;
import android.taskninja.app.TaskGroupActivity;
import android.taskninja.dbmodel.Db_Model;
import android.taskninja.taskgroup.TaskGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class TaskGroupNewDialog extends DialogFragment implements OnClickListener {

    public static TaskGroupNewDialog getInstance() {
    	return new TaskGroupNewDialog();
    }   
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
    	getDialog().setTitle("Make A New Group");
    	getDialog().setContentView(R.layout.taskgroup_dialog_new);
    	getDialog().findViewById(R.id.cancelButton).setOnClickListener(this);
    	getDialog().findViewById(R.id.saveButton).setOnClickListener(this);
    	
        return getView();
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.cancelButton:
			Toast.makeText(getActivity(), "Hello from cancel button", Toast.LENGTH_SHORT).show();
			this.dismiss();
			break;
		case R.id.saveButton:
			Toast.makeText(getActivity(), "Hello from save button", Toast.LENGTH_SHORT).show();
			onSave();
			break;
		}
		
	}

	private void onSave() {
		EditText editText = (EditText) getDialog().findViewById(R.id.editText);
		String title = editText.getText().toString();
		TaskGroup group = TaskGroup.getInstance(title);
		if (group != null){
			Intent i = new Intent(getActivity(), TaskGroupActivity.class);
			i.putExtra(Db_Model.BuiltIn.ID.name(), group.getId());
			startActivity(i);
			this.dismiss();
		}
		
	}

}
