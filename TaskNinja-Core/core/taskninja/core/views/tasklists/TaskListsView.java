package taskninja.core.views.tasklists;

import java.util.LinkedHashSet;

import taskninja.core.dbmodel.tasklist.TaskList;
import taskninja.core.views.buttons.AddButton;
import android.content.Context;
import android.widget.LinearLayout;

public class TaskListsView extends LinearLayout {
	
	public static TaskListsView getInstance(Context context){
		return new TaskListsView(context);
	}
	
	public TaskListsView(Context context) {
		super(context);
		setOrientation(VERTICAL);
		
		LinkedHashSet<TaskList> lists = TaskList.getAll();
		if (lists.size() == 0){
			TaskList list = TaskList.getInstance("Default");
			addView(TaskListButton.getInstance(list, getContext()));
		} else {
			for (TaskList list: lists){
				addView(TaskListButton.getInstance(list, getContext()));
			}
		}
		addView(AddButton.getInstance(context));
	}
	
	



}
