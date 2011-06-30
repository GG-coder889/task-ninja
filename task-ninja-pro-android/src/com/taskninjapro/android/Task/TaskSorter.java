package com.taskninjapro.android.Task;

import java.util.LinkedList;
import java.util.List;

import com.taskninjapro.android.app.Constants;

public class TaskSorter implements Constants {
	
	public static List<Task> sortByCompleted(List<Task> tasks) {
		LinkedList<Task> sorted = new LinkedList<Task>();
		for (Task task: tasks){
			if (task.getAsBoolean(KEY_COMPLETED)){
				sorted.addLast(task);
			} else {
				sorted.addFirst(task);
			}
		}
		return sorted;
	}
	
	public static List<Task> sortByPriority(List<Task> tasks) {
		List<Task> sorted = new LinkedList<Task>();
		for (Task task: tasks){
			for (Task sortedTask: sorted){
				if (task.getAsInteger(KEY_PRIORITY) >= sortedTask.getAsInteger(KEY_PRIORITY)){
					sorted.add(sorted.indexOf(sortedTask), task);
					break;
				}
			}
			if (!sorted.contains(task)){
				sorted.add(task);
			}
		}
		return sorted;
	}
}
