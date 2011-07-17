package android.taskninja.core.views.tasklists;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import android.content.Context;
import android.taskninja.R;
import android.taskninja.core.app.App;
import android.taskninja.core.dbmodel.tasklist.TaskList;
import android.taskninja.core.listeners.OnActionListener;
import android.taskninja.core.settings.Background;
import android.taskninja.core.views.buttons.AddButton;
import android.taskninja.core.views.text.TitleText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TaskListsView extends ListView implements android.widget.AdapterView.OnItemClickListener {
	
	private LinkedHashSet<OnActionListener<TaskList>> mActionListeners = new LinkedHashSet<OnActionListener<TaskList>>();
	private List<TaskList> mLists = new ArrayList<TaskList>();
	ArrayAdapter<TaskList> mAdapter;
	
	public static TaskListsView getInstance(Context context){
		
//		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//			     android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
//		layoutParams.setMargins(40, 0, 0, 0);
		
		return new TaskListsView(context);
	}
	
	public TaskListsView(Context context) {
		super(context);
		setBackgroundDrawable(App.get(Background.Secondary, getContext()));
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
	     android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		setLayoutParams(layoutParams);
		
		addHeaderView(TitleText.getInstance(getContext(), "Task Lists"));
		
		LinkedHashSet<TaskList> lists = TaskList.getAll();
		if (lists.size() == 0){
			TaskList list = TaskList.getInstance("Default");
			lists.add(list);
		}
		
		for (TaskList taskList: lists){
			mLists.add(taskList);
		}
		
		mAdapter = new ArrayAdapter<TaskList>(getContext(), R.layout.text_list_item, mLists);
		setAdapter(mAdapter);
		setOnItemClickListener(this);
		
		AddButton addButton = AddButton.getInstance(getContext());
		addButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				
			}
		});
		addFooterView(addButton);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		if (position < mLists.size()){
			TaskList action = mLists.get(position);
			for (OnActionListener<TaskList> listener: mActionListeners){
				listener.onAction(action);
			}
		}
	}
	
	



}
