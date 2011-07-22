package android.taskninja.task.views;

import android.R;
import android.content.Context;
import android.taskninja.app.App;
import android.taskninja.dbmodel.Db_Listener;
import android.taskninja.task.Task;
import android.taskninja.task.Task_String;
import android.taskninja.tools.Background;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class TaskTitleTextView extends TextView {
	
	private Task mTask;
	
	public static TaskTitleTextView getIntance(Context context, Task task){
		return new TaskTitleTextView(context, task);
	}

	private TaskTitleTextView(Context context, Task task) {
		super(context);
		mTask = task;
		
		setText(mTask.toString());
		setTextSize(20);
//		setTextColor(R.color.white);
		setPadding(10, 10, 10, 10);
		setBackgroundResource(android.R.drawable.screen_background_dark_transparent);
		
		mTask.addListener(new Db_Listener() {
			@Override
			public void onChange(Enum key) {
				if (key.equals(Task_String.title)){
					setText(mTask.toString());
				}
			}
		});
	}

}
