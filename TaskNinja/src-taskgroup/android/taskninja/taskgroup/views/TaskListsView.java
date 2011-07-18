package android.taskninja.taskgroup.views;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import android.content.Context;
import android.taskninja.R;
import android.taskninja.app.App;
import android.taskninja.taskgroup.dbtaskgroup.Db_TaskGroup;
import android.taskninja.tools.Background;
import android.taskninja.tools.OnActionListener;
import android.taskninja.views.AddButton;
import android.taskninja.views.TitleText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class TaskListsView extends ListView implements android.widget.AdapterView.OnItemClickListener {
	
	private LinkedHashSet<OnActionListener<Db_TaskGroup>> mActionListeners = new LinkedHashSet<OnActionListener<Db_TaskGroup>>();
	private List<Db_TaskGroup> mLists = new ArrayList<Db_TaskGroup>();
	ArrayAdapter<Db_TaskGroup> mAdapter;
	
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
		
		LinkedHashSet<Db_TaskGroup> lists = Db_TaskGroup.getAll();
		if (lists.size() == 0){
			Db_TaskGroup list = Db_TaskGroup.getInstance("Default");
			lists.add(list);
		}
		
		for (Db_TaskGroup taskList: lists){
			mLists.add(taskList);
		}
		
		mAdapter = new ArrayAdapter<Db_TaskGroup>(getContext(), R.layout.text_list_item, mLists);
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
			Db_TaskGroup action = mLists.get(position);
			for (OnActionListener<Db_TaskGroup> listener: mActionListeners){
				listener.onAction(action);
			}
		}
	}
	
	



}
