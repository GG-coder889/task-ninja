package taskninja.core.views.tasklists;

import taskninja.core.color.ColorManager;
import taskninja.core.dbmodel.tasklist.TaskList;
import taskninja.core.dbmodel.tasklist.TaskListString;
import android.content.Context;
import android.taskninja.R;
import android.widget.Button;

public class TaskListButton extends Button {
	
	private TaskList mTaskList;
	
	public static TaskListButton getInstance(TaskList taskList, Context context){
		return new TaskListButton(taskList, context);
	}

	public TaskListButton(TaskList taskList, Context context) {
		super(context);
		mTaskList = taskList;
		setText(mTaskList.getString(TaskListString.title));
		setTextColor(ColorManager.get(taskninja.core.color.ColorManager.Color.ButtonText));
		setBackgroundResource(R.drawable.button_background);
	}

}
